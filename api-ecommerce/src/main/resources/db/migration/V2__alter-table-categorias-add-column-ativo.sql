ALTER TABLE categorias ADD ativo tinyint;

UPDATE categorias SET ativo = 1;

ALTER TABLE categorias MODIFY COLUMN ativo tinyint NOT NULL;