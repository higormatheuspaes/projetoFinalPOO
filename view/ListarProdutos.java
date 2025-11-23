package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import services.iEstoqueService;
import model.Produto;

/**
 * Classe responsável pela interface gráfica (GUI) para listar os produtos cadastrados no estoque.
 *
 * Exibe os produtos cadastrados, com as informações sobre o código, nome, preço, quantidade e categoria.
 * Utiliza o serviço {@link iEstoqueService} para acessar os dados dos produtos cadastrados.
 */
public class ListarProdutos extends JPanel {

    private JTable tabela;
    private DefaultTableModel modelo;

    /**
     * Construtor da classe ListarProdutos. Inicializa os componentes da interface gráfica.
     *
     * @param service A instância do serviço {@link iEstoqueService}, usado para acessar os produtos cadastrados no estoque.
     */
    public ListarProdutos(iEstoqueService service) {

        setLayout(new BorderLayout());

        // Título
        JLabel titulo = new JLabel("Produtos Cadastrados", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        // Modelo da tabela
        modelo = new DefaultTableModel(new String[]{
                "Código", "Nome", "Preço", "Quantidade", "Categoria"
        }, 0);

        tabela = new JTable(modelo);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // Carregar os dados dos produtos
        carregarDados(service);
    }

    /**
     * Carrega os dados dos produtos no modelo da tabela.
     *
     * @param service A instância do serviço {@link iEstoqueService}, usado para listar os produtos cadastrados.
     */
    private void carregarDados(iEstoqueService service) {
        modelo.setRowCount(0);

        // Itera sobre os produtos e adiciona na tabela
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
