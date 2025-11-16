package model;
import java.time.LocalDate;

/**
  Classe abstrata que representa um movimento de estoque (Entrada ou Saída).
*/
public abstract class Movimento {
    protected Produto produto;
    protected LocalDate data;
    protected int quantidade;


    // CONSTRUTOR
    public Movimento(Produto produto, LocalDate data, int quantidade) {
        this.produto = produto;
        this.data = data;
        this.quantidade = quantidade;
    }

    // -- GETTERS E SETTERS --
    public Produto getProduto(){
        return produto;
    }

    public LocalDate getData() {
        return data;
    }

    public int getQuantidade() {
        return quantidade;
    }
}
