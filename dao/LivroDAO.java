package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bd.ConexaoBD;

public class LivroDAO {

    public static void marcarLivroComoDisponivel(int codigoLivro) throws SQLException {
        String sql = "UPDATE livros SET disponivel = true WHERE id = ?";
        try (Connection connection = ConexaoBD.obterConexao();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, codigoLivro);
            statement.executeUpdate();
        }
    }
}
