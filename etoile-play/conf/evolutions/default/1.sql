# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table answer (
  id                        bigint not null,
  answer                    TEXT,
  is_saved                  boolean,
  open_question_id          bigint,
  test_id                   bigint,
  usertest_id               bigint,
  group_id                  bigint,
  answer_marker_id          bigint,
  questionevaluation_id     bigint,
  constraint pk_answer primary key (id))
;

create table answer_marker (
  id                        bigint not null,
  answerscore               bigint,
  markerscore               bigint,
  answer_id                 bigint,
  markercomment             TEXT,
  user_email                varchar(255),
  is_marked                 boolean,
  constraint pk_answer_marker primary key (id))
;

create table bibliography (
  id                        bigint not null,
  title                     varchar(255),
  description               TEXT,
  image_url                 TEXT,
  link                      varchar(255),
  module_id                 bigint,
  constraint pk_bibliography primary key (id))
;

create table blog (
  id                        bigint not null,
  header                    varchar(255),
  alternate_header          varchar(255),
  text                      TEXT,
  alternate_text            TEXT,
  article_image_url         TEXT,
  date                      timestamp,
  constraint pk_blog primary key (id))
;

create table category (
  id                        bigint not null,
  keyword                   varchar(255),
  name                      varchar(255),
  description               varchar(255),
  constraint uq_category_keyword unique (keyword),
  constraint pk_category primary key (id))
;

create table comment (
  id                        bigint not null,
  text                      TEXT,
  blog_id                   bigint,
  user_email                varchar(255),
  date                      timestamp,
  constraint pk_comment primary key (id))
;

create table content (
  id                        bigint not null,
  title                     varchar(255),
  text                      TEXT,
  module_id                 bigint,
  constraint pk_content primary key (id))
;

create table continent (
  id                        bigint not null,
  name                      varchar(255),
  acronym                   varchar(255),
  image_url                 TEXT,
  constraint pk_continent primary key (id))
;

create table curriculumlesson (
  id                        bigint not null,
  keyword                   varchar(255),
  name                      varchar(255),
  text                      TEXT,
  constraint uq_curriculumlesson_keyword unique (keyword),
  constraint pk_curriculumlesson primary key (id))
;

create table curriculummodule (
  id                        bigint not null,
  keyword                   varchar(255),
  name                      varchar(255),
  text                      TEXT,
  constraint uq_curriculummodule_keyword unique (keyword),
  constraint uq_curriculummodule_name unique (name),
  constraint pk_curriculummodule primary key (id))
;

create table curriculumtopic (
  id                        bigint not null,
  keyword                   varchar(255),
  text                      TEXT,
  constraint uq_curriculumtopic_keyword unique (keyword),
  constraint pk_curriculumtopic primary key (id))
;

create table forum (
  id                        bigint not null,
  description               varchar(255),
  module_id                 bigint,
  constraint pk_forum primary key (id))
;

create table hypothesis (
  id                        bigint not null,
  number                    integer,
  text                      varchar(255),
  is_correct                boolean,
  is_saved                  boolean,
  question_image_url        varchar(255),
  question_id               bigint,
  selected                  boolean,
  user_email                varchar(255),
  constraint pk_hypothesis primary key (id))
;

create table language (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_language primary key (id))
;

create table lesson (
  id                        bigint not null,
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
  id                        bigint not null,
  name                      varchar(255),
  text                      varchar(255),
  lesson_id                 bigint,
  image_url                 TEXT,
  constraint pk_lessonalert primary key (id))
;

create table lessoncontent (
  id                        bigint not null,
  name                      varchar(255),
  text                      varchar(255),
  lesson_id                 bigint,
  url                       TEXT,
  lesson_content_image_url  TEXT,
  constraint pk_lessoncontent primary key (id))
;

create table module (
  id                        bigint not null,
  name                      varchar(255),
  acronym                   varchar(255),
  duration                  varchar(255),
  description               TEXT,
  short_description         TEXT,
  video_url                 TEXT,
  image_url                 TEXT,
  university_id             bigint,
  language_id               bigint,
  forum_id                  bigint,
  constraint pk_module primary key (id))
;

create table modulescore (
  id                        bigint not null,
  score                     integer,
  module_id                 bigint,
  user_email                varchar(255),
  constraint pk_modulescore primary key (id))
;

create table professor (
  id                        bigint not null,
  email                     varchar(255),
  acronym                   varchar(255),
  firstname                 varchar(255),
  lastname                  varchar(255),
  degree                    varchar(255),
  image_url                 TEXT,
  contact                   TEXT,
  shortdescription          TEXT,
  user_email                varchar(255),
  constraint pk_professor primary key (id))
