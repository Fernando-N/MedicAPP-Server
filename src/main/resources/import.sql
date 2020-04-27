INSERT INTO users (email, password, enabled, first_name, last_name) VALUES ('admin@test.com', '$2a$10$NvOdtAt8y3q32ot4vRzQO.DGiC7zJSzbfcrFqRn9r1pl9qVHvNu9S', true, 'ADMIN', 'TEST');
INSERT INTO users (email, password, enabled, first_name, last_name) VALUES ('user@test.com', '$2a$10$w/eZC1Ck9LV4pwFHKHARlOuYnfxWHvT0qMKhBvTditYQ7zh710Uu6', true, 'USER', 'TEST');
INSERT INTO users (email, password, enabled, first_name, last_name) VALUES ('paramedic@test.com', '$2a$10$w/eZC1Ck9LV4pwFHKHARlOuYnfxWHvT0qMKhBvTditYQ7zh710Uu6', true, 'PARAMEDIC', 'TEST');

INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_PARAMEDIC')


INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (3, 3);
