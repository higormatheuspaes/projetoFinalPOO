package view;

import javax.swing.*;
import java.awt.*;
import services.iEstoqueService;
import java.util.Map;

public class ConsultarSaldoProduto extends JPanel {

    private JTextField txtCodigo;
    private JLabel lblQuantidadeValor;
    private JLabel lblValorTotal;

    public ConsultarSaldoProduto(iEstoqueService service) {

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("Consultar Saldo Atual de Produto");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));

        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        add(titulo, c);

        c.gridwidth = 1;

        // Código
        c.gridy++;
        add(new JLabel("Código do Produto:"), c);

        txtCodigo = new JTextField();
        c.gridx = 1;
        add(txtCodigo, c);

        // Botão Buscar
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

        // Evento do botão
        btnBuscar.addActionListener(e -> {
            try {
                String codigo = txtCodigo.getText().trim();

                Map<String, Object> saldo = service.consultarSaldoAtual(codigo);

                lblQuantidadeValor.setText("Quantidade: " + saldo.get("Quantidade"));
                lblValorTotal.setText("Valor Total: R$ " + saldo.get("ValorTotalEstoque"));

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

}
