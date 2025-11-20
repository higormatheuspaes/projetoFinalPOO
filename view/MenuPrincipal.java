package view;

import javax.swing.*;
import java.awt.*;
import services.iEstoqueService;

public class MenuPrincipal extends JFrame {
    private CardLayout cardLayout;
    private JPanel painelPrincipal;

    public MenuPrincipal(iEstoqueService service) {
        setTitle("Sistema de Estoque - Loja de Informática");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        //Menu Lateral
        JPanel menu = new JPanel();
        menu.setLayout(new GridLayout(10, 1, 5, 5));
        menu.setPreferredSize(new Dimension(180, 0));

        JButton btnCadastroProduto = new JButton("Cadastrar Produto");
        JButton btnRegistrarEntrada = new JButton("Registrar Entrada");
        JButton btnRegistrarSaida = new JButton("Registrar Saída");
        JButton btnListarProdutos = new JButton("Produtos Cadastrados");
        JButton btnListarEntradas = new JButton("Entradas");
        JButton btnListarSaidas = new JButton("Saídas");
        JButton btnMovimentacoes = new JButton("Movimentações");
        JButton btnSaldoProduto = new JButton("Saldo do Produto");
        JButton btnSaldoPeriodo = new JButton("Saldo por Período");

        menu.add(btnCadastroProduto);
        menu.add(btnRegistrarEntrada);
        menu.add(btnRegistrarSaida);
        menu.add(btnListarProdutos);
        menu.add(btnListarProdutos);
        menu.add(btnListarEntradas);
        menu.add(btnListarSaidas);
        menu.add(btnMovimentacoes);
        menu.add(btnSaldoProduto);
        menu.add(btnSaldoPeriodo);

        add(menu, BorderLayout.WEST);

        //Painel Principal (em Cards)
        cardLayout = new CardLayout();
        painelPrincipal = new JPanel(cardLayout);

        painelPrincipal.add(new ProdutoCadastro(service), "cadastroProduto");
        painelPrincipal.add(new ListarEntradas(service), "entradas");
        painelPrincipal.add(new ListarSaidas(service), "saidas");
        painelPrincipal.add(new ListarMovimentacoes(service), "movimentos");
        painelPrincipal.add(new ConsultarSaldoProduto(service), "saldoProduto");
        painelPrincipal.add(new ConsultarSaldoPeriodo(service), "saldoPeriodo");

        add(painelPrincipal, BorderLayout.CENTER);

        //Eventos
        btnCadastroProduto.addActionListener(e -> cardLayout.show(painelPrincipal, "cadastroProduto"));
        btnListarEntradas.addActionListener(e -> cardLayout.show(painelPrincipal, "entradas"));
        btnListarSaidas.addActionListener(e -> cardLayout.show(painelPrincipal, "saidas"));
        btnMovimentacoes.addActionListener(e -> cardLayout.show(painelPrincipal, "movimentos"));
        btnSaldoProduto.addActionListener(e -> cardLayout.show(painelPrincipal, "saldoProduto"));
        btnSaldoPeriodo.addActionListener(e -> cardLayout.show(painelPrincipal, "saldoPeriodo"));
    }

}
