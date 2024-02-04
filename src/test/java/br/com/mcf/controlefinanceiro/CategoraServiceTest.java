package br.com.mcf.controlefinanceiro;

import br.com.mcf.controlefinanceiro.model.dominio.Categoria;
import br.com.mcf.controlefinanceiro.service.dominio.CategoriaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CategoraServiceTest {

    @Autowired
    private CategoriaService service;

    @Test
    void shouldFindCategoryByName() {
        String categoryToFind = "Home";
        Categoria novaCategoria = service.consultarCategoriaPorNome(categoryToFind);

        assertEquals(novaCategoria.getNome(), categoryToFind);

    }

    @Test
    void shouldCreateCategoryWhenOnlyNameIsProvided(){

        service.cadastrarClassificao("nova catt");
        Categoria novaCategoria = service.consultarCategoriaPorNome("nova catt");


        assertEquals(novaCategoria.getNome(), "novaCatt");

    }
}
