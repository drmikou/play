# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table depot (
  id                            bigint not null,
  nom                           varchar(255),
  commentaire                   varchar(255),
  datedepot                     timestamp,
  equipe_id                     bigint,
  constraint pk_depot primary key (id)
);
create sequence depot_seq;

create table equipe (
  id                            bigint not null,
  nom                           varchar(255),
  groupe_id                     bigint,
  constraint pk_equipe primary key (id)
);
create sequence equipe_seq;

create table fonctionnalite (
  id                            bigint not null,
  nom                           varchar(255),
  equipe_id                     bigint,
  trouve                        boolean,
  constraint pk_fonctionnalite primary key (id)
);
create sequence fonctionnalite_seq;

create table groupe (
  id                            bigint not null,
  nom                           varchar(255),
  session_id                    bigint,
  constraint pk_groupe primary key (id)
);
create sequence groupe_seq;

create table projet (
  id                            bigint not null,
  nom                           varchar(255),
  equipe_id                     bigint,
  constraint pk_projet primary key (id)
);
create sequence projet_seq;

create table promotion (
  id                            bigint not null,
  annee                         integer,
  constraint pk_promotion primary key (id)
);
create sequence promotion_seq;

create table reunion (
  id                            bigint not null,
  nom                           varchar(255),
  date                          timestamp,
  equipe_id                     bigint,
  constraint pk_reunion primary key (id)
);
create sequence reunion_seq;

create table session (
  id                            bigint not null,
  nom                           varchar(255),
  promotion_id                  bigint,
  constraint pk_session primary key (id)
);
create sequence session_seq;

create table user (
  id                            bigint not null,
  nom                           varchar(255),
  prenom                        varchar(255),
  equipe_id                     bigint,
  constraint pk_user primary key (id)
);
create sequence user_seq;

alter table depot add constraint fk_depot_equipe_id foreign key (equipe_id) references equipe (id) on delete restrict on update restrict;
create index ix_depot_equipe_id on depot (equipe_id);

alter table equipe add constraint fk_equipe_groupe_id foreign key (groupe_id) references groupe (id) on delete restrict on update restrict;
create index ix_equipe_groupe_id on equipe (groupe_id);

alter table fonctionnalite add constraint fk_fonctionnalite_equipe_id foreign key (equipe_id) references equipe (id) on delete restrict on update restrict;
create index ix_fonctionnalite_equipe_id on fonctionnalite (equipe_id);

alter table groupe add constraint fk_groupe_session_id foreign key (session_id) references session (id) on delete restrict on update restrict;
create index ix_groupe_session_id on groupe (session_id);

alter table projet add constraint fk_projet_equipe_id foreign key (equipe_id) references equipe (id) on delete restrict on update restrict;
create index ix_projet_equipe_id on projet (equipe_id);

alter table reunion add constraint fk_reunion_equipe_id foreign key (equipe_id) references equipe (id) on delete restrict on update restrict;
create index ix_reunion_equipe_id on reunion (equipe_id);

alter table session add constraint fk_session_promotion_id foreign key (promotion_id) references promotion (id) on delete restrict on update restrict;
create index ix_session_promotion_id on session (promotion_id);

alter table user add constraint fk_user_equipe_id foreign key (equipe_id) references equipe (id) on delete restrict on update restrict;
create index ix_user_equipe_id on user (equipe_id);


# --- !Downs

alter table depot drop constraint if exists fk_depot_equipe_id;
drop index if exists ix_depot_equipe_id;

alter table equipe drop constraint if exists fk_equipe_groupe_id;
drop index if exists ix_equipe_groupe_id;

alter table fonctionnalite drop constraint if exists fk_fonctionnalite_equipe_id;
drop index if exists ix_fonctionnalite_equipe_id;

alter table groupe drop constraint if exists fk_groupe_session_id;
drop index if exists ix_groupe_session_id;

alter table projet drop constraint if exists fk_projet_equipe_id;
drop index if exists ix_projet_equipe_id;

alter table reunion drop constraint if exists fk_reunion_equipe_id;
drop index if exists ix_reunion_equipe_id;

alter table session drop constraint if exists fk_session_promotion_id;
drop index if exists ix_session_promotion_id;

alter table user drop constraint if exists fk_user_equipe_id;
drop index if exists ix_user_equipe_id;

drop table if exists depot;
drop sequence if exists depot_seq;

drop table if exists equipe;
drop sequence if exists equipe_seq;

drop table if exists fonctionnalite;
drop sequence if exists fonctionnalite_seq;

drop table if exists groupe;
drop sequence if exists groupe_seq;

drop table if exists projet;
drop sequence if exists projet_seq;

drop table if exists promotion;
drop sequence if exists promotion_seq;

drop table if exists reunion;
drop sequence if exists reunion_seq;

drop table if exists session;
drop sequence if exists session_seq;

drop table if exists user;
drop sequence if exists user_seq;

