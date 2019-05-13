create or replace PROCEDURE addGoal (
n_iduser usersgoals.iduser%type,
n_pname GOALS.gname%type,
n_critercompl GOALS.gcritercompl%type,
n_descrip GOALS.gdescrip%type,
n_status GOALS.gstatus%type,
n_critictime GOALS.gcritictime%type,
n_deadline GOALS.gdeadline%type,
n_dateopen GOALS.gdateopen%type,
n_dateclose GOALS.gdateclose%type,
n_chekcer GOALS.gchekcer%type)
IS  
BEGIN
/*
    добавляем в юзергоалс и в гоалс задачи
        n_idacc,
*/
INSERT INTO GOALS 
        VALUES (
GOAL_PK_NEW.nextval,
n_pname,
n_critercompl,
n_descrip,
n_status,
n_critictime,
n_deadline,
n_dateopen,
n_dateclose,
n_chekcer
);
INSERT INTO USERSGOALS VALUES (n_iduser, GOAL_PK_NEW.currval);

exception  
 when NO_DATA_FOUND /*Любая другая ошибка*/   
 then  
 DBMS_OUTPUT.PUT_LINE('false');
END;
commit;

create or replace PROCEDURE addGoalStage (
n_IDGOALS goalsGOALStages.idgoal%type,
n_NAME goalstages.gsname%type,
n_COMPLETED goalstages.gscompleted%type)
IS  
BEGIN
INSERT INTO GOALSTAGES 
        VALUES (
		GOAL_STAGE_PK_NEW.nextval,
		n_NAME,
		n_COMPLETED);
INSERT INTO GOALSGOALSTAGES VALUES (n_IDGOALS, GOAL_STAGE_PK_NEW.currval);

exception  
 when NO_DATA_FOUND /*Любая другая ошибка*/   
 then  
 DBMS_OUTPUT.PUT_LINE('false');
END;
commit;

create or replace PROCEDURE removeGoal (n_iduser usersgoals.iduser%type, n_idgoal usersgoals.idgoal%type)
IS  
BEGIN
DELETE FROM goalstages WHERE EXISTS
  ( SELECT goalsgoalstages.idgoal
    FROM goalsgoalstages
    WHERE goalsgoalstages.idgoal = n_idgoal and goalsgoalstages.idgoalstage = goalstages.idgstag);
    
DELETE FROM usersgoals WHERE usersgoals.iduser = n_iduser and usersgoals.idgoal = n_idgoal;
DELETE FROM goals WHERE goals.idgoal = n_idgoal;
--DELETE FROM goalsgoalstages WHERE goalsgoalstages.idgoal = n_idgoal;
exception  
 when NO_DATA_FOUND /*Любая другая ошибка*/   
 then  
 DBMS_OUTPUT.PUT_LINE('false');
END;
commit;

create or replace PROCEDURE removeGoalStage ( n_idgoal goalsgoalstages.idgoal%type, n_idstag goalsgoalstages.idgoalstage%type)
IS  
BEGIN
DELETE FROM goalstages WHERE EXISTS
  ( SELECT goalsgoalstages.idgoal
    FROM goalsgoalstages
    WHERE goalsgoalstages.idgoal = n_idgoal and goalsgoalstages.idgoalstage = n_idstag);
exception  
 when NO_DATA_FOUND /*Любая другая ошибка*/   
 then  
 DBMS_OUTPUT.PUT_LINE('false');
END;
commit;
