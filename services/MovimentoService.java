package services;

import java.util.List;
import java.util.stream.Collectors;

import model.Entrada;
import model.Movimento;
import model.Saida;
import repository.iMovimentoRepository;

/**
 * Classe responsável pelos serviços de movimentação de estoque.
 * Esta classe encapsula as operações relacionadas à consulta e cálculo de entradas e saídas de produtos.
 *
 * Utiliza o repositório de movimentos para acessar os dados persistidos e fornece métodos para listar entradas,
 * saídas, calcular totais e outras operações relacionadas às movimentações.
 */
public class MovimentoService {

    private final iMovimentoRepository movimentoRepo;

    /**
     * Construtor da classe MovimentoService.
     *
     * @param movimentoRepo O repositório de movimentos, responsável por persistir e fornecer acesso aos dados de movimento.
     */
    public MovimentoService(iMovimentoRepository movimentoRepo) {
        this.movimentoRepo = movimentoRepo;
    }

    /**
     * Lista todas as entradas registradas no sistema.
     *
     * @return Uma lista de {@link Entrada} contendo todas as entradas de estoque registradas.
     */
    public List<Entrada> listarEntradas() {
        return movimentoRepo.listarTodos()
                .stream()
                .filter(m -> m instanceof Entrada)
                .map(m -> (Entrada) m)
                .collect(Collectors.toList());
    }

    /**
     * Lista todas as saídas registradas no sistema.
     *
     * @return Uma lista de {@link Saida} contendo todas as saídas de estoque registradas.
     */
    public List<Saida> listarSaidas() {
        return movimentoRepo.listarTodos()
                .stream()
                .filter(m -> m instanceof Saida)
                .map(m -> (Saida) m)
                .collect(Collectors.toList());
    }

    /**
     * Lista todas as movimentações (entradas e saídas) registradas no sistema.
     *
     * @return Uma lista de todos os movimentos registrados no sistema, tanto entradas quanto saídas.
     */
    public List<Movimento> listarMovimentacoes() {
        return movimentoRepo.listarTodos();
    }

    /**
     * Calcula o total de valor de todas as entradas registradas no sistema.
     * A soma é feita multiplicando a quantidade de cada entrada pelo seu valor unitário.
     *
     * @return O valor total das entradas registradas no sistema.
     */
    public double calcularTotalEntradas() {
        return listarEntradas()
                .stream()
                .mapToDouble(e -> e.getQuantidade() * e.getValorUnitarioEntrada())
                .sum();
    }

    /**
     * Calcula a quantidade total de saídas registradas no sistema.
     *
     * @return A soma das quantidades de todas as saídas registradas no sistema.
     */
    public int quantidadeTotalSaidas() {
        return listarSaidas()
                .stream()
                .mapToInt(Saida::getQuantidade)
                .sum();
    }
}
