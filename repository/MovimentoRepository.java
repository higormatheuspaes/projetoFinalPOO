package repository;

import model.Movimento;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementação da interface {@link iMovimentoRepository} que utiliza uma lista em memória para armazenar os movimentos
 * Esta classe fornece uma implementação simples de repositório para salvar e listar movimentos de estoque
 * A lista de movimentos é mantida em memória e não persiste os dados entre reinicializações da aplicação
 */
public class MovimentoRepository implements iMovimentoRepository {

    // Lista em memória para armazenar os movimentos
    private List<Movimento> movimentos = new ArrayList<>();

    /**
     * Salva um novo movimento no repositório
     * Este movimento é armazenado em memória
     *
     * @param movimento O movimento a ser salvo, que pode ser uma instância de {@link Movimento}
     */
    @Override
    public void salvar(Movimento movimento) {
        movimentos.add(movimento);
    }

    /**
     * Lista todos os movimentos armazenados no repositório
     *
     * @return Uma lista de todos os movimentos armazenados em memória.
     */
    @Override
    public List<Movimento> listarTodos() {
        return new ArrayList<>(movimentos); // Retorna uma cópia para evitar manipulação direta da lista interna
    }
}
