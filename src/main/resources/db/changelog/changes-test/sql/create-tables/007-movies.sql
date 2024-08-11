CREATE TABLE movies
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    title        VARCHAR(255) NOT NULL,
    description  TEXT,
    release_date DATE,
    category_id  BIGINT,
    movie_price  INT,
    CONSTRAINT FKn00vsw85tow1xyebya4hih3ke
        FOREIGN KEY (category_id) REFERENCES categories (id)
);
