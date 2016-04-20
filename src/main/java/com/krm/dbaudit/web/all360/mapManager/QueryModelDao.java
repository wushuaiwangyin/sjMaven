package com.krm.dbaudit.web.all360.mapManager;

import java.util.List;
import java.util.Map;

import com.krm.dbaudit.web.all360.model.CommonCustInfo;
import com.krm.dbaudit.web.all360.model.CustInfoModel;
import com.krm.dbaudit.web.all360.model.CustRelCustModel;
import com.krm.dbaudit.web.all360.model.HistoryCustRed;
import com.krm.dbaudit.web.all360.model.PhyTableCngModel;
import com.krm.dbaudit.web.all360.model.TabColsDetailModel;
import com.krm.dbaudit.web.all360.model.TableModelColumns;
import com.krm.dbaudit.web.all360.util.EchartSeries;

public interface QueryModelDao {
	/**
	 * 基本查询使用的公共模型，包含查询的物理表及其列字段信息，用以拼装具体业务sql
	 * 
	 * @param params
	 *            model_id 模型编码
	 * @return
	 */
	public List<Map<String, Object>> getModels(Map<String, Object> params);
	
	public List<Map<String, Object> >getQueryModelList(Map<String, Object> params);
	
	public List getColumns(Map<String, Object> params);

	public List<CustInfoModel> getCustInfos(Map<String, Object> params);
	
	/**查询公共客户信息
	 * @param params
	 * @param conditions
	 * @return
	 */
	public List<Map<String, Object>> getCustList(Map<String, Object> params,List<Map<String, Object>> conditions);

	/**
	 * @param params
	 * @param TabColsDetailModel tab
	 *            查询模型中的表
	 * @return
	 */
	public List<Map<String, Object>> getCustList(Map<String, Object> params,
			TabColsDetailModel tab);

	/**
	 * 查询账户基本信息
	 * 
	 * @param params
	 * @param conditions
	 * @return
	 */
	public List<Map<String, Object>> getLoanAccList(Map<String, Object> params,
			List<Map<String, Object>> conditions);

	/**
	 * 获取存款账户总额
	 * 
	 * @param params
	 * @return
	 */
	public Map<String, Object> getDspSumData(Map<String, Object> params);

	public List<Map<String, Object>> getDpsAccountList(
			Map<String, Object> params);

	public List getHistoryCustReds(Map<String, Object> params);

	public void saveHisCustReds(HistoryCustRed hsd);

	public List<Map<String,Object>> getCustRels(Map<String, Object> params,List<Map<String, Object>> conditions);

	public List<Map<String, Object>> getCustSureList(
			Map<String, Object> params, List<Map<String, Object>> conditions);

	public List<Map<String, Object>> getCommonCustInfos(Map<String, Object> params,
			TabColsDetailModel tab);
	public List<Map<String, Object>> getSumDataEveryAccunt(Map<String, Object> params);
	
	public void savePhyCng(PhyTableCngModel tables);
	
	public List getColsByTableName(Map<String, Object> params);
	
	public void deleteModel(long modelid);
	
	public List<Map<String, Object>> getTableCng(PhyTableCngModel tabCng);
	
	public void deleteCols(String tabid,String modelid,String colid);
	
	public TableModelColumns getColInfo(TableModelColumns tmc);
	
	public int saveOneColumnInfo(TableModelColumns tmc);
}
