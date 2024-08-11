CREATE TABLE halls
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(255) NOT NULL,
    capacity  INT,
    cinema_id BIGINT,
    CONSTRAINT FKpst04yq0t1iyprvitond7ly34
        FOREIGN KEY (cinema_id) REFERENCES cinemas (id)
);