INSERT INTO user_lab VALUES ('jrjrjr', 'Josep', 'Roure', 'roure@tecnocampus.cat');
INSERT INTO user_lab VALUES ('jpjpjp', 'Josep', 'Pineda', 'pi@tecnocampus.cat');
INSERT INTO user_lab VALUES ('prprpr', 'Pepapa', 'Roure', 'proure@gmail.com');
INSERT INTO user_lab VALUES ('mememe', 'Maria', 'Espinac', 'espinac@gmail.com');

INSERT INTO note_lab (title, content, date_creation, date_edit, owner) VALUES ('spring', 'va super be', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'jrjrjr');
INSERT INTO note_lab (title, content, date_creation, date_edit, owner) VALUES ('spring boot', 'va encara millor', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'jrjrjr');

INSERT INTO users(username,password,enabled)
VALUES ('aaaaaa','aaaaaa', true);
INSERT INTO users(username,password,enabled)
VALUES ('jrjrjr','jrjrjr', true);

INSERT INTO user_roles (username, role)
VALUES ('aaaaaa', 'ROLE_USER');
INSERT INTO user_roles (username, role)
VALUES ('aaaaaa', 'ROLE_ADMIN');
INSERT INTO user_roles (username, role)
VALUES ('jrjrjr', 'ROLE_USER');