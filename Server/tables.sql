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
    owner varchar(25) references user (id) on delete cascade,
    id    int,
    data  blob,
    primary key (owner, id)
);

create table file_metadata
(
    hash varchar(32) primary key,
    name text,
    size int
);

create table file_permission
(
    hash varchar(32) references file_metadata on delete cascade,
    user varchar(25) references user (id) on delete cascade,
    primary key (hash, user)

);

create table if not exists friend
(
    one    varchar(25) references user (id) on delete cascade,
    two    varchar(25) references user (id) on delete cascade,
    remark blob,
    primary key (one, two)
);

create table if not exists pending_invitation
(
    one    varchar(25) references user (id) on delete cascade,
    two    varchar(25) references user (id) on delete cascade,
    primary key (one, two)
);