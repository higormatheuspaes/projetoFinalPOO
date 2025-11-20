package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import services.iEstoqueService;
import model.Saida;

public class ListarSaidas extends JPanel {

    private JTable tabela;
    private DefaultTableModel modelo;

    public ListarSaidas(iEstoqueService service) {

        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Saídas Registradas", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new String[]{
            "Data", "Produto", "Quantidade", "Tipo"
        }, 0);

        tabela = new JTable(modelo);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        carregarDados(service);
    }

    private void carregarDados(iEstoqueService service) {
        modelo.setRowCount(0);

        for (Saida s : service.listarSaidas()) {
            modelo.addRow(new Object[]{
                s.getData(),
                s.getProduto().getNome(),
                s.getQuantidade(),
                s.getTipo()
            });
        }
    }

}
