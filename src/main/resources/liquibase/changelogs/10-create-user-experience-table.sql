create table user_experience (
    id SERIAL primary key,
    app_user_id bigint not null,
    stop_id UUID not null,
    points int not null,
    deleted boolean not null default false,
    foreign key (app_user_id) references app_user(id) on delete cascade,
    foreign key (stop_id) references stop(id) on delete cascade
);