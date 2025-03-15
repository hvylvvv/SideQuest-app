create table users (
    userID int primary key,
    firstname varchar(255),
    lastname varchar(255),
    password_hash varchar(255),
    nationality varchar(255),
    email varchar(255) unique,
    phone_number varchar(255) unique
);