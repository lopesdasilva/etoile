# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table answer (
  id                        bigint auto_increment not null,
  answer                    TEXT,
  open_question_id          bigint,
  test_id                   bigint,
  user_email                varchar(255),
  constraint pk_answer primary key (id))
;

create table bibliography (
  id                        bigint auto_increment not null,
  title                     varchar(255),
  description               TEXT,
  image_url                 varchar(255),
  link                      varchar(255),
  module_id                 bigint,
  constraint pk_bibliography primary key (id))
;

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
  keyword                   varchar(255),
  name                      varchar(255),
  description               varchar(255),
  constraint uq_category_keyword unique (keyword),
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
  module_id                 bigint,
  constraint pk_content primary key (id))
;

create table continent (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  acronym                   varchar(255),
  image_url                 varchar(255),
  constraint pk_continent primary key (id))
;

create table curriculumlesson (
  id                        bigint auto_increment not null,
  keyword                   varchar(255),
  name                      varchar(255),
  text                      TEXT,
  constraint uq_curriculumlesson_keyword unique (keyword),
  constraint pk_curriculumlesson primary key (id))
;

create table curriculummodule (
  id                        bigint auto_increment not null,
  keyword                   varchar(255),
  name                      varchar(255),
  text                      TEXT,
  constraint uq_curriculummodule_keyword unique (keyword),
  constraint pk_curriculummodule primary key (id))
;

create table curriculumtopic (
  id                        bigint auto_increment not null,
  keyword                   varchar(255),
  text                      TEXT,
  constraint uq_curriculumtopic_keyword unique (keyword),
  constraint pk_curriculumtopic primary key (id))
;

create table forum (
  id                        bigint auto_increment not null,
  description               varchar(255),
  constraint pk_forum primary key (id))
;

create table hypothesis (
  id                        bigint auto_increment not null,
  number                    integer,
  text                      varchar(255),
  question_image_url        varchar(255),
  constraint pk_hypothesis primary key (id))
;

create table lesson (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  acronym                   varchar(255),
  description               TEXT,
  short_description         TEXT,
  image_url                 varchar(255),
  video_url                 varchar(255),
  constraint pk_lesson primary key (id))
;

create table lessoncontent (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  text                      varchar(255),
  url                       varchar(255),
  lesson_content_image_url  varchar(255),
  constraint pk_lessoncontent primary key (id))
;

create table module (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  acronym                   varchar(255),
  duration                  varchar(255),
  description               TEXT,
  video_url                 TEXT,
  image_url                 varchar(255),
  university_id             bigint,
  constraint pk_module primary key (id))
;

create table multiple_choice_answer (
  id                        bigint auto_increment not null,
  multiple_choice_question_id bigint,
  test_id                   bigint,
  user_email                varchar(255),
  constraint pk_multiple_choice_answer primary key (id))
;

create table multiple_choice_hypothesis (
  id                        bigint auto_increment not null,
  number                    integer,
  text                      varchar(255),
  question_image_url        varchar(255),
  constraint pk_multiple_choice_hypothesis primary key (id))
;

create table multiple_choice_question (
  id                        bigint auto_increment not null,
  question                  varchar(255),
  question_image_url        varchar(255),
  test_id                   bigint,
  constraint pk_multiple_choice_question primary key (id))
;

create table one_choice_answer (
  id                        bigint auto_increment not null,
  one_choice_question_id    bigint,
  test_id                   bigint,
  user_email                varchar(255),
  hypothesis_id             bigint,
  constraint pk_one_choice_answer primary key (id))
;

create table one_choice_question (
  id                        bigint auto_increment not null,
  question                  varchar(255),
  correct_hypothesis        varchar(255),
  question_image_url        varchar(255),
  constraint pk_one_choice_question primary key (id))
;

create table open_question (
  id                        bigint auto_increment not null,
  question                  varchar(255),
  question_image_url        varchar(255),
  lesson_id                 bigint,
  user_email                varchar(255),
  constraint pk_open_question primary key (id))
;

create table professor (
  id                        bigint auto_increment not null,
  email                     varchar(255),
  acronym                   varchar(255),
  firstname                 varchar(255),
  lastname                  varchar(255),
  degree                    varchar(255),
  image_url                 varchar(255),
  contact                   TEXT,
  shortdescription          TEXT,
  constraint pk_professor primary key (id))
;

create table professor_content (
  id                        bigint auto_increment not null,
  title                     varchar(255),
  image_url                 varchar(255),
  description               TEXT,
  professor_id              bigint,
  constraint pk_professor_content primary key (id))
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

create table university (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  acronym                   varchar(255),
  image_url                 varchar(255),
  continent_id              bigint,
  constraint pk_university primary key (id))
