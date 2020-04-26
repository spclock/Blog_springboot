create table `blog`
(
    id          bigint primary key auto_increment,
    title       varchar(100),
    description varchar(150),
    content     TEXT,
    user_id     bigint,
    created_at  datetime,
    updated_at  datetime
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
-- charset=utf8mb4;