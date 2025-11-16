package services;
import model.*;
import repository.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementação concreta da camada de negócio.
 * Este é o "cérebro" do sistema, contendo toda a lógica de negócio.
*/
public class EstoqueService implements iEstoqueService{
    
    // --- Dependências (Os "Estoquistas") ---
    private final iProdutoRepository produtoRepository;
    private final iMovimentoRepository movimentoRepository;


    // --- Estado em memória  --- 
    private List<Produto> produtos; 
    private List<Movimento> movimentos;

    // --- Construtor ---
    public EstoqueService(iProdutoRepository produtoRepository, iMovimentoRepository movimentoRepository){
        this.produtoRepository = produtoRepository;
        this.movimentoRepository = movimentoRepository;
        
        //Carrega o estado inicial da "base de dados"  
        this.produtos = produtoRepository.listarTodos();
        this.movimentos = movimentoRepository.listarTodos();
    }

    // -- Implementação de Métodos (R1 - R8)

    @Override
    // Lógica para cadastrar um novo produto no estoque
    public void cadastrarProduto(String codigo, String nome, double precoUnitario, int quantidadeEstoque, String categoriaNome) throws Exception {
        Optional<Produto> jaExiste = this.produtos.stream()
        .filter(p -> p.getCodigo().equalsIgnoreCase(codigo))
        .findFirst();

        if (jaExiste.isPresent()){
            throw new Exception("Produto com código " + codigo + " já existe.");    
        }

        // Lógica para determinar a categoria com base no nome fornecido
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

        //1. Atualiza o estado em memória
        this.produtos.add(novoProduto);

        //2. Manda o repositorio salvar (persistência)
        this.produtoRepository.salvar(novoProduto);
    }

    @Override
    public void registrarEntrada(String codigoProduto, LocalDate data, int quantidade, double valorUnitarioEntrada) throws Exception {

        Produto produto = buscarProdutoOuLancarErro(codigoProduto);

        // 1. Atualiza o produto (logica do model)
        produto.adicionarEstoque(quantidade);

        // 2. Cria o registro histórico da entrada
        Entrada entrada = new Entrada(produto, data, quantidade,  valorUnitarioEntrada);
        
        //3. Atualiza o estado em memória
        this.movimentos.add(entrada);

        //4. Manda o repositorio salvar (persistência)
        this.movimentoRepository.salvar(entrada);
        this.produtoRepository.salvar(produto);   
    }

    @Override
    public void registrarSaida(String codigoProduto, LocalDate data, int quantidade, TipoSaida tipoSaida) throws Exception {
        // 1. Busca produto
        Produto produto = buscarProdutoOuLancarErro(codigoProduto);

        // 2. Atualiza o produto (lógica do model)
        produto.removerEstoque(quantidade);

        // 3. Cria o registro histórico da saída
        Saida saida = new Saida(produto, data, quantidade, tipoSaida);

        // 4. Atualiza o estado em memória
        this.movimentos.add(saida);

        // 5. Manda o repositório salvar (persistência)
        this.produtoRepository.salvar(produto);
        this.movimentoRepository.salvar(saida);
    }

    @Override
    public Map<String, Object> consultarSaldoAtual(String codigoProduto) throws Exception {
        Produto produto = buscarProdutoOuLancarErro(codigoProduto);

        Map<String, Object> saldo = new HashMap<>();
        saldo.put("Quantidade",  produto.getQuantidadeEstoque());
        saldo.put("ValorTotalEstoque", produto.getValorTotal());
        return saldo;
    }

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

    @Override
    public List<Entrada> listarEntradas(){
        //Usamos stream e instanceof para filtrar a lista
        return this.movimentos.stream()
                .filter(m -> m instanceof Entrada)
                .map(m -> (Entrada) m)
                .collect(Collectors.toList());
    }

    @Override
    public List<Saida> listarSaidas(){
        //Usamos stream e instanceof para filtrar a lista
        return this.movimentos.stream()
                .filter(m -> m instanceof Saida)
                .map(m -> (Saida) m)
                .collect(Collectors.toList());
    }

    @Override
    public List<Movimento>  listarTodosMovimentosOrdenadosPorData(){
        this.movimentos.sort(Comparator.comparing(Movimento::getData));
        return new ArrayList<>(this.movimentos);
    }

    @Override
    public List<Produto> listarProdutos(){
        return new ArrayList<>(this.produtos);
    }

    // -- Métodos Auxiliares -- Privados
    private Produto buscarProdutoOuLancarErro(String codigoProduto) throws Exception {
        return this.produtos.stream()
                .filter(p -> p.getCodigo().equalsIgnoreCase(codigoProduto))
                .findFirst()
                .orElseThrow(() -> new Exception("Produto com código " + codigoProduto + " não encontrado."));
    }
}
