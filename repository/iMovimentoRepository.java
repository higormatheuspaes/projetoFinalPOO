package repository;
import model.Movimento;
import java.util.List;
/**
 * Interface (contrato) para operações de persistência da entidade Movimento.
*/
public interface iMovimentoRepository {

    //Salva um novo movimento (entrada ou saída) no repositório
    void salvar(Movimento movimento);

    //Lista todos os movimentos registrados
    List<Movimento> listarTodos();

}
