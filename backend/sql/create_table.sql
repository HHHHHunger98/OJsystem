-- create a new database
create database if not exists ojsystem_db;

-- switch to my database
use ojsystem_db;

-- create user table 
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment 'account name',
    userPassword varchar(512)                           not null comment 'password',
    unionId      varchar(256)                           null comment 'WeChat open platform id',
    mpOpenId     varchar(256)                           null comment 'WeChat Official Account openId',
    userName     varchar(256)                           null comment 'user name',
    userAvatar   varchar(1024)                          null comment 'user avatar',
    userProfile  varchar(512)                           null comment 'user profile',
    userRole     varchar(256) default 'user'            not null comment 'user role type: user/admin/ban',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment 'time created',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment 'update time',
    isDelete     tinyint      default 0                 not null comment 'is deleted',
    index idx_unionId (unionId)
) comment 'user' collate = utf8mb4_unicode_ci;

-- problem table
create table if not exists problem
(
    id              bigint auto_increment comment 'id' primary key,
    title           varchar(512)                       null comment 'title',
    content         text                               null comment 'content',
    tags            varchar(1024)                      null comment 'tags array(json array)',
    solution        text                               null comment 'solution to the problem',
    submitNum       int      default 0                 not null comment 'total submit times',
    acceptNum       int      default 0                 not null comment 'total accepted times',
    judgeCase       text                               null comment 'input/output cases(json array)',
    judgeConfig     text                               null comment 'configuration for judging(json object)',
    thumbNum        int      default 0                 not null comment 'number of likes',
    favourNum       int      default 0                 not null comment 'number of add to favorite',
    userId          bigint                             not null comment 'created user id',
    createTime      datetime default CURRENT_TIMESTAMP not null comment 'created at',
    updateTime      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment 'time updated',
    isDelete        tinyint  default 0                 not null comment 'is deleted',
    index idx_userId (userId)
) comment 'problem' collate = utf8mb4_unicode_ci;

-- submission table
create table if not exists problem_submit 
(
    id         bigint auto_increment comment 'id' primary key,
    problemId  bigint                             not null comment 'problem id',
    userId     bigint                             not null comment 'user id',
    language   varchar(128)                       not null comment 'solution language',
    code       text                               not null comment 'user solution',
    judgeInfo  text                               null comment 'information for judging(json object)',
    status     int      default 0                 not null comment  'judging status(0-pending, 1-judging, 2-accepted, 3-failed)',
    createTime datetime default CURRENT_TIMESTAMP not null comment 'created at',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment 'time updated',
    isDelete   tinyint  default 0                 not null comment 'is deleted',
    index idx_problemId (problemId),
    index idx_userId (userId)
) comment 'problem submission';


-- post table
create table if not exists post
(
    id         bigint auto_increment comment 'id' primary key,
    title      varchar(512)                       null comment 'title',
    content    text                               null comment 'content',
    tags       varchar(1024)                      null comment 'tags array(json array)',
    thumbNum   int      default 0                 not null comment 'number of likes',
    favourNum  int      default 0                 not null comment 'number of add to favorite',
    userId     bigint                             not null comment 'created user id',
    createTime datetime default CURRENT_TIMESTAMP not null comment 'created at',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment 'time updated',
    isDelete   tinyint  default 0                 not null comment 'is deleted',
    index idx_userId (userId)
) comment 'post ' collate = utf8mb4_unicode_ci;

-- post likes table
create table if not exists post_thumb
(
    id         bigint auto_increment comment 'id' primary key,
    postId     bigint                             not null comment 'post id',
    userId     bigint                             not null comment 'user id',
    createTime datetime default CURRENT_TIMESTAMP not null comment 'created at',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment 'time updated',
    index idx_postId (postId),
    index idx_userId (userId)
) comment 'post likes';

-- post favorite table
create table if not exists post_favour
(
    id         bigint auto_increment comment 'id' primary key,
    postId     bigint                             not null comment 'post id',
    userId     bigint                             not null comment 'user id',
    createTime datetime default CURRENT_TIMESTAMP not null comment 'created at',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment 'time updated',
    index idx_postId (postId),
    index idx_userId (userId)
) comment 'post add to favorite';
