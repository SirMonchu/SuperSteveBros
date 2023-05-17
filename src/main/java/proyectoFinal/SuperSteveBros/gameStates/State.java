package proyectoFinal.SuperSteveBros.gameStates;

import javafx.scene.input.MouseEvent;
import proyectoFinal.SuperSteveBros.Game;
import proyectoFinal.SuperSteveBros.Ui.MenuButton;

public class State {
	
	protected Game game;
	
	public State(Game game) {
		this.game = game;
	}
	
	public boolean isIn(MouseEvent event, MenuButton mb) {
		return mb.getBounds().contains(event.getX(), event.getY());
	}
	
	public Game getGame() {
		return game;
		
	}
}
