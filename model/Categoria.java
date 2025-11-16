package model;

/**
 * Classe abstrata que representa a categoria de um produto.
 */
public abstract class Categoria {
    protected String nome;


    //CONSTRUTOR DA CATEGORIA
    public Categoria(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return nome;
    }

}