;

create table account (
  email                     varchar(255) not null,
  id                        bigint,
  name                      varchar(255),
  password                  varchar(255),
  username                  varchar(255),
  country                   varchar(255),
  account_type              varchar(255),
  constraint pk_account primary key (email))
;

create table user_test (
  id                        bigint auto_increment not null,
  inmodule                  tinyint(1) default 0,
  submited                  tinyint(1) default 0,
  expired                   tinyint(1) default 0,
  user_email                varchar(255),
  test_id                   bigint,
  constraint pk_user_test primary key (id))
;


create table category_curriculummodule (
  category_id                    bigint not null,
  curriculummodule_id            bigint not null,
  constraint pk_category_curriculummodule primary key (category_id, curriculummodule_id))
;

create table curriculumlesson_curriculumtopic (
  curriculumlesson_id            bigint not null,
  curriculumtopic_id             bigint not null,
  constraint pk_curriculumlesson_curriculumtopic primary key (curriculumlesson_id, curriculumtopic_id))
;

create table curriculummodule_curriculumlesso (
  curriculummodule_id            bigint not null,
  curriculumlesson_id            bigint not null,
  constraint pk_curriculummodule_curriculumlesso primary key (curriculummodule_id, curriculumlesson_id))
;

create table lesson_test (
  lesson_id                      bigint not null,
  test_id                        bigint not null,
  constraint pk_lesson_test primary key (lesson_id, test_id))
;

create table lesson_lessoncontent (
  lesson_id                      bigint not null,
  lessoncontent_id               bigint not null,
  constraint pk_lesson_lessoncontent primary key (lesson_id, lessoncontent_id))
;

create table module_lesson (
  module_id                      bigint not null,
  lesson_id                      bigint not null,
  constraint pk_module_lesson primary key (module_id, lesson_id))
;

create table module_category (
  module_id                      bigint not null,
  category_id                    bigint not null,
  constraint pk_module_category primary key (module_id, category_id))
;

create table multiple_choice_answer_multiple_ (
  multiple_choice_answer_id      bigint not null,
  multiple_choice_hypothesis_id  bigint not null,
  constraint pk_multiple_choice_answer_multiple_ primary key (multiple_choice_answer_id, multiple_choice_hypothesis_id))
;

create table multiple_choice_question_multipl (
  multiple_choice_question_id    bigint not null,
  multiple_choice_hypothesis_id  bigint not null,
  constraint pk_multiple_choice_question_multipl primary key (multiple_choice_question_id, multiple_choice_hypothesis_id))
;

create table one_choice_question_hypothesis (
  one_choice_question_id         bigint not null,
  hypothesis_id                  bigint not null,
  constraint pk_one_choice_question_hypothesis primary key (one_choice_question_id, hypothesis_id))
;

create table professor_module (
  professor_id                   bigint not null,
  module_id                      bigint not null,
  constraint pk_professor_module primary key (professor_id, module_id))
;

create table test_open_question (
  test_id                        bigint not null,
  open_question_id               bigint not null,
  constraint pk_test_open_question primary key (test_id, open_question_id))
;

create table test_one_choice_question (
  test_id                        bigint not null,
  one_choice_question_id         bigint not null,
  constraint pk_test_one_choice_question primary key (test_id, one_choice_question_id))
;

create table account_module (
  account_email                  varchar(255) not null,
  module_id                      bigint not null,
  constraint pk_account_module primary key (account_email, module_id))
