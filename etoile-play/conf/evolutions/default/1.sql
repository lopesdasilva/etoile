# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table answer (
  id                        bigint auto_increment not null,
  answer                    TEXT,
  open_question_id          bigint,
  test_id                   bigint,
  user_email                varchar(255),
  group_id                  bigint,
  evaluation_id             bigint,
  questionevaluation_id     bigint,
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
  date                      datetime,
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
  date                      datetime,
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

create table evaluation (
  id                        bigint auto_increment not null,
  evaluation                bigint,
  answer_id                 bigint,
  user_email                varchar(255),
  constraint pk_evaluation primary key (id))
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
  is_correct                tinyint(1) default 0,
  question_image_url        varchar(255),
  question_id               bigint,
  selected                  tinyint(1) default 0,
  user_email                varchar(255),
  constraint pk_hypothesis primary key (id))
;

create table lesson (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  number                    integer,
  acronym                   varchar(255),
  description               TEXT,
  short_description         TEXT,
  image_url                 TEXT,
  video_url                 TEXT,
  module_id                 bigint,
  constraint pk_lesson primary key (id))
;

create table lessonalert (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  text                      varchar(255),
  lesson_id                 bigint,
  image_url                 varchar(255),
  constraint pk_lessonalert primary key (id))
;

create table lessoncontent (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  text                      varchar(255),
  lesson_id                 bigint,
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
  user_email                varchar(255),
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

create table question (
  id                        bigint auto_increment not null,
  lesson_id                 bigint,
  user_email                varchar(255),
  weight                    integer,
  weight_to_lose            integer,
  number                    integer,
  type_of_question          integer,
  question                  varchar(255),
  answer_suggested_by_student varchar(255),
  image_url                 varchar(255),
  video_url                 varchar(255),
  keywords                  varchar(255),
  iscopy                    tinyint(1) default 0,
  openanswer_id             bigint,
  constraint pk_question primary key (id))
;

create table question_evaluation (
  id                        bigint auto_increment not null,
  score                     double,
  percent                   integer,
  is_correct                tinyint(1) default 0,
  user_test_id              bigint,
  question_id               bigint,
  answer_id                 bigint,
  constraint pk_question_evaluation primary key (id))
;

create table question_group (
  id                        bigint auto_increment not null,
  test_id                   bigint,
  question                  varchar(255),
  image_url                 varchar(255),
  video_url                 varchar(255),
  number                    integer,
  constraint pk_question_group primary key (id))
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
  expected_duration         varchar(255),
  published                 tinyint(1) default 0,
  lesson_id                 bigint,
  constraint pk_test primary key (id))
;

create table topic (
  id                        bigint auto_increment not null,
  text                      varchar(255),
  forum_id                  bigint,
  constraint pk_topic primary key (id))
;

create table url (
  id                        bigint auto_increment not null,
  adress                    varchar(255),
  likes                     integer,
  name                      varchar(255),
  description               varchar(255),
  image_url                 varchar(255),
  added                     datetime,
  question_id               bigint,
  user_email                varchar(255),
  constraint pk_url primary key (id))
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
  account_type              integer,
  professor_profile_id      bigint,
  constraint pk_account primary key (email))
;

create table user_test (
  id                        bigint auto_increment not null,
  reputation_as_astudent    double,
  reputation_as_amarker     integer,
  inmodule                  tinyint(1) default 0,
  submitted                 tinyint(1) default 0,
  expired                   tinyint(1) default 0,
  reviewd                   tinyint(1) default 0,
  user_email                varchar(255),
  test_id                   bigint,
  progress                  float,
  progress_string           varchar(255),
  constraint pk_user_test primary key (id))
;


create table answer_account (
  answer_id                      bigint not null,
  account_email                  varchar(255) not null,
  constraint pk_answer_account primary key (answer_id, account_email))
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

create table module_category (
  module_id                      bigint not null,
  category_id                    bigint not null,
  constraint pk_module_category primary key (module_id, category_id))
;

create table professor_module (
  professor_id                   bigint not null,
  module_id                      bigint not null,
  constraint pk_professor_module primary key (professor_id, module_id))
;

create table question_group_question (
  question_group_id              bigint not null,
  question_id                    bigint not null,
  constraint pk_question_group_question primary key (question_group_id, question_id))
;

create table account_module (
  account_email                  varchar(255) not null,
  module_id                      bigint not null,
  constraint pk_account_module primary key (account_email, module_id))
