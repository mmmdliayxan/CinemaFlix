CREATE TABLE schedules
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    movie_id     BIGINT,
    start_time   DATETIME,
    hall_id      BIGINT,
    ticket_price INT,
    CONSTRAINT FKrn994bufm9lvyguq5enr8pua2
        FOREIGN KEY (movie_id) REFERENCES movies (id),
    CONSTRAINT FK4r8dtcn1kglg9dntstyrp0cfs
        FOREIGN KEY (hall_id) REFERENCES halls (id)
);