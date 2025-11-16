package services;
import model.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Interface (contrato) principal da camada de negócio.
 * Define todos os Requisitos Funcionais (R1-R8) do sistema.
 * A camada de View (Swing) dependerá APENAS desta interface.
 */
public interface iEstoqueService {
    // R1 - Cadastro de produtos
    void cadastrarProduto(String codigo, String nome, double precoUnitario, int quantidadeEstoque, String categoriaNome) throws Exception;

    // R2 - Registro de entradas no estoque
    void registrarEntrada(String codigoProduto, LocalDate data, int quantidade, double valorUnitarioEntrada) throws Exception;

    // R3 - Registrar saída do estoque
    void registrarSaida(String codigoProduto, LocalDate data, int quantidade, TipoSaida tipoSaida) throws Exception;

    // R4 - Consultar saldo atual de um produto (com map para facilitar exibição)
    Map<String, Object> consultarSaldoAtual(String codigoProduto) throws Exception;

    // R5 - Consultar saldo total do estoque (de todos os produtos) em um determinado período
    double consultarSaldoTotalEstoque(LocalDate de, LocalDate ate);

    // R6 - Listar todas as entradas registradas
    List<Entrada> listarEntradas();

    // R7 - Listar todas as saídas registradas
    List<Saida> listarSaidas();

    // R8 - Listar todos os movimentos (entradas e saídas) ordenados por data
    List<Movimento> listarTodosMovimentosOrdenadosPorData();

    // R9 - Listar todos os produtos cadastrados
    List<Produto> listarProdutos();

}