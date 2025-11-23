package repository;

import model.Produto;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementação da interface {@link iProdutoRepository} que utiliza uma lista em memória para armazenar os produtos
 * Esta classe fornece uma implementação simples de repositório para salvar, buscar e listar produtos
 * A lista de produtos é mantida em memória e não persiste os dados entre reinicializações da aplicação
 */
public class ProdutoRepository implements iProdutoRepository {

    // Lista em memória para armazenar os produtos
    private List<Produto> produtos = new ArrayList<>();

    /**
     * Salva um produto no repositório. Se o produto já existir (mesmo código), ele é removido antes de ser adicionado novamente.
     * Este método realiza uma persistência simples em memória.
     *
     * @param produto O produto a ser salvo no repositório.
     */
    @Override
    public void salvar(Produto produto) {
        // Se já existe, remove e adiciona de novo
        produtos.removeIf(p -> p.getCodigo().equalsIgnoreCase(produto.getCodigo()));
        produtos.add(produto);
    }

    /**
     * Busca um produto pelo código no repositório.
     *
     * @param codigo O código do produto a ser buscado.
     * @return Um {@link Optional} contendo o produto encontrado, ou vazio se o produto não existir.
     */
    @Override
    public Optional<Produto> buscarPorCodigo(String codigo) {
        return produtos.stream()
                .filter(p -> p.getCodigo().equalsIgnoreCase(codigo))
                .findFirst();
    }

    /**
     * Lista todos os produtos armazenados no repositório.
     *
     * @return Uma lista de todos os produtos armazenados em memória.
     */
    @Override
    public List<Produto> listarTodos() {
        return new ArrayList<>(produtos); // Retorna uma cópia para evitar manipulação direta da lista interna
    }

    /**
     * Método não implementado. Lança uma exceção {@link UnsupportedOperationException}.
     *
     * @param produto O produto a ser atualizado.
     * @throws UnsupportedOperationException Quando o método não é implementado.
     */
    @Override
    public void atualizar(Produto produto) {
        throw new UnsupportedOperationException("Unimplemented method 'atualizar'");
    }

    /**
     * Método não implementado. Lança uma exceção {@link UnsupportedOperationException}.
     *
     * @param produtos A lista de produtos a ser atualizada.
     * @throws UnsupportedOperationException Quando o método não é implementado.
     */
    @Override
    public void atualizarTodos(List<Produto> produtos) {
        throw new UnsupportedOperationException("Unimplemented method 'atualizarTodos'");
    }

    /**
     * Método não implementado. Lança uma exceção {@link UnsupportedOperationException}.
     *
     * @param codigo O código do produto a ser removido.
     * @throws UnsupportedOperationException Quando o método não é implementado.
     */
    @Override
    public void remover(String codigo) {
        throw new UnsupportedOperationException("Unimplemented method 'remover'");
    }
}
