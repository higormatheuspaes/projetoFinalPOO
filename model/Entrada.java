package model;
import java.time.LocalDate;
// Classe que representa uma entrada no estoque, passando a quantidade de produtos adicionados.
public class Entrada extends Movimento {
    private double valorUnitarioEntrada;
    // CONSTRUTOR
    public Entrada(Produto produto, LocalDate data, int quantidade, double valorUnitarioEntrada) {

        super(produto, data, quantidade);
        this.valorUnitarioEntrada = valorUnitarioEntrada;
    }

    public double getValorUnitarioEntrada() {
        return valorUnitarioEntrada;
    }

}
