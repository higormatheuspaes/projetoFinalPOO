package repository;

import model.Movimento;
import java.util.ArrayList;
import java.util.List;

public class MovimentoRepository implements iMovimentoRepository {
    private List<Movimento> movimentos = new ArrayList<>();

    @Override
    public void salvar(Movimento movimento) {
        movimentos.add(movimento);
    }

    @Override
    public List<Movimento> listarTodos() {
        return new ArrayList<>(movimentos);
    }
}
