package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bd.ConexaoBD;
import classes.Aluno;

public class AlunoDAO {
    public static boolean inserirAluno(String ra, String nome) {
        String sql = "INSERT INTO alunos (ra, nome) VALUES (?, ?)";
        try (Connection connection = ConexaoBD.obterConexao();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, ra);
            preparedStatement.setString(2, nome);
            int linhasAfetadas = preparedStatement.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean verificarAlunoCadastrado(String raAluno) {
        String query = "SELECT COUNT(*) FROM alunos WHERE ra = ?";
        try (Connection connection = ConexaoBD.obterConexao();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, raAluno);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static boolean verificarDebitosAluno(String raAluno) {
        String query = "SELECT debito FROM alunos WHERE ra = ?";
        try (Connection connection = ConexaoBD.obterConexao();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, raAluno);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                boolean debito = resultSet.getBoolean("debito");
                return debito;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public Aluno buscarAlunoPorRA(String ra) {
        Aluno aluno = null;
        String sql = "SELECT * FROM alunos WHERE ra = ?";

        try (Connection conn = ConexaoBD.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ra);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    aluno = new Aluno(rs.getString("ra"), rs.getString("nome"));
                    aluno.setDebito(rs.getBoolean("debito"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aluno;
    }
}