;
alter table answer add constraint fk_answer_openQuestion_1 foreign key (open_question_id) references question (id) on delete restrict on update restrict;
create index ix_answer_openQuestion_1 on answer (open_question_id);
alter table answer add constraint fk_answer_test_2 foreign key (test_id) references test (id) on delete restrict on update restrict;
create index ix_answer_test_2 on answer (test_id);
alter table answer add constraint fk_answer_user_3 foreign key (user_email) references account (email) on delete restrict on update restrict;
create index ix_answer_user_3 on answer (user_email);
alter table answer add constraint fk_answer_group_4 foreign key (group_id) references question_group (id) on delete restrict on update restrict;
create index ix_answer_group_4 on answer (group_id);
alter table answer add constraint fk_answer_evaluation_5 foreign key (evaluation_id) references evaluation (id) on delete restrict on update restrict;
create index ix_answer_evaluation_5 on answer (evaluation_id);
alter table answer add constraint fk_answer_questionevaluation_6 foreign key (questionevaluation_id) references question_evaluation (id) on delete restrict on update restrict;
create index ix_answer_questionevaluation_6 on answer (questionevaluation_id);
alter table bibliography add constraint fk_bibliography_module_7 foreign key (module_id) references module (id) on delete restrict on update restrict;
create index ix_bibliography_module_7 on bibliography (module_id);
alter table comment add constraint fk_comment_blog_8 foreign key (blog_id) references blog (id) on delete restrict on update restrict;
create index ix_comment_blog_8 on comment (blog_id);
alter table comment add constraint fk_comment_user_9 foreign key (user_email) references account (email) on delete restrict on update restrict;
create index ix_comment_user_9 on comment (user_email);
alter table content add constraint fk_content_module_10 foreign key (module_id) references module (id) on delete restrict on update restrict;
create index ix_content_module_10 on content (module_id);
alter table evaluation add constraint fk_evaluation_answer_11 foreign key (answer_id) references answer (id) on delete restrict on update restrict;
create index ix_evaluation_answer_11 on evaluation (answer_id);
alter table evaluation add constraint fk_evaluation_user_12 foreign key (user_email) references account (email) on delete restrict on update restrict;
create index ix_evaluation_user_12 on evaluation (user_email);
alter table hypothesis add constraint fk_hypothesis_question_13 foreign key (question_id) references question (id) on delete restrict on update restrict;
create index ix_hypothesis_question_13 on hypothesis (question_id);
alter table hypothesis add constraint fk_hypothesis_user_14 foreign key (user_email) references account (email) on delete restrict on update restrict;
create index ix_hypothesis_user_14 on hypothesis (user_email);
alter table lesson add constraint fk_lesson_module_15 foreign key (module_id) references module (id) on delete restrict on update restrict;
create index ix_lesson_module_15 on lesson (module_id);
alter table lessonalert add constraint fk_lessonalert_lesson_16 foreign key (lesson_id) references lesson (id) on delete restrict on update restrict;
create index ix_lessonalert_lesson_16 on lessonalert (lesson_id);
alter table lessoncontent add constraint fk_lessoncontent_lesson_17 foreign key (lesson_id) references lesson (id) on delete restrict on update restrict;
create index ix_lessoncontent_lesson_17 on lessoncontent (lesson_id);
alter table module add constraint fk_module_university_18 foreign key (university_id) references university (id) on delete restrict on update restrict;
create index ix_module_university_18 on module (university_id);
alter table professor add constraint fk_professor_user_19 foreign key (user_email) references account (email) on delete restrict on update restrict;
create index ix_professor_user_19 on professor (user_email);
alter table professor_content add constraint fk_professor_content_professo_20 foreign key (professor_id) references professor (id) on delete restrict on update restrict;
create index ix_professor_content_professo_20 on professor_content (professor_id);
alter table question add constraint fk_question_lesson_21 foreign key (lesson_id) references lesson (id) on delete restrict on update restrict;
create index ix_question_lesson_21 on question (lesson_id);
alter table question add constraint fk_question_user_22 foreign key (user_email) references account (email) on delete restrict on update restrict;
create index ix_question_user_22 on question (user_email);
alter table question add constraint fk_question_openanswer_23 foreign key (openanswer_id) references answer (id) on delete restrict on update restrict;
create index ix_question_openanswer_23 on question (openanswer_id);
alter table question_evaluation add constraint fk_question_evaluation_userTe_24 foreign key (user_test_id) references user_test (id) on delete restrict on update restrict;
create index ix_question_evaluation_userTe_24 on question_evaluation (user_test_id);
alter table question_evaluation add constraint fk_question_evaluation_questi_25 foreign key (question_id) references question (id) on delete restrict on update restrict;
create index ix_question_evaluation_questi_25 on question_evaluation (question_id);
alter table question_evaluation add constraint fk_question_evaluation_answer_26 foreign key (answer_id) references answer (id) on delete restrict on update restrict;
create index ix_question_evaluation_answer_26 on question_evaluation (answer_id);
alter table question_group add constraint fk_question_group_test_27 foreign key (test_id) references test (id) on delete restrict on update restrict;
create index ix_question_group_test_27 on question_group (test_id);
alter table reply add constraint fk_reply_topic_28 foreign key (topic_id) references topic (id) on delete restrict on update restrict;
create index ix_reply_topic_28 on reply (topic_id);
alter table reply add constraint fk_reply_user_29 foreign key (user_email) references account (email) on delete restrict on update restrict;
create index ix_reply_user_29 on reply (user_email);
alter table test add constraint fk_test_lesson_30 foreign key (lesson_id) references lesson (id) on delete restrict on update restrict;
create index ix_test_lesson_30 on test (lesson_id);
alter table topic add constraint fk_topic_forum_31 foreign key (forum_id) references forum (id) on delete restrict on update restrict;
create index ix_topic_forum_31 on topic (forum_id);
alter table url add constraint fk_url_question_32 foreign key (question_id) references question (id) on delete restrict on update restrict;
create index ix_url_question_32 on url (question_id);
alter table url add constraint fk_url_user_33 foreign key (user_email) references account (email) on delete restrict on update restrict;
create index ix_url_user_33 on url (user_email);
alter table university add constraint fk_university_continent_34 foreign key (continent_id) references continent (id) on delete restrict on update restrict;
create index ix_university_continent_34 on university (continent_id);
alter table account add constraint fk_account_professorProfile_35 foreign key (professor_profile_id) references professor (id) on delete restrict on update restrict;
create index ix_account_professorProfile_35 on account (professor_profile_id);
alter table user_test add constraint fk_user_test_user_36 foreign key (user_email) references account (email) on delete restrict on update restrict;
create index ix_user_test_user_36 on user_test (user_email);
alter table user_test add constraint fk_user_test_test_37 foreign key (test_id) references test (id) on delete restrict on update restrict;
create index ix_user_test_test_37 on user_test (test_id);



