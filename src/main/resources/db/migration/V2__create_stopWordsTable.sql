CREATE TABLE stop_words
(
    id         SERIAL PRIMARY KEY,
    word_value VARCHAR(100) NOT NULL UNIQUE
);
INSERT INTO stop_words(word_value)
VALUES ('а'),
       ('на'),
       ('у'),
       ('по'),
       ('с'),
       ('под'),
       ('над'),
       ('в');
