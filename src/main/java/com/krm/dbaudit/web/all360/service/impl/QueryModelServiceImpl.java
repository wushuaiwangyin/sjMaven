package com.krm.dbaudit.web.all360.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.ehcache.search.aggregator.Count;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.web.all360.mapManager.QueryModelDao;
import com.krm.dbaudit.web.all360.model.CommonCustInfo;
import com.krm.dbaudit.web.all360.model.CustInfoModel;
import com.krm.dbaudit.web.all360.model.CustRelCustModel;
import com.krm.dbaudit.web.all360.model.HistoryCustRed;
import com.krm.dbaudit.web.all360.model.PhyTableCngModel;
import com.krm.dbaudit.web.all360.model.QueryModel;
import com.krm.dbaudit.web.all360.model.TabColsDetailModel;
import com.krm.dbaudit.web.all360.model.TableModelColumns;
import com.krm.dbaudit.web.all360.service.QueryModelService;
import com.krm.dbaudit.web.all360.util.EchartSeries;
import com.krm.dbaudit.web.all360.util.FormatCollectObj;
import com.krm.dbaudit.web.all360.util.JsonTools;

@Service("queryModelService")
public class QueryModelServiceImpl implements QueryModelService {
	@Resource
	QueryModelDao queryModelDao;

	public QueryModelDao getQueryModelDao() {
		return queryModelDao;
	}

	public void setQueryModelDao(QueryModelDao queryModelDao) {
		this.queryModelDao = queryModelDao;
	}

	@Override
	public List<Map<String, Object>> getModels(Map<String, Object> params) {
		return queryModelDao.getModels(params);
	}

	@Override
	public List getColumns(Map<String, Object> params) {
		return null;
	}

	@Override
	public List<CustInfoModel> getCustInfos(Map<String, Object> params) {
		// 根据modelid获取查询model，model中包含物理表，

		return null;
	}

	@Override
	public String getCustomAllInfo(Map<String, Object> params) {
		return null;
	}

	@Override
	public List<Map<String, Object>> getCustList(Map<String, Object> params,
			List<Map<String, Object>> conditions) {
		return queryModelDao.getCustList(params, conditions);
	}

	@Override
	public String getLoanAccList(Map<String, Object> params,
			List<Map<String, Object>> conditions) {
		List<Map<String, Object>> dataList=queryModelDao.getLoanAccList(params, conditions);
		TabColsDetailModel tab=FormatCollectObj.getTabForJdbcTemplet(conditions);
		return JsonTools.makeJsonStr(tab, dataList);
	}

	@Override
	public Map<String, Object> getDspSumData(Map<String, Object> params) {
		return queryModelDao.getDspSumData(params);
	}

	@Override
	public List<EchartSeries> getDpsAccountList(Map<String, Object> params) {
		List<Map<String, Object>> list = queryModelDao
				.getDpsAccountList(params);
		List<EchartSeries> elist = new ArrayList<EchartSeries>();
		List<Map<String, Object>> dataMapList;
		Set<String> accSet = new HashSet<String>();// 用来存放集合中所有账号
		double dataArray[] = new double[12];// 12个月份的数据
		Map<String, Object> maps = new HashMap<String, Object>();
		EchartSeries eSeries = null;
		String key = "";
		Object value = null;
		Object temp = null;
		String acc = "";
		if (list.size() > 0) {

			// 获取数据中所有acctno，
			for (Map<String, Object> map : list) {
				// eSeries = new EchartSeries();
				for (Iterator it = map.keySet().iterator(); it.hasNext();) {
					key = it.next().toString().trim().toLowerCase();
					value = map.get(key);
					if ("acctno".equals(key)) {
						accSet.add(value.toString().trim());
					}

				}

			}
			// 组装eSeries
			for (Iterator iterator = accSet.iterator(); iterator.hasNext();) {
				acc = iterator.next().toString().trim();
				eSeries = new EchartSeries();
				// 一个账号=一个eSeries
				eSeries.setName(acc);
				dataMapList = new ArrayList<Map<String, Object>>();
				// 抽取每个账号下的所有数据
				for (Map<String, Object> map : list) {
					for (Iterator it = map.keySet().iterator(); it.hasNext();) {
						key = it.next().toString().trim().toLowerCase();
						value = map.get(key);
						// 判断是否是当前账号下的数据
						if (value.toString().trim().equals(acc)) {
							dataMapList.add(map);
						}

					}
				}
				// 组装账号12个月份的数据
				for (Map<String, Object> map2 : dataMapList) {
					for (Iterator it2 = map2.keySet().iterator(); it2.hasNext();) {
						key = it2.next().toString().trim().toLowerCase();
						value = map2.get(key);
						if ("mmonth".equals(key)) {
							makeOneAccountMonthData(map2, key, value.toString()
									.trim(), dataArray);
						}
					}
				}
				eSeries.setType("line");
				eSeries.setData(dataArray);
				elist.add(eSeries);
				dataArray = new double[12];
			}
		}
		//dataArray = null;
		return elist;
	}

