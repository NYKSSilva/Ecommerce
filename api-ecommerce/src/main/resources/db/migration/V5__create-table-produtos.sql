CREATE TABLE produtos(
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL,
                         sku VARCHAR(20) NOT NULL UNIQUE,
                         descricao VARCHAR(255),
                         preco DECIMAL NOT NULL,
                         estoque BIGINT DEFAULT 0 NOT NULL,
                         ativo TINYINT DEFAULT 1 NOT NULL,
                         categoria_id BIGINT NOT NULL,
                         CONSTRAINT chk_produto_preco CHECK(preco>0),
                         CONSTRAINT chk_produto_estoque CHECK(estoque>=0),
                         CONSTRAINT chk_produto_ativo CHECK(ativo IN(0,1)),
                         CONSTRAINT fk_produto_categoria FOREIGN KEY(categoria_id) REFERENCES categorias(id)
);