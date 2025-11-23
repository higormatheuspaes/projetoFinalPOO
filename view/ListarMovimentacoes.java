package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import services.iEstoqueService;
import model.*;

/**
 * Classe responsável pela interface gráfica (GUI) que exibe as movimentações de estoque (entradas e saídas)
 * em uma tabela, com a possibilidade de visualizar o impacto no valor do estoque. Esta classe faz parte da
 * camada de visualização (view) do sistema.
 *
 * Utiliza o {@link iEstoqueService} para buscar e exibir as movimentações registradas no sistema.
 * A tabela exibe informações como data, produto, tipo de movimento (entrada ou saída), quantidade e impacto no valor do estoque.
 */
public class ListarMovimentacoes extends JPanel {

    private JTable tabela;
    private DefaultTableModel modelo;

    /**
     * Construtor da classe ListarMovimentacoes. Inicializa os componentes da interface gráfica.
     *
     * @param service A instância do serviço {@link iEstoqueService}, usado para listar as movimentações de estoque.
     */
    public ListarMovimentacoes(iEstoqueService service) {

        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Movimentações de Estoque (Extrato)", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new String[]{
                "Data", "Produto", "Tipo", "Quantidade", "Impacto no Valor"
        }, 0);

        tabela = new JTable(modelo);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        carregarDados(service);
    }

    /**
     * Carrega os dados das movimentações de estoque (entradas e saídas) na tabela.
     *
     * Para cada movimento (entrada ou saída), são exibidos os seguintes dados:
     * <ul>
     *   <li>Data do movimento</li>
     *   <li>Nome do produto</li>
     *   <li>Tipo de movimento (entrada ou saída)</li>
     *   <li>Quantidade de produto movimentada</li>
     *   <li>Impacto no valor total do estoque (quantidade * valor unitário)</li>
     * </ul>
     *
     * @param service A instância do serviço {@link iEstoqueService} para listar os movimentos de estoque.
     */
    private void carregarDados(iEstoqueService service) {
        modelo.setRowCount(0);  // Limpa a tabela antes de carregar os dados

        // Itera sobre os movimentos ordenados por data
        for (Movimento m : service.listarTodosMovimentosOrdenadosPorData()) {

            String tipo;
            double impacto = 0;

            // Verifica se o movimento é uma entrada ou saída e calcula o impacto no valor
            if (m instanceof Entrada e) {
                tipo = "ENTRADA";
                impacto = e.getQuantidade() * e.getValorUnitarioEntrada();
            }
            else if (m instanceof Saida s) {
                tipo = "SAÍDA";
                impacto = -s.getQuantidade() * s.getProduto().getPrecoUnitarioPadrao();
            }
            else {
                tipo = "DESCONHECIDO";
            }

            // Adiciona os dados do movimento na tabela
            modelo.addRow(new Object[]{
                    m.getData(),
                    m.getProduto().getNome(),
                    tipo,
                    m.getQuantidade(),
                    impacto
            });
        }
    }
}
