package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bd.ConexaoBD;
import classes.Aluno;
import classes.Emprestimo;

public class EmprestimoDAO {

    public void registrarEmprestimo(Emprestimo emprestimo) {
        String sql = "INSERT INTO Emprestimo (RA_aluno, codigo_livro, data_emprestimo, data_prevista_devolucao) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoBD.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, emprestimo.getRAAluno());
            stmt.setInt(2, emprestimo.getCodigoLivro());
            stmt.setDate(3, new java.sql.Date(emprestimo.getDataEmprestimo().getTime()));
            stmt.setDate(4, new java.sql.Date(emprestimo.getDataPrevistaDevolucao().getTime()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Emprestimo> buscarEmprestimosPorRA(String RA) {
        List<Emprestimo> emprestimos = new ArrayList<>();
        String sql = "SELECT * FROM Emprestimo WHERE RA_aluno = ?";

        try (Connection conn = ConexaoBD.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, RA);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Emprestimo emprestimo = new Emprestimo();
                    emprestimo.setId(rs.getInt("id"));
                    emprestimo.setRAAluno(rs.getString("RA_aluno"));
                    emprestimo.setCodigoLivro(rs.getInt("codigo_livro"));
                    emprestimo.setDataEmprestimo(rs.getDate("data_emprestimo"));
                    emprestimo.setDataPrevistaDevolucao(rs.getDate("data_prevista_devolucao"));
                    emprestimos.add(emprestimo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emprestimos;
    }
}