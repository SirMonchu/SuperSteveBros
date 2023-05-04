package proyectoFinal.SuperSteveBros;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GameWindow extends Application {

    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setTitle("Game Window");
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public void closeWindow() {
        stage.close();
    }
}
