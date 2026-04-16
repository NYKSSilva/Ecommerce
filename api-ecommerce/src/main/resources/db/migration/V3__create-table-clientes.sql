CREATE TABLE clientes(
    id bigint NOT NULL auto_increment,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    cpf CHAR(11) NOT NULL UNIQUE,
    telefone VARCHAR(20),
    ativo tinyint NOT NULL,

    PRIMARY KEY(id)
)

