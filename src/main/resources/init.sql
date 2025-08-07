CREATE TABLE pokemons.trainers
(
    id         INT     NOT NULL AUTO_INCREMENT,
    name       VARCHAR(45) NULL,
    isCatching TINYINT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE pokemons.pokemons
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    trainer_id INT,
    name       VARCHAR(255),
    level      INT NOT NULL,
    FOREIGN KEY (trainer_id) REFERENCES trainers (id)
);

INSERT INTO trainers (name, isCatching)
VALUES ('Ash', 1),
       ('Misty', 0),
       ('Brock', 1);

INSERT INTO pokemons (name, level, trainer_id)
VALUES ('Pikachu', 25, 1),
       ('Charizard', 36, 1),
       ('Psyduck', 18, 2),
       ('Geodude', 22, 3),
       ('Onix', 30, 3),
       ('Eevee', 12, NULL),
       ('Snorlax', 40, NULL),
       ('Magikarp', 5, NULL),
       ('Jigglypuff', 0, NULL),
       ('Meowth', 20, NULL);