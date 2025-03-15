create table app_user (
    id int primary key,
    first_name varchar(255),
    last_name varchar(255),
    password_hash varchar(255),
    nationality varchar(255),
    email varchar(255) unique,
    phone_number varchar(255) unique,
    provider varchar(255)
);