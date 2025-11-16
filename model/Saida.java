package model;

//Representa uma saida de produto herdando de movimento
public class Saida extends Movimento{
    private TipoSaida tipo;

    public Saida(Produto produto, java.time.LocalDate data, int quantidade, TipoSaida tipo) {
        super(produto, data, quantidade);
        this.tipo = tipo;
    }

    public TipoSaida getTipo() {
        return tipo;
    }
}
