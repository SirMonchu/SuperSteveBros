package proyectoFinal.SuperSteveBros;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainClass extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        GameWindow gameWindow = new GameWindow();
        gameWindow.start(stage);
        Game game = new Game(gameWindow);
    }

    public static void main(String[] args) {
        launch(args);
    }
}


