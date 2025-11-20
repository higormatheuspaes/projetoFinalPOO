package view;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import services.iEstoqueService;

public class RegistrarEntrada extends JPanel {

    private JTextField txtCodigoProduto;
    private JTextField txtData;
    private JTextField txtQuantidade;
    private JTextField txtValorUnitario;

    public RegistrarEntrada(iEstoqueService service) {

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("Registrar Entrada de Produto");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));

        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        add(lblTitulo, c);

        c.gridwidth = 1;

        //Código do Produto
        c.gridy++;
        c.gridx = 0;
        add(new JLabel("Código do Produto:"), c);
        txtCodigoProduto = new JTextField();
        c.gridx = 1;
        add(txtCodigoProduto, c);

        //Data
        c.gridy++;
        c.gridx = 0;
        add(new JLabel("Data (AAAA-MM-DD):"), c);
        txtData = new JTextField(LocalDate.now().toString());
        c.gridx = 1;
        add(txtData, c);

        //Quantidade
        c.gridy++;
        c.gridx = 0;
        add(new JLabel("Quantidade:"), c);
        txtQuantidade = new JTextField();
        c.gridx = 1;
        add(txtQuantidade, c);

        //Valor Unitário
        c.gridy++;
        c.gridx = 0;
        add(new JLabel("Valor Unitário da Entrada:"), c);
        txtValorUnitario = new JTextField();
        c.gridx = 1;
        add(txtValorUnitario, c);

        //Botão
        JButton btnSalvar = new JButton("Registrar Entrada");
        c.gridy++;
        c.gridx = 0;
        c.gridwidth = 2;
        add(btnSalvar, c);

        //Evento
        btnSalvar.addActionListener(e -> {
            try {
                String codigo = txtCodigoProduto.getText().trim();
                LocalDate data = LocalDate.parse(txtData.getText().trim());
                int quantidade = Integer.parseInt(txtQuantidade.getText().trim());
                double valor = Double.parseDouble(txtValorUnitario.getText().trim());

                service.registrarEntrada(codigo, data, quantidade, valor);

                JOptionPane.showMessageDialog(this, "Entrada registrada com sucesso!");

                txtQuantidade.setText("");
                txtValorUnitario.setText("");

            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Formato de data inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Quantidade ou valor inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

}
