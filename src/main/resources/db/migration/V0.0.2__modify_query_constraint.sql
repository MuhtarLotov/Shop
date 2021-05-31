create table query_backup
(
    id       integer primary key autoincrement not null,
    name     varchar(512)                      not null,
    sql      varchar(512)                      not null,
    data_id  int                               not null,
    pageable boolean                           not null default false,
    FOREIGN KEY (data_id) REFERENCES data (id)
);

create unique index unique_query_backup_1 on query_backup (name, data_id);

insert into query_backup (name, sql, data_id, pageable) select name, sql, data_id, pageable from query;

drop table query;

alter table query_backup rename to query;
