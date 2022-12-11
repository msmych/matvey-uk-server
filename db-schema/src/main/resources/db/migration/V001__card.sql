create table if not exists card(
    id              uuid            not null primary key,
    type            text            not null,
    title           text            not null,
    url             text            null,
    created_at      timestamp       not null,
    updated_at      timestamp       not null
);