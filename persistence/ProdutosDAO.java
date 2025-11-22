package persistence;

import model.*;
import repository.iProdutoRepository;

import java.io.*;
import java.util.*;

public class ProdutosDAO implements iProdutoRepository {

    private static final String ARQUIVO = "data/produtos.csv";

    public ProdutosDAO() {
        try {
            File pasta = new File("data");
            if (!pasta.exists()) pasta.mkdir();

            File csv = new File(ARQUIVO);
            if (!csv.exists()) csv.createNewFile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<Produto> carregarTodos() {
        List<Produto> lista = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;

            while ((linha = reader.readLine()) != null) {
                if (linha.isBlank()) continue;

                String[] partes = linha.split(";");
                String cod = partes[0];
                String nome = partes[1];
                double preco = Double.parseDouble(partes[2]);
                int qtd = Integer.parseInt(partes[3]);
                String categoria = partes[4];

                Categoria cat;
                switch (categoria.toLowerCase()) {
                    case "hardware": cat = new Hardware(); break;
                    case "periferico": cat = new Periferico(); break;
                    case "acessorio": cat = new Acessorio(); break;
                    default: cat = new Outro();
                }

                lista.add(new Produto(cod, nome, preco, qtd, cat));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    @Override
    public void salvar(Produto p) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO, true))) {
            writer.write(
                p.getCodigo() + ";" +
                p.getNome() + ";" +
                p.getPrecoUnitarioPadrao() + ";" +
                p.getQuantidadeEstoque() + ";" +
                p.getCategoria().getNome()
            );
            writer.newLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Produto> buscarPorCodigo(String codigo) {
        for (Produto p : carregarTodos()) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Produto> listarTodos() {
        return carregarTodos();
    }

    @Override
    public void atualizar(Produto produto) {
        List<Produto> todos = carregarTodos();
        for (int i = 0; i < todos.size(); i++) {
            if (todos.get(i).getCodigo().equalsIgnoreCase(produto.getCodigo())) {
                todos.set(i, produto);
                break;
            }
        }
        atualizarTodos(todos);
    }

    @Override
    public void atualizarTodos(List<Produto> produtos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Produto p : produtos) {
                writer.write(
                    p.getCodigo() + ";" +
                    p.getNome() + ";" +
                    p.getPrecoUnitarioPadrao() + ";" +
                    p.getQuantidadeEstoque() + ";" +
                    p.getCategoria().getNome()
                );
                writer.newLine();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remover(String codigo) {
        List<Produto> todos = carregarTodos();
        todos.removeIf(p -> p.getCodigo().equalsIgnoreCase(codigo));
        atualizarTodos(todos);
    }
}
