CREATE TABLE cinemas
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    location     VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20),
    open_time    TIME,
    close_time   TIME
);