CREATE TABLE reservation_seats
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    reservation_id BIGINT,
    seat_number    INT NOT NULL,
    CONSTRAINT FK755roqq37bto59vxaxis9x3nt
        FOREIGN KEY (reservation_id) REFERENCES reservations (id)
);