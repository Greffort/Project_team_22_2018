CREATE BIGFILE TABLESPACE tbs_sas
	DATAFILE 'tbs_sas.dat'
    SIZE 10M
	
CREATE BIGFILE TABLESPACE def_sas
	DATAFILE 'def_sas.dat'
    SIZE 10M
    AUTOEXTEND ON;

CREATE TEMPORARY TABLESPACE temm_sas
	TEMPFILE 'temm_sas.dbf'
    SIZE 5M
    AUTOEXTEND ON;
	
CREATE BIGFILE TABLESPACE tem_sas
	DATAFILE 'tem_sas.dat'
    SIZE 10M
    AUTOEXTEND ON;
	

	
	
	
	
	