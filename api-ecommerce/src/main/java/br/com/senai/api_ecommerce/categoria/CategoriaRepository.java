package br.com.senai.api_ecommerce.categoria;

import org.apache.logging.log4j.util.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Page<Categoria> findAllByAtivoTrue(Pageable paginacao);
    Optional<Categoria> findByIdAndAtivoTrue(Long id);
}
