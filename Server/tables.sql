create table if not exists user
(
    id        varchar(25) primary key,
    nickname  varchar(50),
    desc      varchar(200),
    pwHash    varchar(64),
    avatar    varchar(200),
    publicKey varchar(183)
);

create table if not exists user_config
(
    id                varchar(25) primary key references user on delete cascade,
    acceptRequest     boolean default 1,
    acceptDMFromGroup boolean default 1,
    autoJoinGroup     boolean default 1
);

create table if not exists message_queue
(
    id     varchar(25) primary key references user on delete cascade,
    type   int,
    sender text,
    data   text
);

create table file_metadata
(
    hash varchar(32) primary key,
    name text,
    size int
);

create table file_permission
(
    hash varchar(32) references file_metadata,
    user varchar(25) references user(id),
    primary key (hash,user)

)