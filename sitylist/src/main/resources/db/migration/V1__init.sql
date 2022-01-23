-- create city table
create table cities
(
    id         uuid not null
        constraint cities_pkey
            primary key,
    name       varchar(40),
    photo_path varchar(800)
);

-- create index 'name'
create
unique index if not exists c_name on cities(name);

-- create users table
create table users
(
    id       uuid         not null
        constraint users_pkey
            primary key,
    email    varchar(40)  not null,
    password varchar(100) not null,
    role     varchar(40)  not null
);


-- creat user index for 'email'
create
unique index if not exists u_name on users(email);

-- insert new users
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

insert into users (id, email, password, role)
values (uuid_generate_v4(), '1@1.com', '$2a$10$pn6rYWViz2K.T5C59LDePuS6aB9dTp/22KFN5kg5Z0XFykL2BuFZu',
        'ROLE_ALLOW_EDIT');

insert into users (id, email, password, role)
values (uuid_generate_v4(), '2@1.com', '$2a$10$pn6rYWViz2K.T5C59LDePuS6aB9dTp/22KFN5kg5Z0XFykL2BuFZu',
        'ROLE_NOT_ALLOW_EDIT');

insert into users (id, email, password, role)
values (uuid_generate_v4(), '3@1.com', '$2a$10$pn6rYWViz2K.T5C59LDePuS6aB9dTp/22KFN5kg5Z0XFykL2BuFZu',
        'ROLE_NOT_ALLOW_EDIT');