package br.com.senai.api_ecommerce.produto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    boolean existsBySku(String sku);

    Page<Produto> findAllByAtivoTrue(Pageable paginacao);
    Optional<Produto> findByIdAndAtivoTrue(Long id);
}