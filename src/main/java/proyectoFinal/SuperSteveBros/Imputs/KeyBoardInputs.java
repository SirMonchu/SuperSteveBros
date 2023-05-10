package proyectoFinal.SuperSteveBros.Imputs;

import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import proyectoFinal.SuperSteveBros.View.GamePanel;
import javafx.event.EventHandler;
import static proyectoFinal.SuperSteveBros.utilz.Constants.Directions.*;

public class KeyBoardInputs {
	
	private GamePanel gamePanel;
	
    public KeyBoardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

    	gamePanel.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case W:
                        gamePanel.setDirection(UP);
                        break;
                    case S:
                    	gamePanel.setDirection(DOWN);
                    	
                        break;
                    case A:
                    	gamePanel.setDirection(LEFT);
                        break;
                    case D:
                    	gamePanel.setDirection(RIGHT);
                        break;
                    case CONTROL:
                    	gamePanel.setSneaking(true);
                    	break;
                    case SHIFT:
                        gamePanel.setRunning(true);
                        break;
                }
            }
        });

    	gamePanel.getScene().setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case W:
                    case S:
                    case A:
                    case D:
                    	gamePanel.setMoving(false);
                    	break;
                    case CONTROL:
                    	gamePanel.setSneaking(false);
                    	break;
                    case SHIFT:
                    	gamePanel.setRunning(false);
                        break;
                }
            }
        });
    }
}
