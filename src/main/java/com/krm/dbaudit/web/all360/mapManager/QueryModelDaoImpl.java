package com.krm.dbaudit.web.all360.mapManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.krm.dbaudit.web.all360.model.CustInfoModel;
import com.krm.dbaudit.web.all360.model.HistoryCustRed;
import com.krm.dbaudit.web.all360.model.PhyTableCngModel;
import com.krm.dbaudit.web.all360.model.TabColsDetailModel;
import com.krm.dbaudit.web.all360.model.TableModelColumns;
import com.krm.dbaudit.web.all360.util.FormatCollectObj;

@Repository("queryModelDao")
public class QueryModelDaoImpl implements QueryModelDao {
	@Autowired
	private JdbcTemplate sqlMapper; 
public JdbcTemplate getSqlMapper() {
		return sqlMapper;
	}

	public void setSqlMapper(JdbcTemplate sqlMapper) {
		this.sqlMapper = sqlMapper;
	}

	/*	private SqlMapper sqlMapper;

	public SqlMapper getSqlMapper() {
		return sqlMapper;
	}

	public void setSqlMapper(SqlMapper sqlMapper) {
		this.sqlMapper = sqlMapper;
	}
*/
	@Override
	public List<Map<String, Object>> getModels(Map<String, Object> params) {

		StringBuffer sb = new StringBuffer("select ");
		sb.append("t.PKID as tabId,t.PHY_TABLE as tableName,t.IS_PUBLIC as isPublic,c.PKID as columnId,c.ALISE_NAME as aliseName,c.NAME as columnName,c.DATA_TYPE as dataType,c.DICID as dicid,m.MODEL_ID as modelId,m.MODEL_NAME as modelName,m.MODEL_PATH as modelPath");
		sb.append(
				"  from SYS360_PHYTABLES_CONFIG  t,SYS360_COLUMNS_CONFIG c, SYS360_MODELS_COLS_CONTRAST MC,SYS360_MODELS_CONFIG M ")
				.append("WHERE  M.MODEL_ID=MC.MODEL_ID AND MC.COL_ID=C.PKID AND MC.TABL_ID=T.PKID  AND  T.PKID=C.TAB_ID  ");
		if (null != params.get("model_id")) {
			sb.append(" and M.model_id="
					+ Long.valueOf(params.get("model_id").toString()));
		}

		//return sqlMapper.selectList(sb.toString());
		return sqlMapper.queryForList(sb.toString());

	}

	@Override
	public List getColumns(Map<String, Object> params) {
		return null;
	}

	@Override
	public List<CustInfoModel> getCustInfos(Map<String, Object> params) {
		return null;
	}

