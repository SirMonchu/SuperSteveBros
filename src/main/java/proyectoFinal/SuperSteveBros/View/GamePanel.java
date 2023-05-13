package proyectoFinal.SuperSteveBros.View;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import proyectoFinal.SuperSteveBros.Game;
import proyectoFinal.SuperSteveBros.Imputs.KeyBoardInputs;

import static proyectoFinal.SuperSteveBros.Game.GAME_HEIGHT;
import static proyectoFinal.SuperSteveBros.Game.GAME_WIDTH;

public class GamePanel extends Pane {

    private Canvas canvas;
    private KeyBoardInputs keyBoardInputs;
    private static Scene scene;
    private Game game;
    GraphicsContext gc;


    public GamePanel(Game game) {
    	this.game = game;
    	canvas = new Canvas(GAME_WIDTH, GAME_HEIGHT);
        scene = new Scene(GamePanel.this, GAME_WIDTH, GAME_HEIGHT, Color.CYAN);
        keyBoardInputs = new KeyBoardInputs(this); // pasar una referencia a esta instancia de GamePanel
        this.setFocusTraversable(true);
        this.getChildren().add(canvas);
        System.out.println("Size: " + GAME_WIDTH + " x " + GAME_HEIGHT);
    }

    
    public void updateGame() {
    	
    }


    public void refresh() {
    	game.render(this);
	}
    
//    public void repaint() {
//    	paintComponent(this.gc);
//    }
//    
//    public void paintComponent(GraphicsContext gc) {
//		game.render(gc);
//	}
    
    public Game getGame() {
    	return game;
    }
    
    public Canvas getCanvas() {
    	return this.canvas;
    }
}

