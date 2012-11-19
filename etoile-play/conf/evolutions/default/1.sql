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
  course_id                 bigint,
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
  course_id                 bigint,
  constraint pk_content primary key (id))
;

create table continent (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  acronym                   varchar(255),
  image_url                 varchar(255),
  constraint pk_continent primary key (id))
;

create table course (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  acronym                   varchar(255),
  duration                  varchar(255),
  description               TEXT,
  video_url                 TEXT,
  image_url                 varchar(255),
  university_id             bigint,
  constraint pk_course primary key (id))
;

create table curriculumcourse (
  id                        bigint auto_increment not null,
  keyword                   varchar(255),
  name                      varchar(255),
  text                      TEXT,
  constraint uq_curriculumcourse_keyword unique (keyword),
  constraint pk_curriculumcourse primary key (id))
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
  text                      varchar(255),
  question_image_url        varchar(255),
  constraint pk_hypothesis primary key (id))
;

create table module (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  acronym                   varchar(255),
  description               TEXT,
  short_description         TEXT,
  image_url                 varchar(255),
  video_url                 varchar(255),
  constraint pk_module primary key (id))
;

create table modulecontent (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  text                      varchar(255),
  url                       varchar(255),
  module_content_image_url  varchar(255),
  constraint pk_modulecontent primary key (id))
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
  module_id                 bigint,
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
  incourse                  tinyint(1) default 0,
  submited                  tinyint(1) default 0,
  expired                   tinyint(1) default 0,
  user_email                varchar(255),
  test_id                   bigint,
  constraint pk_user_test primary key (id))
;


create table category_curriculumcourse (
  category_id                    bigint not null,
  curriculumcourse_id            bigint not null,
  constraint pk_category_curriculumcourse primary key (category_id, curriculumcourse_id))
;

create table course_module (
  course_id                      bigint not null,
  module_id                      bigint not null,
  constraint pk_course_module primary key (course_id, module_id))
;

create table course_category (
  course_id                      bigint not null,
  category_id                    bigint not null,
  constraint pk_course_category primary key (course_id, category_id))
;

create table curriculumcourse_curriculummodul (
  curriculumcourse_id            bigint not null,
  curriculummodule_id            bigint not null,
  constraint pk_curriculumcourse_curriculummodul primary key (curriculumcourse_id, curriculummodule_id))
;

create table curriculummodule_curriculumtopic (
  curriculummodule_id            bigint not null,
  curriculumtopic_id             bigint not null,
  constraint pk_curriculummodule_curriculumtopic primary key (curriculummodule_id, curriculumtopic_id))
;

create table module_test (
  module_id                      bigint not null,
  test_id                        bigint not null,
  constraint pk_module_test primary key (module_id, test_id))
;

create table module_modulecontent (
  module_id                      bigint not null,
  modulecontent_id               bigint not null,
  constraint pk_module_modulecontent primary key (module_id, modulecontent_id))
;

create table one_choice_question_hypothesis (
  one_choice_question_id         bigint not null,
  hypothesis_id                  bigint not null,
  constraint pk_one_choice_question_hypothesis primary key (one_choice_question_id, hypothesis_id))
;

create table professor_course (
  professor_id                   bigint not null,
  course_id                      bigint not null,
  constraint pk_professor_course primary key (professor_id, course_id))
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

create table account_course (
  account_email                  varchar(255) not null,
  course_id                      bigint not null,
  constraint pk_account_course primary key (account_email, course_id))
;
alter table answer add constraint fk_answer_openQuestion_1 foreign key (open_question_id) references open_question (id) on delete restrict on update restrict;
create index ix_answer_openQuestion_1 on answer (open_question_id);
alter table answer add constraint fk_answer_test_2 foreign key (test_id) references test (id) on delete restrict on update restrict;
create index ix_answer_test_2 on answer (test_id);
alter table answer add constraint fk_answer_user_3 foreign key (user_email) references account (email) on delete restrict on update restrict;
create index ix_answer_user_3 on answer (user_email);
alter table bibliography add constraint fk_bibliography_course_4 foreign key (course_id) references course (id) on delete restrict on update restrict;
create index ix_bibliography_course_4 on bibliography (course_id);
alter table comment add constraint fk_comment_blog_5 foreign key (blog_id) references blog (id) on delete restrict on update restrict;
create index ix_comment_blog_5 on comment (blog_id);
alter table comment add constraint fk_comment_user_6 foreign key (user_email) references account (email) on delete restrict on update restrict;
create index ix_comment_user_6 on comment (user_email);
alter table content add constraint fk_content_course_7 foreign key (course_id) references course (id) on delete restrict on update restrict;
create index ix_content_course_7 on content (course_id);
alter table course add constraint fk_course_university_8 foreign key (university_id) references university (id) on delete restrict on update restrict;
create index ix_course_university_8 on course (university_id);
alter table one_choice_answer add constraint fk_one_choice_answer_oneChoice_9 foreign key (one_choice_question_id) references one_choice_question (id) on delete restrict on update restrict;
create index ix_one_choice_answer_oneChoice_9 on one_choice_answer (one_choice_question_id);
alter table one_choice_answer add constraint fk_one_choice_answer_test_10 foreign key (test_id) references test (id) on delete restrict on update restrict;
create index ix_one_choice_answer_test_10 on one_choice_answer (test_id);
alter table one_choice_answer add constraint fk_one_choice_answer_user_11 foreign key (user_email) references account (email) on delete restrict on update restrict;
create index ix_one_choice_answer_user_11 on one_choice_answer (user_email);
alter table one_choice_answer add constraint fk_one_choice_answer_hypothes_12 foreign key (hypothesis_id) references hypothesis (id) on delete restrict on update restrict;
create index ix_one_choice_answer_hypothes_12 on one_choice_answer (hypothesis_id);
alter table open_question add constraint fk_open_question_module_13 foreign key (module_id) references module (id) on delete restrict on update restrict;
create index ix_open_question_module_13 on open_question (module_id);
alter table open_question add constraint fk_open_question_user_14 foreign key (user_email) references account (email) on delete restrict on update restrict;
create index ix_open_question_user_14 on open_question (user_email);
alter table professor_content add constraint fk_professor_content_professo_15 foreign key (professor_id) references professor (id) on delete restrict on update restrict;
create index ix_professor_content_professo_15 on professor_content (professor_id);
alter table reply add constraint fk_reply_topic_16 foreign key (topic_id) references topic (id) on delete restrict on update restrict;
create index ix_reply_topic_16 on reply (topic_id);
alter table reply add constraint fk_reply_user_17 foreign key (user_email) references account (email) on delete restrict on update restrict;
create index ix_reply_user_17 on reply (user_email);
alter table topic add constraint fk_topic_forum_18 foreign key (forum_id) references forum (id) on delete restrict on update restrict;
create index ix_topic_forum_18 on topic (forum_id);
alter table university add constraint fk_university_continent_19 foreign key (continent_id) references continent (id) on delete restrict on update restrict;
create index ix_university_continent_19 on university (continent_id);
alter table user_test add constraint fk_user_test_user_20 foreign key (user_email) references account (email) on delete restrict on update restrict;
create index ix_user_test_user_20 on user_test (user_email);
alter table user_test add constraint fk_user_test_test_21 foreign key (test_id) references test (id) on delete restrict on update restrict;
create index ix_user_test_test_21 on user_test (test_id);



