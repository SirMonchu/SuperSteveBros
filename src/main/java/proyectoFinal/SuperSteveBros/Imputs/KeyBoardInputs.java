package proyectoFinal.SuperSteveBros.Imputs;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import proyectoFinal.SuperSteveBros.View.GamePanel;
import proyectoFinal.SuperSteveBros.gameStates.Gamestate;
import javafx.event.EventHandler;
import static proyectoFinal.SuperSteveBros.utilz.Constants.Directions.*;

public class KeyBoardInputs {
	
	private Scene scene;
	private GamePanel gamePanel;
	
    public KeyBoardInputs(Scene scene , GamePanel gamePanel) {
        this.scene = scene;
        this.gamePanel = gamePanel;
        
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
            	switch (Gamestate.state) {
				case MENU:
					gamePanel.getGame().getMenu().keyPressed(event);
					break;
				case PLAYING:
					gamePanel.getGame().getPlaying().keyPressed(event);
					break;
				default:
					break;
				}
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
            	switch (Gamestate.state) {
				case MENU:
					gamePanel.getGame().getMenu().keyReleased(event);
					break;
				case PLAYING:
					gamePanel.getGame().getPlaying().keyReleased(event);
					break;
				default:
					break;
				}
            }
        });
    }
}
