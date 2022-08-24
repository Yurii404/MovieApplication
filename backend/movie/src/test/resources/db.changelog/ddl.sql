--liquibase formatted sql
--changeset yurii:1

DROP TABLE IF EXISTS "user" CASCADE;
DROP TABLE IF EXISTS "movie_room" CASCADE;
DROP TABLE IF EXISTS "ticket" CASCADE;
DROP TABLE IF EXISTS "movie_session" CASCADE;
DROP SEQUENCE IF EXISTS `TICKET_SEQUENCE`;
DROP SEQUENCE IF EXISTS `USER_SEQUENCE`;
DROP SEQUENCE IF EXISTS `MOVIE_ROOM_SEQUENCE`;
DROP SEQUENCE IF EXISTS `MOVIE_SESSION_SEQUENCE`;


CREATE SEQUENCE `TICKET_SEQUENCE` START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE `MOVIE_ROOM_SEQUENCE` START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE `USER_SEQUENCE` START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE `MOVIE_SESSION_SEQUENCE` START WITH 1 INCREMENT BY 1;

CREATE TABLE "user"
(
    id               INT,
    user_username    VARCHAR(50)  NOT NULL,
    user_email       VARCHAR(100) NOT NULL UNIQUE,
    user_firstname   VARCHAR(50)  NOT NULL,
    user_second_name VARCHAR(50)  NOT NULL,
    user_phone       VARCHAR(50)  NOT NULL,

    CONSTRAINT PK_USER_ID PRIMARY KEY (id)
);


CREATE TABLE "movie_room"
(
    id           INT,
    room_name    VARCHAR(50) UNIQUE NOT NULL,
    room_number  INT UNIQUE         NOT NULL,
    num_of_seats INT                NOT NULL,

    CONSTRAINT PK_MOVIE_ROOM_ID PRIMARY KEY (id)
);

CREATE TABLE "ticket"
(
    id         INT,
    user_id    INT NOT NULL,
    session_id INT NOT NULL,

    CONSTRAINT PK_TICKET_ID PRIMARY KEY (id)
);

CREATE TABLE "movie_session"
(
    id                INT,
    movie_name        VARCHAR(100) NOT NULL,
    movie_room_id     INT          NOT NULL,
    price             INT          NOT NULL,
    date_time         TIMESTAMP    NOT NULL,
    remaining_tickets INT          NOT NULL,

    CONSTRAINT PK_MOVIE_SESSION_ID PRIMARY KEY (id)
);