create table trip (
    id uuid primary key DEFAULT uuid_generate_v4(),
    start_location_id bigint,
    start_location_name varchar(255) not null,
    end_location_id bigint not null,
    end_location_name varchar(255) not null,
    completed boolean not null default false,
    app_user_id bigint not null,
    foreign key (app_user_id) references app_user(id) on delete cascade,
    foreign key (start_location_id) references location(id) on delete cascade,
    foreign key (end_location_id) references location(id) on delete cascade
);