package view;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import services.iEstoqueService;

/**
 * Painel para registrar entradas de produtos no sistema de estoque.
 * Este painel permite ao usuário registrar a entrada de um produto, incluindo as informações sobre
 * o código do produto, data da entrada, quantidade e valor unitário da entrada.
 *
 * O painel interage com a camada de serviço de estoque ({@link iEstoqueService}) para realizar o registro
 * da entrada do produto e atualizar o estoque do sistema.
 */
public class RegistrarEntrada extends JPanel {

    private JTextField txtCodigoProduto;
    private JTextField txtData;
    private JTextField txtQuantidade;
    private JTextField txtValorUnitario;

    /**
     * Construtor da classe RegistrarEntrada. Inicializa os componentes gráficos do painel e configura
     * o evento de registro de entrada de produto.
     *
     * @param service O serviço de estoque ({@link iEstoqueService}) que será utilizado para registrar
     *                a entrada do produto no sistema.
     */
    public RegistrarEntrada(iEstoqueService service) {

        // Layout GridBagLayout para organizar os componentes
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        // Título do painel
        JLabel lblTitulo = new JLabel("Registrar Entrada de Produto");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));

        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        add(lblTitulo, c);

        c.gridwidth = 1;

        // Campo Código do Produto
        c.gridy++;
        c.gridx = 0;
        add(new JLabel("Código do Produto:"), c);
        txtCodigoProduto = new JTextField();
        c.gridx = 1;
        add(txtCodigoProduto, c);

        // Campo Data
        c.gridy++;
        c.gridx = 0;
        add(new JLabel("Data (AAAA-MM-DD):"), c);
        txtData = new JTextField(LocalDate.now().toString());
        c.gridx = 1;
        add(txtData, c);

        // Campo Quantidade
        c.gridy++;
        c.gridx = 0;
        add(new JLabel("Quantidade:"), c);
        txtQuantidade = new JTextField();
        c.gridx = 1;
        add(txtQuantidade, c);

        // Campo Valor Unitário
        c.gridy++;
        c.gridx = 0;
        add(new JLabel("Valor Unitário da Entrada:"), c);
        txtValorUnitario = new JTextField();
        c.gridx = 1;
        add(txtValorUnitario, c);

        // Botão para salvar a entrada
        JButton btnSalvar = new JButton("Registrar Entrada");
        c.gridy++;
        c.gridx = 0;
        c.gridwidth = 2;
        add(btnSalvar, c);

        // Evento de ação do botão "Salvar"
        btnSalvar.addActionListener(e -> {
            try {
                // Obtém os dados inseridos pelo usuário
                String codigo = txtCodigoProduto.getText().trim();
                LocalDate data = LocalDate.parse(txtData.getText().trim());
                int quantidade = Integer.parseInt(txtQuantidade.getText().trim());
                double valor = Double.parseDouble(txtValorUnitario.getText().trim());

                // Chama o serviço para registrar a entrada do produto
                service.registrarEntrada(codigo, data, quantidade, valor);

                // Exibe uma mensagem de sucesso
                JOptionPane.showMessageDialog(this, "Entrada registrada com sucesso!");

                // Limpa os campos após o registro
                txtQuantidade.setText("");
                txtValorUnitario.setText("");

            } catch (DateTimeParseException ex) {
                // Exibe uma mensagem de erro para data inválida
                JOptionPane.showMessageDialog(this, "Formato de data inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                // Exibe uma mensagem de erro para quantidade ou valor inválidos
                JOptionPane.showMessageDialog(this, "Quantidade ou valor inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                // Exibe uma mensagem de erro caso ocorra algum problema
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
