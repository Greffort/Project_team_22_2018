CREATE BIGFILE TABLESPACE tbs_team_2222
	DATAFILE 'tbs_team_2222.dat'
    SIZE 10M
	
CREATE BIGFILE TABLESPACE def_team_2222
	DATAFILE 'def_team_2222.dat'
    SIZE 10M
    AUTOEXTEND ON;

CREATE TEMPORARY TABLESPACE temm_team_2222
	TEMPFILE 'temm_team_2222.dbf'
    SIZE 5M
    AUTOEXTEND ON;
	
CREATE BIGFILE TABLESPACE tem_team_2222
	DATAFILE 'tem_team_2222.dat'
    SIZE 10M
    AUTOEXTEND ON;
	

	
	
	
	
	