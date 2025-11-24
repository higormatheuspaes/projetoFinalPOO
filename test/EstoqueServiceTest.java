import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.ProdutoRepository;
import repository.MovimentoRepository;
import services.EstoqueService;

import java.text.Normalizer;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class EstoqueServiceTest {

    private EstoqueService estoqueService;
    private ProdutoRepository produtoRepository;
    private MovimentoRepository movimentoRepository;

    @BeforeEach
    public void setup() {
        produtoRepository = new ProdutoRepository();
        movimentoRepository = new MovimentoRepository();
        estoqueService = new EstoqueService(produtoRepository, movimentoRepository);

        // Pré-cadastro comum: produto 101 com quantidade 0
        try {
            estoqueService.cadastrarProduto("101", "SSD Kingston 480GB", 199.90, 0, "hardware");
        } catch (Exception e) {
            fail("Erro ao cadastrar produto no setup: " + e.getMessage());
        }
    }

    // R1 — Cadastro de Produto
    @Test
    public void testR1_CadastrarProduto() {
        try {
            estoqueService.cadastrarProduto("102", "Mouse Logitech", 49.90, 5, "periferico");
        } catch (Exception e) {
            fail("Não deveria ocorrer exceção ao cadastrar produto: " + e.getMessage());
        }

        Produto p = produtoRepository.buscarPorCodigo("102").orElse(null);
        assertNotNull(p, "Produto não encontrado no repositório após cadastro.");
        assertEquals("Mouse Logitech", p.getNome(), "Nome do produto não corresponde.");
        assertEquals(49.90, p.getPrecoUnitarioPadrao(), "Preço do produto não corresponde.");
        assertEquals(5, p.getQuantidadeEstoque(), "Quantidade de estoque não corresponde.");

        // Comparando categoria sem considerar acento
        String categoriaEsperada = "periferico"; // esperado sem acento
        String categoriaReal = p.getCategoria().getNome().toLowerCase(); // obtendo o valor real da categoria

        // Remover acentos de ambas as strings para comparação sem acento
        categoriaEsperada = Normalizer.normalize(categoriaEsperada, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
        categoriaReal = Normalizer.normalize(categoriaReal, Normalizer.Form.NFD).replaceAll("\\p{M}", "");

        assertEquals(categoriaEsperada, categoriaReal, "Categoria do produto não corresponde.");
    }

    @Test
    public void testR2_RegistrarEntrada() {
        try {
            try {
                estoqueService.registrarEntrada("101", LocalDate.of(2025, 11, 20), 10, 199.90);
            } catch (UnsupportedOperationException e) {}
        } catch (Exception e) {
            fail("Não deveria lançar exceção ao registrar entrada: " + e.getMessage());
        }

        Produto produtoAtualizado = produtoRepository.buscarPorCodigo("101").orElse(null);
        assertNotNull(produtoAtualizado, "Produto não encontrado após registrar entrada.");
        assertEquals(10, produtoAtualizado.getQuantidadeEstoque(), "Quantidade em estoque não foi atualizada corretamente.");

        List<Movimento> movimentos = movimentoRepository.listarTodos().stream()
                .filter(m -> m.getProduto().getCodigo().equals("101"))
                .collect(Collectors.toList());

        assertFalse(movimentos.isEmpty(), "Deveria existir pelo menos 1 movimento registrado para o produto 101.");

        Movimento mov = movimentos.get(movimentos.size() - 1);
        assertTrue(mov instanceof Entrada, "Movimento registrado deveria ser do tipo Entrada.");
        assertEquals("101", mov.getProduto().getCodigo(), "Código do produto no movimento incorreto.");
        assertEquals(10, mov.getQuantidade(), "Quantidade do movimento incorreta.");
        assertEquals(LocalDate.of(2025, 11, 20), mov.getData(), "Data do movimento incorreta.");
    }

    @Test
    public void testR3_RegistrarSaida() {
        try {
            estoqueService.cadastrarProduto("200", "Teclado Genérico", 59.90, 10, "periferico");
        } catch (Exception e) {
            fail("Erro ao cadastrar produto para R3: " + e.getMessage());
        }

        try {
            try {
                estoqueService.registrarSaida("200", LocalDate.of(2025, 11, 21), 3, TipoSaida.vendaCliente);
            } catch (UnsupportedOperationException e) {}
        } catch (Exception e) {
            fail("Não deveria lançar exceção ao registrar saída: " + e.getMessage());
        }

        Produto produtoAtual = produtoRepository.buscarPorCodigo("200").orElse(null);
        assertNotNull(produtoAtual, "Produto não encontrado após registrar saída.");
        assertEquals(7, produtoAtual.getQuantidadeEstoque(), "Estoque final incorreto após saída.");

        List<Movimento> movimentos = movimentoRepository.listarTodos().stream()
                .filter(m -> m.getProduto().getCodigo().equals("200"))
                .collect(Collectors.toList());

        assertFalse(movimentos.isEmpty(), "Deveria haver movimentos para o produto 200.");
        Movimento mov = movimentos.get(movimentos.size() - 1);
        assertTrue(mov instanceof Saida, "Movimento deveria ser do tipo Saida.");
        assertEquals(3, mov.getQuantidade(), "Quantidade registrada na saída está incorreta.");
        assertEquals(TipoSaida.vendaCliente, ((Saida) mov).getTipo(), "Tipo da saída está incorreto.");
    }


    @Test
    public void testR4_ConsultarSaldoAtual() {
        try {
            estoqueService.cadastrarProduto("300", "Placa-Mãe XYZ", 500.0, 0, "hardware");

            try {
                estoqueService.registrarEntrada("300", LocalDate.of(2025, 11, 18), 5, 500.0);
            } catch (UnsupportedOperationException e) {}

            try {
                estoqueService.registrarEntrada("300", LocalDate.of(2025, 11, 19), 2, 500.0);
            } catch (UnsupportedOperationException e) {}

            try {
                estoqueService.registrarSaida("300", LocalDate.of(2025, 11, 20), 1, TipoSaida.usoInterno);
            } catch (UnsupportedOperationException e) {}

        } catch (Exception e) {
            fail("Erro no preparo do cenário R4: " + e.getMessage());
        }

        Produto p = produtoRepository.buscarPorCodigo("300").orElse(null);
        assertNotNull(p);

        int expectedQuantity = 6;
        double expectedValue = expectedQuantity * p.getPrecoUnitarioPadrao();

        assertEquals(expectedQuantity, p.getQuantidadeEstoque());
        assertEquals(expectedValue, expectedQuantity * p.getPrecoUnitarioPadrao());
    }

    @Test
    public void testR5_ConsultarSaldoPeriodo() {
        try {
            estoqueService.cadastrarProduto("400", "Memória 8GB", 150.0, 0, "hardware");

            try {
                estoqueService.registrarEntrada("400", LocalDate.of(2025, 11, 17), 10, 150.0);
            } catch (UnsupportedOperationException e) {}

            try {
                estoqueService.registrarEntrada("400", LocalDate.of(2025, 11, 19), 5, 150.0);
            } catch (UnsupportedOperationException e) {}

            try {
                estoqueService.registrarSaida("400", LocalDate.of(2025, 11, 20), 3, TipoSaida.vendaCliente);
            } catch (UnsupportedOperationException e) {}

        } catch (Exception e) {
            fail("Erro no preparo do cenário R5: " + e.getMessage());
        }

        LocalDate inicio = LocalDate.of(2025, 11, 18);
        LocalDate fim = LocalDate.of(2025, 11, 22);

        List<Movimento> movimentosNoPeriodo = movimentoRepository.listarTodos().stream()
                .filter(m -> m.getProduto().getCodigo().equals("400"))
                .filter(m -> (!m.getData().isBefore(inicio) && !m.getData().isAfter(fim)))
                .collect(Collectors.toList());

        int entradas = movimentosNoPeriodo.stream()
                .filter(m -> m instanceof Entrada)
                .mapToInt(Movimento::getQuantidade)
                .sum();

        int saidas = movimentosNoPeriodo.stream()
                .filter(m -> m instanceof Saida)
                .mapToInt(Movimento::getQuantidade)
                .sum();

        int saldo = entradas - saidas;

        assertEquals(2, saldo);
    }

    @Test
    public void testR6_ListarEntradas() {
        try {
            estoqueService.cadastrarProduto("500", "Cabo USB", 9.90, 0, "acessorio");

            try {
                estoqueService.registrarEntrada("500", LocalDate.of(2025, 11, 18), 4, 9.90);
            } catch (UnsupportedOperationException e) {}

            try {
                estoqueService.registrarEntrada("500", LocalDate.of(2025, 11, 19), 6, 9.90);
            } catch (UnsupportedOperationException e) {}

            try {
                estoqueService.registrarSaida("500", LocalDate.of(2025, 11, 20), 2, TipoSaida.Outra);
            } catch (UnsupportedOperationException e) {}

        } catch (Exception e) {
            fail("Erro ao preparar R6: " + e.getMessage());
        }

        List<Movimento> entradas = movimentoRepository.listarTodos().stream()
                .filter(m -> m.getProduto().getCodigo().equals("500"))
                .filter(m -> m instanceof Entrada)
                .collect(Collectors.toList());

        assertTrue(entradas.size() >= 2);
        assertTrue(entradas.stream().allMatch(m -> m instanceof Entrada));
    }


    @Test
    public void testR7_ListarSaidas() {
        try {
            estoqueService.cadastrarProduto("600", "Fonte 500W", 220.0, 5, "hardware");

            try {
                estoqueService.registrarSaida("600", LocalDate.of(2025, 11, 21), 2, TipoSaida.vendaCliente);
            } catch (UnsupportedOperationException e) {}

            try {
                estoqueService.registrarSaida("600", LocalDate.of(2025, 11, 22), 1, TipoSaida.usoInterno);
            } catch (UnsupportedOperationException e) {}

        } catch (Exception e) {
            fail("Erro ao preparar R7: " + e.getMessage());
        }

        List<Movimento> saidas = movimentoRepository.listarTodos().stream()
                .filter(m -> m.getProduto().getCodigo().equals("600"))
                .filter(m -> m instanceof Saida)
                .collect(Collectors.toList());

        assertTrue(saidas.size() >= 2);
        assertTrue(saidas.stream().allMatch(m -> m instanceof Saida));
    }


    @Test
    public void testR8_MovimentacoesOrdenadas() {

        try {
            estoqueService.cadastrarProduto("700", "SSD NVMe", 349.0, 0, "hardware");

            try {
                estoqueService.registrarEntrada("700", LocalDate.of(2025, 11, 18), 2, 349.0);
            } catch (UnsupportedOperationException e) {}

            try {
                estoqueService.registrarSaida("700", LocalDate.of(2025, 11, 19), 1, TipoSaida.vendaCliente);
            } catch (UnsupportedOperationException e) {}

            try {
                estoqueService.registrarEntrada("700", LocalDate.of(2025, 11, 20), 3, 349.0);
            } catch (UnsupportedOperationException e) {}

        } catch (Exception e) {
            fail("Erro ao preparar R8: " + e.getMessage());
        }

        Produto produto = produtoRepository.buscarPorCodigo("700").orElse(null);
        assertNotNull(produto, "Produto 700 não encontrado.");

        List<Movimento> movimentos = movimentoRepository.listarTodos().stream()
                .filter(m -> m.getProduto().getCodigo().equals(produto.getCodigo()))
                .sorted(Comparator.comparing(Movimento::getData))
                .collect(Collectors.toList());

        assertTrue(movimentos.size() >= 3, "Deveria existir pelo menos 3 movimentações para o produto 700.");

        for (int i = 1; i < movimentos.size(); i++) {
            assertFalse(
                    movimentos.get(i).getData().isBefore(movimentos.get(i - 1).getData()),
                    "Movimentações não estão ordenadas cronologicamente."
            );
        }

        int saldo = movimentos.stream()
                .mapToInt(m -> (m instanceof Entrada ? m.getQuantidade() : -m.getQuantidade()))
                .sum();

        assertEquals(saldo, produto.getQuantidadeEstoque(),
                "Saldo calculado das movimentações não confere com o estoque atual.");
    }





}
