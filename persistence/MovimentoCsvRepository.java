package persistence;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.*;
import repository.iMovimentoRepository;
import repository.iProdutoRepository;

public class MovimentoCsvRepository implements iMovimentoRepository {

    private static final String NOME_ARQUIVO = "movimentos.csv";
    private File arquivoCsv;
    private final iProdutoRepository produtoRepository;

    
    public MovimentoCsvRepository(iProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;

        try {
            String diretorioBase = System.getProperty("user.dir");
            
            File pasta = new File(diretorioBase, "data");
            if (!pasta.exists()) {
                boolean criou = pasta.mkdir();
                if (!criou) throw new IOException("Não foi possível criar a pasta 'data'");
            }

            this.arquivoCsv = new File(pasta, NOME_ARQUIVO);
            if (!this.arquivoCsv.exists()) {
                this.arquivoCsv.createNewFile();
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro fatal ao iniciar repositório de movimentos", e);
        }
    }

    @Override
    public List<Movimento> listarTodos() {
        List<Movimento> lista = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoCsv))) {
            String linha;

            while ((linha = reader.readLine()) != null) {
                if (linha.isBlank()) continue;

                String[] p = linha.split(";");
                if (p.length < 4) continue;

                String tipo = p[0];
                String codigoProduto = p[1];
                LocalDate data = LocalDate.parse(p[2]);
                int quantidade = Integer.parseInt(p[3]);

                // Busca o produto completo usando o repositório de produtos
                Optional<Produto> produtoOpt = produtoRepository.buscarPorCodigo(codigoProduto);
                
                // Se o produto não existe mais (foi deletado), ignoramos este movimento ou tratamos erro
                if (produtoOpt.isEmpty()) continue; 
                
                Produto produto = produtoOpt.get();

                if ("ENTRADA".equalsIgnoreCase(tipo)) {
                    double valor = 0.0;
                    if (p.length >= 5) {
                        try { valor = Double.parseDouble(p[4]); } catch (Exception ignored) {}
                    }
                    lista.add(new Entrada(produto, data, quantidade, valor));
                }
                else if ("SAIDA".equalsIgnoreCase(tipo)) {
                    TipoSaida tipoSaida = TipoSaida.Outra; // Valor padrão seguro
                    if (p.length >= 5) {
                        try { 
                            // Tenta converter ignorando maiúsculas/minúsculas
                            tipoSaida = TipoSaida.valueOf(p[4].toUpperCase()); 
                        } catch (Exception e) {
                            // Se der erro (ex: nome mudou), mantém OUTRA
                        }
                    }
                    lista.add(new Saida(produto, data, quantidade, tipoSaida));
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler movimentos", e);
        }

        return lista;
    }

    @Override
    public void salvar(Movimento movimento) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoCsv, true))) { // append = true

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
                        saida.getTipo().name() // Salva como STRING (ex: "VENDA_CLIENTE")
                ));
            }
            writer.newLine();

        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar movimento", e);
        }
    }
}