	@Override
	public List<Map<String, Object>> getCustList(Map<String, Object> params,
			TabColsDetailModel tab) {
		String sql = tab.getSql();
		if(StringUtils.isBlank(sql)){
			return new ArrayList<Map<String,Object>>();
		}
		if (null != params.get("custId")) {
			sql += " and trim(CUSTOMID)='" + params.get("custId").toString().trim()+"'";
		}

		return sqlMapper.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getLoanAccList(Map<String, Object> params,
			List<Map<String, Object>> conditions) {
		//String sql = FormatCollectObj.genConditons(conditions);
		String sql = FormatCollectObj.genEnConditons(conditions);
		if(StringUtils.isBlank(sql)){
			return new ArrayList<Map<String,Object>>();
		}
		if (null != params.get("acctno")) {
			sql += " and trim(acctno)='" + params.get("acctno").toString().trim()+"'";
		}
		if (null != params.get("custId")) {
			sql += " and trim(CUSTOMID)='" + params.get("custId").toString().trim()+"'";
		}

		return sqlMapper.queryForList(sql);
	}

	@Override
	public Map<String,Object> getDspSumData(Map<String, Object> params) {
		String sql ="";
		String tabName="";
		if("dps".equals(params.get("sum"))){
			if("1".equals(params.get("isPublic").toString())){
				tabName="PRIV_DEPO_INFO";
			}else{
				tabName="ENTE_DEPO_INFO";//ENTE_DEPO_INFO
			}
			sql= "select sum(nvl(bal,0)) as balance  from "+tabName+" where trim(CUSTOMID)='"+params.get("custId").toString()+"'";
		}
		if("loan".equals(params.get("sum"))){
			if("1".equals(params.get("isPublic").toString())){
				tabName="PRIV_LOAN_ACCT";
			}else{
				tabName="ENTE_LOAN_ACCT";//ENTE_LOAN_ACCT
			}
			sql= "select sum(bal) as balance  from  "+tabName+" where trim(CUSTOMID)='"+params.get("custId").toString()+"'";
		}

	//	return sqlMapper.selectOne(sql);
		return sqlMapper.queryForMap(sql);
	}

	@Override
	public List<Map<String, Object>> getDpsAccountList(
			Map<String, Object> params) {
		String tabName="";
		if("1".equals(params.get("isPublic").toString())){//private
			tabName="PRIV_DEPO_INFO";
		}else{
			tabName="ENTE_DEPO_INFO";
		}
		//获取每月一号数据，按月分组求取数据
		String sql="SELECT  ACCTNO,sum(nvl(bal,0)) as BALANCE ,substr( replace (optiondate,'-',''),5,2) as mmonth  FROM "+tabName+"  WHERE trim(CUSTOMID)='"+params.get("custId").toString().trim()+"'  and substr(replace (optiondate,'-',''),7,2)='01'  GROUP BY ACCTNO,substr( replace (optiondate,'-',''),5,2)";
	//	String sql="SELECT  ACCTNO,sum(bal) as BALANCE ,substr( replace (LASTPRTDATE,'-',''),5,2) as mmonth  FROM "+tabName+"  WHERE trim(CUSTOMID)='"+params.get("custId").toString().trim()+"'    GROUP BY ACCTNO,substr( replace (LASTPRTDATE,'-',''),5,2)";
		//return sqlMapper.selectList(sql, params.get("custId").toString());
		
		return sqlMapper.queryForList(sql);
	}

	@Override
	public List getHistoryCustReds(Map<String, Object> params) {
		String sql="";
		String customTableName="";
//		if("1".equals(params.get("isPublic").toString())){//对私
//			customTableName="PRIV_CUST_INFO";
//		}
//		if("2".equals(params.get("isPublic").toString())){//对公
//			customTableName="ENTE_CUST_INFO";
//		}
//		if(null==params.get("isPublic")){
//			
//		}
		sql="select distinct(h.CUSTOMID) as custId,h.CUSTNAME as custName,max(h.HS_DATE) as hsDate,h.ISPUBLIC   FROM SYS360_HS_CUST h where rownum<"+Integer.valueOf(params.get("row").toString())+" group by h.CUSTOMID,h.CUSTNAME,h.ISPUBLIC";
		return sqlMapper.queryForList(sql);
	}

	@Override
	public void saveHisCustReds(HistoryCustRed hsd) {
		String sql="";
		String customTableName="";
//		if("1".equals(hsd.getIsPublic())){//对私
//			customTableName="PRIV_CUST_INFO";
//		}else {//对公
//			customTableName="ENTE_CUST_INFO";
//		}
//		 sql=" insert into SYS360_HS_CUST (pkid,customid,custname,hs_date) SELECT SYS360_HS_CUST_SEQ.nextval,TRIM(CUSTOMID),TRIM(CUSTOMNAME),'"+hsd.getHsDate()+"' FROM "+customTableName+" where trim(customid)='"+hsd.getCustId()+"'";
		 sql=" insert into SYS360_HS_CUST (pkid,customid,custname,hs_date,ispublic) values(SYS360_HS_CUST_SEQ.nextval,'"+hsd.getCustId()+"','"+hsd.getCustName()+"','"+hsd.getHsDate()+"', '"+hsd.getIsPublic()+"' )";
		sqlMapper.execute(sql);		
	}


@Override
public List<Map<String, Object>> getCustRels(Map<String, Object> params,List<Map<String, Object>> conditions) {
	//String sql="select cus_id_rel as custId ,cus_name_rel as relName,invt_typ as idType, inst_code as idNo from TEST_CUS_COM_REL_APITAL where cus_id='"+params.get("custId").toString().trim()+"'";
//	List<Map<String, Object>> list=sqlMapper.queryForList(sql);
//	CustRelCustModel cus=null;
	/*List<CustRelCustModel> clist=new ArrayList<CustRelCustModel>();
	if(list.size()>0){
		for(Map<String, Object> map2:list){
			CustRelCustModel cus =new CustRelCustModel();
			cus.setCustId(map2.get("custId".toUpperCase()).toString());
			cus.setIdNo(map2.get("idNo".toUpperCase()).toString());
			cus.setIdType(map2.get("idType".toUpperCase()).toString());
			cus.setRelName(map2.get("relName".toUpperCase()).toString());
			clist.add(cus);
		}
	}*/
	String sql = FormatCollectObj.genEnConditons(conditions);
	if(StringUtils.isBlank(sql)){
		return new ArrayList<Map<String,Object>>();
	}
	if (null != params.get("custId")) {
		sql += " and trim(CUSTOMID)='" + params.get("custId").toString().trim()+"'";
	}

	return sqlMapper.queryForList(sql);
}

@Override
public List<Map<String, Object>> getCustSureList(Map<String, Object> params,
		List<Map<String, Object>> conditions) {
	String sql = FormatCollectObj.genEnConditons(conditions);
	if(StringUtils.isBlank(sql)){
		return new ArrayList<Map<String,Object>>();
	}
	if (null != params.get("custId")) {
		sql += " and trim(CUSTOMID)='" + params.get("custId").toString().trim()+"'";
	}
	return sqlMapper.queryForList(sql);
}

@Override
public List<Map<String, Object>> getCommonCustInfos(Map<String, Object> params,
		TabColsDetailModel tab) {
	//只含英文列名的查询语句
	String sql=tab.getSql();
	if(StringUtils.isBlank(sql)){
		return new ArrayList<Map<String,Object>>();
	}
	if(null!=params.get("custId")){
		sql += " and trim(CUSTOMID)='" + params.get("custId").toString().trim()+"'";
	}
	
	//返回的数据集合
	List<Map<String, Object>> list=sqlMapper.queryForList(sql);
	
	return list;
}

@Override
public List<Map<String, Object>> getCustList(Map<String, Object> params,
		List<Map<String, Object>> conditions) {
	
	String sql = FormatCollectObj.genConditons(conditions);
	if(StringUtils.isBlank(sql)){
		return new ArrayList<Map<String,Object>>();
	}
	if (null != params.get("custId")) {
		sql += " and trim(CUSTOMID)='" + params.get("custId").toString().trim()+"'";
	}

	return sqlMapper.queryForList(sql);
}

@Override
public List<Map<String, Object>> getQueryModelList(Map<String, Object> params) {
String sql="select model_id as modelId,model_name as modelName from SYS360_MODELS_CONFIG where 1=1 ";
if(null!=params.get("modelId")){
	sql+=" and  model_id ="+Long.valueOf(params.get("modelId").toString());
}
	return sqlMapper.queryForList(sql);
}

@Override
public List<Map<String, Object>> getSumDataEveryAccunt(
		Map<String, Object> params) {
	String sql ="";
	String tabname="";
	if("dps".equals(params.get("sum"))){
	//	sql= "select ACCTNO,sum(balance) as balance  from test_dpsaccountinfo where trim(CUSTOMID)='"+params.get("custId").toString()+"'  group by ACCTNO";
	if("1".equals(params.get("isPublic").toString())){
		tabname="PRIV_DEPO_INFO";
	}else{
		tabname="ENTE_DEPO_INFO";
	}
		sql="select ACCTNO,balance from ("
+" select  ACCTNO ,sum(nvl(bal,0)) as balance,dense_rank() over(order by sum(nvl(bal, 0)) desc) as rn "
+"  from "+tabname+"  where trim(CUSTOMID)='"+params.get("custId").toString().trim()+"' group by ACCTNO "
+"  ) where rn <4";
	}
	if("loan".equals(params.get("sum"))){ //TEST_LNSACCTINFO
		if("1".equals(params.get("isPublic").toString())){
			tabname="PRIV_LOAN_ACCT";
		}else{
			tabname="ENTE_LOAN_ACCT";
		}
		sql="select ACCTNO,balance from ("
				+" select  ACCTNO ,sum(nvl(bal,0)) as balance,dense_rank() over(order by sum(nvl(bal, 0)) desc) as rn "
				+"  from "+tabname+"  where trim(CUSTOMID)='"+params.get("custId").toString().trim()+"' group by ACCTNO "
				+"  ) where rn <4";
	}

	return sqlMapper.queryForList(sql);
}

@Override
public void savePhyCng(PhyTableCngModel tables) {
	 String sql="";

	if( tables.getPkid()==0){
    sql =" INSERT INTO SYS360_PHYTABLES_CONFIG(PKID, PHY_TABLE, IS_PUBLIC) VALUES (SYS360_TABCNG_SEQ.NEXTVAL,'"+tables.getPhyTable()+"','"+tables.getIsPublic()+"') ";
	}else{
	sql=" update SYS360_PHYTABLES_CONFIG set PHY_TABLE='"+tables.getPhyTable()+"',IS_PUBLIC='"+tables.getIsPublic()+"'"	;
	}
  sqlMapper.execute(sql);
}

@Override
public List  getColsByTableName(Map<String, Object> params) {
	
	String sql="select * from "+params.get("tableName").toString() +" where 1=0";
	
	Connection conn =null;
	Statement stmt =null;
	ResultSet rs=null;
	ResultSetMetaData rsmd=null;
	int colsCount=0;
	List list=new ArrayList();
	try {
		conn=sqlMapper.getDataSource().getConnection();
		stmt=conn.createStatement();
		rs=stmt.executeQuery(sql);
		rsmd=rs.getMetaData();
		colsCount=rsmd.getColumnCount();
		for (int i = 1; i <= colsCount; i++) {
			list.add(rsmd.getColumnName(i));
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally{
		try {
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
	}
	return list;
}

@Override
public void deleteModel(long modelid) {
	String sql0="delete from SYS360_MODELS_COLS_CONTRAST where model_id="+modelid;
	String sql1="delete from SYS360_MODELS_CONFIG where model_id="+modelid;
	sqlMapper.batchUpdate(sql0,sql1);
}

@Override
public List<Map<String, Object>> getTableCng(PhyTableCngModel tabCng) {
	String sql="select * from SYS360_PHYTABLES_CONFIG where 1=1 ";
	if(tabCng.getPkid()!=Long.valueOf(0)){
	sql+=" and pkid="+tabCng.getPkid();
	}
	if(!StringUtils.isBlank(tabCng.getPhyTable())){
		sql+=" and phy_table='"+tabCng.getPhyTable()+"'";
	}
	return sqlMapper.queryForList(sql);
}

@Override
public void deleteCols(String tabid, String modelid, String colid) {
		String	sql1=" delete from SYS360_COLUMNS_CONFIG where pkid="+Long.valueOf(colid);
		String sql2="delete from SYS360_MODELS_COLS_CONTRAST where model_id="+Long.valueOf(modelid)+" and tabl_id="+Long.valueOf(tabid)+" and col_id="+Long.valueOf(colid);
	 sqlMapper.batchUpdate(sql1,sql2);
}

@Override
public TableModelColumns getColInfo(TableModelColumns tmc) {
	String sql="select PKID,NAME,TAB_ID,ALISE_NAME,DICID from SYS360_COLUMNS_CONFIG where PKID="+tmc.getId();
	List<Map<String, Object>> list=sqlMapper.queryForList(sql);
	if(list.size()>0){
		Map<String, Object> map=list.get(0);
		String key="";
		for (Iterator iterator=map.keySet().iterator();iterator.hasNext();) {
			key=iterator.next().toString().toUpperCase();
			if("TAB_ID".equals(key)){
				tmc.setTab_id(Long.valueOf(map.get(key).toString()));
			}
			if("ALISE_NAME".equals(key)){
				tmc.setAlise(map.get(key).toString());
			}
			if("NAME".equals(key)){
				tmc.setColName(map.get(key).toString());
			}
			if("DICID".equals(key)){
				tmc.setDicid(Long.valueOf(map.get(key).toString()));
			}
		}
		
	}
	return tmc;
}

@Override
public int saveOneColumnInfo(TableModelColumns tmc) {
String sql="";
if(tmc.getId()==0){
	sql=" insert into SYS360_COLUMNS_CONFIG (pkid,tab_id,alise_name,is_show,data_type,dicid,name) values()";
}else{
	sql=" update SYS360_COLUMNS_CONFIG set tab_id="+tmc.getTab_id()+" ,alise_name='"+tmc.getAlise()+"',dicid="+tmc.getDicid()+",name='"+tmc.getColName()+"' where pkid="+tmc.getId();
}

return sqlMapper.update(sql);
}
}
