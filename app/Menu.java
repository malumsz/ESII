import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Menu extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Menu");

        // Criar botões para as opções
        Button btnCadastrarLivro = new Button("Cadastrar Livro");
        Button btnCadastrarAluno = new Button("Cadastrar Aluno");
        Button btnSair = new Button("Sair");

        // Adicionar ação aos botões
        btnCadastrarLivro.setOnAction(e -> {
            CadastroLivro cadastroLivro = new CadastroLivro();
            cadastroLivro.start(new Stage());
        });

        btnCadastrarAluno.setOnAction(e -> {
            CadastroAluno cadastroAluno = new CadastroAluno();
            cadastroAluno.start(new Stage());
        });

        btnSair.setOnAction(e -> primaryStage.close());

        // Layout do menu
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(new Label("Selecione uma opção:"), btnCadastrarLivro, btnCadastrarAluno, btnSair);

        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