;
alter table answer add constraint fk_answer_openQuestion_1 foreign key (open_question_id) references open_question (id) on delete restrict on update restrict;
create index ix_answer_openQuestion_1 on answer (open_question_id);
alter table answer add constraint fk_answer_test_2 foreign key (test_id) references test (id) on delete restrict on update restrict;
create index ix_answer_test_2 on answer (test_id);
alter table answer add constraint fk_answer_user_3 foreign key (user_email) references account (email) on delete restrict on update restrict;
create index ix_answer_user_3 on answer (user_email);
alter table bibliography add constraint fk_bibliography_module_4 foreign key (module_id) references module (id) on delete restrict on update restrict;
create index ix_bibliography_module_4 on bibliography (module_id);
alter table comment add constraint fk_comment_blog_5 foreign key (blog_id) references blog (id) on delete restrict on update restrict;
create index ix_comment_blog_5 on comment (blog_id);
alter table comment add constraint fk_comment_user_6 foreign key (user_email) references account (email) on delete restrict on update restrict;
create index ix_comment_user_6 on comment (user_email);
alter table content add constraint fk_content_module_7 foreign key (module_id) references module (id) on delete restrict on update restrict;
create index ix_content_module_7 on content (module_id);
alter table module add constraint fk_module_university_8 foreign key (university_id) references university (id) on delete restrict on update restrict;
create index ix_module_university_8 on module (university_id);
alter table multiple_choice_answer add constraint fk_multiple_choice_answer_mult_9 foreign key (multiple_choice_question_id) references multiple_choice_question (id) on delete restrict on update restrict;
create index ix_multiple_choice_answer_mult_9 on multiple_choice_answer (multiple_choice_question_id);
alter table multiple_choice_answer add constraint fk_multiple_choice_answer_tes_10 foreign key (test_id) references test (id) on delete restrict on update restrict;
create index ix_multiple_choice_answer_tes_10 on multiple_choice_answer (test_id);
alter table multiple_choice_answer add constraint fk_multiple_choice_answer_use_11 foreign key (user_email) references account (email) on delete restrict on update restrict;
create index ix_multiple_choice_answer_use_11 on multiple_choice_answer (user_email);
alter table multiple_choice_question add constraint fk_multiple_choice_question_t_12 foreign key (test_id) references test (id) on delete restrict on update restrict;
create index ix_multiple_choice_question_t_12 on multiple_choice_question (test_id);
alter table one_choice_answer add constraint fk_one_choice_answer_oneChoic_13 foreign key (one_choice_question_id) references one_choice_question (id) on delete restrict on update restrict;
create index ix_one_choice_answer_oneChoic_13 on one_choice_answer (one_choice_question_id);
alter table one_choice_answer add constraint fk_one_choice_answer_test_14 foreign key (test_id) references test (id) on delete restrict on update restrict;
create index ix_one_choice_answer_test_14 on one_choice_answer (test_id);
alter table one_choice_answer add constraint fk_one_choice_answer_user_15 foreign key (user_email) references account (email) on delete restrict on update restrict;
create index ix_one_choice_answer_user_15 on one_choice_answer (user_email);
alter table one_choice_answer add constraint fk_one_choice_answer_hypothes_16 foreign key (hypothesis_id) references hypothesis (id) on delete restrict on update restrict;
create index ix_one_choice_answer_hypothes_16 on one_choice_answer (hypothesis_id);
alter table open_question add constraint fk_open_question_lesson_17 foreign key (lesson_id) references lesson (id) on delete restrict on update restrict;
create index ix_open_question_lesson_17 on open_question (lesson_id);
alter table open_question add constraint fk_open_question_user_18 foreign key (user_email) references account (email) on delete restrict on update restrict;
create index ix_open_question_user_18 on open_question (user_email);
alter table professor_content add constraint fk_professor_content_professo_19 foreign key (professor_id) references professor (id) on delete restrict on update restrict;
create index ix_professor_content_professo_19 on professor_content (professor_id);
alter table reply add constraint fk_reply_topic_20 foreign key (topic_id) references topic (id) on delete restrict on update restrict;
create index ix_reply_topic_20 on reply (topic_id);
alter table reply add constraint fk_reply_user_21 foreign key (user_email) references account (email) on delete restrict on update restrict;
create index ix_reply_user_21 on reply (user_email);
alter table topic add constraint fk_topic_forum_22 foreign key (forum_id) references forum (id) on delete restrict on update restrict;
create index ix_topic_forum_22 on topic (forum_id);
alter table university add constraint fk_university_continent_23 foreign key (continent_id) references continent (id) on delete restrict on update restrict;
create index ix_university_continent_23 on university (continent_id);
alter table user_test add constraint fk_user_test_user_24 foreign key (user_email) references account (email) on delete restrict on update restrict;
create index ix_user_test_user_24 on user_test (user_email);
alter table user_test add constraint fk_user_test_test_25 foreign key (test_id) references test (id) on delete restrict on update restrict;
create index ix_user_test_test_25 on user_test (test_id);



alter table category_curriculummodule add constraint fk_category_curriculummodule__01 foreign key (category_id) references category (id) on delete restrict on update restrict;

alter table category_curriculummodule add constraint fk_category_curriculummodule__02 foreign key (curriculummodule_id) references curriculummodule (id) on delete restrict on update restrict;

alter table curriculumlesson_curriculumtopic add constraint fk_curriculumlesson_curriculu_01 foreign key (curriculumlesson_id) references curriculumlesson (id) on delete restrict on update restrict;

alter table curriculumlesson_curriculumtopic add constraint fk_curriculumlesson_curriculu_02 foreign key (curriculumtopic_id) references curriculumtopic (id) on delete restrict on update restrict;

alter table curriculummodule_curriculumlesso add constraint fk_curriculummodule_curriculu_01 foreign key (curriculummodule_id) references curriculummodule (id) on delete restrict on update restrict;

