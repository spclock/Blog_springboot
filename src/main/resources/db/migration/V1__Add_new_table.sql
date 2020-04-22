create table `user`
(
    id                 bigint primary key auto_increment,
    username           varchar(100),
    encrypted_password varchar(150),
    avatar             varchar(300),
    created_at         datetime,
    updated_at         datetime
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;
-- charset=utf8mb4;