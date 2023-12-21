CREATE TABLE IF NOT EXISTS users (
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    surname    VARCHAR(255) NOT NULL,
    name       VARCHAR(255) NOT NULL,
    email      VARCHAR(512) UNIQUE NOT NULL,
    position   VARCHAR(255) NOT NULL,
    department VARCHAR(255) NOT NULL
);