CREATE DATABASE herosquad;
\c herosquad;
CREATE TABLE heroes (id SERIAL PRIMARY KEY, name VARCHAR, age INTEGER, powers TEXT,
weakness TEXT);
CREATE TABLE squad (id SERIAL PRIMARY KEY, name VARCHAR, cause TEXT, members VARCHAR);
CREATE DATABASE herosquad_test WITH TEMPLATE herosquad;