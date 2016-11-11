INSERT INTO user_lab VALUES ('jrjrjr', 'Josep', 'Roure', 'roure@tecnocampus.cat');
INSERT INTO user_lab VALUES ('sergi', 'Sergi', 'A', 'sergi@mail.com');
INSERT INTO user_lab VALUES ('jpjpjp', 'Josep', 'Pineda', 'pi@tecnocampus.cat');
INSERT INTO user_lab VALUES ('prprpr', 'Pepapa', 'Roure', 'proure@gmail.com');
INSERT INTO user_lab VALUES ('mememe', 'Maria', 'Espinac', 'espinac@gmail.com');
INSERT INTO user_lab VALUES ('xxxxxx', 'xMaria', 'xEspinac', 'xespinac@gmail.com');

INSERT INTO note_lab (title, content, date_creation, date_edit, owner) VALUES ('spring', 'va super be', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'jrjrjr');
INSERT INTO note_lab (title, content, date_creation, date_edit, owner) VALUES ('spring boot', 'va encara millor', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'jrjrjr');

INSERT INTO users(username,password,enabled)
VALUES ('xxxxxx','$2a$10$YZ6x3Na69Txamx0kmKIw8.boBT1w8Fgaw.I/H8er7lUE1tfq705Dq', true);
INSERT INTO users(username,password,enabled)
VALUES ('jrjrjr','jrjrjr', true);
INSERT INTO users(username,password,enabled)
VALUES ('sergi','$2a$10$LpDY8AN3d7ZVtA7SDeK7FO/hydIIMeHrSIMYIQiZR4fXWvLlKy1Pu', true);

INSERT INTO user_roles (username, role)
VALUES ('xxxxxx', 'ROLE_USER');
INSERT INTO user_roles (username, role)
VALUES ('xxxxxx', 'ROLE_ADMIN');
INSERT INTO user_roles (username, role)
VALUES ('sergi', 'ROLE_USER');
INSERT INTO user_roles (username, role)
VALUES ('jrjrjr', 'ROLE_USER');