CREATE TABLE reservations
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id      BIGINT,
    schedule_id      BIGINT,
    ticket_count     INT CHECK (ticket_count <= 10),
    ticket_price     INT,
    reservation_date DATETIME,
    status           ENUM ('PENDING', 'CANCELED', 'CONFIRM') DEFAULT 'PENDING',
    CONSTRAINT FK8eccffekcj27jkdiyw2e9r8ks
        FOREIGN KEY (customer_id) REFERENCES customers (id),
    CONSTRAINT FK18e32ynjgl2tsxq1d9jpp6erg
        FOREIGN KEY (schedule_id) REFERENCES schedules (id)
);