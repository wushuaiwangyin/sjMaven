package com.krm.dbaudit.web.all360.model;

import java.util.Map;

import com.github.abel533.mapper.Mapper;

public interface CustInfoMapper extends Mapper<CustInfoModel> {
public CustInfoModel  getCustInfo(Map<String, Object> params) ;

}
