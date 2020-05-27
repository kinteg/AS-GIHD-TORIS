create sequence hibernate_sequence start 1 increment 1;

create table user_role
(
    user_id int8 not null,
    roles   varchar(255)
);

create table usr
(
    id         int8 not null,
    fio        text not null,
    secret_key text not null,
    primary key (id)
);

create table if not exists source
(
    id                int8      not null,
    name              text      not null default '-',
    long_name         text      not null default '-',
    short_name        text      not null,
    description       text      not null default '-',
    add_description   text      not null default '-',
    scope             text      not null default '-',
    periodicity       text      not null default '-',
    renewal_period    text      not null default '-',
    type              text      not null default '-',
    tags              text      not null default '-',
    provider_link     text      not null default '-',
    data_source       text      not null default '-',
    archive           boolean   not null default false,
    date_creation     timestamp not null default current_timestamp,
    date_deactivation timestamp,
    date_activation   timestamp not null default current_timestamp,
    last_update       timestamp not null default current_timestamp,
    primary key (id),
    unique (short_name)
);

create table if not exists pattern
(
    id                 int8      not null,
    description        text      not null default '-',
    direction          text      not null default '-',
    file_count         integer   not null default 0,
    archive_file_count integer   not null default 0,
    management         text      not null,
    archive            boolean   not null default false,
    name               text      not null default '-',
    source_id          int8      not null,
    date_creation      timestamp not null default current_timestamp,
    date_deactivation  timestamp,
    date_activation    timestamp not null default current_timestamp,
    last_update        timestamp not null default current_timestamp,
    primary key (id),
    foreign key (source_id) references source (id)
        on delete cascade
        on update cascade
);

create table pattern_table
(
    id                int8      not null,
    name_file         text      not null,
    name_table        text      not null,
    source_id         int8      not null,
    pattern_id        int8      not null,
    date_creation     timestamp not null default current_timestamp,
    date_deactivation timestamp,
    date_activation   timestamp not null default current_timestamp,
    last_update       timestamp not null default current_timestamp,
    version           int8      not null,
    active            boolean   not null default true,
    date_kill         timestamp,
    old_name          text,
    archive           boolean   not null default false,
    primary key (id),
    foreign key (pattern_id) references pattern (id)
        on delete cascade
        on update cascade,
    foreign key (source_id) references source (id)
        on delete cascade
        on update cascade
);



create table errors
(
    id           int8 not null,
    name         text not null,
    error        text not null,
    error_status text not null,
    primary key (id),
    unique (error)
);

create table statuses
(
    id     int8 not null,
    name   text not null,
    status text not null,
    primary key (id),
    unique (status)
);

create table actions
(
    id     int8 not null,
    name   text not null,
    action text not null,
    primary key (id),
    unique (action)
);

create table source_logger
(
    id            int8      not null,
    action_id     int8      not null,
    status_id     int8      not null,
    error_id      int8      not null,
    source_id     int8      not null,
    date_creation timestamp not null default current_timestamp,
    usr_id        int8,
    primary key (id),
    foreign key (action_id) references actions (id)
        on delete cascade
        on update cascade,
    foreign key (status_id) references statuses (id)
        on delete cascade
        on update cascade,
    foreign key (error_id) references errors (id)
        on delete cascade
        on update cascade,
    foreign key (usr_id) references usr (id)
);

create table before_after_source
(
    id               int8 not null,
    source_logger_id int8 not null,
    column_name      text not null,
    before           text not null,
    after            text not null,
    primary key (id),
    foreign key (source_logger_id) references source_logger (id)
        on delete cascade
        on update cascade
);

create table pattern_logger
(
    id            int8      not null,
    action_id     int8      not null,
    status_id     int8      not null,
    error_id      int8      not null,
    pattern_id    int8      not null,
    date_creation timestamp not null default current_timestamp,
    usr_id        int8,
    primary key (id),
    foreign key (action_id) references actions (id)
        on delete cascade
        on update cascade,
    foreign key (status_id) references statuses (id)
        on delete cascade
        on update cascade,
    foreign key (error_id) references errors (id)
        on delete cascade
        on update cascade,
    foreign key (usr_id) references usr (id)
);

create table before_after_pattern
(
    id                int8 not null,
    pattern_logger_id int8 not null,
    column_name       text not null,
    before            text not null,
    after             text not null,
    primary key (id),
    foreign key (pattern_logger_id) references pattern_logger (id)
        on delete cascade
        on update cascade
);

create table pattern_table_logger
(
    id               int8      not null,
    action_id        int8      not null,
    status_id        int8      not null,
    error_id         int8      not null,
    pattern_table_id int8      not null,
    date_creation    timestamp not null default current_timestamp,
    usr_id           int8,
    primary key (id),
    foreign key (action_id) references actions (id)
        on delete cascade
        on update cascade,
    foreign key (status_id) references statuses (id)
        on delete cascade
        on update cascade,
    foreign key (error_id) references errors (id)
        on delete cascade
        on update cascade,
    foreign key (usr_id) references usr (id)
);

create table before_after_pattern_table
(
    id                      int8 not null,
    pattern_table_logger_id int8 not null,
    column_name             text not null,
    before                  text not null,
    after                   text not null,
    primary key (id),
    foreign key (pattern_table_logger_id) references pattern_table_logger (id)
        on delete cascade
        on update cascade
);

create table pattern_file
(
    id            int8      not null,
    pattern_id    int8      not null,
    file          text      not null,
    date_creation timestamp not null default current_timestamp,
    primary key (id),
    foreign key (pattern_id) references pattern (id)
        on delete cascade
        on update cascade
);

create table pattern_table_file
(
    id               int8      not null,
    pattern_table_id int8      not null,
    file             text      not null,
    date_creation    timestamp not null default current_timestamp,
    primary key (id),
    foreign key (pattern_table_id) references pattern_table (id)
        on delete cascade
        on update cascade
);

create table source_set
(
    id        int8 not null,
    usr_id    int8 not null,
    source_id int8 not null,
    primary key (id),
    foreign key (usr_id) references usr (id)
        on delete cascade
        on update cascade,
    foreign key (source_id) references source (id)
        on delete cascade
        on update cascade
);

alter table if exists user_role
    add constraint user_role_user_fk
        foreign key (user_id) references usr;