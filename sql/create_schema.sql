USE `certificate_db`;

CREATE TABLE `tag`
(
    `id`   INTEGER      NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL UNIQUE,
    PRIMARY KEY (`id`)
);

CREATE TABLE `gift_certificate`
(
    `id`               INTEGER      NOT NULL AUTO_INCREMENT,
    `name`             VARCHAR(255) NOT NULL,
    `description`      VARCHAR(400),
    `cost`             DECIMAL(10, 4),
    `currency`         TINYINT CHECK (`currency` IN (0, 1, 2, 3)),
    `duration`         BIGINT,
    `create_date`      DATETIME,
    `last_update_date` DATETIME,
    PRIMARY KEY (`id`)
);

CREATE TABLE `tag_gift_certificate`
(
    `tag_id`              INTEGER NOT NULL,
    `gift_certificate_id` INTEGER NOT NULL,
    FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE CASCADE ,
    FOREIGN KEY (`gift_certificate_id`) REFERENCES `gift_certificate` (`id`) ON DELETE CASCADE,
    PRIMARY KEY (`tag_id`, `gift_certificate_id`)
);