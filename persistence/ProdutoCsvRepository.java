package persistence;

import java.util.List;
import java.util.Optional;

import model.Produto;
import repository.iProdutoRepository;

public class ProdutoCsvRepository implements iProdutoRepository{

    @Override
    public Optional<Produto> buscarPorCodigo(String codigo) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public List<Produto> listarTodos() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void salvar(Produto produto) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void atualizar(Produto produto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'atualizar'");
    }

    @Override
    public void atualizarTodos(List<Produto> produtos) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'atualizarTodos'");
    }

    @Override
    public void remover(String codigo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remover'");
    }

    
}
