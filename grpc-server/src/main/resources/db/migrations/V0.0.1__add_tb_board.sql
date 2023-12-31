create table member (
    id              BIGINT          generated by default as identity,
    category_id     VARCHAR(255),
    title           VARCHAR(255),
    content         CLOB(10k),
    status          VARCHAR(255),
    created_at      TIMESTAMP,
    updated_at      TIMESTAMP,
    primary key (id)
);