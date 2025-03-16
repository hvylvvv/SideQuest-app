create table user_interest (
    id serial primary key,
    app_user_id bigint not null,
    interest_id bigint not null,
    active boolean not null,
    unique (app_user_id, interest_id),
    foreign key (app_user_id) references app_user(id) on delete cascade,
    foreign key (interest_id) references interest(id) on delete cascade
);