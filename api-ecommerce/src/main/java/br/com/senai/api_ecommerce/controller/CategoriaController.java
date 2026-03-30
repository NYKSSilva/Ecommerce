package br.com.senai.api_ecommerce.controller;

import br.com.senai.api_ecommerce.categoria.DadosCadastroCategoria;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("categorias")
public class CategoriaController {

    @PostMapping
    public void cadastrarCategoria(@RequestBody DadosCadastroCategoria dados){

    }

}
