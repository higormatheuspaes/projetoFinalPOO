package persistence;

import model.*;
import repository.iProdutoRepository;

import java.io.*;
import java.util.*;

/**
 * Repositório de produtos que armazena e recupera os dados de produtos em um arquivo CSV.
 * Implementa a interface {@link iProdutoRepository}.
 * Os produtos são armazenados no arquivo "produtos.csv", localizado na pasta "data".
 */
public class ProdutoCsvRepository implements iProdutoRepository {

    private static final String NOME_ARQUIVO = "produtos.csv";
    private File arquivoCsv;

    /**
     * Construtor que inicializa o repositório de produtos.
     * Garante que o diretório "data" e o arquivo "produtos.csv" existam.
     */
    public ProdutoCsvRepository() {
        try {
            String diretorioBase = System.getProperty("user.dir");
            System.out.println(">>> Debug Path: " + diretorioBase);

            // Cria a pasta 'data' caso não exista
            File pasta = new File(diretorioBase, "data");
            if (!pasta.exists()) {
                boolean criou = pasta.mkdir();
                if (!criou) throw new IOException("Não foi possível criar a pasta 'data' em: " + pasta.getAbsolutePath());
            }

            this.arquivoCsv = new File(pasta, NOME_ARQUIVO);
            if (!this.arquivoCsv.exists()) {
                this.arquivoCsv.createNewFile();
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro fatal ao iniciar repositório de produtos: " + e.getMessage(), e);
        }
    }

    /**
     * Método auxiliar para carregar todos os produtos do arquivo CSV.
     *
     * @return Uma lista de objetos {@link Produto} carregados do arquivo CSV.
     */
    private List<Produto> carregarTodosDoArquivo() {
        List<Produto> lista = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoCsv))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.isBlank()) continue;

                String[] partes = linha.split(";");
                if (partes.length < 5) continue;

                String cod = partes[0];
                String nome = partes[1];
                double preco = Double.parseDouble(partes[2]);
                int qtd = Integer.parseInt(partes[3]);
                String categoriaStr = partes[4];

                Categoria cat;
                switch (categoriaStr.toLowerCase()) {
                    case "hardware": cat = new Hardware(); break;
                    case "periferico": cat = new Periferico(); break;
                    case "acessorio": cat = new Acessorio(); break;
                    default: cat = new Outro();
                }

                lista.add(new Produto(cod, nome, preco, qtd, cat));
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler produtos", e);
        }
        return lista;
    }

    /**
     * Método auxiliar para reescrever o arquivo CSV com a lista atualizada de produtos.
     *
     * @param produtos A lista de produtos a ser salva no arquivo CSV.
     */
    private void reescreverArquivo(List<Produto> produtos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoCsv, false))) { // false = sobrescreve
            for (Produto p : produtos) {
                writer.write(String.join(";",
                        p.getCodigo(),
                        p.getNome(),
                        String.valueOf(p.getPrecoUnitarioPadrao()),
                        String.valueOf(p.getQuantidadeEstoque()),
                        p.getCategoria().getNome()
                ));
                writer.newLine();
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao reescrever arquivo de produtos", e);
        }
    }

    /**
     * Salva um novo produto no arquivo CSV.
     *
     * @param p O produto a ser salvo.
     */
    @Override
    public void salvar(Produto p) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoCsv, true))) { // true = append
            writer.write(String.join(";",
                    p.getCodigo(),
                    p.getNome(),
                    String.valueOf(p.getPrecoUnitarioPadrao()),
                    String.valueOf(p.getQuantidadeEstoque()),
                    p.getCategoria().getNome()
            ));
            writer.newLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Busca um produto pelo seu código.
     *
     * @param codigo O código do produto a ser buscado.
     * @return Um {@link Optional} com o produto encontrado, ou vazio se o produto não existir.
     */
    @Override
    public Optional<Produto> buscarPorCodigo(String codigo) {
        return carregarTodosDoArquivo().stream()
                .filter(p -> p.getCodigo().equalsIgnoreCase(codigo))
                .findFirst();
    }

    /**
     * Lista todos os produtos do repositório.
     *
     * @return Uma lista de todos os produtos.
     */
    @Override
    public List<Produto> listarTodos() {
        return carregarTodosDoArquivo();
    }

    /**
     * Atualiza um produto no arquivo CSV.
     *
     * @param produto O produto atualizado.
     */
    @Override
    public void atualizar(Produto produto) {
        List<Produto> todos = carregarTodosDoArquivo();
        boolean encontrou = false;

        for (int i = 0; i < todos.size(); i++) {
            if (todos.get(i).getCodigo().equalsIgnoreCase(produto.getCodigo())) {
                todos.set(i, produto);
                encontrou = true;
                break;
            }
        }

        if (encontrou) {
            reescreverArquivo(todos);
        }
    }

    /**
     * Atualiza todos os produtos no repositório, sobrescrevendo o arquivo CSV.
     *
     * @param produtos A lista de produtos a ser salva no arquivo.
     */
    public void atualizarTodos(List<Produto> produtos) {
        reescreverArquivo(produtos);
    }

    /**
     * Remove um produto pelo seu código.
     *
     * @param codigo O código do produto a ser removido.
     */
    @Override
    public void remover(String codigo) {
        List<Produto> todos = carregarTodosDoArquivo();
        todos.removeIf(p -> p.getCodigo().equalsIgnoreCase(codigo));
        reescreverArquivo(todos);
    }
}
