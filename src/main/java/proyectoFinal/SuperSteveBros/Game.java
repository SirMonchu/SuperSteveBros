package proyectoFinal.SuperSteveBros;
/**
import proyectoFinal.SuperSteveBros.Imputs.KeyBoardListener;
import proyectoFinal.SuperSteveBros.View.GamePanel;
import proyectoFinal.SuperSteveBros.controllers.MovementController;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Game {

//	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private KeyBoardListener keyBoardListener;
	
    public Game(GamePanel gamePanel, ImageView hero) {
    	gamePanel.dibujar();
    	keyBoardListener = new KeyBoardListener(gamePanel);
    	MovementController movementController = new MovementController(hero, keyBoardListener);
    	movementController.moverHeroe();
    }       
   


}
**/