alter table answer_account add constraint fk_answer_account_answer_01 foreign key (answer_id) references answer (id) on delete restrict on update restrict;

alter table answer_account add constraint fk_answer_account_account_02 foreign key (account_email) references account (email) on delete restrict on update restrict;

alter table category_curriculummodule add constraint fk_category_curriculummodule__01 foreign key (category_id) references category (id) on delete restrict on update restrict;

alter table category_curriculummodule add constraint fk_category_curriculummodule__02 foreign key (curriculummodule_id) references curriculummodule (id) on delete restrict on update restrict;

alter table curriculumlesson_curriculumtopic add constraint fk_curriculumlesson_curriculu_01 foreign key (curriculumlesson_id) references curriculumlesson (id) on delete restrict on update restrict;

alter table curriculumlesson_curriculumtopic add constraint fk_curriculumlesson_curriculu_02 foreign key (curriculumtopic_id) references curriculumtopic (id) on delete restrict on update restrict;

alter table curriculummodule_curriculumlesso add constraint fk_curriculummodule_curriculu_01 foreign key (curriculummodule_id) references curriculummodule (id) on delete restrict on update restrict;

alter table curriculummodule_curriculumlesso add constraint fk_curriculummodule_curriculu_02 foreign key (curriculumlesson_id) references curriculumlesson (id) on delete restrict on update restrict;

alter table module_category add constraint fk_module_category_module_01 foreign key (module_id) references module (id) on delete restrict on update restrict;

alter table module_category add constraint fk_module_category_category_02 foreign key (category_id) references category (id) on delete restrict on update restrict;

alter table professor_module add constraint fk_professor_module_professor_01 foreign key (professor_id) references professor (id) on delete restrict on update restrict;

alter table professor_module add constraint fk_professor_module_module_02 foreign key (module_id) references module (id) on delete restrict on update restrict;

alter table question_group_question add constraint fk_question_group_question_qu_01 foreign key (question_group_id) references question_group (id) on delete restrict on update restrict;

alter table question_group_question add constraint fk_question_group_question_qu_02 foreign key (question_id) references question (id) on delete restrict on update restrict;

alter table account_module add constraint fk_account_module_account_01 foreign key (account_email) references account (email) on delete restrict on update restrict;

alter table account_module add constraint fk_account_module_module_02 foreign key (module_id) references module (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table answer;

drop table answer_account;

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

drop table evaluation;

drop table forum;

drop table hypothesis;

drop table lesson;

drop table lessonalert;

drop table lessoncontent;

drop table module;

drop table account_module;

drop table professor_module;

drop table professor;

drop table professor_content;

drop table question;

drop table question_group_question;

drop table question_evaluation;

drop table question_group;

drop table reply;

drop table test;

drop table topic;

drop table url;

drop table university;

drop table account;

drop table user_test;

SET FOREIGN_KEY_CHECKS=1;

