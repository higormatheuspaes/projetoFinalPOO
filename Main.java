import view.MenuPrincipal;
import repository.*;
import services.*;

public class Main {
    public static void main(String[] args) {
        iProdutoRepository prodRepo = new ProdutoRepository();
        iMovimentoRepository movRepo = new MovimentoRepository();

        iEstoqueService service = new EstoqueService(prodRepo, movRepo);

        MenuPrincipal view = new MenuPrincipal(service);
        view.setVisible(true);
    }
}