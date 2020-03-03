create table discussion
(
    discussion_id CHAR(16) FOR BIT DATA not null,
    created       timestamp             not null,
    name          varchar(1024),
    text          varchar(4096)         not null,
    updated       timestamp             not null,
    primary key (discussion_id)
);
create table keyword
(
    keyword_id CHAR(16) FOR BIT DATA not null,
    created    timestamp             not null,
    name       varchar(1024)         not null,
    updated    timestamp             not null,
    primary key (keyword_id)
);
create table user_client
(
    user_id CHAR(16) FOR BIT DATA not null,
    created timestamp             not null,
    text    varchar(4096)         not null,
    updated timestamp             not null,
    primary key (user_id)
);
create index IDXaarrsdrssdpj0lv4br81x4i7v on discussion (created);
create unique index UK_8atiou53wmjov911aq6wgcsuu on discussion (name);
alter table keyword
    add constraint UK_hvq9bm3mbguqoicyv02g5crjs unique (name);
create index IDX1is2ofqlamqj61qfoq2km5c2h on user_client (created);
alter table user_client
    add constraint UK_hors5sq0k5mli3g9xsixm7ghl unique (text);
