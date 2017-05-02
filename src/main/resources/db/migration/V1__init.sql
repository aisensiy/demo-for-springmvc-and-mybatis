create table users (
  id varchar(255) primary key,
  username varchar(255) not null,
  unique key (username)
);
