package services;

import java.util.List;
import java.util.stream.Collectors;

import model.Entrada;
import model.Movimento;
import model.Saida;
import repository.iMovimentoRepository;

public class MovimentoService {

    private final iMovimentoRepository movimentoRepo;

    public MovimentoService(iMovimentoRepository movimentoRepo) {
        this.movimentoRepo = movimentoRepo;
    }

    public List<Entrada> listarEntradas() {
        return movimentoRepo.listarTodos()
                .stream()
                .filter(m -> m instanceof Entrada)
                .map(m -> (Entrada) m)
                .collect(Collectors.toList());
    }
    
    public List<Saida> listarSaidas() {
        return movimentoRepo.listarTodos()
                .stream()
                .filter(m -> m instanceof Saida)
                .map(m -> (Saida) m)
                .collect(Collectors.toList());
    }

    public List<Movimento> listarMovimentacoes() {
        return movimentoRepo.listarTodos();
    }

    public double calcularTotalEntradas() {
        return listarEntradas()
                .stream()
                .mapToDouble(e -> e.getQuantidade() * e.getValorUnitarioEntrada())
                .sum();
    }

    public int quantidadeTotalSaidas() {
        return listarSaidas()
                .stream()
                .mapToInt(Saida::getQuantidade)
                .sum();
    }
}

