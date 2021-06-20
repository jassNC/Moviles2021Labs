CREATE OR REPLACE VIEW TOUR_ACTIVITIES AS SELECT AC.ID AC_ID, AC.BODY AC_BODY, T.ID TOUR_ID 
FROM ACTIVITY AC 
INNER JOIN FEATURE F ON F.ACTIVITY_FK = AC.ID 
INNER JOIN TOUR T ON F.TOUR_FK = T.ID;

DROP PROCEDURE IF EXISTS PUT_RESERVATION;
DELIMITER //
CREATE PROCEDURE PUT_RESERVATION
(IN P_SEATS INT, IN P_USER INT, IN P_TOUR INT)
BEGIN
DECLARE V_SEATS INT;
    SELECT SEATS INTO V_SEATS FROM TOUR WHERE ID = P_TOUR;
    UPDATE TOUR SET SEATS = V_SEATS - P_SEATS WHERE ID = P_TOUR;
	INSERT INTO RESERVATION (SEATS, USER_FK, TOUR_FK) 
				VALUES (P_SEATS, P_USER, P_TOUR);
END 
//DELIMITER ;

DROP PROCEDURE IF EXISTS PUT_USER;
DELIMITER //
CREATE PROCEDURE PUT_USER
(IN P_NAME VARCHAR(45), IN P_EMAIL VARCHAR(45), IN P_BIRTH_DATE DATE, 
IN P_PASSWORD VARCHAR(45), IN P_COUNTRY VARCHAR(45))
BEGIN
	INSERT INTO USER (NAME, EMAIL, BIRTH_DATE, PASSWORD, COUNTRY) 
				VALUES (P_NAME, P_EMAIL, P_BIRTH_DATE, P_PASSWORD, P_COUNTRY);
END 
//DELIMITER ;

DROP PROCEDURE IF EXISTS ADD_TO_FAV;
DELIMITER //
CREATE PROCEDURE ADD_TO_FAV
(IN P_USER_ID INT, IN P_TOUR_ID INT)
BEGIN
	INSERT INTO FAVORITE (USER_ID, TOUR_ID) VALUES (P_USER_ID, P_TOUR_ID);
END 
//DELIMITER ;

DROP PROCEDURE IF EXISTS REMOVE_FROM_FAV;
DELIMITER //
CREATE PROCEDURE REMOVE_FROM_FAV
(IN P_USER_ID INT, IN P_TOUR_ID INT)
BEGIN
	DELETE FROM tourdb.FAVORITE WHERE USER_ID = P_USER_ID AND TOUR_ID = P_TOUR_ID;
END 
//DELIMITER ;

DROP PROCEDURE IF EXISTS PUT_TOUR;
DELIMITER //
CREATE PROCEDURE PUT_TOUR
(IN P_NAME VARCHAR(45), IN P_DESC VARCHAR(100), IN P_RATING FLOAT, IN P_LEAVE_DATE DATE, P_RETURN_DATE DATE,
IN P_PRICE DOUBLE, IN P_SEATS INT, IN P_CAT INT, IN P_CITY VARCHAR(45))
BEGIN
DECLARE V_COUNTRY_ID INT;
    SELECT ID INTO V_COUNTRY_ID FROM COUNTRY WHERE NAME = P_COUN;
	INSERT INTO TOUR (NAME, DESCRIPTION, RATING, LEAVE_DATE, RETURN_DATE, PRICE, SEATS, CATEGORY_FK,CITY) 
	VALUES(P_NAME, P_DESC, P_RATING, P_LEAVE_DATE, P_RETURN_DATE, P_PRICE, P_SEATS, P_CAT, P_CITY);
END 
//DELIMITER ;

DROP PROCEDURE IF EXISTS LINK_ACTIVITY;
DELIMITER //
CREATE PROCEDURE LINK_ACTIVITY
(IN P_ID_TOUR INT, IN P_ID_ACT INT)
BEGIN
	INSERT INTO FEATURE (TOUR_FK, ACTIVITY_FK) VALUES (P_ID_TOUR,P_ID_ACT);
END 
//DELIMITER ;

INSERT INTO activity (BODY) VALUES ('Bungie Jump');
INSERT INTO activity (BODY) VALUES ('Caminata en la playa');
INSERT INTO activity (BODY) VALUES ('Zafari');
INSERT INTO activity (BODY) VALUES ('Paintball');
INSERT INTO activity (BODY) VALUES ('Paseo en bicicleta');
INSERT INTO activity (BODY) VALUES ('Museum guide');
INSERT INTO activity (BODY) VALUES ('Surf');
INSERT INTO activity (BODY) VALUES ('Paseo en bote');
INSERT INTO activity (BODY) VALUES ('Visita guiada');
INSERT INTO activity (BODY) VALUES ('Comida');
INSERT INTO activity (BODY) VALUES ('Alojamiento');

select * from activity;

INSERT INTO CATEGORY (NAME) VALUES ("Aventura");
INSERT INTO CATEGORY (NAME) VALUES ("Historico");
INSERT INTO CATEGORY (NAME) VALUES ("Recreativo");
select * from category;

