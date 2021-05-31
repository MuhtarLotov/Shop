create table user
(
    id       integer primary key autoincrement not null,
    login    varchar(225) UNIQUE not null,
    password varchar(225)        not null
);

create table role
(
    role varchar(225) UNIQUE not null
);

create table user_roles
(
    user_id int not null,
    role    int not null,

    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (role) REFERENCES role (role)
);

create table proxy
(
    id      integer primary key autoincrement not null,
    service varchar(255) UNIQUE not null,
    url     varchar(255)        not null
);

create table proxy_roles
(
    proxy_id int not null,
    role     int not null,

    FOREIGN KEY (proxy_id) REFERENCES proxy (id),
    FOREIGN KEY (role) REFERENCES role (role)
);

create table database_configuration
(
    id                integer primary key autoincrement not null,
    connection_string varchar(512) not null,
    username          varchar(512) not null,
    password          varchar(512) not null
);

create table data
(
    id                        integer primary key autoincrement not null,
    service                   varchar(255) UNIQUE not null,
    database_configuration_id int                 not null REFERENCES database_configuration (id),
    pageable                  boolean             not null default false,
    FOREIGN KEY (database_configuration_id) REFERENCES database_configuration (id)
);

create table data_roles
(
    data_id int not null,
    role    int not null,

    FOREIGN KEY (data_id) REFERENCES data (id),
    FOREIGN KEY (role) REFERENCES role (role)
);

create table query
(
    id      integer primary key autoincrement not null,
    name    varchar(512) UNIQUE not null,
    sql     varchar(512)        not null,
    data_id int                 not null,
    FOREIGN KEY (data_id) REFERENCES data (id)
);
