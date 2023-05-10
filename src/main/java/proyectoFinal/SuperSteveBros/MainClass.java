package proyectoFinal.SuperSteveBros;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainClass extends Application{

	private static Scene scene;
	
	@Override
	public void start(Stage stage) throws Exception {
		Game game = new Game();
		stage.setScene(game.getGamePanel().getScene());
		stage.setResizable(false); // asegurar que la ventana no sea redimensionable
		stage.show();
	    stage.setOnCloseRequest(event -> {
	        game.stopGameLoop();
	    });
	}
	
	static void setRoot(String fxml) throws IOException {
		scene.setRoot(loadFXML(fxml));
	}
	
	private static Parent loadFXML(String fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(MainClass.class.getResource(fxml + ".fxml"));
		return fxmlLoader.load();
	}
	
	public static void main(String[] args) {
		launch();
	}
}

