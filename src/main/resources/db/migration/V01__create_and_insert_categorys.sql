CREATE TABLE category (
	code BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO category (name) values ('Lazer');
INSERT INTO category (name) values ('Alimentação');
INSERT INTO category (name) values ('Supermercado');
INSERT INTO category (name) values ('Farmácia');
INSERT INTO category (name) values ('Outros');