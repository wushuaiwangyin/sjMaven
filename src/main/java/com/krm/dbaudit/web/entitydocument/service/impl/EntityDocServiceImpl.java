package com.krm.dbaudit.web.entitydocument.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.krm.dbaudit.web.entitydocument.mapper.EntityDocDao;
import com.krm.dbaudit.web.entitydocument.model.JyzbDatas;
import com.krm.dbaudit.web.entitydocument.model.LinkManInfo;
import com.krm.dbaudit.web.entitydocument.model.OtherThings;
import com.krm.dbaudit.web.entitydocument.service.EntityDocService;
import com.krm.dbaudit.web.entitydocument.utill.DateUtil;

@Service("entityDocService")
public class EntityDocServiceImpl implements EntityDocService {
	@Resource
	private EntityDocDao entityDocManDao;

	public EntityDocDao getEntityDocDao() {
		return entityDocManDao;
	}

	public void setEntityDocDao(EntityDocDao entityDocManDao) {
		this.entityDocManDao = entityDocManDao;
	}

	@Override
	public Set getAllOtherIds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set getAllLinkIds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OtherThings> getOtherList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LinkManInfo> getLinkList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String saveOrUpdateOthers(Set ids, List<OtherThings> list) {
		String successString = "操作成功";

		List<OtherThings> updateList = new ArrayList<OtherThings>();
		List<OtherThings> saveList = new ArrayList<OtherThings>();
		// 在内存中比对set集合与本次请求的集合，存在相同id的则更新当前id对应的数据库记录，不存在则增加心得记录
		if (ids == null) {
			saveList.addAll(list);
		} else {
			for (OtherThings ot : list) {
				for (Iterator iterator = ids.iterator(); iterator.hasNext();) {
					if (Long.valueOf(iterator.next().toString()) == ot.getId()) {
						// updateEntity
						updateList.add(ot);
					} else {
						// saveEntity
						saveList.add(ot);
					}
				}
			}
		}

		// 与数据库交互实现层方法
		return successString;
	}

	@Override
	public String saveOrUpdateLinks(Set ids, List<LinkManInfo> list) {
		String successString = "操作成功";

		List<LinkManInfo> updateList = new ArrayList<LinkManInfo>();
		List<LinkManInfo> saveList = new ArrayList<LinkManInfo>();
		// 在内存中比对set集合与本次请求的集合，存在相同id的则更新当前id对应的数据库记录，不存在则增加心得记录
		if (ids == null) {
			saveList.addAll(list);
		} else {
			for (LinkManInfo ot : list) {
				for (Iterator iterator = ids.iterator(); iterator.hasNext();) {
					if (Long.valueOf(iterator.next().toString()) == ot.getId()) {
						// updateEntity
						updateList.add(ot);
					} else {
						// saveEntity
						saveList.add(ot);
					}
				}
			}
		}

		// 与数据库交互实现层方法
		return successString;
	}

	@Override
	public String getZbjyList(Map<String, Object> params) throws ParseException {
		List<Map<String, Object>> list = entityDocManDao.getZbjyList(params);
		// 构造一个12个月份的数组，
		// 按照项目名称行转列，
		Set<String> set = new HashSet<String>();
		String date1 = params.get("startDate").toString().replaceAll("-", "");
		String date2 = params.get("endDate").toString().replaceAll("-", "");
		String pattern = "yyyyMMdd";
		int countMonth = DateUtil.countMonth(date1, date2, pattern);
		String year1 = String.valueOf(DateUtil.getYear(date1, pattern));
		String year2 = String.valueOf(DateUtil.getYear(date2, pattern));
		int acrossYear = DateUtil.countAcrossYear(date1, date2, pattern);// 跨年的下标即新一年的一月份
		String[] cols = new String[countMonth];// 最后一个用于存放name
		String titles[] = new String[countMonth];// 最后一个用于存放name
		// cols[0]="name";
		// titles[0]="项目";
		int startMonth = DateUtil.getMonth(date1, pattern);
		int resetMonth = 1;// 重置月份
		for (int i = 0; i < countMonth; i++) {
			if (i >= acrossYear - 1) {// 到达跨年的下标处
				cols[i] = year2 + DateUtil.concatMonthStr(resetMonth);
				titles[i] = year2 + "/" + DateUtil.concatMonthStr(resetMonth);
				resetMonth++;
			} else if (i == 0) {
				cols[0] = date1.substring(0, 6);// 只截取到月份
				titles[0] = year1 + "/" + date1.substring(4, 6);
			} else {
				cols[i] = year1 + DateUtil.concatMonthStr(startMonth + i);
				titles[i] = year1 + "/"
						+ DateUtil.concatMonthStr(startMonth + i);

			}
		}
		// String[] cols = { "201503", "201504", "201505", "201506", "201507",
		// "201508", "201509", "201510", "201511", "201512", "201601",
		// "201602" };

		// String
		// titles[]={"3月","4月","5月","6月","7月","8月","9月","10月","11月","12月","1月","2月"};
		String key = "";
		// 对name的值去重
		for (Map<String, Object> map : list) {
			set.add(map.get("name").toString());
		}

		// key=项目名称 ，value=list（201503:v1，201504:v2,201505:v3....）
		Map<String, List> datas = new HashMap<String, List>();// 存放行列转置后的中间数据
		changeRowToColumn(set, datas, list);
		// 格式化13个月份的数据
		List<Map<String, String>> resultList = formatMonthDatas(datas, cols);

		JyzbDatas jzd = new JyzbDatas();
		jzd.setTitles(titles);
		jzd.setCols(cols);
		jzd.setDataList(resultList);
		JSONObject jsonObject = new JSONObject();
		String json = jsonObject.toJSON(jzd).toString();
		System.out.println(json);

		return json;
	}

