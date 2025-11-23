package model;

// Representa uma saída de produto, herdando da classe {@link Movimento}.
public class Saida extends Movimento {

    // Tipo de saída (ex: venda, devolução, etc.)
    private TipoSaida tipo;

    /**
     * Construtor que inicializa uma saída de produto com os dados fornecidos.
     *
     * @param produto O produto que está sendo removido do estoque.
     * @param data A data em que a saída ocorreu.
     * @param quantidade A quantidade de unidades do produto sendo removida do estoque.
     * @param tipo O tipo de saída, que pode ser uma instância da classe {@link TipoSaida}.
     */
    public Saida(Produto produto, java.time.LocalDate data, int quantidade, TipoSaida tipo) {
        super(produto, data, quantidade);
        this.tipo = tipo;
    }

    /**
     * Retorna o tipo de saída associada ao movimento.
     *
     * @return O tipo de saída do produto.
     */
    public TipoSaida getTipo() {
        return tipo;
    }
}
