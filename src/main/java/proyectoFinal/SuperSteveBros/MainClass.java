package proyectoFinal.SuperSteveBros;

import javafx.application.Application;
import javafx.stage.Stage;
import proyectoFinal.SuperSteveBros.View.GamePanel;
import proyectoFinal.SuperSteveBros.Imputs.KeyBoardListener;
import proyectoFinal.SuperSteveBros.controllers.MovementController;


public class MainClass extends Application {
	

    @Override
    public void start(Stage stage) throws Exception {
        GamePanel gamePanel = new GamePanel();
        KeyBoardListener keyBoardListener = new KeyBoardListener(gamePanel);
        stage.setScene(keyBoardListener.getScene());
        stage.show();

        MovementController movementController = new MovementController(gamePanel.getHero(), keyBoardListener);
        movementController.moverHeroe();
    }


    public static void main(String[] args) {
        launch(args);
    }
}