alter table curriculummodule_curriculumlesso add constraint fk_curriculummodule_curriculu_02 foreign key (curriculumlesson_id) references curriculumlesson (id) on delete restrict on update restrict;

alter table lesson_test add constraint fk_lesson_test_lesson_01 foreign key (lesson_id) references lesson (id) on delete restrict on update restrict;

alter table lesson_test add constraint fk_lesson_test_test_02 foreign key (test_id) references test (id) on delete restrict on update restrict;

alter table lesson_lessoncontent add constraint fk_lesson_lessoncontent_lesso_01 foreign key (lesson_id) references lesson (id) on delete restrict on update restrict;

alter table lesson_lessoncontent add constraint fk_lesson_lessoncontent_lesso_02 foreign key (lessoncontent_id) references lessoncontent (id) on delete restrict on update restrict;

alter table module_lesson add constraint fk_module_lesson_module_01 foreign key (module_id) references module (id) on delete restrict on update restrict;

alter table module_lesson add constraint fk_module_lesson_lesson_02 foreign key (lesson_id) references lesson (id) on delete restrict on update restrict;

alter table module_category add constraint fk_module_category_module_01 foreign key (module_id) references module (id) on delete restrict on update restrict;

alter table module_category add constraint fk_module_category_category_02 foreign key (category_id) references category (id) on delete restrict on update restrict;

alter table multiple_choice_answer_multiple_ add constraint fk_multiple_choice_answer_mul_01 foreign key (multiple_choice_answer_id) references multiple_choice_answer (id) on delete restrict on update restrict;

alter table multiple_choice_answer_multiple_ add constraint fk_multiple_choice_answer_mul_02 foreign key (multiple_choice_hypothesis_id) references multiple_choice_hypothesis (id) on delete restrict on update restrict;

alter table multiple_choice_question_multipl add constraint fk_multiple_choice_question_m_01 foreign key (multiple_choice_question_id) references multiple_choice_question (id) on delete restrict on update restrict;

alter table multiple_choice_question_multipl add constraint fk_multiple_choice_question_m_02 foreign key (multiple_choice_hypothesis_id) references multiple_choice_hypothesis (id) on delete restrict on update restrict;

alter table one_choice_question_hypothesis add constraint fk_one_choice_question_hypoth_01 foreign key (one_choice_question_id) references one_choice_question (id) on delete restrict on update restrict;

alter table one_choice_question_hypothesis add constraint fk_one_choice_question_hypoth_02 foreign key (hypothesis_id) references hypothesis (id) on delete restrict on update restrict;

alter table professor_module add constraint fk_professor_module_professor_01 foreign key (professor_id) references professor (id) on delete restrict on update restrict;

alter table professor_module add constraint fk_professor_module_module_02 foreign key (module_id) references module (id) on delete restrict on update restrict;

alter table test_open_question add constraint fk_test_open_question_test_01 foreign key (test_id) references test (id) on delete restrict on update restrict;

alter table test_open_question add constraint fk_test_open_question_open_qu_02 foreign key (open_question_id) references open_question (id) on delete restrict on update restrict;

alter table test_one_choice_question add constraint fk_test_one_choice_question_t_01 foreign key (test_id) references test (id) on delete restrict on update restrict;

alter table test_one_choice_question add constraint fk_test_one_choice_question_o_02 foreign key (one_choice_question_id) references one_choice_question (id) on delete restrict on update restrict;

alter table account_module add constraint fk_account_module_account_01 foreign key (account_email) references account (email) on delete restrict on update restrict;

alter table account_module add constraint fk_account_module_module_02 foreign key (module_id) references module (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table answer;

drop table bibliography;

drop table blog;

drop table category;

drop table module_category;

drop table category_curriculummodule;

drop table comment;

drop table content;

drop table continent;

drop table curriculumlesson;

drop table curriculumlesson_curriculumtopic;

drop table curriculummodule;

drop table curriculummodule_curriculumlesso;

drop table curriculumtopic;

drop table forum;

drop table hypothesis;

drop table lesson;

drop table lesson_test;

drop table lesson_lessoncontent;

drop table lessoncontent;

drop table module;

drop table account_module;

drop table professor_module;

drop table module_lesson;

drop table multiple_choice_answer;

drop table multiple_choice_answer_multiple_;

drop table multiple_choice_hypothesis;

drop table multiple_choice_question;

drop table multiple_choice_question_multipl;

drop table one_choice_answer;

drop table one_choice_question;

drop table one_choice_question_hypothesis;

drop table open_question;

drop table professor;

drop table professor_content;

drop table reply;

drop table test;

drop table test_open_question;

drop table test_one_choice_question;

drop table topic;

drop table university;

drop table account;

drop table user_test;

SET FOREIGN_KEY_CHECKS=1;

