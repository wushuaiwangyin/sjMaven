package com.krm.dbaudit.web.all360.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.web.all360.model.CommonCustInfo;
import com.krm.dbaudit.web.all360.model.CustInfoModel;
import com.krm.dbaudit.web.all360.model.CustRelCustModel;
import com.krm.dbaudit.web.all360.model.HistoryCustRed;
import com.krm.dbaudit.web.all360.model.PhyTableCngModel;
import com.krm.dbaudit.web.all360.model.QueryModel;
import com.krm.dbaudit.web.all360.model.TableModelColumns;
import com.krm.dbaudit.web.all360.util.EchartSeries;

public interface QueryModelService {
public List<Map<String, Object>> getModels(Map<String,Object> params);
//public PageInfo<QueryModel> getQueryModelsList(Map<String,Object> params);
public List<Map<String, Object>> getQueryModelsList(Map<String,Object> params);
/**查询用户基本信息
 * @param params 参数列表
 * @param conditions 查询模型中的表和列信息
 * @return 用户信息列表
 */
public List<Map<String, Object>>  getCustList(Map<String,Object> params,List<Map<String, Object>> conditions);

/**获取公共客户信息
 * @param params
 * @param conditions
 * @return
 */
public CommonCustInfo  getCommonCustInfos(Map<String,Object> params,List<Map<String, Object>> conditions);
/**查询客户账户基本信息
 * @param params
 * @param conditions
 * @return
 */
public String  getLoanAccList(Map<String,Object> params,List<Map<String, Object>> conditions);
public List getColumns(Map<String,Object> params);
public List<CustInfoModel> getCustInfos(Map<String,Object> params);
public String getCustomAllInfo(Map<String,Object> params);
/**计算存款账户总余额,贷款总合同额
 * @param params
 * @return
 */
public Map<String,Object> getDspSumData(Map<String,Object> params);

/**（按客户号）获取存款账户余额信息
 * @param params
 * @return
 */
public List<EchartSeries> getDpsAccountList(Map<String,Object> params);

/**历史查询记录
 * @param params
 * @return
 */
public List<Map<String, Object>> getHistoryCustReds(Map<String,Object> params);
/**保存查询记录
 * @param hs
 * @return
 */
public String saveHisCustReds(HistoryCustRed hs);

/**获取关联信息
 * @param params
 * @return
 */
public String getCustRels(Map<String,Object> params,List<Map<String, Object>> conditions);

/**获取担保信息
 * @param params
 * @return
 */
public String getCustSureList(Map<String,Object> params,List<Map<String, Object>> conditions);

/**
 * @param params
 * @param conditions
 * @return
 */
public String custList2(Map<String,Object> params,List<Map<String, Object>> conditions);

public List<TableModelColumns>  getTabModelCosPageInfo(Map<String, Object> conditions);

public String getLoanData(Map<String, Object> params);

/**保存物理表信息
 * @param tables
 */
public void savePhyTable(PhyTableCngModel tables);

/**根据物理表名获取物理表列信息。
 * @param params
 * @return
 */
public List getColsByTableName(Map<String, Object> params);

public int deleteModel(long pkid);

public int deleteCols(String colsId,String tabid,String modelid);

public PhyTableCngModel getPhyTableCng (PhyTableCngModel tabCng);
public TableModelColumns getColInfo(TableModelColumns tmc);

public String saveOneColumnInfo(TableModelColumns tmc);
}
