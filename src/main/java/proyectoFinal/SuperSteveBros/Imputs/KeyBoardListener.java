package proyectoFinal.SuperSteveBros.Imputs;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import proyectoFinal.SuperSteveBros.View.GamePanel;
import static proyectoFinal.SuperSteveBros.utils.Contants.Directions.*;

public class KeyBoardListener {
    private static final double W = 1920, H = 1080;
    private Scene scene;
    private boolean running, goNorth, goSouth, goEast, goWest;
    private KeyBoardListener keyBoardListener;
    
    public KeyBoardListener() {
    	
    }
    
    public KeyBoardListener(GamePanel gamePanel) {
        scene = new Scene(gamePanel, W, H, Color.CYAN);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case W:
                        goNorth = true;
                        break;
                    case S:
                        goSouth = true;
                        break;
                    case A:
                        goWest = true;
                        gamePanel.Setmoving_left(true);
                        break;
                    case D:
                        goEast = true;
                        gamePanel.Setmoving_right(true);
                        break;
                    case SHIFT:
                        running = true;
                        break;
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case W:
                        goNorth = false;
                        gamePanel.setMoving(false);
                        break;
                    case S:
                        goSouth = false;
                        gamePanel.setMoving(false);
                        break;
                    case A:
                        goWest = false;
                        gamePanel.Setmoving_left(false);
                        gamePanel.setMoving(false);
                        break;
                    case D:
                        goEast = false;
                        gamePanel.Setmoving_right(false);
                        gamePanel.setMoving(false);
                        break;
                    case SHIFT:
                        running = false;
                        break;
                }
            }
        });
    }

    public Scene getScene() {
        return scene;
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isGoNorth() {
        return goNorth;
    }

    public boolean isGoSouth() {
        return goSouth;
    }

    public boolean isGoEast() {
        return goEast;
    }

    public boolean isGoWest() {
        return goWest;
    }
    
    public KeyBoardListener getKeyBoardListener() {
    	
    	return keyBoardListener;
    }
}
