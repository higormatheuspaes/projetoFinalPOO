import view.MenuPrincipal;
import repository.*;
import services.*;
import persistence.*;

/**
 * Classe principal do sistema.
 *
 * A classe {@link Main} é responsável por inicializar o sistema de estoque, criando as instâncias necessárias
 * dos repositórios e do serviço de estoque. Em seguida, ela cria a interface gráfica principal ({@link MenuPrincipal})
 * e a exibe para o usuário.
 */
public class Main {

    /**
     * Método principal da aplicação. Este método é o ponto de entrada do programa,
     * onde as dependências necessárias para o funcionamento do sistema são criadas
     * e a interface gráfica é iniciada.
     *
     * @param args Argumentos passados pela linha de comando (não utilizados neste caso).
     */
    public static void main(String[] args) {

        // Criação dos repositórios (persistência)
        iProdutoRepository prodRepo = new ProdutoCsvRepository();  // Repositório de produtos em CSV
        iMovimentoRepository movRepo = new MovimentoCsvRepository(prodRepo); // Repositório de movimentos em CSV

        // Criação do serviço de estoque
        iEstoqueService service = new EstoqueService(prodRepo, movRepo);

        // Criação da interface gráfica (MenuPrincipal)
        MenuPrincipal view = new MenuPrincipal(service);

        // Torna a interface gráfica visível
        view.setVisible(true);
    }
}
