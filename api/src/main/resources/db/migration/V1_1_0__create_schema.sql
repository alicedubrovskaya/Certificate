CREATE TABLE tag
(
    id   SERIAL       NOT NULL,
    name VARCHAR(255) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE gift_certificate
(
    id               SERIAL       NOT NULL,
    name             VARCHAR(255) NOT NULL,
    description      VARCHAR(400),
    cost             DECIMAL(10, 4),
    currency         SMALLINT CHECK (currency IN (0, 1, 2, 3)),
    duration         BIGINT,
    create_date      timestamp,
    last_update_date timestamp,
    PRIMARY KEY (id)
);

CREATE TABLE tag_gift_certificate
(
    tag_id              INTEGER NOT NULL,
    gift_certificate_id INTEGER NOT NULL,
    FOREIGN KEY (tag_id) REFERENCES tag (id) ON DELETE CASCADE,
    FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificate (id) ON DELETE CASCADE,
    PRIMARY KEY (tag_id, gift_certificate_id)
);