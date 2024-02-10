import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bd.ConexaoBD;
import classes.Aluno;

public class CadastroAluno extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Cadastro de Aluno");

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label lblNome = new Label("Nome:");
        TextField txtNome = new TextField();
        gridPane.add(lblNome, 0, 0);
        gridPane.add(txtNome, 1, 0);

        Label lblRA = new Label("RA:");
        TextField txtRA = new TextField();
        gridPane.add(lblRA, 0, 1);
        gridPane.add(txtRA, 1, 1);

        Button btnCadastrar = new Button("Cadastrar");
        Button btnVoltarInicio = new Button("Voltar ao Início");

        btnCadastrar.setOnAction(e -> {
            String nome = txtNome.getText();
            String ra = txtRA.getText();
            Aluno aluno = new Aluno(ra, nome);
            boolean cadastrou = cadastrarAluno(nome, ra);
            if (cadastrou) {
                exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Aluno cadastrado com sucesso!");
            } else {
                exibirAlerta(Alert.AlertType.ERROR, "Erro", "Ocorreu um erro ao cadastrar o aluno.");
            }
        });

        btnVoltarInicio.setOnAction(e ->  primaryStage.close());

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(gridPane, btnCadastrar, btnVoltarInicio);

        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static boolean cadastrarAluno(String nome, String ra) {
        String sql = "INSERT INTO alunos (nome, ra) VALUES (?, ?)";

        try (Connection conn = ConexaoBD.obterConexao();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, ra);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void exibirAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
