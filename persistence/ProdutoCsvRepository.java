package persistence;

import model.*;
import repository.iProdutoRepository;

import java.io.*;
import java.util.*;

public class ProdutoCsvRepository implements iProdutoRepository {

    private static final String NOME_ARQUIVO = "produtos.csv";
    private File arquivoCsv;

    public ProdutoCsvRepository() {
        try {
            String diretorioBase = System.getProperty("user.dir");
            System.out.println(">>> Debug Path: " + diretorioBase);

            File pasta = new File(diretorioBase, "data");
            
            // Tenta criar a pasta e avisa se falhar
            if (!pasta.exists()) {
                boolean criou = pasta.mkdir();
                if (!criou) throw new IOException("Não foi possível criar a pasta 'data' em: " + pasta.getAbsolutePath());
            }

            this.arquivoCsv = new File(pasta, NOME_ARQUIVO);
            if (!this.arquivoCsv.exists()) {
                this.arquivoCsv.createNewFile();
            }
            // -----------------------------------------------

        } catch (Exception e) {
            throw new RuntimeException("Erro fatal ao iniciar repositório de produtos: " + e.getMessage(), e);
        }
    }

    // Método auxiliar para ler o arquivo
    private List<Produto> carregarTodosDoArquivo() {
        List<Produto> lista = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoCsv))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.isBlank()) continue;

                String[] partes = linha.split(";");
                // Evita erro se a linha estiver incompleta
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

    // Método auxiliar para reescrever o arquivo inteiro (usado em atualizar/remover)
    private void reescreverArquivo(List<Produto> produtos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoCsv, false))) { // false = sobrescreve
            for (Produto p : produtos) {
                writer.write(String.join(";",
                    p.getCodigo(),
                    p.getNome(),
                    String.valueOf(p.getPrecoUnitarioPadrao()),
                    String.valueOf(p.getQuantidadeEstoque()), // Cuidado: verifique se o getter é getQuantidadeEstoque ou getQuantidadeEmEstoque na sua Model
                    p.getCategoria().getNome()
                ));
                writer.newLine();
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao reescrever arquivo de produtos", e);
        }
    }

    @Override
    public void salvar(Produto p) {
        // true = append (adiciona no final)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoCsv, true))) { 
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

    @Override
    public Optional<Produto> buscarPorCodigo(String codigo) {
        return carregarTodosDoArquivo().stream()
                .filter(p -> p.getCodigo().equalsIgnoreCase(codigo))
                .findFirst();
    }

    @Override
    public List<Produto> listarTodos() {
        return carregarTodosDoArquivo();
    }

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

    // Caso sua interface exija esse método
    public void atualizarTodos(List<Produto> produtos) {
        reescreverArquivo(produtos);
    }

    @Override
    public void remover(String codigo) {
        List<Produto> todos = carregarTodosDoArquivo();
        todos.removeIf(p -> p.getCodigo().equalsIgnoreCase(codigo));
        reescreverArquivo(todos);
    }
}