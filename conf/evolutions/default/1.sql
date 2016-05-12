# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table promotion (
  id                            bigint auto_increment not null,
  annee                         integer,
  constraint pk_promotion primary key (id)
);

create table session (
  id                            bigint auto_increment not null,
  nom                           varchar(255),
  constraint pk_session primary key (id)
);


# --- !Downs

drop table if exists promotion;

drop table if exists session;

