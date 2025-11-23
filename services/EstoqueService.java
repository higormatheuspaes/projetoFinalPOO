package services;

import model.*;
import repository.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementação concreta da camada de negócio (service) para o gerenciamento de estoque.
 * Esta classe contém a lógica de negócios relacionada ao cadastro, registro de entradas e saídas,
 * consultas de saldo de estoque e movimentações.
 *
 * Utiliza repositórios para persistência de {@link Produto} e {@link Movimento}.
 */
public class EstoqueService implements iEstoqueService {

    // --- Dependências (Os "Estoquistas") ---
    private final iProdutoRepository produtoRepository;
    private final iMovimentoRepository movimentoRepository;

    // --- Estado em memória ---
    private List<Produto> produtos;
    private List<Movimento> movimentos;

    // --- Construtor ---
    /**
     * Construtor da classe EstoqueService, que inicializa os repositórios e carrega o estado inicial.
     *
     * @param produtoRepository Repositório de produtos.
     * @param movimentoRepository Repositório de movimentos (entradas e saídas).
     */
    public EstoqueService(iProdutoRepository produtoRepository, iMovimentoRepository movimentoRepository){
        this.produtoRepository = produtoRepository;
        this.movimentoRepository = movimentoRepository;

        // Carrega o estado inicial da "base de dados"
        this.produtos = produtoRepository.listarTodos();
        this.movimentos = movimentoRepository.listarTodos();
    }

    // -- Implementação de Métodos (R1 - R8)

    /**
     * Cadastra um novo produto no estoque.
     *
     * @param codigo O código do novo produto.
     * @param nome O nome do produto.
     * @param precoUnitario O preço unitário do produto.
     * @param quantidadeEstoque A quantidade inicial do produto no estoque.
     * @param categoriaNome O nome da categoria do produto (ex: "hardware", "periférico").
     * @throws Exception Se o produto já existir no estoque.
     */
    @Override
    public void cadastrarProduto(String codigo, String nome, double precoUnitario, int quantidadeEstoque, String categoriaNome) throws Exception {
        Optional<Produto> jaExiste = this.produtos.stream()
                .filter(p -> p.getCodigo().equalsIgnoreCase(codigo))
                .findFirst();

        if (jaExiste.isPresent()){
            throw new Exception("Produto com código " + codigo + " já existe.");
        }

        // Determina a categoria com base no nome fornecido
        Categoria categoria;
        switch (categoriaNome.toLowerCase()){
            case "hardware":
                categoria = new Hardware();
                break;
            case "periferico":
                categoria = new Periferico();
                break;
            case "acessorio":
                categoria = new Acessorio();
                break;
            default:
                categoria = new Outro();
        }

        Produto novoProduto = new Produto(codigo, nome, precoUnitario, quantidadeEstoque, categoria);

        // Atualiza o estado em memória
        this.produtos.add(novoProduto);

        // Persiste o novo produto
        this.produtoRepository.salvar(novoProduto);
    }

    /**
     * Registra uma entrada de produto no estoque.
     *
     * @param codigoProduto O código do produto a ser registrado.
     * @param data A data da entrada.
     * @param quantidade A quantidade de unidades do produto a ser adicionada.
     * @param valorUnitarioEntrada O valor unitário de entrada.
     * @throws Exception Se o produto não for encontrado.
     */
    @Override
    public void registrarEntrada(String codigoProduto, LocalDate data, int quantidade, double valorUnitarioEntrada) throws Exception {
        Produto produto = buscarProdutoOuLancarErro(codigoProduto);

        // Atualiza o estoque do produto
        produto.adicionarEstoque(quantidade);

        // Cria o registro de entrada
        Entrada entrada = new Entrada(produto, data, quantidade,  valorUnitarioEntrada);

        // Atualiza o estado em memória
        this.movimentos.add(entrada);

        // Persiste o registro de entrada
        this.movimentoRepository.salvar(entrada);
        this.produtoRepository.atualizarTodos(this.produtos);
    }