	private void makeOneAccountMonthData(Map<String, Object> map, String key,
			String month, double[] arrays) {
		String kk = null;
		double vv = 0;
		BigDecimal big;
		for (Iterator it = map.keySet().iterator(); it.hasNext();) {
			kk = it.next().toString().trim().toLowerCase();
		//	vv =Double.valueOf(map.get(kk).toString().trim()) ;
			big=new BigDecimal(map.get(kk).toString().trim()).setScale(4,BigDecimal.ROUND_HALF_UP);
			vv=big.doubleValue();
			if ("balance".equals(kk)) {
				switch (Integer.valueOf(month)) {
				case 1:
					arrays[0] = vv;
					break;
				case 2:
					arrays[1] = vv;
					break;
				case 3:
					arrays[2] = vv;
					break;
				case 4:
					arrays[3] = vv;
					break;
				case 5:
					arrays[4] = vv;
					break;
				case 6:
					arrays[5] = vv;
					break;
				case 7:
					arrays[6] = vv;
					break;
				case 8:
					arrays[7] = vv;
					break;
				case 9:
					arrays[8] = vv;
					break;
				case 10:
					arrays[9] = vv;
					break;
				case 11:
					arrays[10] = vv;
					break;
				case 12:
					arrays[11] = vv;
					break;
				default:
					break;
				}
			}
		}

	}

	@Override
	public List<Map<String, Object>> getHistoryCustReds(Map<String, Object> params) {
		
		return queryModelDao.getHistoryCustReds(params);
	}

	@Override
	public String saveHisCustReds(HistoryCustRed hs) {
		queryModelDao.saveHisCustReds(hs);
		return "";
	}

	@Override
	public String getCustRels(Map<String, Object> params,List<Map<String, Object>> conditions) {
		//PageHelper.startPage(params);
		List<Map<String, Object>> list=queryModelDao.getCustRels(params,conditions);
		TabColsDetailModel tab=FormatCollectObj.getTabForJdbcTemplet(conditions);
		return JsonTools.makeJsonStr(tab, list);
	}

	@Override
	public String getCustSureList(Map<String, Object> params,List<Map<String, Object>> conditions) {
		TabColsDetailModel tab=FormatCollectObj.getTabForJdbcTemplet(conditions);
		List<Map<String, Object>>list=queryModelDao.getCustSureList(params,conditions);
		return JsonTools.makeJsonStr(tab, list);
	}

	@Override
	public String custList2(Map<String, Object> params,
			List<Map<String, Object>> conditions) {
		TabColsDetailModel tab=FormatCollectObj.getTabForJdbcTemplet(conditions);
		List<Map<String, Object>>list=	queryModelDao.getCommonCustInfos(params, tab);
		//JSONObject jObject = new JSONObject();
	//	JSONArray jArray = (JSONArray) JSONArray.toJSON(list);
		//jObject.put("cols", JSONObject.toJSON(headMap));
	//	jObject.put("list",jArray);
	//	jObject.put("msg", "sucess");
	//	jObject.put("total", list.size());
		return JsonTools.makeJsonStr(tab, list);
	}

