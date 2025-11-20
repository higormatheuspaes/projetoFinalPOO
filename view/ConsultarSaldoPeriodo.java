package view;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import services.iEstoqueService;

public class ConsultarSaldoPeriodo extends JPanel {
    
    private JTextField txtDataDe;
    private JTextField txtDataAte;
    private JLabel lblResultado;

    public ConsultarSaldoPeriodo(iEstoqueService service) {

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("Saldo Total do Estoque por Período");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));

        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        add(titulo, c);

        c.gridwidth = 1;

        // Data inicial
        c.gridy++;
        add(new JLabel("De (AAAA-MM-DD):"), c);
        txtDataDe = new JTextField(LocalDate.now().minusMonths(1).toString());
        c.gridx = 1;
        add(txtDataDe, c);

        // Data final
        c.gridy++;
        c.gridx = 0;
        add(new JLabel("Até (AAAA-MM-DD):"), c);
        txtDataAte = new JTextField(LocalDate.now().toString());
        c.gridx = 1;
        add(txtDataAte, c);

        // Botão consultar
        JButton btnConsultar = new JButton("Consultar");
        c.gridy++;
        c.gridx = 0;
        c.gridwidth = 2;
        add(btnConsultar, c);

        // Resultado
        lblResultado = new JLabel("Saldo Total no Período: -");
        c.gridy++;
        add(lblResultado, c);

        // Evento
        btnConsultar.addActionListener(e -> {
            try {
                LocalDate de = LocalDate.parse(txtDataDe.getText().trim());
                LocalDate ate = LocalDate.parse(txtDataAte.getText().trim());

                double total = service.consultarSaldoTotalEstoque(de, ate);

                lblResultado.setText("Saldo Total no Período: R$ " + total);

            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Data inválida!", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

}
