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
import classes.Titulo;
import classes.Livro;

public class CadastroLivro extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Cadastro de Livro");

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label lblTitulo = new Label("Título:");
        TextField txtTitulo = new TextField();
        gridPane.add(lblTitulo, 0, 0);
        gridPane.add(txtTitulo, 1, 0);

        Label lblPrazo = new Label("Prazo de Empréstimo:");
        TextField txtPrazo = new TextField();
        gridPane.add(lblPrazo, 0, 1);
        gridPane.add(txtPrazo, 1, 1);

        Button btnCadastrar = new Button("Cadastrar");
        Button btnVoltarInicio = new Button("Voltar ao Início");

        btnCadastrar.setOnAction(e -> {
            String titulo = txtTitulo.getText();
            int prazo = Integer.parseInt(txtPrazo.getText());
            //Titulo t = new Titulo(prazo, titulo);
            boolean cadastrou = cadastrarLivro(titulo, prazo);
            if (cadastrou) {
                exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Livro cadastrado com sucesso!");
            } else {
                exibirAlerta(Alert.AlertType.ERROR, "Erro", "Ocorreu um erro ao cadastrar o livro.");
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

    private static boolean cadastrarLivro(String titulo, int prazo) {
        String sql = "INSERT INTO livros (titulo, prazo_emprestimo) VALUES (?, ?)";
    
        try (Connection conn = ConexaoBD.obterConexao();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, titulo);
            stmt.setInt(2, prazo);
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
