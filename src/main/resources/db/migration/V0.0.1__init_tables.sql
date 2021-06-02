insert into user (id, login, password)
values (1, 'admin', 'admin');
insert into role (role)
values ('ADMIN');
insert into role (role)
values ('BUYER');
insert into role (role)
values ('SELLER');
insert into user_roles (user_id, role)
values (1, 'ADMIN');
