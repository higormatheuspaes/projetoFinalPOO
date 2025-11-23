package view;

import javax.swing.*;
import java.awt.*;
import services.iEstoqueService;

/**
 * Painel de cadastro de produtos no sistema de estoque.
 * Este painel permite ao usuário inserir informações sobre um novo produto e cadastrá-lo no sistema.
 *
 * As informações necessárias para o cadastro incluem:
 * - Código do produto
 * - Nome do produto
 * - Preço unitário
 * - Quantidade inicial em estoque
 * - Categoria do produto
 */
public class ProdutoCadastro extends JPanel {

    private JTextField txtCodigo;
    private JTextField txtNome;
    private JTextField txtPreco;
    private JTextField txtQuantidade;
    private JComboBox<String> cbCategoria;

    /**
     * Construtor da classe ProdutoCadastro. Inicializa os componentes gráficos do painel e configura o evento
     * de cadastro do produto.
     *
     * @param service O serviço de estoque ({@link iEstoqueService}) que será utilizado para realizar o cadastro.
     */
    public ProdutoCadastro(iEstoqueService service) {

        // Layout GridBagLayout para organizar os componentes
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        // Título do painel
        JLabel lblTitulo = new JLabel("Cadastro de Produto");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));

        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        add(lblTitulo, c);

        c.gridwidth = 1;

        // Campo Código
        c.gridy++;
        add(new JLabel("Código:"), c);
        txtCodigo = new JTextField();
        c.gridx = 1;
        add(txtCodigo, c);

        // Campo Nome
        c.gridy++;
        c.gridx = 0;
        add(new JLabel("Nome:"), c);
        txtNome = new JTextField();
        c.gridx = 1;
        add(txtNome, c);

        // Campo Preço Unitário
        c.gridy++;
        c.gridx = 0;
        add(new JLabel("Preço Unitário:"), c);
        txtPreco = new JTextField();
        c.gridx = 1;
        add(txtPreco, c);

        // Campo Quantidade Inicial
        c.gridy++;
        c.gridx = 0;
        add(new JLabel("Quantidade Inicial:"), c);
        txtQuantidade = new JTextField();
        c.gridx = 1;
        add(txtQuantidade, c);

        // Campo Categoria
        c.gridy++;
        c.gridx = 0;
        add(new JLabel("Categoria:"), c);
        cbCategoria = new JComboBox<>(new String[]{
                "hardware", "periferico", "acessorio", "outro"
        });
        c.gridx = 1;
        add(cbCategoria, c);

        // Botão Salvar Produto
        JButton btnSalvar = new JButton("Salvar Produto");
        c.gridy++;
        c.gridx = 0;
        c.gridwidth = 2;
        add(btnSalvar, c);

        // Evento de ação do botão Salvar
        btnSalvar.addActionListener(e -> {
            try {
                // Obtém os dados inseridos pelo usuário
                String codigo = txtCodigo.getText().trim();
                String nome = txtNome.getText().trim();
                double preco = Double.parseDouble(txtPreco.getText().trim());
                int quantidade = Integer.parseInt(txtQuantidade.getText().trim());
                String categoria = cbCategoria.getSelectedItem().toString();

                // Chama o serviço para cadastrar o produto
                service.cadastrarProduto(codigo, nome, preco, quantidade, categoria);

                // Exibe uma mensagem de sucesso
                JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!");

                // Limpa os campos após o cadastro
                txtCodigo.setText("");
                txtNome.setText("");
                txtPreco.setText("");
                txtQuantidade.setText("");

            } catch (Exception ex) {
                // Exibe uma mensagem de erro caso ocorra algum problema
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
