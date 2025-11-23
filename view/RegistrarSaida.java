package view;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import services.iEstoqueService;
import model.TipoSaida;

/**
 * Painel para registrar saídas de produtos no sistema de estoque.
 * Este painel permite ao usuário registrar a saída de um produto, incluindo as informações sobre
 * o código do produto, data da saída, quantidade e tipo de saída.
 *
 * O painel interage com a camada de serviço de estoque ({@link iEstoqueService}) para realizar o registro
 * da saída do produto e atualizar o estoque do sistema.
 */
public class RegistrarSaida extends JPanel {

    private JTextField txtCodigoProduto;
    private JTextField txtData;
    private JTextField txtQuantidade;
    private JComboBox<TipoSaida> cbTipoSaida;

    /**
     * Construtor da classe RegistrarSaida. Inicializa os componentes gráficos do painel e configura
     * o evento de registro de saída de produto.
     *
     * @param service O serviço de estoque ({@link iEstoqueService}) que será utilizado para registrar
     *                a saída do produto no sistema.
     */
    public RegistrarSaida(iEstoqueService service) {

        // Layout GridBagLayout para organizar os componentes
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        // Título do painel
        JLabel lblTitulo = new JLabel("Registrar Saída de Produto");
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

        // Campo Tipo de Saída
        c.gridy++;
        c.gridx = 0;
        add(new JLabel("Tipo de Saída:"), c);

        cbTipoSaida = new JComboBox<>(TipoSaida.values());
        c.gridx = 1;
        add(cbTipoSaida, c);

        // Botão para salvar a saída
        JButton btnSalvar = new JButton("Registrar Saída");
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
                TipoSaida tipo = (TipoSaida) cbTipoSaida.getSelectedItem();

                // Chama o serviço para registrar a saída do produto
                service.registrarSaida(codigo, data, quantidade, tipo);

                // Exibe uma mensagem de sucesso
                JOptionPane.showMessageDialog(this, "Saída registrada com sucesso!");

                // Limpa o campo de quantidade após o registro
                txtQuantidade.setText("");

            } catch (DateTimeParseException ex) {
                // Exibe uma mensagem de erro para data inválida
                JOptionPane.showMessageDialog(this, "Formato de data inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                // Exibe uma mensagem de erro para quantidade inválida
                JOptionPane.showMessageDialog(this, "Quantidade inválida!", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                // Exibe uma mensagem de erro caso ocorra algum problema
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
