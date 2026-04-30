ALTER TABLE produtos
    ADD CONSTRAINT chk_produto_sku CHECK (
        sku REGEXP '^[^[:space:]]{1,20}$'
    );