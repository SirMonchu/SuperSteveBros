package proyectoFinal.SuperSteveBros.Imputs;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import proyectoFinal.SuperSteveBros.View.GamePanel;

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
                        break;
                    case D:
                        goEast = true;
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
                        break;
                    case S:
                        goSouth = false;
                        break;
                    case A:
                        goWest = false;
                        break;
                    case D:
                        goEast = false;
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
