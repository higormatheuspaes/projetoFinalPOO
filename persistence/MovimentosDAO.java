package persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Entrada;
import model.Movimento;
import model.Produto;
import model.Saida;
import model.TipoSaida;
import repository.iMovimentoRepository;
import repository.iProdutoRepository;

public class MovimentosDAO implements iMovimentoRepository {

    private static final String ARQUIVO = "data/movimentos.csv";
    private final iProdutoRepository produtoRepository;

    public MovimentosDAO(iProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;

        try {
            File pasta = new File("data");
            if (!pasta.exists()) pasta.mkdir();
            File csv = new File(ARQUIVO);
            if (!csv.exists()) csv.createNewFile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Movimento> listarTodos() {
        List<Movimento> lista = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;

            while ((linha = reader.readLine()) != null) {
                if (linha.isBlank()) continue;

                String[] p = linha.split(";");
                if (p.length < 4) continue;

                String tipo = p[0];
                String codigoProduto = p[1];
                LocalDate data = LocalDate.parse(p[2]);
                int quantidade = Integer.parseInt(p[3]);

                Optional<Produto> produtoOpt = produtoRepository.buscarPorCodigo(codigoProduto);
                if (produtoOpt.isEmpty()) continue;
                Produto produto = produtoOpt.get();

                if ("ENTRADA".equalsIgnoreCase(tipo)) {
                    double valor = 0.0;
                    if (p.length >= 5) {
                        try { valor = Double.parseDouble(p[4]); } catch (Exception ignored) {}
                    }
                    Entrada entrada = new Entrada(produto, data, quantidade, valor);
                    lista.add(entrada);
                }

                else if ("SAIDA".equalsIgnoreCase(tipo)) {

                    TipoSaida tipoSaida = TipoSaida.outra;

                    if (p.length >= 5) {
                        try { tipoSaida = TipoSaida.valueOf(p[4]); }
                        catch (Exception ignored) {}
                    }

                    Saida saida = new Saida(produto, data, quantidade, tipoSaida);
                    lista.add(saida);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    @Override
    public void salvar(Movimento movimento) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO, true))) {

            if (movimento instanceof Entrada entrada) {

                writer.write(String.join(";",
                        "ENTRADA",
                        entrada.getProduto().getCodigo(),
                        entrada.getData().toString(),
                        String.valueOf(entrada.getQuantidade()),
                        String.valueOf(entrada.getValorUnitarioEntrada())
                ));
            }

            else if (movimento instanceof Saida saida) {

                writer.write(String.join(";",
                        "SAIDA",
                        saida.getProduto().getCodigo(),
                        saida.getData().toString(),
                        String.valueOf(saida.getQuantidade()),
                        saida.getTipo().name() 
                ));
            }

            writer.newLine();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
