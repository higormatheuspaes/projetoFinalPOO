package view;

import javax.swing.*;
import java.awt.*;
import services.iEstoqueService;
import java.util.Map;

/**
 * Classe responsável pela interface gráfica (GUI) para consultar o saldo atual de um produto no estoque.
 *
 * A classe permite que o usuário informe o código de um produto e, ao clicar no botão "Consultar",
 * a interface exibe a quantidade e o valor total do estoque desse produto.
 *
 * Utiliza o serviço {@link iEstoqueService} para buscar os dados do produto e exibir as informações de saldo.
 */
public class ConsultarSaldoProduto extends JPanel {

    private JTextField txtCodigo;
    private JLabel lblQuantidadeValor;
    private JLabel lblValorTotal;

    /**
     * Construtor da classe ConsultarSaldoProduto. Inicializa os componentes da interface gráfica.
     *
     * @param service A instância do serviço {@link iEstoqueService}, usado para consultar o saldo atual de um produto.
     */
    public ConsultarSaldoProduto(iEstoqueService service) {

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel titulo = new JLabel("Consultar Saldo Atual de Produto");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));

        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        add(titulo, c);

        c.gridwidth = 1;

        // Código do produto
        c.gridy++;
        add(new JLabel("Código do Produto:"), c);

        txtCodigo = new JTextField();
        c.gridx = 1;
        add(txtCodigo, c);

        // Botão Consultar
        JButton btnBuscar = new JButton("Consultar");
        c.gridy++;
        c.gridx = 0;
        c.gridwidth = 2;
        add(btnBuscar, c);

        // Resultados
        c.gridy++;
        lblQuantidadeValor = new JLabel("Quantidade: -");
        add(lblQuantidadeValor, c);

        c.gridy++;
        lblValorTotal = new JLabel("Valor Total: -");
        add(lblValorTotal, c);

        // Evento de clique no botão Consultar
        btnBuscar.addActionListener(e -> {
            try {
                String codigo = txtCodigo.getText().trim();

                // Chama o serviço para consultar o saldo do produto
                Map<String, Object> saldo = service.consultarSaldoAtual(codigo);

                // Exibe os dados na interface
                lblQuantidadeValor.setText("Quantidade: " + saldo.get("Quantidade"));
                lblValorTotal.setText("Valor Total: R$ " + saldo.get("ValorTotalEstoque"));

            } catch (Exception ex) {
                // Exibe erro caso ocorra alguma exceção
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
