CREATE TABLE user (
	code BIGINT(20) PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	password VARCHAR(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE permission (
	code BIGINT(20) PRIMARY KEY,
	description VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE user_permission (
	code_user BIGINT(20) NOT NULL,
	code_permission BIGINT(20) NOT NULL,
	PRIMARY KEY (code_user, code_permission),
	FOREIGN KEY (code_user) REFERENCES user(code),
	FOREIGN KEY (code_permission) REFERENCES permission(code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO user (code, name, email, password) values (1, 'Administrador', 'admin@moneyapi.com', '$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.');
INSERT INTO user (code, name, email, password) values (2, 'Bernardo Viana', 'maria@moneyapi.com', '$2a$10$Zc3w6HyuPOPXamaMhh.PQOXvDnEsadztbfi6/RyZWJDzimE8WQjaq');

INSERT INTO permission (code, description) values (1, 'ROLE_REGISTER_CATEGORY');
INSERT INTO permission (code, description) values (2, 'ROLE_SEARCH_CATEGORIA');

INSERT INTO permission (code, description) values (3, 'ROLE_REGISTER_PERSON');
INSERT INTO permission (code, description) values (4, 'ROLE_REMOVER_PERSON');
INSERT INTO permission (code, description) values (5, 'ROLE_SEARCH_PERSON');

INSERT INTO permission (code, description) values (6, 'ROLE_REGISTER_LAUNCH');
INSERT INTO permission (code, description) values (7, 'ROLE_REMOVE_LAUNCH');
INSERT INTO permission (code, description) values (8, 'ROLE_SEARCH_LAUNCH');

-- admin
INSERT INTO user_permission (code_user, code_permission) values (1, 1);
INSERT INTO user_permission (code_user, code_permission) values (1, 2);
INSERT INTO user_permission (code_user, code_permission) values (1, 3);
INSERT INTO user_permission (code_user, code_permission) values (1, 4);
INSERT INTO user_permission (code_user, code_permission) values (1, 5);
INSERT INTO user_permission (code_user, code_permission) values (1, 6);
INSERT INTO user_permission (code_user, code_permission) values (1, 7);
INSERT INTO user_permission (code_user, code_permission) values (1, 8);

-- bernardo
INSERT INTO user_permission (code_user, code_permission) values (2, 2);
INSERT INTO user_permission (code_user, code_permission) values (2, 5);
INSERT INTO user_permission (code_user, code_permission) values (2, 8);