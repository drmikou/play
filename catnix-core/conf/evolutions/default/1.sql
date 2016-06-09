# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table user (
  user_id                   bigint not null,
  first_name                varchar(255),
  last_name                 varchar(255),
  promotion                 integer,
  active                    boolean,
  stranger                  boolean,
  sect_inter                boolean,
  phone                     varchar(255),
  mail                      varchar(255),
  mail_alt                  varchar(255),
  adresse                   varchar(255),
  linkedin_id               varchar(255),
  poste_csv                 varchar(255),
  poste_hierarchy           integer,
  login                     varchar(255),
  constraint pk_user primary key (user_id))
;

create table user_checkout (
  checkout_id               bigint not null,
  checkout_date             timestamp,
  demission                 boolean,
  leave_note                TEXT,
  user_user_id              bigint,
  constraint uq_user_checkout_user_user_id unique (user_user_id),
  constraint pk_user_checkout primary key (checkout_id))
;

create table user_permission (
  id                        bigint not null,
  value                     varchar(255) not null,
  constraint uq_user_permission_value unique (value),
  constraint pk_user_permission primary key (id))
;

create table user_registration (
  registration_id           bigint not null,
  registration_date         timestamp,
  no_secu                   bigint,
  with_partner              boolean,
  cet                       varchar(255),
  identite                  varchar(255),
  secu                      varchar(255),
  certif_scolarite          varchar(255),
  cotisation                varchar(255),
  visa                      varchar(255),
  inscription_complete      boolean,
  user_user_id              bigint,
  constraint uq_user_registration_user_user_i unique (user_user_id),
  constraint pk_user_registration primary key (registration_id))
;


create table user_user_permission (
  user_user_id                   bigint not null,
  user_permission_id             bigint not null,
  constraint pk_user_user_permission primary key (user_user_id, user_permission_id))
;
create sequence user_seq;

create sequence user_checkout_seq;

create sequence user_permission_seq;

create sequence user_registration_seq;

alter table user_checkout add constraint fk_user_checkout_user_1 foreign key (user_user_id) references user (user_id) on delete restrict on update restrict;
create index ix_user_checkout_user_1 on user_checkout (user_user_id);
alter table user_registration add constraint fk_user_registration_user_2 foreign key (user_user_id) references user (user_id) on delete restrict on update restrict;
create index ix_user_registration_user_2 on user_registration (user_user_id);



alter table user_user_permission add constraint fk_user_user_permission_user_01 foreign key (user_user_id) references user (user_id) on delete restrict on update restrict;

alter table user_user_permission add constraint fk_user_user_permission_user__02 foreign key (user_permission_id) references user_permission (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists user;

drop table if exists user_user_permission;

drop table if exists user_checkout;

drop table if exists user_permission;

drop table if exists user_registration;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists user_seq;

drop sequence if exists user_checkout_seq;

drop sequence if exists user_permission_seq;

drop sequence if exists user_registration_seq;

