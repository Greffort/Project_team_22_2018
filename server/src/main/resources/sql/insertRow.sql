/*	***		INSERT	***		*/


/*USERS*/
INSERT INTO USERS VALUES (1, 'User' || to_char(1));
COMMIT;

prompt ***		INSERT INTO USERS  completed
--pause Press Enter to view results

/*PASS*/
INSERT INTO PASS VALUES (1, '1' || to_char(1),1);
COMMIT;

prompt ***		INSERT INTO PASS  completed
--pause Press Enter to view results

alter session set nls_date_language='American';

/*GOALS*/
INSERT INTO "DB_TEAM_22"."GOALS" (IDGOAL, GCHEKCER, GCRITERCOMPL, GCRITICTIME, GDATECLOSE, GDATEOPEN, GDEADLINE, GDESCRIP, GNAME, GSTATUS)
VALUES ('1',
'0',
'goal1',
TO_DATE('2019-05-12 11:29:25',
'YYYY-MM-DD HH24:MI:SS'),
TO_DATE('2019-05-12 11:29:29',
'YYYY-MM-DD HH24:MI:SS'),
TO_DATE('2019-05-12 11:29:35',
'YYYY-MM-DD HH24:MI:SS'),
TO_DATE('2019-05-12 11:29:38',
'YYYY-MM-DD HH24:MI:SS'),
'goal1',
'goal1',
'goal1');

INSERT INTO "DB_TEAM_22"."GOALS" (IDGOAL, GCHEKCER, GCRITERCOMPL, GCRITICTIME, GDATECLOSE, GDATEOPEN, GDEADLINE, GDESCRIP, GNAME, GSTATUS)
VALUES ('2',
'0',
'goal2',
TO_DATE('2019-05-12 11:29:25',
'YYYY-MM-DD HH24:MI:SS'),
TO_DATE('2019-05-12 11:29:29',
'YYYY-MM-DD HH24:MI:SS'),
TO_DATE('2019-05-12 11:29:35',
'YYYY-MM-DD HH24:MI:SS'),
TO_DATE('2019-05-12 11:29:38',
'YYYY-MM-DD HH24:MI:SS'),
'goal2',
'goal2',
'goal2');
-- INSERT INTO goals VALUES (
--         4,
--         'goal_' || to_char(4),
--         'goal_' || to_char(4),
--         'goal_' || to_char(4),
--         'goal_' || to_char(4),
--         TO_DATE('17-NOV-1983', 'DD-MON-YYYY'),
--         TO_DATE('17-NOV-1983', 'DD-MON-YYYY'),
--         TO_DATE('17-NOV-1983', 'DD-MON-YYYY') ,
--         TO_DATE('17-NOV-1983', 'DD-MON-YYYY'),
--         0
-- );
COMMIT;

prompt ***		INSERT INTO GOALS  completed
--pause Press Enter to view results

/*GOALSTAGES*/
INSERT INTO "DB_TEAM_22"."GOALSSTAGES" (IDGSTAG, GSCOMPLETED, GSNAME) VALUES ('1', 'gs1', 'gs2')

INSERT INTO DB_TEAM_22.GOALSSTAGES (IDGSTAG, GSCOMPLETED, GSNAME) VALUES('1','goal_stage','goal_stage');
INSERT INTO DB_TEAM_22.GOALSSTAGES (IDGSTAG, GSCOMPLETED, GSNAME) VALUES('2','goal_stage','goal_stage');
INSERT INTO DB_TEAM_22.GOALSSTAGES (IDGSTAG, GSCOMPLETED, GSNAME) VALUES('3','goal_stage','goal_stage');
COMMIT;

prompt ***		INSERT INTO GOALSTAGES  completed
--pause Press Enter to view results

/*USERSGOALS*/
INSERT INTO USERSGOALS VALUES (1, 1);
INSERT INTO USERSGOALS VALUES (1, 2);
-- INSERT INTO USERSGOALS VALUES (2, 3);
COMMIT;

prompt ***		INSERT INTO USERSGOALS  completed
--pause Press Enter to view results

-- /*GOALSGOALSTAGES*/
INSERT INTO GOALSGOALSTAGES VALUES (1, 1);
INSERT INTO GOALSGOALSTAGES VALUES (1, 2);
INSERT INTO GOALSGOALSTAGES VALUES (1, 3);
COMMIT;

prompt ***		INSERT INTO GOALSGOALSTAGES 	completed