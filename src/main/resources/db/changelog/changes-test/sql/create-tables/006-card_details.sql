CREATE TABLE card_details
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id  BIGINT,
    bank_account VARCHAR(16) NOT NULL,
    cvv          INT         NOT NULL,
    expiry_date  DATE        NOT NULL,
    CONSTRAINT FKd4bn6lxfd34wo3lacfuyf3jqy
        FOREIGN KEY (customer_id) REFERENCES customers (id)
);