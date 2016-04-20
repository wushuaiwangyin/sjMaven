--审计专项模型没有业务条件，只含专项主题，如高管人员经济责任稽核
update SLSUSER.MODEL_BUZ_PROPERTY set HAVE_SUBJECT=1,HAVE_LINE=0 where ID =4;
--二次分析模型是自动跑出来的，不需要做配置
update SLSUSER.MODEL_BUZ_PROPERTY set DEL_FLAG=1 where ID =6;
--合规，监管，重大风险模型下面只有条件，不含主题
update SLSUSER.MODEL_BUZ_PROPERTY set HAVE_SUBJECT=0 where ID in(2,3,5)