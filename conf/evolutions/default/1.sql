# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table depot (
  id                            bigint auto_increment not null,
  nom                           varchar(255),
  commentaire                   varchar(255),
  datedepot                     datetime(6),
  equipe_id                     bigint,
  constraint pk_depot primary key (id)
);

create table equipe (
  id                            bigint auto_increment not null,
  nom                           varchar(255),
  groupe_id                     bigint,
  constraint pk_equipe primary key (id)
);

create table fonctionnalite (
  id                            bigint auto_increment not null,
  nom                           varchar(255),
  equipe_id                     bigint,
  trouve                        tinyint(1) default 0,
  constraint pk_fonctionnalite primary key (id)
);

create table groupe (
  id                            bigint auto_increment not null,
  nom                           varchar(255),
  session_id                    bigint,
  constraint pk_groupe primary key (id)
);

create table projet (
  id                            bigint auto_increment not null,
  nom                           varchar(255),
  equipe_id                     bigint,
  constraint pk_projet primary key (id)
);

create table promotion (
  id                            bigint auto_increment not null,
  annee                         integer,
  constraint pk_promotion primary key (id)
);

create table reunion (
  id                            bigint auto_increment not null,
  nom                           varchar(255),
  date                          datetime(6),
  equipe_id                     bigint,
  constraint pk_reunion primary key (id)
);

create table session (
  id                            bigint auto_increment not null,
  nom                           varchar(255),
  promotion_id                  bigint,
  constraint pk_session primary key (id)
);

create table user (
  id                            bigint auto_increment not null,
  nom                           varchar(255),
  prenom                        varchar(255),
  equipe_id                     bigint,
  constraint pk_user primary key (id)
);

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

alter table depot drop foreign key fk_depot_equipe_id;
drop index ix_depot_equipe_id on depot;

alter table equipe drop foreign key fk_equipe_groupe_id;
drop index ix_equipe_groupe_id on equipe;

alter table fonctionnalite drop foreign key fk_fonctionnalite_equipe_id;
drop index ix_fonctionnalite_equipe_id on fonctionnalite;

alter table groupe drop foreign key fk_groupe_session_id;
drop index ix_groupe_session_id on groupe;

alter table projet drop foreign key fk_projet_equipe_id;
drop index ix_projet_equipe_id on projet;

alter table reunion drop foreign key fk_reunion_equipe_id;
drop index ix_reunion_equipe_id on reunion;

alter table session drop foreign key fk_session_promotion_id;
drop index ix_session_promotion_id on session;

alter table user drop foreign key fk_user_equipe_id;
drop index ix_user_equipe_id on user;

drop table if exists depot;

drop table if exists equipe;

drop table if exists fonctionnalite;

drop table if exists groupe;

drop table if exists projet;

drop table if exists promotion;

drop table if exists reunion;

drop table if exists session;

drop table if exists user;

