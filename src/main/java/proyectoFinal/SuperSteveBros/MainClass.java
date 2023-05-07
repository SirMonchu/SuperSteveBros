package proyectoFinal.SuperSteveBros;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import proyectoFinal.SuperSteveBros.View.GamePanel;
import proyectoFinal.SuperSteveBros.Imputs.KeyBoardListener;
import proyectoFinal.SuperSteveBros.controllers.MovementController;
import javafx.scene.image.Image;
import java.io.File;

public class MainClass extends Application {
	
	File file = new File("C:\\Users\\ramon\\eclipse-workspace\\SuperSteveBros\\src\\main\\resources\\proyectoFinal\\SuperSteveBros\\Steve_character_left.png");
	Image image = new Image(file.toURI().toString());
	ImageView steve_right = new ImageView(image);

    @Override
    public void start(Stage stage) throws Exception {

        GamePanel gamePanel = new GamePanel();
        
        ImageView test = gamePanel.importImg();
        gamePanel.dibujar();

        KeyBoardListener keyBoardListener = new KeyBoardListener(gamePanel);
        stage.setScene(keyBoardListener.getScene());

        MovementController movementController = new MovementController(gamePanel.getHero(), keyBoardListener);
        movementController.moverHeroe();

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}