package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bd.ConexaoBD;
import classes.*;

public class LivroDAO {

    public static void marcarLivroComoDisponivel(int codigoLivro) throws SQLException {
        String sql = "UPDATE livros SET disponivel = true AND prazo_emprestimo = NULL WHERE id = ?";
        try (Connection connection = ConexaoBD.obterConexao();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, codigoLivro);
            statement.executeUpdate();
        }
    }

    public Livro buscarLivroPorCodigo(int codigo) {
        Livro livro = null;
        String sql = "SELECT * FROM livros WHERE id = ?";

        try (Connection conn = ConexaoBD.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String titulo = rs.getString("titulo");
                    livro = new Livro(codigo, titulo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livro;
    }

    public boolean verificaDisponibilidade(int codigoLivro) {
        boolean disponivel = false;
        String sql = "SELECT disponivel FROM livros WHERE codigo = ?";

        try (Connection conn = ConexaoBD.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigoLivro);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    disponivel = rs.getBoolean("disponivel");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return disponivel;
    }

    public Date getDataDevolucao(int codigoLivro) {
        Date dataDevolucao = null;
        String sql = "SELECT data_prevista_devolucao FROM Emprestimo WHERE codigo_livro = ?";

        try (Connection conn = ConexaoBD.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigoLivro);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    dataDevolucao = rs.getDate("data_prevista_devolucao");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return dataDevolucao;
    }
    
}