INSERT INTO TOUR (NAME, DESCRIPTION, RATING, LEAVE_DATE, RETURN_DATE, PRICE, SEATS, CATEGORY_FK,CITY, IMAGE, DURATION) 
VALUES('Descubre las piramides de Mexico','Ve a las mas famosas piramides de mexico y descubre sus secretos',
5,STR_TO_DATE('20-06-2021','%d-%m-%Y'),STR_TO_DATE('24-06-2021','%d-%m-%Y'),200,40,2,'CDMX','https://ichef.bbci.co.uk/news/640/cpsprodpb/16D7C/production/_102946539_gettyimages-909755746.jpg',1.3);

INSERT INTO TOUR (NAME, DESCRIPTION, RATING, LEAVE_DATE, RETURN_DATE, PRICE, SEATS, CATEGORY_FK,CITY, IMAGE,DURATION) 
VALUES('Descansa en las playas de Costa Rica','Visita las hermosas playas del pais centroamericano',
5,STR_TO_DATE('10-05-2021','%d-%m-%Y'),STR_TO_DATE('21-05-2021','%d-%m-%Y'),500,30,1,'Jaco','https://upload.wikimedia.org/wikipedia/commons/a/ab/Jaco_Beach_Costa_Rica.jpg',4.3);

INSERT INTO TOUR (NAME, DESCRIPTION, RATING, LEAVE_DATE, RETURN_DATE, PRICE, SEATS, CATEGORY_FK,CITY, IMAGE,DURATION) 
VALUES('Increibles volcanes en Costa Rica','Visita los preciosos y antiguos volcanes del pais centroamericano',
3,STR_TO_DATE('18-07-2021','%d-%m-%Y'),STR_TO_DATE('21-07-2021','%d-%m-%Y'),150,25,1,'Cartago','https://www.govisitcostarica.co.cr/images/photos/full-irazu-volcano-crater.jpg',5.3);

INSERT INTO TOUR (NAME, DESCRIPTION, RATING, LEAVE_DATE, RETURN_DATE, PRICE, SEATS, CATEGORY_FK,CITY, IMAGE,DURATION) 
VALUES('Preciosas islas en Tailandia!','Visita y disfruta de las playas de este salvaje pais',
4,STR_TO_DATE('05-06-2021','%d-%m-%Y'),STR_TO_DATE('12-06-2021','%d-%m-%Y'),700,50,1,'Hua Hin','https://upload.wikimedia.org/wikipedia/commons/5/53/Hua_Hin_hotels.jpg',9.2);

INSERT INTO TOUR (NAME, DESCRIPTION, RATING, LEAVE_DATE, RETURN_DATE, PRICE, SEATS, CATEGORY_FK,CITY, IMAGE,DURATION) 
VALUES('Los mejores museos de USA','Visita y aprende en los mejores museos de Nueva York',
2,STR_TO_DATE('07-08-2021','%d-%m-%Y'),STR_TO_DATE('08-08-2021','%d-%m-%Y'),220,80,3,'NY','https://upload.wikimedia.org/wikipedia/commons/4/47/New_york_times_square-terabass.jpg',13.1);

INSERT INTO FEATURE (TOUR_FK, ACTIVITY_FK) VALUES (1,10);
INSERT INTO FEATURE (TOUR_FK, ACTIVITY_FK) VALUES (1,9);
INSERT INTO FEATURE (TOUR_FK, ACTIVITY_FK) VALUES (1,3);
INSERT INTO FEATURE (TOUR_FK, ACTIVITY_FK) VALUES (2,7);
INSERT INTO FEATURE (TOUR_FK, ACTIVITY_FK) VALUES (2,10);
INSERT INTO FEATURE (TOUR_FK, ACTIVITY_FK) VALUES (3,10);
INSERT INTO FEATURE (TOUR_FK, ACTIVITY_FK) VALUES (3,9);
INSERT INTO FEATURE (TOUR_FK, ACTIVITY_FK) VALUES (3,11);
INSERT INTO FEATURE (TOUR_FK, ACTIVITY_FK) VALUES (3,1);
INSERT INTO FEATURE (TOUR_FK, ACTIVITY_FK) VALUES (4,11);
INSERT INTO FEATURE (TOUR_FK, ACTIVITY_FK) VALUES (4,10);
INSERT INTO FEATURE (TOUR_FK, ACTIVITY_FK) VALUES (4,8);
INSERT INTO FEATURE (TOUR_FK, ACTIVITY_FK) VALUES (5,9);
INSERT INTO FEATURE (TOUR_FK, ACTIVITY_FK) VALUES (5,10);
SELECT * FROM activity;

insert into REVIEW (BODY, TOUR_FK) VALUES ('Muy buen tour me gusto mucho',1);
insert into REVIEW (BODY, TOUR_FK) VALUES ('Muy bonito pero caluroso',1);
insert into REVIEW (BODY, TOUR_FK) VALUES ('Hermosas playas!',2);
insert into REVIEW (BODY, TOUR_FK) VALUES ('Muy bonito pero con olor a Azufre en el Irazu',3);
insert into REVIEW (BODY, TOUR_FK) VALUES ('Hermosos volcanes',3);
insert into REVIEW (BODY, TOUR_FK) VALUES ('Hermoso pais',4);
insert into REVIEW (BODY, TOUR_FK) VALUES ('Geniales vistas',4);
insert into REVIEW (BODY, TOUR_FK) VALUES ('Precioso!',4);
insert into REVIEW (BODY, TOUR_FK) VALUES ('Fosiles de dinosaurio increibles',5);
insert into REVIEW (BODY, TOUR_FK) VALUES ('Muy buena experiencia',5);