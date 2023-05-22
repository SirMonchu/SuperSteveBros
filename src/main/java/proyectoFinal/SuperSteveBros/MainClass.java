package proyectoFinal.SuperSteveBros;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import proyectoFinal.SuperSteveBros.entities.Player;

public class MainClass extends Application {

    private static Scene loginScene;
    private static Scene gameScene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        setLoginScene();
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    static void setLoginScene() throws IOException {
        Parent root = loadFXML("Login");
        loginScene = new Scene(root);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainClass.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Método para realizar la transición al juego después del inicio de sesión
    public static void startGame(Stage stage) throws IOException {
        Game game = new Game();
        stage.setScene(game.getGamePanel().getScene());
        stage.setResizable(false);
        stage.show();

        stage.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                game.windowFocusLost();
            }
        });

        stage.setOnCloseRequest(event -> {
            game.stopGameLoop();
        });
    }
}
