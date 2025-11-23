package model;

/**
 * Representa um produto na loja de informática.
 * Esta classe armazena os dados do produto e gerencia seu próprio estoque, permitindo
 * operações de adição e remoção de unidades no estoque, além de calcular o valor total em estoque.
 */
public class Produto {

    private String codigo;
    private String nome;
    private double precoUnitarioPadrao;
    private int quantidadeEstoque;
    private Categoria categoria; // Composição: um produto tem uma categoria

    /**
     * Construtor que inicializa um novo produto com os dados fornecidos.
     *
     * @param codigo O código do produto.
     * @param nome O nome do produto.
     * @param precoUnitarioPadrao O preço unitário padrão do produto.
     * @param quantidadeEstoque A quantidade de unidades do produto no estoque.
     * @param categoria A categoria do produto, que deve ser uma instância de {@link Categoria}.
     */
    public Produto(String codigo, String nome, double precoUnitarioPadrao, int quantidadeEstoque, Categoria categoria) {
        this.codigo = codigo;
        this.nome = nome;
        this.precoUnitarioPadrao = precoUnitarioPadrao;
        this.quantidadeEstoque = quantidadeEstoque;
        this.categoria = categoria;
    }

    // -- Métodos de Lógica de Negócio --

    /**
     * Adiciona a quantidade fornecida ao estoque do produto.
     *
     * @param quantidade A quantidade de unidades a ser adicionada ao estoque.
     */
    public void adicionarEstoque(int quantidade) {
        if (quantidade > 0) {
            this.quantidadeEstoque += quantidade;
        }
    }

    /**
     * Remove a quantidade fornecida do estoque do produto.
     * Lança uma exceção se a quantidade for negativa ou se houver estoque insuficiente.
     *
     * @param quantidade A quantidade de unidades a ser removida do estoque.
     * @throws IllegalArgumentException Se a quantidade for negativa ou se houver estoque insuficiente.
     */
    public void removerEstoque(int quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException("Quantidade inválida para remoção de estoque.");
        }
        if (this.quantidadeEstoque < quantidade) {
            throw new IllegalArgumentException("Estoque insuficiente para remoção.");
        }
        this.quantidadeEstoque -= quantidade;
    }

    /**
     * Calcula o valor total do produto em estoque (preço unitário * quantidade em estoque).
     *
     * @return O valor total do produto em estoque.
     */
    public double getValorTotal() {
        return this.precoUnitarioPadrao * this.quantidadeEstoque;
    }

    // -- Getters e Setters --

    /**
     * Retorna o código do produto.
     *
     * @return O código do produto.
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Define o código do produto.
     *
     * @param codigo O novo código do produto.
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Retorna o nome do produto.
     *
     * @return O nome do produto.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do produto.
     *
     * @param nome O novo nome do produto.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o preço unitário padrão do produto.
     *
     * @return O preço unitário padrão do produto.
     */
    public double getPrecoUnitarioPadrao() {
        return precoUnitarioPadrao;
    }

    /**
     * Define o preço unitário padrão do produto.
     *
     * @param precoUnitarioPadrao O novo preço unitário do produto.
     */
    public void setPrecoUnitarioPadrao(double precoUnitarioPadrao) {
        this.precoUnitarioPadrao = precoUnitarioPadrao;
    }

    /**
     * Retorna a quantidade em estoque do produto.
     *
     * @return A quantidade de unidades do produto no estoque.
     */
    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    /**
     * Retorna a categoria do produto.
     *
     * @return A categoria do produto.
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * Define a categoria do produto.
     *
     * @param categoria A nova categoria do produto.
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
