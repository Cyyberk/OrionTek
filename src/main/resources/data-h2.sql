INSERT INTO roles(role) values ('ADMIN')
INSERT INTO roles(role) values ('USER')

INSERT INTO users(username, password, email) values ('jean18699','abc','jean18699@gmail.com')
INSERT INTO user_role(role, username) values ('ADMIN','jean18699')