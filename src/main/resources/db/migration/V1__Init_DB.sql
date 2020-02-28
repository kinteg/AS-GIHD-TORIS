create sequence hibernate_sequence start 1 increment 1;

create table source (
    id int8 not null,
    name varchar(255),
    primary key (id),
    unique (name)
);

create table if not exists pattern (
    id int8 not null,
    date_creation timestamp,
    description varchar(255),
    direction varchar(255),
    file_count varchar(255),
    management varchar(255),
    name varchar(255),
    source_id int8 not null,
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
    foreign key (pattern_id) references source (id)
    on delete cascade
    on update cascade
);

