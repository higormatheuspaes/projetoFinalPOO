package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import services.iEstoqueService;
import model.Entrada;

public class ListarEntradas extends JPanel {

    private JTable tabela;
    private DefaultTableModel modelo;

    public ListarEntradas(iEstoqueService service) {

        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Entradas Registradas", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new String[]{
            "Data", "Produto", "Quantidade", "Valor Unitário"
        }, 0);

        tabela = new JTable(modelo);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        carregarDados(service);
    }

    private void carregarDados(iEstoqueService service) {
        modelo.setRowCount(0);

        for (Entrada e : service.listarEntradas()) {
            modelo.addRow(new Object[]{
                e.getData(),
                e.getProduto().getNome(),
                e.getQuantidade(),
                e.getValorUnitarioEntrada()
            });
        }
    }

}
