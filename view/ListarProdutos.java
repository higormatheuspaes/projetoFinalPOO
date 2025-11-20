package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import services.iEstoqueService;
import model.Produto;

public class ListarProdutos extends JPanel {
    
    private JTable tabela;
    private DefaultTableModel modelo;

    public ListarProdutos(iEstoqueService service) {

        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Produtos Cadastrados", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new String[]{
            "Código", "Nome", "Preço", "Quantidade", "Categoria"
        }, 0);

        tabela = new JTable(modelo);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        carregarDados(service);
    }

    private void carregarDados(iEstoqueService service) {
        modelo.setRowCount(0);

        for (Produto p : service.listarProdutos()) {
            modelo.addRow(new Object[]{
                p.getCodigo(),
                p.getNome(),
                p.getPrecoUnitarioPadrao(),
                p.getQuantidadeEstoque(),
                p.getCategoria().getNome()
            });
        }
    }
}