	@Override
	public CommonCustInfo getCommonCustInfos(Map<String, Object> params,
			List<Map<String, Object>> conditions) {
		TabColsDetailModel tab=FormatCollectObj.getTabForJdbcTemplet(conditions);
		List<Map<String, Object>> list=queryModelDao.getCustList(params, tab);
		CommonCustInfo cus =null;
		if(list.size()>0){
			for(Map<String, Object> map:list){
				for(Map<String, Object> map2:list){
					 cus =new CommonCustInfo();
					 cus.setCustName((null==map2.get("customname".toUpperCase()))?"":map2.get("customname".toUpperCase()).toString().trim());
					cus.setCustId((null==map2.get("CUSTOMID".toUpperCase()))?"":map2.get("CUSTOMID".toUpperCase()).toString().trim());
					cus.setIdNo((null==map2.get("IDNO".toUpperCase()))?"":map2.get("IDNO".toUpperCase()).toString().trim());
					cus.setCustType((null==map2.get("CUSTOMTYPE".toUpperCase()))?"":map2.get("CUSTOMTYPE".toUpperCase()).toString().trim());
					cus.setIdtType((null==map2.get("IDTYPE".toUpperCase()))?"":map2.get("IDTYPE".toUpperCase()).toString().trim());
				}
			}
		}
		return cus;
	}

	@Override
	public List<TableModelColumns> getTabModelCosPageInfo(
			Map<String, Object> params) {
	//	PageHelper.startPage(params);
		List<Map<String, Object>> list=queryModelDao.getModels(params);
		List<TableModelColumns> tList=new ArrayList<TableModelColumns>();
		String key="";
		TableModelColumns tab=null;
		if(list.size()>0){
			for(Map<String, Object> map:list){
				tab=new	TableModelColumns ();
				for(Iterator it=map.keySet().iterator();it.hasNext();){
					key=it.next().toString().toUpperCase().trim();
					if(key.equals("tabId".toUpperCase())){
						tab.setTab_id(Long.valueOf(map.get(key).toString().trim()));
					}
					if(key.equals("tableName".toUpperCase())){
						tab.setTabName(map.get(key).toString().trim());
					}
					if(key.equals("isShow".toUpperCase())){
						tab.setIsShow(map.get(key).toString().trim());
					}
					if(key.equals("aliseName".toUpperCase())){
						tab.setAlise(map.get(key).toString().trim());
					}
					if(key.equals("columnName".toUpperCase())){
						tab.setColName(map.get(key).toString().trim());
					}
					if(key.equals("columnId".toUpperCase())){
						tab.setId(Long.valueOf(map.get(key).toString().trim()));
						
					}
					if(key.equals("dicid".toUpperCase())){
						tab.setDicid(Long.valueOf(map.get(key).toString().trim()));
					}
					if(key.equals("modelId".toUpperCase())){
						tab.setModelId(Long.valueOf(map.get(key).toString().trim()));
					}
				}
				tList.add(tab);
			}
		}
		return tList;
	}

	@Override
	public List<Map<String, Object>> getQueryModelsList(Map<String, Object> params) {
		//PageHelper.startPage(params);
		List<Map<String, Object>> list=queryModelDao.getQueryModelList(params);
		/*List<QueryModel> qList=new ArrayList<QueryModel>();
		String key="";
		QueryModel qm=null;
		if(list.size()>0){
			for(Map<String, Object> map:list){
				qm=new	QueryModel();
				for(Iterator it=map.keySet().iterator();it.hasNext();){
					key=it.next().toString().toUpperCase().trim();
					
					if(key.equals("modelId".toUpperCase())){
						qm.setModelId(Long.valueOf(map.get(key).toString().trim()));
					}
					if(key.equals("modelName".toUpperCase())){
						qm.setModelName(map.get(key).toString().trim());
					}
				}
				qList.add(qm);
			}
		}*/
		return list;
	}

