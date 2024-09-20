package br.ufrn.imd;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * Classe principal que inicia a aplicação JavaFX.
 * Esta classe é responsável por carregar a interface gráfica definida em TelaPrincipal.fxml
 * e exibi-la ao usuário.
 */
public class Principal extends Application {
	/**
     * Método sobrescrito do JavaFX Application class para iniciar a aplicação.
     * Este método carrega a interface gráfica do usuário (GUI) definida em TelaPrincipal.fxml,
     * configura a cena e o palco (stage), e exibe a GUI ao usuário.
     *
     * @param primaryStage O Stage principal usado para exibir a GUI.
     * @throws Exception Lança exceções que podem ocorrer durante a inicialização da GUI.
     */
	
	
    @Override
    public void start(Stage primaryStage) throws Exception {
      
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Principal.class.getResource("visao/TelaPrincipal.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1200, 800);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Batalha Naval");
        primaryStage.show();
    }
    
    
    /**
     * Ponto de entrada da aplicação.
     * Este método chama o método start() para iniciar a aplicação.
     *
     * @param args Argumentos da linha de comando.
     */
    public static void main(String[] args) {
        launch(args);
    }
}

