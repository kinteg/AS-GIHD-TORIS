create sequence hibernate_sequence start 1 increment 1;

create table if not exists source
(
    id                int8 not null,
    name              text,
    long_name         text,
    short_name        text,
    description       text,
    add_description   text,
    scope             text,
    periodicity       text,
    renewal_period    text,
    type              text,
    tags              text,
    provider_link     text,
    data_source       text,
    archive           boolean,
    date_creation     timestamp,
    date_deactivation timestamp,
    date_activation   timestamp,
    last_update       timestamp,
    primary key (id),
    unique (short_name)
);

create table if not exists pattern
(
    id                 int8 not null,
    description        text,
    direction          text,
    file_count         integer,
    archive_file_count integer,
    management         text,
    archive            boolean,
    name               text,
    source_id          int8 not null,
    date_creation      timestamp,
    date_deactivation  timestamp,
    date_activation    timestamp,
    last_update        timestamp,
    primary key (id),
    foreign key (source_id) references source (id)
        on delete cascade
        on update cascade
);

create table pattern_table
(
    id                int8 not null,
    name_file         text,
    name_table        text,
    source_id         int8 not null,
    pattern_id        int8 not null,
    date_creation     timestamp,
    date_deactivation timestamp,
    date_activation   timestamp,
    last_update       timestamp,
    archive           boolean,
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
    name         text,
    error        text not null,
    error_status text not null,
    primary key (id),
    unique (error)
);

create table statuses
(
    id     int8 not null,
    name   text,
    status text not null,
    primary key (id),
    unique (status)
);

create table objects
(
    id     int8 not null,
    name   text,
    object text not null,
    primary key (id),
    unique (object)
);

create table actions
(
    id     int8 not null,
    name   text,
    action text not null,
    primary key (id),
    unique (action)
);

create table source_logger
(
    id            int8 not null,
    action_id     int8 not null,
    status_id     int8 not null,
    error_id      int8 not null,
    source_id     int8 not null,
    date_creation timestamp,
    primary key (id),
    foreign key (action_id) references actions (id)
        on delete cascade
        on update cascade,
    foreign key (status_id) references statuses (id)
        on delete cascade
        on update cascade,
    foreign key (error_id) references errors (id)
        on delete cascade
        on update cascade
);

create table before_after_source
(
    id        int8 not null,
    source_logger_id int8 not null,
    before    text,
    after     text,
    primary key (id),
    foreign key (source_logger_id) references source_logger (id)
        on delete cascade
        on update cascade
);

create table pattern_table_logger
(
    id            int8 not null,
    action_id     int8 not null,
    status_id     int8 not null,
    error_id      int8 not null,
    pattern_id     int8 not null,
    date_creation timestamp,
    primary key (id),
    foreign key (action_id) references actions (id)
        on delete cascade
        on update cascade,
    foreign key (status_id) references statuses (id)
        on delete cascade
        on update cascade,
    foreign key (error_id) references errors (id)
        on delete cascade
        on update cascade
);

create table before_after_pattern_table
(
    id        int8 not null,
    pattern_table_logger_id int8 not null,
    before    text,
    after     text,
    primary key (id),
    foreign key (pattern_table_logger_id) references pattern_table_logger (id)
        on delete cascade
        on update cascade
);
