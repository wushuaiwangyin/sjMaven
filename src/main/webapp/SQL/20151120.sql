CREATE OR REPLACE PROCEDURE "SLSUSER"."P_GET_STEP_BY_CONDITION" (IN_ACCTNO IN VARCHAR2,IN_STEP IN INT,IN_SEARCH_ID IN INT,IN_TRAN_DATE IN VARCHAR2,OUT_STEP OUT INT)
IS
    V_ROW_COUNT INT DEFAULT 0;
    V_STEP_SEQ NUMBER(20,0);
    V_ACCTNO VARCHAR2(32);
    V_IN_STEP INT;
    type flow_cur_type  is ref cursor return SYSMONEY_FLOW_TEMP_1%rowtype;
    flow_cur flow_cur_type;
    flow_record SYSMONEY_FLOW_TEMP_1%rowtype;
    V_OUT_STEP INT;
    V_TRAN_DATE VARCHAR2(12);
    V_CONDITION VARCHAR(1000) DEFAULT '';
    V_INSERT_STR VARCHAR(1000);
    V_SEARCH_ID INT;
BEGIN
    V_STEP_SEQ := SYSMONEY_STEP_SEQ.NEXTVAL;
    V_IN_STEP := IN_STEP +1;
    
    --当前查询唯一编号
    IF IN_SEARCH_ID IS NULL THEN
       V_SEARCH_ID := sysmoney_search_seq.NEXTVAL;
       ELSE
          V_SEARCH_ID := IN_SEARCH_ID;
    END IF;
    
    
    --判断是否传入交易日期
    IF IN_TRAN_DATE IS NOT NULL THEN 
       V_CONDITION := ' AND TRANDATE >= '||''''||IN_TRAN_DATE||'''';
    END IF;
    
    V_INSERT_STR := 'INSERT INTO SYSMONEY_FLOW_TEMP_1(ID, TRANDATE, ACCTNO, SERSEQNO, NAME, ACCTNO1, CUSID, TRANAMT, CDFLAG, CASHTRANFLAG, TRANCODE, BRC, TELLER,SEARCH_SEQ,SEARCH_STEP,search_flow )
                     SELECT SYSMONEY_FLOW_SEQ.NEXTVAL AS ID, a.TRANDATE, a.ACCTNO, a.PTXNSEQ, c.CUSTOMNAME NAME, a.FTFACCTNO, c.CUSTOMID, a.TRANAMT, a.CDFLG, a.CTFLG,a.PTXNCODE, a.tranbrc, a.TELLER ,'||V_STEP_SEQ||','||V_IN_STEP||','||V_SEARCH_ID||'
                     FROM  DPSPERSAVELIST a
                     left join ciscustaccinfo b on a.ACCTNO = b.ACCTNO
                     left join ciscustomerinfo c on c.CUSTOMID = b.CUSTOMID
                     where 
                     a.cdflg=''D'' and a.CTXNCODE like ''dps%'' and trim(a.FTFACCTNO) is not null 
                     and a.TELLER <> ''tttt'' and trim(a.acctno)=trim('||IN_ACCTNO||') '||V_CONDITION;
      
    DBMS_OUTPUT.PUT_LINE(V_INSERT_STR);
    execute immediate V_INSERT_STR;   
    commit;

    select count(0) ct into V_ROW_COUNT from SYSMONEY_FLOW_TEMP_1 where SEARCH_SEQ = V_STEP_SEQ and SEARCH_STEP = V_IN_STEP;
    if V_ROW_COUNT < 1 then 
        OUT_STEP := V_SEARCH_ID;
     else
        open flow_cur  for select *  from SYSMONEY_FLOW_TEMP_1 where SEARCH_SEQ = V_STEP_SEQ and SEARCH_STEP = V_IN_STEP;
        loop
            fetch flow_cur  into flow_record;
            exit when flow_cur%notfound;
            V_ACCTNO := flow_record.ACCTNO1;
            V_TRAN_DATE := flow_record.trandate;
            V_OUT_STEP := V_IN_STEP;
            
            if V_IN_STEP=5 then
                 exit;
            end if;
            P_GET_STEP_BY_CONDITION(V_ACCTNO,V_IN_STEP,V_SEARCH_ID,V_TRAN_DATE,V_OUT_STEP);
            
        end loop;
        close flow_cur;
        OUT_STEP := V_SEARCH_ID;
    end if;
END;
/


CREATE SEQUENCE SLSUSER.SYSMONEY_FLOW_SEQ
    INCREMENT BY 1
    START WITH 8041
    MAXVALUE 9999999999999999999999999999
    NOMINVALUE
    NOCYCLE
    CACHE 20
    NOORDER
/
CREATE SEQUENCE SLSUSER.SYSMONEY_SEARCH_SEQ
    INCREMENT BY 1
    START WITH 301
    MAXVALUE 9999999999999999999999999999
    NOMINVALUE
    NOCYCLE
    CACHE 20
    NOORDER
/
CREATE SEQUENCE SLSUSER.SYSMONEY_STEP_SEQ
    INCREMENT BY 1
    START WITH 5281
    MAXVALUE 9999999999999999999999999999
    NOMINVALUE
    NOCYCLE
    CACHE 20
    NOORDER
/


CREATE TABLE SLSUSER.SYSMONEY_FLOW_TEMP_1  ( 
	ID          	NUMBER(20) NULL,
	TRANDATE    	VARCHAR2(10) NULL,
	ACCTNO      	VARCHAR2(32) NULL,
	SERSEQNO    	NUMBER(*, 0) NULL,
	NAME        	VARCHAR2(70) NULL,
	ACCTNO1     	VARCHAR2(32) NULL,
	CUSID       	VARCHAR2(32) NULL,
	TRANAMT     	NUMBER(16,2) NULL,
	CDFLAG      	VARCHAR2(1) NULL,
	CASHTRANFLAG	VARCHAR2(1) NULL,
	TRANCODE    	VARCHAR2(6) NULL,
	BRC         	VARCHAR2(9) NULL,
	TELLER      	VARCHAR2(6) NULL,
	SEARCH_SEQ  	NUMBER(*, 0) NULL,
	SEARCH_STEP 	NUMBER(*, 0) NULL,
	SEARCH_FLOW 	NUMBER(*, 0) NULL 
	)
TABLESPACE SJPRODUCT NOCOMPRESS PCTFREE 10 INITRANS 1 MAXTRANS 255 
STORAGE( INITIAL 983040 NEXT 8192 MINEXTENTS 1 MAXEXTENTS 2147483645 BUFFER_POOL DEFAULT )
NOPARALLEL
NOLOGGING 
NOCACHE 
MONITORING 
NOROWDEPENDENCIES
DISABLE ROW MOVEMENT 
GO

