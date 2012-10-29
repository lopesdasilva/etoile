# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table blog (
  id                        bigint auto_increment not null,
  header                    varchar(255),
  alternate_header          varchar(255),
  text                      TEXT,
  alternate_text            TEXT,
  article_image_url         varchar(255),
  constraint pk_blog primary key (id))
;

create table category (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  description               varchar(255),
  constraint pk_category primary key (id))
;

create table comment (
  id                        bigint auto_increment not null,
  text                      varchar(255),
  blog_id                   bigint,
  user_email                varchar(255),
  constraint pk_comment primary key (id))
;

create table content (
  id                        bigint auto_increment not null,
  title                     varchar(255),
  text                      TEXT,
  course_id                 bigint,
  constraint pk_content primary key (id))
;

create table course (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  description               varchar(255),
  course_image_url          varchar(255),
  constraint pk_course primary key (id))
;

create table forum (
  id                        bigint auto_increment not null,
  description               varchar(255),
  constraint pk_forum primary key (id))
;

create table module (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  text                      TEXT,
  alternate_text            TEXT,
  module_image_url          varchar(255),
  constraint pk_module primary key (id))
;

create table open_question (
  id                        bigint auto_increment not null,
  question                  varchar(255),
  question_image_url        varchar(255),
  constraint pk_open_question primary key (id))
;

create table reply (
  id                        bigint auto_increment not null,
  text                      varchar(255),
  topic_id                  bigint,
  user_email                varchar(255),
  constraint pk_reply primary key (id))
;

create table test (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  text                      TEXT,
  test_image_url            varchar(255),
  constraint pk_test primary key (id))
;

create table topic (
  id                        bigint auto_increment not null,
  text                      varchar(255),
  forum_id                  bigint,
  constraint pk_topic primary key (id))
;

create table account (
  email                     varchar(255) not null,
  id                        bigint,
  name                      varchar(255),
  password                  varchar(255),
  account_type              varchar(255),
  constraint pk_account primary key (email))
;


create table category_course (
  category_id                    bigint not null,
  course_id                      bigint not null,
  constraint pk_category_course primary key (category_id, course_id))
;

create table course_module (
  course_id                      bigint not null,
  module_id                      bigint not null,
  constraint pk_course_module primary key (course_id, module_id))
;

create table module_test (
  module_id                      bigint not null,
  test_id                        bigint not null,
  constraint pk_module_test primary key (module_id, test_id))
;

create table test_open_question (
  test_id                        bigint not null,
  open_question_id               bigint not null,
  constraint pk_test_open_question primary key (test_id, open_question_id))
;

create table account_course (
  account_email                  varchar(255) not null,
  course_id                      bigint not null,
  constraint pk_account_course primary key (account_email, course_id))
;
alter table comment add constraint fk_comment_blog_1 foreign key (blog_id) references blog (id) on delete restrict on update restrict;
create index ix_comment_blog_1 on comment (blog_id);
alter table comment add constraint fk_comment_user_2 foreign key (user_email) references account (email) on delete restrict on update restrict;
create index ix_comment_user_2 on comment (user_email);
alter table content add constraint fk_content_course_3 foreign key (course_id) references course (id) on delete restrict on update restrict;
create index ix_content_course_3 on content (course_id);
alter table reply add constraint fk_reply_topic_4 foreign key (topic_id) references topic (id) on delete restrict on update restrict;
create index ix_reply_topic_4 on reply (topic_id);
alter table reply add constraint fk_reply_user_5 foreign key (user_email) references account (email) on delete restrict on update restrict;
create index ix_reply_user_5 on reply (user_email);
alter table topic add constraint fk_topic_forum_6 foreign key (forum_id) references forum (id) on delete restrict on update restrict;
create index ix_topic_forum_6 on topic (forum_id);



alter table category_course add constraint fk_category_course_category_01 foreign key (category_id) references category (id) on delete restrict on update restrict;

alter table category_course add constraint fk_category_course_course_02 foreign key (course_id) references course (id) on delete restrict on update restrict;

alter table course_module add constraint fk_course_module_course_01 foreign key (course_id) references course (id) on delete restrict on update restrict;

alter table course_module add constraint fk_course_module_module_02 foreign key (module_id) references module (id) on delete restrict on update restrict;

alter table module_test add constraint fk_module_test_module_01 foreign key (module_id) references module (id) on delete restrict on update restrict;

alter table module_test add constraint fk_module_test_test_02 foreign key (test_id) references test (id) on delete restrict on update restrict;

alter table test_open_question add constraint fk_test_open_question_test_01 foreign key (test_id) references test (id) on delete restrict on update restrict;

alter table test_open_question add constraint fk_test_open_question_open_qu_02 foreign key (open_question_id) references open_question (id) on delete restrict on update restrict;

alter table account_course add constraint fk_account_course_account_01 foreign key (account_email) references account (email) on delete restrict on update restrict;

alter table account_course add constraint fk_account_course_course_02 foreign key (course_id) references course (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table blog;

drop table category;

drop table category_course;

drop table comment;

drop table content;

drop table course;

drop table course_module;

drop table forum;

drop table module;

drop table module_test;

drop table open_question;

drop table reply;

drop table test;

drop table test_open_question;

drop table topic;

drop table account;

drop table account_course;

SET FOREIGN_KEY_CHECKS=1;