    /**
     * Registra uma saída de produto do estoque.
     *
     * @param codigoProduto O código do produto a ser registrado.
     * @param data A data da saída.
     * @param quantidade A quantidade de unidades do produto a ser removida.
     * @param tipoSaida O tipo de saída (ex: venda, uso interno, devolução).
     * @throws Exception Se o produto não for encontrado ou se não houver estoque suficiente.
     */
    @Override
    public void registrarSaida(String codigoProduto, LocalDate data, int quantidade, TipoSaida tipoSaida) throws Exception {
        Produto produto = buscarProdutoOuLancarErro(codigoProduto);

        // Atualiza o estoque do produto
        produto.removerEstoque(quantidade);

        // Cria o registro de saída
        Saida saida = new Saida(produto, data, quantidade, tipoSaida);

        // Atualiza o estado em memória
        this.movimentos.add(saida);

        // Persiste o registro de saída
        this.movimentoRepository.salvar(saida);
        this.produtoRepository.atualizarTodos(this.produtos);
    }

    /**
     * Consulta o saldo atual de um produto no estoque, incluindo a quantidade e o valor total.
     *
     * @param codigoProduto O código do produto a ser consultado.
     * @return Um mapa contendo a quantidade e o valor total em estoque do produto.
     * @throws Exception Se o produto não for encontrado.
     */
    @Override
    public Map<String, Object> consultarSaldoAtual(String codigoProduto) throws Exception {
        Produto produto = buscarProdutoOuLancarErro(codigoProduto);

        Map<String, Object> saldo = new HashMap<>();
        saldo.put("Quantidade",  produto.getQuantidadeEstoque());
        saldo.put("ValorTotalEstoque", produto.getValorTotal());
        return saldo;
    }

    /**
     * Consulta o saldo total de estoque entre duas datas.
     * O saldo é calculado com base nas entradas e saídas de produtos durante o período.
     *
     * @param de A data inicial do período.
     * @param ate A data final do período.
     * @return O saldo total do estoque durante o período especificado.
     */
    @Override
    public double consultarSaldoTotalEstoque(LocalDate de, LocalDate ate) {
        return this.movimentos.stream()
                .filter(m -> !m.getData().isBefore(de) && !m.getData().isAfter(ate))
                .mapToDouble(m -> {
                    if (m instanceof Entrada entrada) {
                        return entrada.getQuantidade() * entrada.getValorUnitarioEntrada();
                    } else if (m instanceof Saida saida) {
                        return -saida.getQuantidade() * saida.getProduto().getPrecoUnitarioPadrao();
                    }
                    return 0;
                })
                .sum();
    }

    /**
     * Lista todas as entradas registradas no sistema.
     *
     * @return Uma lista de entradas registradas no estoque.
     */
    @Override
    public List<Entrada> listarEntradas(){
        return this.movimentos.stream()
                .filter(m -> m instanceof Entrada)
                .map(m -> (Entrada) m)
                .collect(Collectors.toList());
    }

    /**
     * Lista todas as saídas registradas no sistema.
     *
     * @return Uma lista de saídas registradas no estoque.
     */
    @Override
    public List<Saida> listarSaidas(){
        return this.movimentos.stream()
                .filter(m -> m instanceof Saida)
                .map(m -> (Saida) m)
                .collect(Collectors.toList());
    }

    /**
     * Lista todos os movimentos registrados no sistema, ordenados por data.
     *
     * @return Uma lista de todos os movimentos, ordenados por data.
     */
    @Override
    public List<Movimento> listarTodosMovimentosOrdenadosPorData(){
        this.movimentos.sort(Comparator.comparing(Movimento::getData));
        return new ArrayList<>(this.movimentos);
    }

    /**
     * Lista todos os produtos registrados no sistema.
     *
     * @return Uma lista de todos os produtos.
     */
    @Override
    public List<Produto> listarProdutos(){
        return new ArrayList<>(this.produtos);
    }

    // -- Métodos Auxiliares -- Privados

    /**
     * Busca um produto pelo código e lança uma exceção caso não seja encontrado.
     *
     * @param codigoProduto O código do produto a ser buscado.
     * @return O produto encontrado.
     * @throws Exception Se o produto não for encontrado.
     */
    private Produto buscarProdutoOuLancarErro(String codigoProduto) throws Exception {
        return this.produtos.stream()
                .filter(p -> p.getCodigo().equalsIgnoreCase(codigoProduto))
                .findFirst()
                .orElseThrow(() -> new Exception("Produto com código " + codigoProduto + " não encontrado."));
    }
}
