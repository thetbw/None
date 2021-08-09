-- apply changes
create table sys_user (
  id                            varchar(255) not null,
  name                          varchar(32) not null,
  nick_name                     varchar(32),
  email                         varchar(128),
  gender                        integer not null,
  role                          varchar(255),
  when_created                  timestamp not null,
  when_modified                 timestamp not null,
  deleted                       boolean default false not null,
  constraint pk_sys_user primary key (id)
);

