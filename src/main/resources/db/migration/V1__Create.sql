create table tsubuyaki (
    id serial primary key,
    message varchar(100) not null,
    version integer not null default 1,
    created_time timestamp not null default current_timestamp
);