alter table category_curriculumcourse add constraint fk_category_curriculumcourse__01 foreign key (category_id) references category (id) on delete restrict on update restrict;

alter table category_curriculumcourse add constraint fk_category_curriculumcourse__02 foreign key (curriculumcourse_id) references curriculumcourse (id) on delete restrict on update restrict;

alter table course_module add constraint fk_course_module_course_01 foreign key (course_id) references course (id) on delete restrict on update restrict;

alter table course_module add constraint fk_course_module_module_02 foreign key (module_id) references module (id) on delete restrict on update restrict;

alter table course_category add constraint fk_course_category_course_01 foreign key (course_id) references course (id) on delete restrict on update restrict;

alter table course_category add constraint fk_course_category_category_02 foreign key (category_id) references category (id) on delete restrict on update restrict;

alter table curriculumcourse_curriculummodul add constraint fk_curriculumcourse_curriculu_01 foreign key (curriculumcourse_id) references curriculumcourse (id) on delete restrict on update restrict;

alter table curriculumcourse_curriculummodul add constraint fk_curriculumcourse_curriculu_02 foreign key (curriculummodule_id) references curriculummodule (id) on delete restrict on update restrict;

alter table curriculummodule_curriculumtopic add constraint fk_curriculummodule_curriculu_01 foreign key (curriculummodule_id) references curriculummodule (id) on delete restrict on update restrict;

alter table curriculummodule_curriculumtopic add constraint fk_curriculummodule_curriculu_02 foreign key (curriculumtopic_id) references curriculumtopic (id) on delete restrict on update restrict;

alter table module_test add constraint fk_module_test_module_01 foreign key (module_id) references module (id) on delete restrict on update restrict;

alter table module_test add constraint fk_module_test_test_02 foreign key (test_id) references test (id) on delete restrict on update restrict;

alter table module_modulecontent add constraint fk_module_modulecontent_modul_01 foreign key (module_id) references module (id) on delete restrict on update restrict;

alter table module_modulecontent add constraint fk_module_modulecontent_modul_02 foreign key (modulecontent_id) references modulecontent (id) on delete restrict on update restrict;

alter table one_choice_question_hypothesis add constraint fk_one_choice_question_hypoth_01 foreign key (one_choice_question_id) references one_choice_question (id) on delete restrict on update restrict;

alter table one_choice_question_hypothesis add constraint fk_one_choice_question_hypoth_02 foreign key (hypothesis_id) references hypothesis (id) on delete restrict on update restrict;

alter table professor_course add constraint fk_professor_course_professor_01 foreign key (professor_id) references professor (id) on delete restrict on update restrict;

alter table professor_course add constraint fk_professor_course_course_02 foreign key (course_id) references course (id) on delete restrict on update restrict;

alter table test_open_question add constraint fk_test_open_question_test_01 foreign key (test_id) references test (id) on delete restrict on update restrict;

alter table test_open_question add constraint fk_test_open_question_open_qu_02 foreign key (open_question_id) references open_question (id) on delete restrict on update restrict;

alter table test_one_choice_question add constraint fk_test_one_choice_question_t_01 foreign key (test_id) references test (id) on delete restrict on update restrict;

alter table test_one_choice_question add constraint fk_test_one_choice_question_o_02 foreign key (one_choice_question_id) references one_choice_question (id) on delete restrict on update restrict;

alter table account_course add constraint fk_account_course_account_01 foreign key (account_email) references account (email) on delete restrict on update restrict;

alter table account_course add constraint fk_account_course_course_02 foreign key (course_id) references course (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table answer;

drop table bibliography;

drop table blog;

drop table category;

drop table course_category;

drop table category_curriculumcourse;

drop table comment;

drop table content;

drop table continent;

drop table course;

drop table account_course;

drop table professor_course;

drop table course_module;

drop table curriculumcourse;

drop table curriculumcourse_curriculummodul;

drop table curriculummodule;

drop table curriculummodule_curriculumtopic;

drop table curriculumtopic;

drop table forum;

drop table hypothesis;

drop table module;

drop table module_test;

drop table module_modulecontent;

drop table modulecontent;

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

