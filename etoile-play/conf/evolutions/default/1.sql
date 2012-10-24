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
  user_email                varchar(255),
  constraint pk_course primary key (id))
;

create table account (
  email                     varchar(255) not null,
  id                        bigint,
  name                      varchar(255),
  password                  varchar(255),
  constraint pk_account primary key (email))
;

alter table comment add constraint fk_comment_blog_1 foreign key (blog_id) references blog (id) on delete restrict on update restrict;
create index ix_comment_blog_1 on comment (blog_id);
alter table course add constraint fk_course_user_2 foreign key (user_email) references account (email) on delete restrict on update restrict;
create index ix_course_user_2 on course (user_email);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table blog;

drop table comment;

drop table course;

drop table account;

SET FOREIGN_KEY_CHECKS=1;

