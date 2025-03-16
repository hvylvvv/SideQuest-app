create table user_authority (
    id SERIAL primary key,
    app_user_id bigint not null,
    authority_id bigint not null,
    foreign key (app_user_id) references app_user(id) on delete cascade,
    foreign key (authority_id) references authority(id) on delete cascade
);