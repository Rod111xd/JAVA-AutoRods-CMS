CREATE SCHEMA IF NOT EXISTS AutoRods;
USE AutoRods;

CREATE TABLE User (
	id int not null primary key auto_increment,
	name varchar(50) not null,
	password varchar(255) not null,
	email varchar(50) not null
);