CREATE TABLE user_authorities
(
    user_id        BIGINT,
    authority_name VARCHAR(255),
    CONSTRAINT FKhiiib540jf74gksgb87oofni
        FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT FK93l3o3nqoog62d07mtjxy6sem
        FOREIGN KEY (authority_name) REFERENCES authorities (type),
    PRIMARY KEY (user_id, authority_name)
);