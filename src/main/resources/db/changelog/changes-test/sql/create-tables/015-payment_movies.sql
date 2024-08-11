CREATE TABLE payment_movies
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    movie_id        BIGINT,
    user_id         BIGINT,
    payment_date    DATETIME,
    price           INT,
    card_details_id BIGINT,
    status          INT NOT NULL DEFAULT 1,
    CONSTRAINT FKs8cu4cd7g17x4wfp7elon9wc4
        FOREIGN KEY (movie_id) REFERENCES movies (id),
    CONSTRAINT FKen8yhoeittgedo5q6wr63ai11
        FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT FKee2iswp3t9ryards8nuq04gec
        FOREIGN KEY (card_details_id) REFERENCES card_details (id)
);