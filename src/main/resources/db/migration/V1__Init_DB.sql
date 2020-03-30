create sequence hibernate_sequence start 1 increment 1;

create table if not exists source (
    id int8 not null,
    name varchar(255),
    long_name varchar(255),
    short_name varchar(255),
    description varchar(500),
    add_description varchar(255),
    scope varchar(255),
    periodicity varchar(255),
    renewal_period varchar(255),
    type varchar(255),
    tags varchar (255),
    provider_link varchar(255),
    data_source varchar(255),
    archive boolean,
    date_creation timestamp,
    date_deactivation timestamp,
    date_activation timestamp,
    last_update timestamp,
    primary key (id),
    unique (short_name)
);

create table if not exists pattern (
    id int8 not null,
    description varchar(255),
    direction varchar(255),
    file_count integer,
    management varchar(255),
    is_archive boolean,
    name varchar(255),
    source_id int8 not null,
    date_creation timestamp,
    date_deactivation timestamp,
    date_activation timestamp,
    last_update timestamp,
    primary key (id),
    foreign key (source_id) references source (id)
    on delete cascade
    on update cascade
);

create table pattern_table (
    id int8 not null,
    name_file varchar(255),
    name_table varchar(255),
    pattern_id int8 not null,
    primary key (id),
    foreign key (pattern_id) references pattern (id)
    on delete cascade
    on update cascade
);