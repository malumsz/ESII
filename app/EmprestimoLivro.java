import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bd.ConexaoBD;

public class EmprestimoLivro extends Application {

    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label lblRA = new Label("RA do Aluno:");
        TextField txtRA = new TextField();
        gridPane.add(lblRA, 0, 0);
        gridPane.add(txtRA, 1, 0);

        Label lblCodigos = new Label("Códigos dos Livros:");
        TextField txtCodigos = new TextField();
        gridPane.add(lblCodigos, 0, 1);
        gridPane.add(txtCodigos, 1, 1);

        Button btnEmprestar = new Button("Emprestar");
        Button btnVoltarInicio = new Button("Voltar ao Início");

        btnEmprestar.setOnAction(e -> emprestarLivros(txtRA.getText(), txtCodigos.getText()));
        
        btnVoltarInicio.setOnAction(e ->  primaryStage.close());

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(gridPane, btnEmprestar, btnVoltarInicio);

        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setTitle("Emprestar Livro");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void emprestarLivros(String ra, String codigos) {
        boolean alunoCadastrado = verificarAlunoCadastrado(ra);
        boolean debitosAluno = verificarDebitosAluno(ra);
        boolean emprestimoEfetuado = false;

        if (alunoCadastrado && !debitosAluno) {
            String[] codigosLivros = codigos.split(",");
            for (String codigo : codigosLivros) {
                boolean livroEmprestado = emprestarLivro(Integer.parseInt(codigo), ra);
                if (!livroEmprestado) {
                    exibirMensagem("Livro com código " + codigo + " não pode ser emprestado.");
                    return;
                }
            }
            emprestimoEfetuado = true;
        }

        if (emprestimoEfetuado) {
            exibirMensagem("Empréstimo efetuado com sucesso!");
        } else {
            exibirMensagem("Erro ao efetuar o empréstimo. Verifique os dados informados.");
        }
    }

    private boolean verificarAlunoCadastrado(String ra) {
        String sql = "SELECT COUNT(*) FROM alunos WHERE ra = ?";
        try (Connection conn = ConexaoBD.obterConexao();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ra);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public static boolean verificarDebitosAluno(String ra) {
        String sql = "SELECT debito FROM alunos WHERE ra = ?";
        try (Connection conn = ConexaoBD.obterConexao();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ra);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                boolean debito = resultSet.getBoolean("debito");
                return debito;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean emprestarLivro(int codigoLivro, String ra) {
        String sql = "SELECT disponivel FROM livros WHERE id = ?";
        try (Connection conn = ConexaoBD.obterConexao();
            PreparedStatement stmt = conn.prepareStatement(sql)) {    // Verificar a disponibilidade do livro
            PreparedStatement verificarDisponibilidadeStatement = conn.prepareStatement(sql);
            verificarDisponibilidadeStatement.setInt(1, codigoLivro);
            ResultSet disponibilidadeResultSet = verificarDisponibilidadeStatement.executeQuery();
            if (disponibilidadeResultSet.next()) {
                boolean disponivel = disponibilidadeResultSet.getBoolean("disponivel");
                if (!disponivel) {
                    // Livro não está disponível
                    return false;
                }
            } else {
                // Livro não encontrado
                return false;
            }

            // Gravar empréstimo no banco de dados
            String inserirEmprestimoQuery = "INSERT INTO emprestimo (codigo_livro, ra_aluno) VALUES (?, ?)";
            PreparedStatement inserirEmprestimoStatement = conn.prepareStatement(inserirEmprestimoQuery);
            inserirEmprestimoStatement.setInt(1, codigoLivro);
            inserirEmprestimoStatement.setString(2, ra);
            inserirEmprestimoStatement.executeUpdate();

            // Atualizar status do livro para indisponível
            String atualizarStatusQuery = "UPDATE livros SET disponivel = false WHERE id = ?";
            PreparedStatement atualizarStatusStatement = conn.prepareStatement(atualizarStatusQuery);
            atualizarStatusStatement.setInt(1, codigoLivro);
            atualizarStatusStatement.executeUpdate();

            return true; // Empréstimo efetuado com sucesso
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void exibirMensagem(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}