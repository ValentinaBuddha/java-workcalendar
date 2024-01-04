CREATE TABLE IF NOT EXISTS users (
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    surname    VARCHAR(50) NOT NULL,
    name       VARCHAR(50) NOT NULL,
    email      VARCHAR(512) UNIQUE NOT NULL,
    position   VARCHAR(255) NOT NULL,
    department VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS events(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    title        VARCHAR(255) NOT NULL,
    start_date   TIMESTAMP,
    end_date     TIMESTAMP,
    location     VARCHAR(255) NOT NULL,
    description  VARCHAR(7000) NOT NULL,
    initiator_id BIGINT REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS requests (
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    initiator_id   BIGINT REFERENCES users(id),
    event_id       BIGINT REFERENCES events(id),
    participant_id BIGINT REFERENCES users(id),
    status         VARCHAR(50)
);
