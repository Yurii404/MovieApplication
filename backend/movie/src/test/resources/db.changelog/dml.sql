--liquibase formatted sql
--changeset yurii:2

INSERT INTO "user" (id, user_username, user_firstname, user_second_name, user_email, user_phone)
VALUES (2, 'Test1', 'Test1', 'Test1', 'Test1@gmail.com', 3809123913);
INSERT INTO "user" (id, user_username, user_firstname, user_second_name, user_email, user_phone)
VALUES (3, 'Test2', 'Test2', 'Test2', 'Test2@gmail.com', 3809123913);
INSERT INTO "user" (id, user_username, user_firstname, user_second_name, user_email, user_phone)
VALUES (4, 'Test3', 'Test3', 'Test3', 'Test3@gmail.com', 3809123913);

INSERT INTO "movie_room" (id, room_name, room_number, num_of_seats)
VALUES (2, 'First', 2, 20);
INSERT INTO "movie_room" (id, room_name, room_number, num_of_seats)
VALUES (3, 'Second', 3, 20);
INSERT INTO "movie_room" (id, room_name, room_number, num_of_seats)
VALUES (4, 'Third', 4, 20);

INSERT INTO "movie_session" (id, movie_name, movie_room_id, price, date_time, remaining_tickets)
VALUES (2, 'Test1', 2, 500, '2025-01-01 00:00:01', 20);
INSERT INTO "movie_session" (id, movie_name, movie_room_id, price, date_time, remaining_tickets)
VALUES (3, 'Test2', 3, 500, now(), 20);
INSERT INTO "movie_session" (id, movie_name, movie_room_id, price, date_time, remaining_tickets)
VALUES (4, 'Test3', 4, 500, now(), 20);

INSERT INTO "ticket" (id, user_id, session_id)
VALUES ( 2, 2, 2 );
INSERT INTO "ticket" (id, user_id, session_id)
VALUES ( 3, 2, 2 );
INSERT INTO "ticket" (id, user_id, session_id)
VALUES ( 4, 3, 4 );
INSERT INTO "ticket" (id, user_id, session_id)
VALUES ( 5, 2, 2 );
INSERT INTO "ticket" (id, user_id, session_id)
VALUES ( 6, 4, 2 );