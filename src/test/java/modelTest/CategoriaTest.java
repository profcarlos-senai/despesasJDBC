package modelTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Categoria;

class CategoriaTest {

    @Test
    void deveriaCriarCategoriaComNomeValido() {
        Categoria categoria = new Categoria("Eletrônicos");

        assertEquals("Eletrônicos", categoria.getNome());
    }

    @Test
    void deveriaCriarCategoriaComIdENome() {
        Categoria categoria = new Categoria(1, "Informática");

        assertEquals(1, categoria.getId());
        assertEquals("Informática", categoria.getNome());
    }

    @Test
    void deveriaAlterarNomeComSetNome() {
        Categoria categoria = new Categoria("Eletrônicos");

        categoria.setNome("Games");

        assertEquals("Games", categoria.getNome());
    }

    @Test
    void deveriaAlterarIdComSetId() {
        Categoria categoria = new Categoria("Eletrônicos");

        categoria.setId(10);

        assertEquals(10, categoria.getId());
    }

    @Test
    void deveriaLancarExcecaoQuandoNomeForNull() {
    	//Categoria categoria = new Categoria(null);

        assertThrows(IllegalArgumentException.class, () -> {
            new Categoria(null);
        });

    }

    @Test
    void deveriaLancarExcecaoQuandoNomeForVazio() {
    	//Categoria categoria = new Categoria("   ");
    	
        assertThrows(IllegalArgumentException.class, () -> {
            new Categoria("");
        });

    }

    @Test
    void deveriaLancarExcecaoAoSetarNomeVazio() {

        Categoria categoria = new Categoria("Eletrônicos");

        assertThrows(IllegalArgumentException.class, () -> {
            categoria.setNome("");
        });

    }

}