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
                	gamePanel.getGame().getPlayer().setUp(true);
                	break;
                case S:
                	gamePanel.getGame().getPlayer().setDown(true);
                	break;
                case A:
                	gamePanel.getGame().getPlayer().setLeft(true);
                	break;
                case D:
                	gamePanel.getGame().getPlayer().setRight(true);
                	break;
                    case CONTROL:
                    	gamePanel.getGame().getPlayer().setSneaking(true);
                    	break;
                    case SHIFT:
                        gamePanel.getGame().getPlayer().setRunning(true);
                        break;
                }
            }
        });

    	gamePanel.getScene().setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case W:
                    	gamePanel.getGame().getPlayer().setUp(false);
                    	break;
                    case S:
                    	gamePanel.getGame().getPlayer().setDown(false);
                    	break;
                    case A:
                    	gamePanel.getGame().getPlayer().setLeft(false);
                    	break;
                    case D:
                    	gamePanel.getGame().getPlayer().setRight(false);
                    	break;
                    case CONTROL:
                    	gamePanel.getGame().getPlayer().setSneaking(false);
                    	break;
                    case SHIFT:
                    	gamePanel.getGame().getPlayer().setRunning(false);
                        break;
                }
            }
        });
    }
}
