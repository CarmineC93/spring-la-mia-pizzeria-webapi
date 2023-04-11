INSERT INTO users (email, first_name, last_name, password) VALUES('pippo@email.it', 'Pippo', 'Pippetto', '{noop}pippo123');
INSERT INTO users (email, first_name, last_name, password) VALUES('gino@email.it', 'Gino', 'Pizzaiolo', '{noop}gino123');
INSERT INTO users (email, first_name, last_name, password) VALUES('pluto@email.it', 'Pluto', 'Plutino', '{noop}pluto123');

INSERT INTO roles (id, name) VALUES(1, 'ADMIN');
INSERT INTO roles (id, name) VALUES(2, 'USER');

INSERT INTO users_roles(user_id, roles_id) VALUES (1,1);
INSERT INTO users_roles(user_id, roles_id) VALUES (2,1);
INSERT INTO users_roles(user_id, roles_id) VALUES (3,2);

INSERT INTO pizzas (name, description, price, user_id) VALUES('capricciosa', 'roba mista', 16.00, 1);
INSERT INTO pizzas (name, description, price, user_id) VALUES('margherita', 'roba semplice', 10.00, 1);
INSERT INTO pizzas (name, description, price, user_id) VALUES('diavola', 'roba piccantina', 15.00, 1);

INSERT INTO ingredients (name, description) VALUES('pomodorino', 'biologico');
INSERT INTO ingredients (name, description) VALUES('mozzarella', 'km0');
INSERT INTO ingredients (name, description) VALUES('farina', 'farina di semola');