;

create table professor_content (
  id                        bigint not null,
  title                     varchar(255),
  image_url                 TEXT,
  description               TEXT,
  professor_id              bigint,
  constraint pk_professor_content primary key (id))
;

create table question (
  id                        bigint not null,
  lesson_id                 bigint,
  user_email                varchar(255),
  weight                    integer,
  weight_to_lose            integer,
  number                    integer,
  type_of_question          integer,
  question                  TEXT,
  answer_suggested_by_student TEXT,
  image_url                 TEXT,
  video_url                 TEXT,
  keywords                  varchar(255),
  iscopy                    boolean,
  openanswer_id             bigint,
  subtopic_id               bigint,
  constraint pk_question primary key (id))
;

create table question_evaluation (
  id                        bigint not null,
  score                     double,
  percent                   integer,
  is_correct                boolean,
  usertest_id               bigint,
  question_id               bigint,
  answer_id                 bigint,
  evalutationcomment        TEXT,
  professormarker_id        bigint,
  constraint pk_question_evaluation primary key (id))
;

create table question_group (
  id                        bigint not null,
  test_id                   bigint,
  question                  TEXT,
  image_url                 varchar(255),
  video_url                 varchar(255),
  number                    integer,
  constraint pk_question_group primary key (id))
;

create table reply (
  id                        bigint not null,
  text                      TEXT,
  topic_id                  bigint,
  user_email                varchar(255),
  date                      timestamp,
  constraint pk_reply primary key (id))
;

create table student (
  id                        bigint not null,
  email                     varchar(255),
  acronym                   varchar(255),
  firstname                 varchar(255),
  lastname                  varchar(255),
  scientific_area           varchar(255),
  degree                    varchar(255),
  university_id             bigint,
  description               TEXT,
  image_url                 TEXT,
  webpage                   TEXT,
  contact                   TEXT,
  address                   TEXT,
  shortdescription          TEXT,
  date_of_birth             timestamp,
  user_email                varchar(255),
  male                      boolean,
  private_profile           boolean,
  cssid                     integer,
  constraint pk_student primary key (id))
;

create table subtopic_reputation (
  id                        bigint not null,
  reputation_as_astudent    bigint,
  reputation_as_amarker     bigint,
  subtopic_id               bigint,
  user_email                varchar(255),
  constraint pk_subtopic_reputation primary key (id))
;

create table test (
  id                        bigint not null,
  name                      varchar(255),
  text                      TEXT,
  test_image_url            TEXT,
  expected_duration         varchar(255),
  published                 boolean,
  expired                   boolean,
  lesson_id                 bigint,
  begin_date                timestamp,
  finish_date               timestamp,
  markers_limit_date        timestamp,
  constraint pk_test primary key (id))
;

create table topic (
  id                        bigint not null,
  title                     varchar(255),
  forum_id                  bigint,
  starter_email             varchar(255),
  date                      timestamp,
  constraint pk_topic primary key (id))
;

create table url (
  id                        bigint not null,
  adress                    TEXT,
  likes                     integer,
  name                      varchar(255),
  description               varchar(255),
  image_url                 TEXT,
  added                     timestamp,
  question_id               bigint,
  user_email                varchar(255),
  constraint pk_url primary key (id))
;

create table university (
  id                        bigint not null,
  name                      varchar(255),
  acronym                   varchar(255),
  image_url                 TEXT,
  continent_id              bigint,
  constraint uq_university_name unique (name),
  constraint pk_university primary key (id))
;

create table account (
  email                     varchar(255) not null,
  id                        bigint,
  olduser                   boolean,
  name                      varchar(255),
  password                  varchar(255),
  username                  varchar(255),
  country                   varchar(255),
  account_type              integer,
  professor_profile_id      bigint,
  student_profile_id        bigint,
  global_reputation         bigint,
  constraint pk_account primary key (email))
;

create table usertest (
  id                        bigint not null,
  reputation_as_astudent    double,
  reputation_as_amarker     integer,
  inmodule                  boolean,
  submitted                 boolean,
  expired                   boolean,
  reviewd                   boolean,
  user_email                varchar(255),
  test_id                   bigint,
  progress                  float,
  progress_string           varchar(255),
  constraint pk_usertest primary key (id))
;

create table usertopic (
  id                        bigint not null,
  topic_id                  bigint,
  user_email                varchar(255),
  seen                      boolean,
  constraint pk_usertopic primary key (id))
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
create sequence answer_seq;

