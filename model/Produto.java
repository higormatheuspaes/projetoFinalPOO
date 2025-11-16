package model;

/**
 * Representa um produto na loja de informática.
 * Esta classe armazena os dados do produto e gerencia seu próprio estoque.
 */

public class Produto {
    private String codigo;
    private String nome;
    private double precoUnitarioPadrao;
    private int quantidadeEstoque;
    private Categoria categoria; // composição: um produto tem uma categoria


    //CONSTRUTOR DO PRODUTO
    public Produto(String codigo, String nome, double precoUnitarioPadrao, int quantidadeEstoque, Categoria categoria) {
        this.codigo = codigo;
        this.nome = nome;
        this.precoUnitarioPadrao = precoUnitarioPadrao;
        this.quantidadeEstoque = quantidadeEstoque;
        this.categoria = categoria;
    }

    // -- Métodos de Lógica de Negócio --

    //adicionar estoque
    public void adicionarEstoque(int quantidade){
        if (quantidade > 0) {
            this.quantidadeEstoque += quantidade;
        }
    }

    //remove estoque
    public void removerEstoque(int quantidade){
        if (quantidade < 0){
            throw new IllegalArgumentException("Quantidade inválida para remoção de estoque.");
        }
        if (this.quantidadeEstoque < quantidade){
            throw new IllegalArgumentException("Estoque insuficiente para remoção.");
        }
        this.quantidadeEstoque -= quantidade;
    }


    //calcula valor total deste produto em estoque
    public double getValorTotal(){
        return this.precoUnitarioPadrao * this.quantidadeEstoque;
    }

    // -- Getters e Setters --
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPrecoUnitarioPadrao() {
        return precoUnitarioPadrao;
    }

    public void setPrecoUnitarioPadrao(double precoUnitarioPadrao) {
        this.precoUnitarioPadrao = precoUnitarioPadrao;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    // Nota: Não há um setQuantidadeEmEstoque. 
    // A quantidade SÓ PODE ser alterada por adicionarEstoque() ou removerEstoque().
    
    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
