INSERT INTO users (email, password, enabled, first_name, last_name) VALUES ('fernando.md5@outlook.com', '$2a$10$NvOdtAt8y3q32ot4vRzQO.DGiC7zJSzbfcrFqRn9r1pl9qVHvNu9S', true, 'Fernando', 'Neira');
INSERT INTO users (email, password, enabled, first_name, last_name) VALUES ('dasda@sadfa.com', '$2a$10$w/eZC1Ck9LV4pwFHKHARlOuYnfxWHvT0qMKhBvTditYQ7zh710Uu6', true, 'adada', 'asad');

INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_USER');


INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2);
