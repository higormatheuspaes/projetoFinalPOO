package repository;

import model.Produto;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProdutoRepository implements iProdutoRepository {
     private List<Produto> produtos = new ArrayList<>();

    @Override
    public void salvar(Produto produto) {
        // Se já existe, remove e adiciona de novo (simples persistência em memória)
        produtos.removeIf(p -> p.getCodigo().equalsIgnoreCase(produto.getCodigo()));
        produtos.add(produto);
    }

    @Override
    public Optional<Produto> buscarPorCodigo(String codigo) {
        return produtos.stream()
                .filter(p -> p.getCodigo().equalsIgnoreCase(codigo))
                .findFirst();
    }

    @Override
    public List<Produto> listarTodos() {
        return new ArrayList<>(produtos);
    }
}
