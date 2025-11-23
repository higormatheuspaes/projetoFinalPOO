package model;

import java.time.LocalDate;

/**
 * Classe abstrata que representa um movimento de estoque, que pode ser uma entrada ou saída de produtos.
 * Esta classe contém as informações básicas sobre o produto, a data e a quantidade envolvida no movimento.
 */
public abstract class Movimento {

    // Produto associado ao movimento
    protected Produto produto;

    // Data do movimento
    protected LocalDate data;

    // Quantidade de produtos no movimento
    protected int quantidade;

    /**
     * Construtor que inicializa um novo movimento de estoque.
     *
     * @param produto O produto que está sendo movimentado.
     * @param data A data em que o movimento ocorreu.
     * @param quantidade A quantidade de unidades do produto envolvidas no movimento.
     */
    public Movimento(Produto produto, LocalDate data, int quantidade) {
        this.produto = produto;
        this.data = data;
        this.quantidade = quantidade;
    }

    /**
     * Retorna o produto associado ao movimento.
     *
     * @return O produto do movimento.
     */
    public Produto getProduto() {
        return produto;
    }

    /**
     * Retorna a data em que o movimento ocorreu.
     *
     * @return A data do movimento.
     */
    public LocalDate getData() {
        return data;
    }

    /**
     * Retorna a quantidade de produtos movimentados.
     *
     * @return A quantidade de unidades movimentadas.
     */
    public int getQuantidade() {
        return quantidade;
    }
}
