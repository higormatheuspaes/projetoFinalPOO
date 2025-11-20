package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import services.iEstoqueService;
import model.*;

public class ListarMovimentacoes extends JPanel {
    
    private JTable tabela;
    private DefaultTableModel modelo;

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

    private void carregarDados(iEstoqueService service) {
        modelo.setRowCount(0);

        for (Movimento m : service.listarTodosMovimentosOrdenadosPorData()) {

            String tipo;
            double impacto = 0;

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
