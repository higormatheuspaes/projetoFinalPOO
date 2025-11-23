package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import services.iEstoqueService;
import model.Saida;

/**
 * Classe responsável pela interface gráfica (GUI) para listar as saídas registradas do estoque.
 *
 * Exibe as saídas registradas no sistema, com informações sobre a data, produto, quantidade e tipo de saída.
 * Utiliza o serviço {@link iEstoqueService} para acessar os dados das saídas registradas.
 */
public class ListarSaidas extends JPanel {

    private JTable tabela;
    private DefaultTableModel modelo;

    /**
     * Construtor da classe ListarSaidas. Inicializa os componentes da interface gráfica.
     *
     * @param service A instância do serviço {@link iEstoqueService}, usado para acessar as saídas registradas do estoque.
     */
    public ListarSaidas(iEstoqueService service) {

        setLayout(new BorderLayout());

        // Título
        JLabel titulo = new JLabel("Saídas Registradas", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        // Modelo da tabela
        modelo = new DefaultTableModel(new String[]{
                "Data", "Produto", "Quantidade", "Tipo"
        }, 0);

        tabela = new JTable(modelo);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // Carregar os dados das saídas
        carregarDados(service);
    }

    /**
     * Carrega os dados das saídas no modelo da tabela.
     *
     * @param service A instância do serviço {@link iEstoqueService}, usado para listar as saídas registradas.
     */
    private void carregarDados(iEstoqueService service) {
        modelo.setRowCount(0);

        // Itera sobre as saídas e adiciona na tabela
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
