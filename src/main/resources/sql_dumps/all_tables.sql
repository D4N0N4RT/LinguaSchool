create table spring_session
(
    primary_id            char(36) not null
        constraint spring_session_pk
            primary key,
    session_id            char(36) not null,
    creation_time         bigint   not null,
    last_access_time      bigint   not null,
    max_inactive_interval integer  not null,
    expiry_time           bigint   not null,
    principal_name        varchar(100)
);

alter table spring_session
    owner to "user";

create unique index spring_session_ix1
    on spring_session (session_id);

create index spring_session_ix2
    on spring_session (expiry_time);

create index spring_session_ix3
    on spring_session (principal_name);

create table spring_session_attributes
(
    session_primary_id char(36)     not null
        constraint spring_session_attributes_fk
            references spring_session
            on delete cascade,
    attribute_name     varchar(200) not null,
    attribute_bytes    bytea        not null,
    constraint spring_session_attributes_pk
        primary key (session_primary_id, attribute_name)
);

alter table spring_session_attributes
    owner to "user";

create table users
(
    id         bigserial
        constraint users_pk
            primary key,
    name       varchar(30)  not null,
    surname    varchar(40)  not null,
    email      varchar(100) not null,
    password   varchar(250) not null,
    teacher_id bigint,
    is_active  boolean,
    role       varchar(50)
);

alter table users
    owner to "user";

create unique index users_email_uindex
    on users (email);

create table teachers
(
    id              bigserial
        constraint teachers_pk
            primary key,
    name            varchar(30) not null,
    surname         varchar(40) not null,
    patronymic      varchar(40),
    work_experience integer,
    language        varchar(30),
    age             integer
);

alter table teachers
    owner to "user";

create table reviews
(
    id             bigserial
        constraint reviews_pk
            primary key,
    author_id      bigint  not null,
    teacher_id     bigint  not null,
    text           text    not null,
    is_recommended boolean not null
);

alter table reviews
    owner to "user";

create table user_role
(
    user_id bigint not null,
    role    varchar(50)
);

alter table user_role
    owner to "user";
