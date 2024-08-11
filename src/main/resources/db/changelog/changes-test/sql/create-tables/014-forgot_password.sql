CREATE TABLE forgot_password
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    token           VARCHAR(255) NOT NULL,
    expiration_date DATETIME,
    user_id         BIGINT,
    CONSTRAINT FKjfa13lhndn1q66kheuyjk2i5l
        FOREIGN KEY (user_id) REFERENCES users (id)
);