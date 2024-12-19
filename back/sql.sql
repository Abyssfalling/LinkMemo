create table link
(
    link_center   int(10)      not null comment '主单词的id'
        primary key,
    link_looklike varchar(255) null,
    link_meanlike varchar(255) null,
    link_relate   varchar(255) null
)
    charset = latin1;

create table association
(
    id   int auto_increment comment '关联id'
        primary key,
    uid  int null comment '主单词id',
    lid  int null comment '关联单词id',
    type int null,
    constraint association___link
        foreign key (lid) references link (link_center)
)
    charset = latin1
    row_format = DYNAMIC;

create table node_chinese
(
    id      int unsigned auto_increment comment 'ID'
        primary key,
    Chinese varchar(255) charset utf8 null
)
    charset = latin1;

create table node_english
(
    English_id int unsigned auto_increment
        primary key,
    English    varchar(100) not null
)
    charset = latin1;

create table point
(
    point_id      int auto_increment
        primary key,
    point_english varchar(255) charset latin1 null,
    point_chinese varchar(255)                null
);

create table user
(
    user_id       int unsigned auto_increment comment 'ID'
        primary key,
    user_name     varchar(20)                  not null comment '用户名',
    user_password varchar(32) default '123456' null comment '密码',
    user_nickname varchar(255)                 null
);