create sequence answer_marker_seq;

create sequence bibliography_seq;

create sequence blog_seq;

create sequence category_seq;

create sequence comment_seq;

create sequence content_seq;

create sequence continent_seq;

create sequence curriculumlesson_seq;

create sequence curriculummodule_seq;

create sequence curriculumtopic_seq;

create sequence forum_seq;

create sequence hypothesis_seq;

create sequence language_seq;

create sequence lesson_seq;

create sequence lessonalert_seq;

create sequence lessoncontent_seq;

create sequence module_seq;

create sequence modulescore_seq;

create sequence professor_seq;

create sequence professor_content_seq;

create sequence question_seq;

create sequence question_evaluation_seq;

create sequence question_group_seq;

create sequence reply_seq;

create sequence student_seq;

create sequence subtopic_reputation_seq;

create sequence test_seq;

create sequence topic_seq;

create sequence url_seq;

create sequence university_seq;

create sequence account_seq;

create sequence usertest_seq;

create sequence usertopic_seq;

alter table answer add constraint fk_answer_openQuestion_1 foreign key (open_question_id) references question (id) on delete restrict on update restrict;
create index ix_answer_openQuestion_1 on answer (open_question_id);
alter table answer add constraint fk_answer_test_2 foreign key (test_id) references test (id) on delete restrict on update restrict;
create index ix_answer_test_2 on answer (test_id);
alter table answer add constraint fk_answer_usertest_3 foreign key (usertest_id) references usertest (id) on delete restrict on update restrict;
create index ix_answer_usertest_3 on answer (usertest_id);
alter table answer add constraint fk_answer_group_4 foreign key (group_id) references question_group (id) on delete restrict on update restrict;
create index ix_answer_group_4 on answer (group_id);
alter table answer add constraint fk_answer_answerMarker_5 foreign key (answer_marker_id) references answer_marker (id) on delete restrict on update restrict;
create index ix_answer_answerMarker_5 on answer (answer_marker_id);
alter table answer add constraint fk_answer_questionevaluation_6 foreign key (questionevaluation_id) references question_evaluation (id) on delete restrict on update restrict;
create index ix_answer_questionevaluation_6 on answer (questionevaluation_id);
alter table answer_marker add constraint fk_answer_marker_answer_7 foreign key (answer_id) references answer (id) on delete restrict on update restrict;
create index ix_answer_marker_answer_7 on answer_marker (answer_id);
alter table answer_marker add constraint fk_answer_marker_user_8 foreign key (user_email) references account (email) on delete restrict on update restrict;
create index ix_answer_marker_user_8 on answer_marker (user_email);
alter table bibliography add constraint fk_bibliography_module_9 foreign key (module_id) references module (id) on delete restrict on update restrict;
create index ix_bibliography_module_9 on bibliography (module_id);
alter table comment add constraint fk_comment_blog_10 foreign key (blog_id) references blog (id) on delete restrict on update restrict;
create index ix_comment_blog_10 on comment (blog_id);
alter table comment add constraint fk_comment_user_11 foreign key (user_email) references account (email) on delete restrict on update restrict;
create index ix_comment_user_11 on comment (user_email);
alter table content add constraint fk_content_module_12 foreign key (module_id) references module (id) on delete restrict on update restrict;
create index ix_content_module_12 on content (module_id);
alter table forum add constraint fk_forum_module_13 foreign key (module_id) references module (id) on delete restrict on update restrict;
create index ix_forum_module_13 on forum (module_id);
alter table hypothesis add constraint fk_hypothesis_question_14 foreign key (question_id) references question (id) on delete restrict on update restrict;
create index ix_hypothesis_question_14 on hypothesis (question_id);
alter table hypothesis add constraint fk_hypothesis_user_15 foreign key (user_email) references account (email) on delete restrict on update restrict;
create index ix_hypothesis_user_15 on hypothesis (user_email);
alter table lesson add constraint fk_lesson_module_16 foreign key (module_id) references module (id) on delete restrict on update restrict;
create index ix_lesson_module_16 on lesson (module_id);
alter table lessonalert add constraint fk_lessonalert_lesson_17 foreign key (lesson_id) references lesson (id) on delete restrict on update restrict;
create index ix_lessonalert_lesson_17 on lessonalert (lesson_id);
alter table lessoncontent add constraint fk_lessoncontent_lesson_18 foreign key (lesson_id) references lesson (id) on delete restrict on update restrict;
create index ix_lessoncontent_lesson_18 on lessoncontent (lesson_id);
alter table module add constraint fk_module_university_19 foreign key (university_id) references university (id) on delete restrict on update restrict;
create index ix_module_university_19 on module (university_id);
alter table module add constraint fk_module_language_20 foreign key (language_id) references language (id) on delete restrict on update restrict;
create index ix_module_language_20 on module (language_id);
alter table module add constraint fk_module_forum_21 foreign key (forum_id) references forum (id) on delete restrict on update restrict;
create index ix_module_forum_21 on module (forum_id);
alter table modulescore add constraint fk_modulescore_module_22 foreign key (module_id) references module (id) on delete restrict on update restrict;
create index ix_modulescore_module_22 on modulescore (module_id);
alter table modulescore add constraint fk_modulescore_user_23 foreign key (user_email) references account (email) on delete restrict on update restrict;
create index ix_modulescore_user_23 on modulescore (user_email);
alter table professor add constraint fk_professor_user_24 foreign key (user_email) references account (email) on delete restrict on update restrict;
create index ix_professor_user_24 on professor (user_email);
alter table professor_content add constraint fk_professor_content_professo_25 foreign key (professor_id) references professor (id) on delete restrict on update restrict;
create index ix_professor_content_professo_25 on professor_content (professor_id);
alter table question add constraint fk_question_lesson_26 foreign key (lesson_id) references lesson (id) on delete restrict on update restrict;
create index ix_question_lesson_26 on question (lesson_id);
alter table question add constraint fk_question_user_27 foreign key (user_email) references account (email) on delete restrict on update restrict;
create index ix_question_user_27 on question (user_email);
alter table question add constraint fk_question_openanswer_28 foreign key (openanswer_id) references answer (id) on delete restrict on update restrict;
create index ix_question_openanswer_28 on question (openanswer_id);
alter table question add constraint fk_question_subtopic_29 foreign key (subtopic_id) references curriculummodule (id) on delete restrict on update restrict;
create index ix_question_subtopic_29 on question (subtopic_id);
alter table question_evaluation add constraint fk_question_evaluation_userte_30 foreign key (usertest_id) references usertest (id) on delete restrict on update restrict;
create index ix_question_evaluation_userte_30 on question_evaluation (usertest_id);
alter table question_evaluation add constraint fk_question_evaluation_questi_31 foreign key (question_id) references question (id) on delete restrict on update restrict;
create index ix_question_evaluation_questi_31 on question_evaluation (question_id);
alter table question_evaluation add constraint fk_question_evaluation_answer_32 foreign key (answer_id) references answer (id) on delete restrict on update restrict;
create index ix_question_evaluation_answer_32 on question_evaluation (answer_id);
alter table question_evaluation add constraint fk_question_evaluation_profes_33 foreign key (professormarker_id) references professor (id) on delete restrict on update restrict;
create index ix_question_evaluation_profes_33 on question_evaluation (professormarker_id);
alter table question_group add constraint fk_question_group_test_34 foreign key (test_id) references test (id) on delete restrict on update restrict;
create index ix_question_group_test_34 on question_group (test_id);
alter table reply add constraint fk_reply_topic_35 foreign key (topic_id) references topic (id) on delete restrict on update restrict;
create index ix_reply_topic_35 on reply (topic_id);
alter table reply add constraint fk_reply_user_36 foreign key (user_email) references account (email) on delete restrict on update restrict;
create index ix_reply_user_36 on reply (user_email);
alter table student add constraint fk_student_university_37 foreign key (university_id) references university (id) on delete restrict on update restrict;
create index ix_student_university_37 on student (university_id);
alter table student add constraint fk_student_user_38 foreign key (user_email) references account (email) on delete restrict on update restrict;
create index ix_student_user_38 on student (user_email);
alter table subtopic_reputation add constraint fk_subtopic_reputation_subtop_39 foreign key (subtopic_id) references curriculummodule (id) on delete restrict on update restrict;
create index ix_subtopic_reputation_subtop_39 on subtopic_reputation (subtopic_id);
alter table subtopic_reputation add constraint fk_subtopic_reputation_user_40 foreign key (user_email) references account (email) on delete restrict on update restrict;
create index ix_subtopic_reputation_user_40 on subtopic_reputation (user_email);
alter table test add constraint fk_test_lesson_41 foreign key (lesson_id) references lesson (id) on delete restrict on update restrict;
create index ix_test_lesson_41 on test (lesson_id);
alter table topic add constraint fk_topic_forum_42 foreign key (forum_id) references forum (id) on delete restrict on update restrict;
create index ix_topic_forum_42 on topic (forum_id);
alter table topic add constraint fk_topic_starter_43 foreign key (starter_email) references account (email) on delete restrict on update restrict;
create index ix_topic_starter_43 on topic (starter_email);
alter table url add constraint fk_url_question_44 foreign key (question_id) references question (id) on delete restrict on update restrict;
create index ix_url_question_44 on url (question_id);
alter table url add constraint fk_url_user_45 foreign key (user_email) references account (email) on delete restrict on update restrict;
create index ix_url_user_45 on url (user_email);
alter table university add constraint fk_university_continent_46 foreign key (continent_id) references continent (id) on delete restrict on update restrict;
create index ix_university_continent_46 on university (continent_id);
alter table account add constraint fk_account_professorProfile_47 foreign key (professor_profile_id) references professor (id) on delete restrict on update restrict;
create index ix_account_professorProfile_47 on account (professor_profile_id);
alter table account add constraint fk_account_studentProfile_48 foreign key (student_profile_id) references student (id) on delete restrict on update restrict;
create index ix_account_studentProfile_48 on account (student_profile_id);
alter table usertest add constraint fk_usertest_user_49 foreign key (user_email) references account (email) on delete restrict on update restrict;
create index ix_usertest_user_49 on usertest (user_email);
alter table usertest add constraint fk_usertest_test_50 foreign key (test_id) references test (id) on delete restrict on update restrict;
create index ix_usertest_test_50 on usertest (test_id);
alter table usertopic add constraint fk_usertopic_topic_51 foreign key (topic_id) references topic (id) on delete restrict on update restrict;
create index ix_usertopic_topic_51 on usertopic (topic_id);
alter table usertopic add constraint fk_usertopic_user_52 foreign key (user_email) references account (email) on delete restrict on update restrict;
create index ix_usertopic_user_52 on usertopic (user_email);



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

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists answer;

