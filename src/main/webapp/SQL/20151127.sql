CREATE TABLE SLSUSER.SRCDATA_TYPE  ( 
	ID         	NUMBER(11) NOT NULL,
	NAME       	VARCHAR2(100 CHAR) NULL,
	SORT       	NUMBER(6) NULL,
	PARENT_ID  	NUMBER(11) NULL,
	DESCRIPTION	VARCHAR2(200 CHAR) NULL,
	DEL_FLAG   	CHAR(1) NULL
)
GO
COMMENT ON TABLE SLSUSER.SRCDATA_TYPE IS '数据源类型表'
GO
CREATE TABLE SLSUSER.SRCDATA_TYPE_DATA  ( 
	ID         	NUMBER(11) NOT NULL,
	TYPE       	NUMBER(11) NULL,
	TABLE_ID      	NUMBER(11) NULL,
	SORT       	NUMBER(3) NULL,
	DEL_FLAG   	CHAR(1) NULL
)
GO
COMMENT ON COLUMN SLSUSER.SRCDATA_TYPE_DATA.TABLE_ID IS '数据源表ID(MODEL_DEF_TABLE.ID)'

create sequence SRCDATA_TYPE_SEQ
GO
create sequence SRCDATA_TYPE_DATA_SEQ
GO

INSERT INTO SLSUSER.SRCDATA_TYPE(ID, NAME, SORT, PARENT_ID, DESCRIPTION, DEL_FLAG) 
	VALUES(SRCDATA_TYPE_SEQ.NEXTVAL, '核心系统', 1, 0, '核心系统', '0')
GO
INSERT INTO SLSUSER.SRCDATA_TYPE(ID, NAME, SORT, PARENT_ID, DESCRIPTION, DEL_FLAG) 
	VALUES(SRCDATA_TYPE_SEQ.NEXTVAL, '信贷系统', 2, 0, '信贷系统', '0')
GO
INSERT INTO SLSUSER.SRCDATA_TYPE(ID, NAME, SORT, PARENT_ID, DESCRIPTION, DEL_FLAG) 
	VALUES(SRCDATA_TYPE_SEQ.NEXTVAL, '财管系统', 3, 0, '核心系统', '0')
GO
INSERT INTO SLSUSER.SRCDATA_TYPE(ID, NAME, SORT, PARENT_ID, DESCRIPTION, DEL_FLAG) 
	VALUES(SRCDATA_TYPE_SEQ.NEXTVAL, '网银系统', 4, 0, '核心系统', '0')
GO