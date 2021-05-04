INSERT INTO tag(name)
VALUES ('relax'),
       ('cheap'),
       ('spa');

INSERT INTO gift_certificate(name, description, cost, currency, duration, create_date, last_update_date)
VALUES ('Name1', 'one film', 9, 3, 6, timestamp '2021-05-01 18:35:37', timestamp '2021-05-01 18:35:37'),
       ('Certificate2', 'Second Description', 3, 2, 8, timestamp '2021-05-01 18:35:37', timestamp '2021-05-01 18:35:37'),
       ('Certificate3', 'Third Description', 3, 2, 8, timestamp '2021-05-01 18:35:37', timestamp '2021-05-01 18:35:37');


INSERT INTO tag_gift_certificate(gift_certificate_id, tag_id)
VALUES (1, 3),
       (2, 1),
       (2, 2),
       (2, 3);