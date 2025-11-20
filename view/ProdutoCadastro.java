package view;

import javax.swing.*;
import java.awt.*;
import services.iEstoqueService;

public class ProdutoCadastro extends JPanel {
    private JTextField txtCodigo;
    private JTextField txtNome;
    private JTextField txtPreco;
    private JTextField txtQuantidade;
    private JComboBox<String> cbCategoria;

    public ProdutoCadastro(iEstoqueService service) {

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("Cadastro de Produto");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));

        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        add(lblTitulo, c);

        c.gridwidth = 1;

        //Código
        c.gridy++;
        add(new JLabel("Código:"), c);
        txtCodigo = new JTextField();
        c.gridx = 1;
        add(txtCodigo, c);

        //Nome
        c.gridy++;
        c.gridx = 0;
        add(new JLabel("Nome:"), c);
        txtNome = new JTextField();
        c.gridx = 1;
        add(txtNome, c);

        //Preço
        c.gridy++;
        c.gridx = 0;
        add(new JLabel("Preço Unitário:"), c);
        txtPreco = new JTextField();
        c.gridx = 1;
        add(txtPreco, c);

        //Quantidade
        c.gridy++;
        c.gridx = 0;
        add(new JLabel("Quantidade Inicial:"), c);
        txtQuantidade = new JTextField();
        c.gridx = 1;
        add(txtQuantidade, c);

        //Categoria
        c.gridy++;
        c.gridx = 0;
        add(new JLabel("Categoria:"), c);
        cbCategoria = new JComboBox<>(new String[]{
            "hardware", "periferico", "acessorio", "outro"
        });
        c.gridx = 1;
        add(cbCategoria, c);

        //Botão Salvar
        JButton btnSalvar = new JButton("Salvar Produto");
        c.gridy++;
        c.gridx = 0;
        c.gridwidth = 2;
        add(btnSalvar, c);

        //Evento do botão
        btnSalvar.addActionListener(e -> {
            try {
                String codigo = txtCodigo.getText().trim();
                String nome = txtNome.getText().trim();
                double preco = Double.parseDouble(txtPreco.getText().trim());
                int quantidade = Integer.parseInt(txtQuantidade.getText().trim());
                String categoria = cbCategoria.getSelectedItem().toString();

                service.cadastrarProduto(codigo, nome, preco, quantidade, categoria);

                JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!");

                txtCodigo.setText("");
                txtNome.setText("");
                txtPreco.setText("");
                txtQuantidade.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

}
