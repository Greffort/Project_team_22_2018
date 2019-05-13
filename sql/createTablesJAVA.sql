
  CREATE TABLE DB_TEAM_22.USERS
   (	"IDUSER" VARCHAR2(255 CHAR) NOT NULL ENABLE,
	"UNAME" VARCHAR2(255 CHAR),
	 PRIMARY KEY ("IDUSER")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "DEF_TEAM_22"  ENABLE
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE DEF_TEAM_22

COMMIT

  CREATE TABLE "DB_TEAM_22"."PASS"
   (	"ID" VARCHAR2(255 CHAR) NOT NULL ENABLE,
	"PPASS" VARCHAR2(255 CHAR),
	"USERS_IDUSER" VARCHAR2(255 CHAR),
	 PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "DEF_TEAM_22"  ENABLE,
	 CONSTRAINT "FKKBTJGMLDBJ8K2VGMY6VE1W7VE" FOREIGN KEY ("USERS_IDUSER")
	  REFERENCES "DB_TEAM_22"."USERS" ("IDUSER") ENABLE
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "DEF_TEAM_22"

COMMIT

  CREATE TABLE "DB_TEAM_22"."GOALS"
   (	"IDGOAL" VARCHAR2(255 CHAR) NOT NULL ENABLE,
	"GCHEKCER" NUMBER(10,0),
	"GCRITERCOMPL" VARCHAR2(255 CHAR),
	"GCRITICTIME" DATE,
	"GDATECLOSE" DATE,
	"GDATEOPEN" DATE,
	"GDEADLINE" DATE,
	"GDESCRIP" VARCHAR2(255 CHAR),
	"GNAME" VARCHAR2(255 CHAR),
	"GSTATUS" VARCHAR2(255 CHAR),
	 PRIMARY KEY ("IDGOAL")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "DEF_TEAM_22"  ENABLE
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "DEF_TEAM_22"

COMMIT

  CREATE GLOBAL TEMPORARY TABLE "DB_TEAM_22"."HT_GOALS"
   (	"IDGOAL" VARCHAR2(255 CHAR) NOT NULL ENABLE
   ) ON COMMIT DELETE ROWS ;

  CREATE TABLE "DB_TEAM_22"."USERSGOALS"
   (	"IDUSER" VARCHAR2(255 CHAR),
	"IDGOAL" VARCHAR2(255 CHAR) NOT NULL ENABLE,
	 PRIMARY KEY ("IDGOAL")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "DEF_TEAM_22"  ENABLE,
	 CONSTRAINT "FKACK7NC6BLUYEUE0TYW0QDG0HW" FOREIGN KEY ("IDUSER")
	  REFERENCES "DB_TEAM_22"."USERS" ("IDUSER") ENABLE,
	 CONSTRAINT "FKTFWAS23LP2H4JETNV7RVLNUMS" FOREIGN KEY ("IDGOAL")
	  REFERENCES "DB_TEAM_22"."GOALS" ("IDGOAL") ENABLE
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "DEF_TEAM_22"

COMMIT


  CREATE TABLE "DB_TEAM_22"."GOALSSTAGES"
   (	"IDGSTAG" VARCHAR2(255 CHAR) NOT NULL ENABLE,
	"GSCOMPLETED" VARCHAR2(255 CHAR),
	"GSNAME" VARCHAR2(255 CHAR),
	 PRIMARY KEY ("IDGSTAG")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "DEF_TEAM_22"  ENABLE
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "DEF_TEAM_22"

COMMIT

  CREATE TABLE "DB_TEAM_22"."GOALSGOALSTAGES"
   (	"IDGOAL" VARCHAR2(255 CHAR),
	"IDGSTAG" VARCHAR2(255 CHAR) NOT NULL ENABLE,
	 PRIMARY KEY ("IDGSTAG")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "DEF_TEAM_22"  ENABLE,
	 CONSTRAINT "FKGDQ62PUUSUBQAXN3CCF5JLHRV" FOREIGN KEY ("IDGOAL")
	  REFERENCES "DB_TEAM_22"."GOALS" ("IDGOAL") ENABLE,
	 CONSTRAINT "FKKUBQRYHDW46IQJB179RGNHFYO" FOREIGN KEY ("IDGSTAG")
	  REFERENCES "DB_TEAM_22"."GOALSSTAGES" ("IDGSTAG") ENABLE
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "DEF_TEAM_22"

COMMIT


--  /* 	***		SEQUENCE	***		*/
-- 	CREATE SEQUENCE  "DB_TEAM_22"."USER_PK_NEW"  MINVALUE 1 MAXVALUE 10000 INCREMENT BY 1 START WITH 5 NOCACHE  NOORDER  NOCYCLE ;
-- 	CREATE SEQUENCE  "DB_TEAM_22"."GOAL_PK_NEW"  MINVALUE 1 MAXVALUE 100000 INCREMENT BY 1 START WITH 5 NOCACHE  NOORDER  NOCYCLE ;
-- 	CREATE SEQUENCE  "DB_TEAM_22"."GOAL_STAGE_PK_NEW"  MINVALUE 1 MAXVALUE 100000 INCREMENT BY 1 START WITH 5 NOCACHE  NOORDER  NOCYCLE ;
-- 	   COMMIT;
--
-- 	prompt ***		CREATE	SEQUENCE completed
-- --pause Press Enter to view results
--
--    /*	***		CREATE		***		*/
--
-- /*создание таблицы Users*/
--
-- 	  CREATE TABLE "DB_TEAM_22"."USERS"
--    (	"IDUSER" VARCHAR2(20 BYTE) NOT NULL ENABLE,
-- 	"UNAME" VARCHAR2(20 BYTE),
-- 	 CONSTRAINT "USER_PK" PRIMARY KEY ("IDUSER")
--   USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
--   STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
--   PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
--   TABLESPACE "DEF_TEAM_22"  ENABLE
--    ) SEGMENT CREATION IMMEDIATE
--   PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
--   STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
--   PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
--   TABLESPACE "DEF_TEAM_22" ;
--     COMMIT;
--
--   	prompt ***		CREATE	TABLE "DB_TEAM_22"."USERS"  completed
-- --pause Press Enter to view results
--
--   /*создание таблицы PASS*/
--   CREATE TABLE "DB_TEAM_22"."PASS"
--    (	"IDUSER" VARCHAR2(20 BYTE) NOT NULL ENABLE,
-- 	"PPASS" VARCHAR2(20 BYTE),
-- 	 CONSTRAINT "PASS_PK" PRIMARY KEY ("IDUSER")
--   USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
--   STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
--   PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
--   TABLESPACE "DEF_TEAM_22"  ENABLE,
-- 	 CONSTRAINT "PASS_FK" FOREIGN KEY ("IDUSER")
-- 	  REFERENCES "DB_TEAM_22"."USERS" ("IDUSER") ON DELETE CASCADE
--    ) SEGMENT CREATION IMMEDIATE
--   PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
--   STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
--   PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
--   TABLESPACE "DEF_TEAM_22" ;
--     COMMIT;
--
--   	prompt ***		CREATE	TABLE "DB_TEAM_22"."PASS"  completed
-- --pause Press Enter to view results
--
--   /*создание таблицы GOALS*/
--     CREATE TABLE "DB_TEAM_22"."GOALS"
--    (	"IDGOAL" VARCHAR2(20 BYTE) NOT NULL ENABLE,
-- 	"GNAME" VARCHAR2(20 BYTE),
-- 	"GCRITERCOMPL" VARCHAR2(20 BYTE),
-- 	"GDESCRIP" VARCHAR2(20 BYTE),
-- 	"GSTATUS" VARCHAR2(20 BYTE),
-- 	"GCRITICTIME" DATE,
-- 	"GDEADLINE" DATE,
-- 	"GDATEOPEN" DATE,
-- 	"GDATECLOSE" DATE,
-- 	"GCHEKCER" NUMBER,
-- 	 CONSTRAINT "GOAL_PK" PRIMARY KEY ("IDGOAL")
--   USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
--   STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
--   PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
--   TABLESPACE "DEF_TEAM_22"  ENABLE
--    ) SEGMENT CREATION IMMEDIATE
--   PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
--   STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
--   PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
--   TABLESPACE "DEF_TEAM_22" ;
--     COMMIT;
--
--   	prompt ***		CREATE	TABLE "DB_TEAM_22"."GOALS"  completed
-- --pause Press Enter to view results
--
--   /*создание таблицы USERSGOALS*/
--
--   CREATE TABLE "DB_TEAM_22"."USERSGOALS"
--    (	"IDUSER" VARCHAR2(20 BYTE) NOT NULL ENABLE,
-- 	"IDGOAL" VARCHAR2(20 BYTE),
-- 	 CONSTRAINT "USERSGOALS_FK1" FOREIGN KEY ("IDUSER")
-- 	  REFERENCES "DB_TEAM_22"."USERS" ("IDUSER") ON DELETE CASCADE ENABLE,
-- 	 CONSTRAINT "USERSGOALS_FK2" FOREIGN KEY ("IDGOAL")
-- 	  REFERENCES "DB_TEAM_22"."GOALS" ("IDGOAL") ON DELETE CASCADE ENABLE
--    ) SEGMENT CREATION IMMEDIATE
--   PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
--   STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
--   PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
--   TABLESPACE "DEF_TEAM_22" ;
--
--   COMMIT;
--
--
--
--    	prompt ***		CREATE	TABLE "DB_TEAM_22"."USERSGOALS"  completed
-- --pause Press Enter to view results
--
--   /*создание таблицы GOALSSTAGES*/
--      CREATE TABLE "DB_TEAM_22"."GOALSTAGES"
--    (	"IDGSTAG" VARCHAR2(20 BYTE) NOT NULL ENABLE,
-- 	"GSNAME" VARCHAR2(20 BYTE),
-- 	"GSCOMPLETED" VARCHAR2(20 BYTE),
-- 	 CONSTRAINT "GOALSTAGE_PK" PRIMARY KEY ("IDGSTAG")
--   USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
--   STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
--   PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
--   TABLESPACE "DEF_TEAM_22"  ENABLE
--    ) SEGMENT CREATION IMMEDIATE
--   PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
--   STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
--   PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
--   TABLESPACE "DEF_TEAM_22" ;
--     COMMIT;
--
--   prompt ***		CREATE	TABLE "DB_TEAM_22"."GOALSTAGES"  completed
-- --pause Press Enter to view results
--
--   /*создание таблицы GOALSGOALSSTAGES*/
--
--   CREATE TABLE "DB_TEAM_22"."GOALSGOALSTAGES"
--    (	"IDGOAL" VARCHAR2(20 BYTE),
-- 	"IDGOALSTAGE" VARCHAR2(20 BYTE),
-- 	 CONSTRAINT "GOALSGOALSTAGES_FK1" FOREIGN KEY ("IDGOAL")
-- 	  REFERENCES "DB_TEAM_22"."GOALS" ("IDGOAL") ON DELETE CASCADE ENABLE,
-- 	 CONSTRAINT "GOALSGOALSTAGES_FK2" FOREIGN KEY ("IDGOALSTAGE")
-- 	  REFERENCES "DB_TEAM_22"."GOALSTAGES" ("IDGSTAG") ON DELETE CASCADE ENABLE
--    ) SEGMENT CREATION IMMEDIATE
--   PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
--   STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
--   PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
--   TABLESPACE "DEF_TEAM_22" ;
--     COMMIT;
--
--
--   prompt ***		CREATE	TABLE "DB_TEAM_22"."GOALSGOALSTAGES"  completed
-- --pause Press Enter to view results
--
--
