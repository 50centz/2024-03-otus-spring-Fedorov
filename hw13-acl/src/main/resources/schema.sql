drop table if exists comments;
drop table if exists books;
drop table if exists authors;
drop table if exists genres;
drop table if exists users;


create table if not exists authors (
    id bigserial,
    full_name varchar(255),
    primary key (id)
);

create table if not exists genres (
    id bigserial,
    name varchar(255),
    primary key (id)
);

create table if not exists books (
    id bigserial,
    title varchar(255),
    author_id bigint references authors (id) on delete cascade,
    genre_id bigint references genres(id) on delete cascade,
    primary key (id)
);


create table if not exists comments (
    id bigserial,
    comment varchar(255),
    book_id bigint references books (id),
    primary key (id)
);

create table if not exists users (
    id bigserial,
    username varchar(255) not null,
    password varchar(255) not null,
    authority varchar(255) not null,
    primary key (id)
);


-- ACL Schema SQL for PostgreSQL

drop table if exists acl_entry;
drop table if exists acl_object_identity;
drop table if exists acl_class;
drop table if exists acl_sid;

create table if not exists acl_sid(
                        id integer  not null PRIMARY KEY auto_increment,
                        principal boolean not null,
                        sid varchar(100) not null,
                        constraint unique_uk_1 unique(sid,principal)
);

create table if not exists acl_class(
                          id integer  not null PRIMARY KEY auto_increment,
                          class varchar(100) not null,
                          class_id_type varchar(100),
                          constraint unique_uk_2 unique(class)
);

create table if not exists acl_object_identity(
                                    id integer  PRIMARY KEY auto_increment,
                                    object_id_class bigint not null,
                                    object_id_identity varchar(36) not null,
                                    parent_object bigint,
                                    owner_sid bigint,
                                    entries_inheriting boolean not null,
                                    constraint unique_uk_3 unique(object_id_class,object_id_identity),
                                    constraint foreign_fk_1 foreign key(parent_object)references acl_object_identity(id),
                                    constraint foreign_fk_2 foreign key(object_id_class)references acl_class(id),
                                    constraint foreign_fk_3 foreign key(owner_sid)references acl_sid(id)
);

create table if not exists acl_entry(
                          id integer  PRIMARY KEY auto_increment,
                          acl_object_identity bigint not null,
                          ace_order int not null,
                          sid bigint not null,
                          mask integer not null,
                          granting boolean not null,
                          audit_success boolean not null,
                          audit_failure boolean not null,
                          constraint unique_uk_4 unique(acl_object_identity,ace_order),
                          constraint foreign_fk_4 foreign key(acl_object_identity) references acl_object_identity(id),
                          constraint foreign_fk_5 foreign key(sid) references acl_sid(id)
);
