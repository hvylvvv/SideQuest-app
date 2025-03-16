create table stop (
    id uuid primary key DEFAULT uuid_generate_v4(),
    place_id varchar(255) not null,
    trip_id uuid not null,
    deleted boolean not null default false,
    completed boolean not null default false,
    foreign key (trip_id) references trip(id) on delete cascade,
    foreign key (place_id) references place(id) on delete cascade
);