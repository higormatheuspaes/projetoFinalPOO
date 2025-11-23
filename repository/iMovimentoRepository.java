package repository;

import model.Movimento;
import java.util.List;

/**
 * Interface (contrato) para operações de persistência da entidade {@link Movimento}.
 * Esta interface define os métodos necessários para salvar e listar movimentos de estoque,
 * como entradas e saídas, em um repositório persistente.
 */
public interface iMovimentoRepository {

    /**
     * Salva um novo movimento (entrada ou saída) no repositório.
     *
     * @param movimento O movimento a ser salvo, que pode ser uma instância de {@link Movimento}.
     */
    void salvar(Movimento movimento);

    /**
     * Lista todos os movimentos registrados no repositório.
     *
     * @return Uma lista de objetos {@link Movimento} (entradas e saídas).
     */
    List<Movimento> listarTodos();
}
