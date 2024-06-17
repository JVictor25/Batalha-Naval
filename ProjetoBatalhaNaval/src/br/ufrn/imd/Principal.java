package br.ufrn.imd;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Principal extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Carrega o arquivo FXML
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Principal.class.getResource("visao/TelaPrincipal.fxml"));
        Parent root = loader.load();

        // Configura a cena
        Scene scene = new Scene(root, 1200, 800);

        // Configura o palco (stage)
        primaryStage.setScene(scene);
        primaryStage.setTitle("Batalha Naval");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

