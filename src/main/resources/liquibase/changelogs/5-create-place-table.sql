create table place (
    id varchar(255) primary key,
    name varchar(255) not null,
    rating float not null,
    tot_visits bigint not null
);