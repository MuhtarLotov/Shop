create table user
(
    id          integer primary key autoincrement not null,
    login       varchar(225) UNIQUE not null,
    password    varchar(225)        not null
);

create table role
(
    role        varchar(225) UNIQUE not null
);

create table user_roles
(
    user_id     int not null,
    role        int not null,

    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (role) REFERENCES role (role)
);

create table product
(
    id          integer primary key autoincrement not null,
    product_name varchar(225) UNIQUE not null
);

create table ads
(
    id          integer primary key autoincrement not null,
    user_id     int not null,
    product_id  int not null,
    count       int not null,
    price       double not null,
    isActive    boolean,

    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
);

create table buy_and_sell
(
    id           integer primary key autoincrement not null,
    buy_user_id  int not null,
    sell_user_id int not null,
    ads_id      int not null,
    count       int not null,
    price       double not null,
    isActive    boolean,

    FOREIGN KEY (buy_user_id) REFERENCES user (id),
    FOREIGN KEY (sell_user_id) REFERENCES user (id),
    FOREIGN KEY (ads_id) REFERENCES ads (id)
);

