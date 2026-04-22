ALTER TABLE clientes ADD(
    logradouro VARCHAR(255) NOT NULL,
    bairro VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    cep VARCHAR(8) NOT NULL,
    uf VARCHAR(2) NOT NULL ,
    complemento VARCHAR(100),
    numero VARCHAR(10)
);

ALTER TABLE clientes
    ADD CONSTRAINT  chk_cep
    CHECK(cep REGEXP '^[0-9]{8}$');

ALTER TABLE clientes
    ADD CONSTRAINT  chk_uf
    CHECK(uf REGEXP '^[A-Z]{2}$');