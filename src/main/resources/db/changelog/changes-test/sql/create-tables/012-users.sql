CREATE TABLE users
(
    id                         BIGINT AUTO_INCREMENT PRIMARY KEY,
    username                   VARCHAR(255) NOT NULL,
    password                   VARCHAR(255) NOT NULL,
    customer_id                BIGINT,
    is_account_non_expired     BOOLEAN                     DEFAULT TRUE,
    is_account_non_locked      BOOLEAN                     DEFAULT TRUE,
    is_credentials_non_expired BOOLEAN                     DEFAULT TRUE,
    is_enabled                 BOOLEAN                     DEFAULT TRUE,
    status                     ENUM ('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    CONSTRAINT FKchxdoybbydcaj5smgxe0qq5mk
        FOREIGN KEY (customer_id) REFERENCES customers (id)
);