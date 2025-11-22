package repository;
import model.Produto;
import java.util.List;
import java.util.Optional;

// Este é o contrato para quem for gerenciar os produtos. A nossa lógica de negócio (EstoqueService) vai usar este contrato para pedir que produtos sejam salvos ou buscados, sem se importar se isso é feito em CSV ou binário.

/**
 * Interface (contrato) para operações de persistência da entidade Produto.
 * Define o que a camada de negócio espera da camada de persistência.
 */

public interface iProdutoRepository {
    //Salva um produto no repositório
    void salvar(Produto produto);

    //Busca um produto pelo código
    Optional<Produto> buscarPorCodigo(String codigo);

    //Lista todos os produtos
    List<Produto> listarTodos();

    void atualizar (Produto produto);

    void atualizarTodos(List<Produto> produtos);

    void remover(String codigo);
}
