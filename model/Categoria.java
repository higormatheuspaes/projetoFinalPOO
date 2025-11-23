package model;

/**
 * Classe abstrata que representa a categoria de um produto.
 * Cada categoria possui um nome que pode ser usado para identificar o tipo de produto.
 */
public abstract class Categoria {

    // Nome da categoria
    protected String nome;

    /**
     * Construtor que inicializa uma nova categoria com o nome fornecido.
     *
     * @param nome O nome da categoria.
     */
    public Categoria(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o nome da categoria.
     *
     * @return O nome da categoria.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna uma representação em String do nome da categoria.
     *
     * @return A String com o nome da categoria.
     */
    @Override
    public String toString() {
        return nome;
    }
}
