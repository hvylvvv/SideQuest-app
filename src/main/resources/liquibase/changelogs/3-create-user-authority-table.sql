create table user_authority (
    id int primary key,
    user_id int,
    authority_id int,
    foreign key (user_id) references app_user(id) on delete cascade,
    foreign key (authority_id) references authority(id) on delete cascade
);