package model;

import java.time.LocalDate;

/**
 * Classe que representa uma entrada no estoque, registrando a quantidade de produtos adicionados.
 * Extende a classe {@link Movimento} e adiciona a informação do valor unitário de entrada.
 */
public class Entrada extends Movimento {

    // Valor unitário do produto na entrada
    private double valorUnitarioEntrada;

    /**
     * Construtor que inicializa uma nova entrada no estoque.
     *
     * @param produto O produto que está sendo adicionado ao estoque.
     * @param data A data em que a entrada ocorreu.
     * @param quantidade A quantidade de unidades do produto sendo adicionada.
     * @param valorUnitarioEntrada O valor unitário de cada unidade do produto na entrada.
     */
    public Entrada(Produto produto, LocalDate data, int quantidade, double valorUnitarioEntrada) {
        super(produto, data, quantidade);
        this.valorUnitarioEntrada = valorUnitarioEntrada;
    }

    /**
     * Retorna o valor unitário de entrada do produto.
     *
     * @return O valor unitário do produto na entrada.
     */
    public double getValorUnitarioEntrada() {
        return valorUnitarioEntrada;
    }
}
