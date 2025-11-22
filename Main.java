import view.MenuPrincipal;
import repository.*;
import services.*;   
import persistence.*; 

public class Main {
    public static void main(String[] args) {
        
        iProdutoRepository prodRepo = new ProdutoCsvRepository(); 
        iMovimentoRepository movRepo = new MovimentoCsvRepository(prodRepo);
        iEstoqueService service = new EstoqueService(prodRepo, movRepo);

        MenuPrincipal view = new MenuPrincipal(service);
        view.setVisible(true);
    }
}