package proyectoFinal.SuperSteveBros;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import proyectoFinal.SuperSteveBros.model.Player;

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
        FXMLLoader fxmlLoader = new FXMLLoader();
        String fullPath = MainClass.class.getResource("/proyectoFinal/SuperSteveBros/" + fxml + ".fxml").toExternalForm();
        fxmlLoader.setLocation(new URL(fullPath));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Método para realizar la transición al juego después del inicio de sesión
    public static void startGame(Stage stage, Player player) throws IOException {
        Game game = new Game(player, stage);
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
