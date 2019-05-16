CREATE USER DB_TEAM_2222
	IDENTIFIED BY root
	DEFAULT TABLESPACE def_team_2222
	TEMPORARY TABLESPACE temm_team_2222;

GRANT create session TO DB_TEAM_2222;
GRANT create table TO DB_TEAM_2222;
GRANT create view TO DB_TEAM_2222;
GRANT create any trigger TO DB_TEAM_2222;
GRANT create any procedure TO DB_TEAM_2222;
GRANT create sequence TO DB_TEAM_2222;
GRANT create synonym TO DB_TEAM_2222;
