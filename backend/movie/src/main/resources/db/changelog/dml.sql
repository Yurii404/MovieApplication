--liquibase formatted sql
--changeset yurii:2

INSERT INTO "roles" (id, role_name)
VALUES (1, 'ROLE_USER');
INSERT INTO "roles" (id, role_name)
VALUES (2, 'ROLE_ADMIN');

insert into "user" (ID, USER_USERNAME, USER_PASSWORD, USER_EMAIL, USER_FIRSTNAME, USER_SECOND_NAME, USER_PHONE, ROLE_ID)
VALUES (1, 'YIK', '$2a$10$gjcXYG8amZF80yuGqphjRuRkdi5dI3xn.cj.FhLYoxUS.m2H02N4y', 'yik@gmail.com', 'Yurii', 'Kupchyn', '+380932389819', 2 );
insert into "user" (ID, USER_USERNAME, USER_PASSWORD, USER_EMAIL, USER_FIRSTNAME, USER_SECOND_NAME, USER_PHONE, ROLE_ID)
VALUES (2, 'OLG', '$2a$10$gjcXYG8amZF80yuGqphjRuRkdi5dI3xn.cj.FhLYoxUS.m2H02N4y', 'olg@gmail.com', 'Oleg', 'Pampkin', '+380932389818', 2 );
insert into "user" (ID, USER_USERNAME, USER_PASSWORD, USER_EMAIL, USER_FIRSTNAME, USER_SECOND_NAME, USER_PHONE, ROLE_ID)
VALUES (3, 'JOA', '$2a$10$gjcXYG8amZF80yuGqphjRuRkdi5dI3xn.cj.FhLYoxUS.m2H02N4y', 'joa@gmail.com', 'Joan', 'James', '+380932389816', 1 );
insert into "user" (ID, USER_USERNAME, USER_PASSWORD, USER_EMAIL, USER_FIRSTNAME, USER_SECOND_NAME, USER_PHONE, ROLE_ID)
VALUES (4, 'MIC', '$2a$10$gjcXYG8amZF80yuGqphjRuRkdi5dI3xn.cj.FhLYoxUS.m2H02N4y', 'mic@gmail.com', 'Michael', 'Anderson', '+380932389817', 1 );

insert into "cinema" (ID, CINEMA_NAME, USER_ADMIN_ID)
VALUES ( 1, 'OGO', 1 );
insert into "cinema" (ID, CINEMA_NAME, USER_ADMIN_ID)
VALUES ( 2, 'FORES', 2 );

insert into "movie_room" (ID, ROOM_NAME, ROOM_NUMBER, NUM_OF_SEATS, CINEMA_ID )
VALUES ( 1, 'Young', 034, 50, 1 );
insert into "movie_room" (ID, ROOM_NAME, ROOM_NUMBER, NUM_OF_SEATS, CINEMA_ID )
VALUES ( 2, 'Adult', 001, 25, 1 );
insert into "movie_room" (ID, ROOM_NAME, ROOM_NUMBER, NUM_OF_SEATS, CINEMA_ID )
VALUES ( 3, 'First', 11, 20, 2 );
insert into "movie_room" (ID, ROOM_NAME, ROOM_NUMBER, NUM_OF_SEATS, CINEMA_ID )
VALUES ( 4, 'Second', 25, 50, 2 );

insert into "movie_session" (ID, MOVIE_NAME, MOVIE_ROOM_ID, PRICE, DATE_TIME, REMAINING_TICKETS)
VALUES (1, 'Avatar', 1, 150, '2025-11-09T11:44:44.797', 50);
insert into "movie_session" (ID, MOVIE_NAME, MOVIE_ROOM_ID, PRICE, DATE_TIME, REMAINING_TICKETS)
VALUES (2, 'Fast Furious 9', 2, 180, '2025-11-09T11:44:44.797', 25);
insert into "movie_session" (ID, MOVIE_NAME, MOVIE_ROOM_ID, PRICE, DATE_TIME, REMAINING_TICKETS)
VALUES (3, 'Top Gun', 3, 250, '2025-11-09T11:44:44.797', 20);
insert into "movie_session" (ID, MOVIE_NAME, MOVIE_ROOM_ID, PRICE, DATE_TIME, REMAINING_TICKETS)
VALUES (4, 'Tenet', 4, 300, '2025-11-09T11:44:44.797', 50);

insert into "ticket" (ID, USER_ID, SESSION_ID)
VALUES (1, 3, 1 );
insert into "ticket" (ID, USER_ID, SESSION_ID)
VALUES (2, 3, 2 );
insert into "ticket" (ID, USER_ID, SESSION_ID)
VALUES (3, 4, 3 );
insert into "ticket" (ID, USER_ID, SESSION_ID)
VALUES (4, 4, 4 );