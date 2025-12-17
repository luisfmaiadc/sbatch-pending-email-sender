CREATE DATABASE sbatch_execution;
CREATE DATABASE sbatch_notification;

USE sbatch_notification;

CREATE TABLE TbPendencia (
     id INT PRIMARY KEY,
     assunto VARCHAR(75) NOT NULL,
     descricao VARCHAR(255) NOT NULL,
     status VARCHAR(20) NOT NULL,
     responsavel VARCHAR(150) NOT NULL,
     dataCriacao TIMESTAMP NOT NULL
);