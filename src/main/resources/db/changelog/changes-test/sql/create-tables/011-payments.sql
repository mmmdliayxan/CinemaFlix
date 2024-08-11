CREATE TABLE payments
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    reservation_id  BIGINT,
    payment_date    DATETIME,
    total_price     BIGINT,
    card_details_id BIGINT,
    status          INT NOT NULL DEFAULT 1,
    CONSTRAINT FKp8yh4sjt3u0g6aru1oxfh3o14
        FOREIGN KEY (reservation_id) REFERENCES reservations (id),
    CONSTRAINT FKblbi19m92q4cff3i0sk7vkl92
        FOREIGN KEY (card_details_id) REFERENCES card_details (id)
);