	@Override
	public String getLoanData(Map<String, Object> params) {
		JSONObject jsonObject=new JSONObject();
		//每个账户的余额情况
	List<Map<String, Object>> list=queryModelDao.getSumDataEveryAccunt(params);
	List<Map<String, Object>> list2=new ArrayList<Map<String,Object>>();
	//获取所有账户总额
		Map<String, Object> map=getDspSumData( params);
		Map<String, Object> dMap=null;
		Map<String, Object> otherMap=null;
//	if(list.size()>0){
	//for(Map<String, Object> maps:list){
	//	dMap=new HashMap<String, Object>();
	//	otherMap = new HashMap<String, Object>();
	//	maps.put("totalSum", map.get("BALANCE"));
	//	dMap.put("totalSum", map.get("BALANCE"));
	//	dMap.put("name", maps.get("ACCTNO").toString().trim());//当前客户所有账户总余额
	//	dMap.put("value", maps.get("BALANCE"));//每个账户的余额
	//	dMap.put("itemStyle", "labelTop");
	//	list2.add(dMap);
	//	otherMap.put("name", "other");
	//	otherMap.put("value", Long.valueOf(map.get("BALANCE").toString())-Long.valueOf(maps.get("BALANCE").toString()));
	//	otherMap.put("itemStyle", "labelBottom");
	//	list2.add(otherMap);
//	}
//	}
	
	//var data1=[{name:'other', value:46, itemStyle : labelBottom},{name:'GoogleMaps', value:54,itemStyle : labelTop}];
	JSONArray jsonArray=(JSONArray) JSONArray.toJSON(list);
	jsonObject.put("datas", jsonArray);
	jsonObject.put("totalSum", map.get("BALANCE"));
		return jsonObject.toJSONString();
	}

	@Override
	public void savePhyTable(PhyTableCngModel tables) {
		queryModelDao.savePhyCng(tables);
	}

	@Override
	public List getColsByTableName(Map<String, Object> params) {
		
		return queryModelDao.getColsByTableName(params);
	}

	@Override
	public int deleteModel(long pkid) {
		int count=0;
		try {
			queryModelDao.deleteModel(pkid);
			count=1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public PhyTableCngModel getPhyTableCng(PhyTableCngModel tabCng) {
		List<Map<String, Object>> list=queryModelDao.getTableCng(tabCng);
		if(list.size()>0){
			String key="";
			Map<String, Object> map=list.get(0);
			for (Iterator iterator=map.keySet().iterator();iterator.hasNext();) {
				key=iterator.next().toString().toLowerCase();
				if(key.equals("is_public")){
					tabCng.setIsPublic(map.get(key).toString());
				}
				if(key.equals("phy_table")){
					tabCng.setPhyTable(map.get(key).toString());
				}
				if(key.equals("pkid")){
					tabCng.setPkid(Long.valueOf(map.get(key).toString()));
				}
			}
		  	
		}
		return tabCng;
	}

	@Override
	public int deleteCols(String colsId, String tabid, String modelid) {
		int count=0;
		try {
			queryModelDao.deleteCols(tabid, modelid, colsId);
			 count=1;
			
		} catch (Exception e) {
			count=0;
			e.printStackTrace();
		}
		
		return count;
	}

	@Override
	public TableModelColumns getColInfo(TableModelColumns tmc) {
		return queryModelDao.getColInfo(tmc);
	}

	@Override
	public String saveOneColumnInfo(TableModelColumns tmc) {
		String succ="对不起，操作异常！";
		int count=queryModelDao.saveOneColumnInfo(tmc);
		if(count>0){
			succ="恭喜您，操作成功了！";
		}
		return succ;
	}
}
