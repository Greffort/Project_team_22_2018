CREATE USER SAS
	IDENTIFIED BY root
	DEFAULT TABLESPACE def_sas
	TEMPORARY TABLESPACE temm_sas;

GRANT create session TO SAS;
GRANT create table TO SAS;
GRANT create view TO SAS;
GRANT create any trigger TO SAS;
GRANT create any procedure TO SAS;
GRANT create sequence TO SAS;
GRANT create synonym TO SAS;
