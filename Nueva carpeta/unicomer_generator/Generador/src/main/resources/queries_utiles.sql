select * from all_sequences where sequence_owner='ADMIHSEC';--UN_RET_HIERARCHYGROUP_SEQ, 56000
SELECT * FROM ADMIHSEC.UN_HIERARCHY_GROUP HIERARCHY_GROUP_ID;
SELECT * FROM ADMIHSEC.UN_HIERARCHY_GROUP;
alter table ADMIHSEC.UN_HIERARCHY_GROUP ADD user_id number(10,0) NULL;
UPDATE ADMIHSEC.UN_HIERARCHY_GROUP SET USER_ID=50341

alter table ADMIHSEC.NUEVATABLA ADD user_id number(10,0) NULL;
alter table ADMIHSEC.NUEVATABLA ADD UN_HIERARCHY_GROUP_ID number(10,0) NULL;
alter table ADMIHSEC.NUEVATABLA ADD ID_ number(10,0) NULL;
ALTER TABLE ADMIHOTH.nuevatabla add INHOUSE_SOURCE_ID NUMBER (0,5) NULL ;

SELECT * FROM ADMIHOTH.OTH_ACCOUNTING_ENTRY;

select * from user_col_comments where table_name = 'OTH_DRIVER'
COMMENT ON COLUMN ADMIHOTH.OTH_DRIVER.DRIVER_ID IS 'ID';

ALTER TABLE ADMIHOTH.OTH_ACCOUNTING_ENTRY modify INHOUSE_SOURCE_ID NUMBER (0,-127) NULL ;

ALTER TABLE ADMIHSEC.NUEVATABLA ADD CONSTRAINT FK_TEST FOREIGN KEY (USER_ID) REFERENCES ADMIHSEC.UN_USER(USER_ID)
ALTER TABLE ADMIHSEC.NUEVATABLA ADD CONSTRAINT PK_TEST PRIMARY KEY (ID_) 


COMMIT;

select * from admihsec.un_user where user_credential='francisco_medina';

alter table ADMIHSEC.UN_HIERARCHY_GROUP drop column nuevaFecha float(7) null;
alter table ADMIHSEC.UN_HIERARCHY_GROUP drop column nuevaFecha2 clob null
alter table ADMIHSEC.UN_HIERARCHY_GROUP drop column nuevaFecha3 nvarchar2(10) null
alter table ADMIHSEC.UN_HIERARCHY_GROUP drop column nuevaFecha4 TIMESTAMP WITH TIME ZONE null
alter table ADMIHSEC.UN_HIERARCHY_GROUP drop column nuevaFecha5 TIMESTAMP null
alter table ADMIHSEC.UN_HIERARCHY_GROUP drop column nuevaFecha6 blob null
alter table ADMIHSEC.UN_HIERARCHY_GROUP drop column nuevaFecha7 varchar(200) null
alter table ADMIHSEC.UN_HIERARCHY_GROUP drop column nuevaFecha8 char(1) null
alter table ADMIHSEC.UN_HIERARCHY_GROUP drop column nuevaFecha9 date null
alter table ADMIHSEC.UN_HIERARCHY_GROUP drop column nuevaFecha10 integer null
alter table ADMIHSEC.UN_HIERARCHY_GROUP drop column nuevaFecha11 byte null
alter table ADMIHSEC.UN_HIERARCHY_GROUP drop column nuevaFecha12 NUMBER(10,2) null
alter table ADMIHSEC.UN_HIERARCHY_GROUP drop column nuevaFecha13 NUMERIC(10,2) null


alter table ADMIHSEC.UN_HIERARCHY_GROUP drop column nuevaFecha date(7) null

create table admihsec.nuevaTabla(campo1 varchar2(100));

select * from ADMIHOTH.QRTZ_JOB_DETAILS;


select * from ALL_CONSTRAINTS where constraint_type not in('R','C');