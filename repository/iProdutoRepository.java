package repository;

import model.Produto;
import java.util.List;
import java.util.Optional;

/**
 * Interface (contrato) para operações de persistência da entidade {@link Produto}.
 * Esta interface define os métodos necessários para salvar, buscar, listar, atualizar e remover produtos
 * em um repositório persistente, como um arquivo CSV ou banco de dados.
 * A lógica de negócios (como {@link EstoqueService}) vai usar essa interface sem se preocupar com a implementação
 * específica do repositório de persistência.
 */
public interface iProdutoRepository {

    /**
     * Salva um novo produto no repositório.
     *
     * @param produto O produto a ser salvo no repositório.
     */
    void salvar(Produto produto);

    /**
     * Busca um produto no repositório pelo seu código.
     *
     * @param codigo O código do produto a ser buscado.
     * @return Um {@link Optional} contendo o produto encontrado, ou vazio se não encontrado.
     */
    Optional<Produto> buscarPorCodigo(String codigo);

    /**
     * Lista todos os produtos armazenados no repositório.
     *
     * @return Uma lista de todos os produtos.
     */
    List<Produto> listarTodos();

    /**
     * Atualiza um produto no repositório.
     *
     * @param produto O produto a ser atualizado.
     */
    void atualizar(Produto produto);

    /**
     * Atualiza todos os produtos no repositório.
     *
     * @param produtos A lista de produtos a ser salva no repositório.
     */
    void atualizarTodos(List<Produto> produtos);

    /**
     * Remove um produto do repositório pelo seu código.
     *
     * @param codigo O código do produto a ser removido.
     */
    void remover(String codigo);
}