	/**
	 * 行转列
	 * 
	 * @param set
	 *            去重后的项目名称
	 * @param datas
	 *            key=项目名称 ，value=list（201503:v1，201504:v2,201505:v3....）
	 *            以map的形式存放转还后每行的数据
	 * @param list
	 *            原始数据
	 */
	private void changeRowToColumn(Set set, Map<String, List> datas,
			List<Map<String, Object>> list) {
		String titleName = "";
		List<Map<String, String>> vlist = null; // 存放每个项目名称下12个月份的数据
		Map<String, String> cellMap = null;
		String key1 = null;
		String v1 = null;
		for (Iterator iterator = set.iterator(); iterator.hasNext();) {// 不重复的项目名称（值）
			titleName = iterator.next().toString();
			vlist = new ArrayList<Map<String, String>>();
			// cellMap = new HashMap<String, String>();
			for (Map<String, Object> map : list) {
				if (titleName.equals(map.get("name").toString())) {// 把相同name的不同日期下的数据放入vlist集合
					cellMap = new HashMap<String, String>();
					key1 = map.get("data_date").toString().substring(0, 6);
					v1 = map.get("data1").toString();
					cellMap.put(key1, v1);
					vlist.add(cellMap);
				}
			}

			datas.put(titleName, vlist);

		}
	}

	/**格式化13个月份的数据，默认起始日期到结束日期间隔12个月份的数据
	 * @param datas 行转列后的数据
	 * @param cols 列属性
	 * @return
	 */
	private List<Map<String, String>> formatMonthDatas(Map<String, List> datas,
			String[] cols) {
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();// 最终返回的补全13个月份，行转列后的数据集合
		List<Map<String, String>> dataList = null;
		Map<String, String> dataMap = null;
		String key = null;
		// 再次按日期月份格式化
		for (Iterator it = datas.keySet().iterator(); it.hasNext();) {
			key = it.next().toString();
			dataList = datas.get(key);
			dataMap = new HashMap<String, String>();

			// 遍历每个项目名称
			for (Map<String, String> map : dataList) {
				dataMap.put("name", key);// 将name放入dataMap中
				// 遍历每个项目名称的数据集
				for (int i = 0; i < cols.length; i++) {
					if (dataMap.containsKey(cols[i])
							&& null == map.get(cols[i])) // 如果dataMap在上一次循环中已经初始化了12个月份的key-value，防止数据被覆盖
						continue;
					if (map.containsKey(cols[i])) {
						dataMap.put(cols[i], map.get(cols[i])); // 有数据
					} else {
						dataMap.put(cols[i], "-"); // 无数据
					}
				}

			}
			resultList.add(dataMap);

		}
		return resultList;
	}

	public static void main(String[] args) throws ParseException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("name", "存款总额");
		map1.put("data1", "5000");
		map1.put("data_date", "201505");
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("name", "存款总额");
		map2.put("data1", "3000");
		map2.put("data_date", "201506");

		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("name", "对公存款");
		map3.put("data1", "9000");
		map3.put("data_date", "201504");
		Map<String, Object> map5 = new HashMap<String, Object>();
		map5.put("name", "对公存款");
		map5.put("data1", "3000");
		map5.put("data_date", "201601");
		Map<String, Object> map4 = new HashMap<String, Object>();
		map4.put("name", "对私贷款");
		map4.put("data1", "4600");
		map4.put("data_date", "201504");
		Map<String, Object> map6 = new HashMap<String, Object>();
		map6.put("name", "对私贷款");
		map6.put("data1", "4900");
		map6.put("data_date", "201505");
		Map<String, Object> map7 = new HashMap<String, Object>();
		map7.put("name", "对私贷款");
		map7.put("data1", "5900");
		map7.put("data_date", "201506");
		Map<String, Object> map8 = new HashMap<String, Object>();
		map8.put("name", "对私贷款");
		map8.put("data1", "7900");
		map8.put("data_date", "201507");

