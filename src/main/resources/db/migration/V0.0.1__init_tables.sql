insert into user (id, login, password)
values (1, 'admin', 'admin');
insert into role (role)
values ('ADMIN');
insert into user_roles (user_id, role)
values (1, 'ADMIN');

INSERT INTO proxy (id, service, url)
VALUES (1, '/wiki-proxy-service', 'https://en.wikipedia.org');
INSERT INTO proxy_roles (proxy_id, role)
VALUES (1, 'ADMIN');
