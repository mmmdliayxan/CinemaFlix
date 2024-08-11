CREATE TABLE customers
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(255),
    surname      VARCHAR(255),
    email        VARCHAR(255),
    phone_number VARCHAR(20),
    status       INT NOT NULL DEFAULT 1
);