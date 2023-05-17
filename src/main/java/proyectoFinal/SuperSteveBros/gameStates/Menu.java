package proyectoFinal.SuperSteveBros.gameStates;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import proyectoFinal.SuperSteveBros.Game;
import proyectoFinal.SuperSteveBros.Imputs.MouseInputs;
import proyectoFinal.SuperSteveBros.View.GamePanel;
import proyectoFinal.SuperSteveBros.Ui.MenuButton;

public class Menu extends State implements StateMethods {
	
	private MenuButton[] buttons = new MenuButton[3];
	
	public Menu(Game game) {
		super(game);
		loadButtons();
	}

	private void loadButtons() {
		buttons[0] = new MenuButton(Game.GAME_WIDTH / 2,(int) (150 * Game.SCALE), 0, Gamestate.PLAYING);
		buttons[1] = new MenuButton(Game.GAME_WIDTH / 2,(int) (220 * Game.SCALE), 1, Gamestate.OPTIONS);
		buttons[2] = new MenuButton(Game.GAME_WIDTH / 2,(int) (290 * Game.SCALE), 2, Gamestate.QUIT);
	}

	@Override
	public void update() {
		for (MenuButton mb : buttons) {
			mb.update();
		}
	}

	@Override
	public void draw(Pane root) {
		for (MenuButton mb : buttons) {
			mb.draw(root);
		}
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if (event.getCode() == event.getCode().ENTER) {
			Gamestate.state = Gamestate.PLAYING;
			System.out.println("Estas en el juego");
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (MenuButton mb : buttons) {
			if (isIn(e, mb)) {
				mb.setMousePressed(true);
			}
		}

	}


	private void resetButtons() {
		for (MenuButton mb : buttons) {
			mb.resetBools();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for (MenuButton mb : buttons) {
			if (isIn(e, mb)) {
				if (mb.isMousePressed())
					mb.applyGamestate();
				break;
			}
		}

		resetButtons();

	}

	@Override
	public void mouseMoved(MouseEvent event) {
		for (MenuButton mb : buttons) {
			mb.setMouseOver(false);
		}
		for (MenuButton mb : buttons) {
			if (isIn(event, mb)) {
				mb.setMouseOver(true);
				break;
			}
		}
	}

}
