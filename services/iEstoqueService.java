package services;

import model.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Interface (contrato) principal da camada de negócio, responsável por definir os Requisitos Funcionais
 * do sistema relacionados à gestão de estoque. A camada de apresentação (View) dependerá exclusivamente
 * desta interface, permitindo a implementação dos requisitos de forma independente da tecnologia de persistência.
 *
 * Esta interface define operações como cadastro de produtos, registro de entradas e saídas de estoque,
 * consultas de saldo, e listagem de movimentos de estoque.
 */
public interface iEstoqueService {

    /**
     * Cadastro de um novo produto no estoque.
     *
     * @param codigo O código do produto.
     * @param nome O nome do produto.
     * @param precoUnitario O preço unitário do produto.
     * @param quantidadeEstoque A quantidade inicial de unidades no estoque.
     * @param categoriaNome O nome da categoria do produto (ex: "hardware", "periferico").
     * @throws Exception Se um produto com o mesmo código já existir no estoque.
     */
    void cadastrarProduto(String codigo, String nome, double precoUnitario, int quantidadeEstoque, String categoriaNome) throws Exception;

    /**
     * Registra uma entrada de produto no estoque.
     *
     * @param codigoProduto O código do produto a ser registrado.
     * @param data A data da entrada.
     * @param quantidade A quantidade de unidades do produto a ser adicionada.
     * @param valorUnitarioEntrada O valor unitário de entrada do produto.
     * @throws Exception Se o produto não for encontrado.
     */
    void registrarEntrada(String codigoProduto, LocalDate data, int quantidade, double valorUnitarioEntrada) throws Exception;

    /**
     * Registra uma saída de produto do estoque.
     *
     * @param codigoProduto O código do produto a ser registrado.
     * @param data A data da saída.
     * @param quantidade A quantidade de unidades do produto a ser removida.
     * @param tipoSaida O tipo de saída (ex: venda, uso interno, devolução).
     * @throws Exception Se o produto não for encontrado ou se não houver estoque suficiente.
     */
    void registrarSaida(String codigoProduto, LocalDate data, int quantidade, TipoSaida tipoSaida) throws Exception;

    /**
     * Consulta o saldo atual de um produto no estoque, incluindo a quantidade e o valor total.
     *
     * @param codigoProduto O código do produto a ser consultado.
     * @return Um mapa contendo a quantidade e o valor total em estoque do produto.
     * @throws Exception Se o produto não for encontrado.
     */
    Map<String, Object> consultarSaldoAtual(String codigoProduto) throws Exception;

    /**
     * Consulta o saldo total de estoque (de todos os produtos) durante um período específico.
     * O saldo é calculado com base nas entradas e saídas de produtos no intervalo de datas fornecido.
     *
     * @param de A data inicial do período.
     * @param ate A data final do período.
     * @return O saldo total do estoque durante o período especificado.
     */
    double consultarSaldoTotalEstoque(LocalDate de, LocalDate ate);

    /**
     * Lista todas as entradas registradas no sistema.
     *
     * @return Uma lista de todas as entradas registradas.
     */
    List<Entrada> listarEntradas();

    /**
     * Lista todas as saídas registradas no sistema.
     *
     * @return Uma lista de todas as saídas registradas.
     */
    List<Saida> listarSaidas();

    /**
     * Lista todos os movimentos (entradas e saídas) registrados no sistema, ordenados por data.
     *
     * @return Uma lista de todos os movimentos, ordenados por data.
     */
    List<Movimento> listarTodosMovimentosOrdenadosPorData();

    /**
     * Lista todos os produtos cadastrados no sistema.
     *
     * @return Uma lista de todos os produtos registrados.
     */
    List<Produto> listarProdutos();
}
