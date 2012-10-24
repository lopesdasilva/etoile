# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table blog (
  id                        bigint auto_increment not null,
  header                    varchar(255),
  text                      TEXT,
  constraint pk_blog primary key (id))
;

create table comment (
  id                        bigint auto_increment not null,
  text                      varchar(255),
  blog_id                   bigint,
  constraint pk_comment primary key (id))
;

create table course (
  id                        bigint auto_increment not null,
  text                      varchar(255),
  constraint pk_course primary key (id))
;

create table account (
  email                     varchar(255) not null,
  id                        bigint,
  name                      varchar(255),
  password                  varchar(255),
  constraint pk_account primary key (email))
;


create table account_course (
  account_email                  varchar(255) not null,
  course_id                      bigint not null,
  constraint pk_account_course primary key (account_email, course_id))
;
alter table comment add constraint fk_comment_blog_1 foreign key (blog_id) references blog (id) on delete restrict on update restrict;
create index ix_comment_blog_1 on comment (blog_id);



alter table account_course add constraint fk_account_course_account_01 foreign key (account_email) references account (email) on delete restrict on update restrict;

alter table account_course add constraint fk_account_course_course_02 foreign key (course_id) references course (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table blog;

drop table comment;

drop table course;

drop table account;

drop table account_course;

SET FOREIGN_KEY_CHECKS=1;

