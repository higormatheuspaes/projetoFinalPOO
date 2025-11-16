package services;
import model.*;
import repository.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementação concreta da camada de negócio.
 * Este é o "cérebro" do sistema, contendo toda a lógica de negócio.
*/
public class EstoqueService implements iEstoqueService{
    
    // --- Dependências (Os "Estoquistas") ---
    private final iProdutoRepository produtoRepository;
    private final iMovimentoRepository movimentoRepository;


    // --- Estado em memória  --- 
    private List<Produto> produtos; 
    private List<Movimento> movimentos;

    // --- Construtor ---
    public EstoqueService(iProdutoRepository produtoRepository, iMovimentoRepository movimentoRepository){
        this.produtoRepository = produtoRepository;
        this.movimentoRepository = movimentoRepository;
        
        //Carrega o estado inicial da "base de dados"  
        this.produtos = produtoRepository.listarTodos();
        this.movimentos = movimentoRepository.listarTodos();
    }

    // -- Implementação de Métodos (R1 - R8)
    // Implementar ainda...
}