		list.add(map1);
		list.add(map2);
		list.add(map3);
		list.add(map4);
		list.add(map5);
		list.add(map6);
		list.add(map7);
		list.add(map8);
		// 按照项目名称行转列，
		Set<String> set = new HashSet<String>();
		String date1 = "20150101";
		String date2 = "20160101";
		String pattern = "yyyyMMdd";
		int countMonth = DateUtil.countMonth(date1, date2, pattern);
		String year1 = String.valueOf(DateUtil.getYear(date1, pattern));
		String year2 = String.valueOf(DateUtil.getYear(date2, pattern));
		int acrossYear = DateUtil.countAcrossYear(date1, date2, pattern);// 跨年的下标即新一年的一月份
		String[] cols = new String[countMonth];
		String titles[] = new String[countMonth];
		// cols[0]="name";
		// titles[0]="项目";
		int startMonth = DateUtil.getMonth(date1, pattern);
		int resetMonth = 1;// 重置月份
		for (int i = 1; i < countMonth; i++) {
			if (i >= acrossYear - 1) {// 到达跨年的下标处
				cols[i] = year2 + DateUtil.concatMonthStr(resetMonth);
				titles[i] = year2 + "/" + DateUtil.concatMonthStr(resetMonth);
				resetMonth++;
			} else if (i == 0) {
				cols[0] = date1.substring(0, 6);// 只截取到月份
				titles[0] = year1 + "/" + date1.substring(4, 6);
			} else {
				cols[i] = year1 + DateUtil.concatMonthStr(startMonth + i);
				titles[i] = year1 + "/"
						+ DateUtil.concatMonthStr(startMonth + i);

			}
		}
		String key = "";
		// 对name的值去重
		for (Map<String, Object> map : list) {
			for (Iterator iterator = map.keySet().iterator(); iterator
					.hasNext();) {
				key = iterator.next().toString();
				if ("name".equals(key)) {
					set.add(map.get(key).toString());
				}
			}
		}

		// key=项目名称 ，value=list（201503:v1，201504:v2,201505:v3....）
		String titleName = "";
		List<Map<String, String>> vlist = null; // 存放每个项目12个月份的数据
		Map<String, List> datas = new HashMap<String, List>();// 存放行列转置后的中间数据
		Map<String, String> cellMap = null;
		String key1 = null;
		String v1 = null;
		for (Iterator iterator = set.iterator(); iterator.hasNext();) {// 不重复的项目名称（值）
			titleName = iterator.next().toString();
			vlist = new ArrayList<Map<String, String>>();
			// cellMap = new HashMap<String, String>();
			for (Map<String, Object> map : list) {
				if (titleName.equals(map.get("name").toString())) {// 把相同name的不同日期下的数据放入vlist集合
					cellMap = new HashMap<String, String>();
					key1 = map.get("data_date").toString().substring(0, 6);
					v1 = map.get("data1").toString();
					cellMap.put(key1, v1);
					vlist.add(cellMap);
				}
			}

			datas.put(titleName, vlist);

		}

		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		List<Map<String, String>> dataList = null;
		Map<String, String> dataMap = null;
		// 再次按月份格式化
		for (Iterator it = datas.keySet().iterator(); it.hasNext();) {
			key = it.next().toString();
			dataList = datas.get(key);
			dataMap = new HashMap<String, String>();

			// 遍历每个项目名称
			for (Map<String, String> map : dataList) {
				dataMap.put("name", key);// 将name放入dataMap中
				// 遍历每个项目名称的数据集
				for (int i = 0; i < cols.length; i++) {
					if (dataMap.containsKey(cols[i])
							&& null == map.get(cols[i])) // 如果dataMap在上一次循环中已经初始化了12个月份的key-value，防止数据被覆盖
						continue;
					if (map.containsKey(cols[i])) {
						dataMap.put(cols[i], map.get(cols[i])); // 有数据
					} else {
						dataMap.put(cols[i], "-"); // 无数据
					}
				}

			}
			resultList.add(dataMap);

		}

		JyzbDatas jzd = new JyzbDatas();
		jzd.setTitles(titles);
		jzd.setCols(cols);
		jzd.setDataList(resultList);
		JSONObject jsonObject = new JSONObject();
		String json = jsonObject.toJSON(jzd).toString();
		System.out.println(json);
	}
}
