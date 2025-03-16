create table app_user (
    id SERIAL primary key,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    password_hash varchar(255) not null,
    nationality varchar(255),
    email varchar(255) unique not null
);