drop table if exists answer_marker;

drop table if exists bibliography;

drop table if exists blog;

drop table if exists category;

drop table if exists module_category;

drop table if exists category_curriculummodule;

drop table if exists comment;

drop table if exists content;

drop table if exists continent;

drop table if exists curriculumlesson;

drop table if exists curriculumlesson_curriculumtopic;

drop table if exists curriculummodule;

drop table if exists curriculummodule_curriculumlesso;

drop table if exists curriculumtopic;

drop table if exists forum;

drop table if exists hypothesis;

drop table if exists language;

drop table if exists lesson;

drop table if exists lessonalert;

drop table if exists lessoncontent;

drop table if exists module;

drop table if exists account_module;

drop table if exists professor_module;

drop table if exists modulescore;

drop table if exists professor;

drop table if exists professor_content;

drop table if exists question;

drop table if exists question_group_question;

drop table if exists question_evaluation;

drop table if exists question_group;

drop table if exists reply;

drop table if exists student;

drop table if exists subtopic_reputation;

drop table if exists test;

drop table if exists topic;

drop table if exists url;

drop table if exists university;

drop table if exists account;

drop table if exists usertest;

drop table if exists usertopic;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists answer_seq;

drop sequence if exists answer_marker_seq;

drop sequence if exists bibliography_seq;

drop sequence if exists blog_seq;

drop sequence if exists category_seq;

drop sequence if exists comment_seq;

drop sequence if exists content_seq;

drop sequence if exists continent_seq;

drop sequence if exists curriculumlesson_seq;

drop sequence if exists curriculummodule_seq;

drop sequence if exists curriculumtopic_seq;

drop sequence if exists forum_seq;

drop sequence if exists hypothesis_seq;

drop sequence if exists language_seq;

drop sequence if exists lesson_seq;

drop sequence if exists lessonalert_seq;

drop sequence if exists lessoncontent_seq;

drop sequence if exists module_seq;

drop sequence if exists modulescore_seq;

drop sequence if exists professor_seq;

drop sequence if exists professor_content_seq;

drop sequence if exists question_seq;

drop sequence if exists question_evaluation_seq;

drop sequence if exists question_group_seq;

drop sequence if exists reply_seq;

drop sequence if exists student_seq;

drop sequence if exists subtopic_reputation_seq;

drop sequence if exists test_seq;

drop sequence if exists topic_seq;

drop sequence if exists url_seq;

drop sequence if exists university_seq;

drop sequence if exists account_seq;

drop sequence if exists usertest_seq;

drop sequence if exists usertopic_seq;

