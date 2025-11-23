package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import services.iEstoqueService;
import model.Entrada;

/**
 * Classe responsável pela interface gráfica (GUI) para listar as entradas registradas no estoque.
 *
 * Exibe as entradas realizadas, com as informações sobre a data, produto, quantidade e valor unitário.
 * Utiliza o serviço {@link iEstoqueService} para acessar os dados das entradas registradas.
 */
public class ListarEntradas extends JPanel {

    private JTable tabela;
    private DefaultTableModel modelo;

    /**
     * Construtor da classe ListarEntradas. Inicializa os componentes da interface gráfica.
     *
     * @param service A instância do serviço {@link iEstoqueService}, usado para acessar as entradas registradas no estoque.
     */
    public ListarEntradas(iEstoqueService service) {

        setLayout(new BorderLayout());

        // Título
        JLabel titulo = new JLabel("Entradas Registradas", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        // Modelo da tabela
        modelo = new DefaultTableModel(new String[]{
                "Data", "Produto", "Quantidade", "Valor Unitário"
        }, 0);

        tabela = new JTable(modelo);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // Carregar os dados das entradas
        carregarDados(service);
    }

    /**
     * Carrega os dados das entradas no modelo da tabela.
     *
     * @param service A instância do serviço {@link iEstoqueService}, usado para listar as entradas registradas.
     */
    private void carregarDados(iEstoqueService service) {
        modelo.setRowCount(0);

        // Itera sobre as entradas e adiciona na tabela
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
