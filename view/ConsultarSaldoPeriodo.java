package view;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import services.iEstoqueService;

/**
 * Classe responsável pela interface gráfica (GUI) para consultar o saldo total do estoque em um período específico.
 *
 * O usuário pode informar as datas de início e fim (em formato "AAAA-MM-DD") e, ao clicar no botão "Consultar",
 * a interface exibe o saldo total do estoque dentro do período informado.
 *
 * Utiliza o serviço {@link iEstoqueService} para calcular o saldo total do estoque no período especificado.
 */
public class ConsultarSaldoPeriodo extends JPanel {

    private JTextField txtDataDe;
    private JTextField txtDataAte;
    private JLabel lblResultado;

    /**
     * Construtor da classe ConsultarSaldoPeriodo. Inicializa os componentes da interface gráfica.
     *
     * @param service A instância do serviço {@link iEstoqueService}, usado para consultar o saldo total do estoque em um período.
     */
    public ConsultarSaldoPeriodo(iEstoqueService service) {

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        // Título
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
        txtDataDe = new JTextField(LocalDate.now().minusMonths(1).toString());  // Data de 1 mês atrás por padrão
        c.gridx = 1;
        add(txtDataDe, c);

        // Data final
        c.gridy++;
        c.gridx = 0;
        add(new JLabel("Até (AAAA-MM-DD):"), c);
        txtDataAte = new JTextField(LocalDate.now().toString());  // Data atual por padrão
        c.gridx = 1;
        add(txtDataAte, c);

        // Botão Consultar
        JButton btnConsultar = new JButton("Consultar");
        c.gridy++;
        c.gridx = 0;
        c.gridwidth = 2;
        add(btnConsultar, c);

        // Resultado
        lblResultado = new JLabel("Saldo Total no Período: -");
        c.gridy++;
        add(lblResultado, c);

        // Evento de clique no botão Consultar
        btnConsultar.addActionListener(e -> {
            try {
                // Lê e converte as datas de entrada
                LocalDate de = LocalDate.parse(txtDataDe.getText().trim());
                LocalDate ate = LocalDate.parse(txtDataAte.getText().trim());

                // Consulta o saldo total no período
                double total = service.consultarSaldoTotalEstoque(de, ate);

                // Exibe o resultado na interface
                lblResultado.setText("Saldo Total no Período: R$ " + total);

            } catch (DateTimeParseException ex) {
                // Exibe erro caso as datas estejam no formato incorreto
                JOptionPane.showMessageDialog(this, "Data inválida!", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                // Exibe qualquer outro erro
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
