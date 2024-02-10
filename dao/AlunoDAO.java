package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bd.ConexaoBD;

public class AlunoDAO {
    public static boolean inserirAluno(String ra, String nome) {
        String sql = "INSERT INTO aluno (ra, nome) VALUES (?, ?)";
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
}
