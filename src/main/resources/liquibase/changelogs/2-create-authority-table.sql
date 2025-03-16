create table authority (
    id SERIAL primary key,
    name varchar(255) unique not null,
    provider varchar(255) not null
);