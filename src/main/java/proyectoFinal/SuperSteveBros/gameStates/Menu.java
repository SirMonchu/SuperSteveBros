package proyectoFinal.SuperSteveBros.gameStates;

import java.awt.image.BufferedImage;

import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import proyectoFinal.SuperSteveBros.Game;
import proyectoFinal.SuperSteveBros.Ui.MenuButton;
import proyectoFinal.SuperSteveBros.utilz.LoadSave;

public class Menu extends State implements StateMethods {
	
	private MenuButton[] buttons = new MenuButton[3];
	private BufferedImage backgroundImg;
	private int menuX, menuY, menuWight, menuHeight;
	private ImageView background;
	
	public Menu(Game game) {
		super(game);
		loadButtons();
		loadBackground();
	}

	private void loadBackground() {
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
		menuWight = (int) (backgroundImg.getWidth() * Game.SCALE);
		menuHeight = (int) (backgroundImg.getHeight() * Game.SCALE);
		menuX = Game.GAME_WIDTH / 2 - menuWight / 2;
		menuY = (int) (45 * Game.SCALE);
		background = LoadSave.convertToFxImageView(backgroundImg);
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
		
		background.setX(menuX);
		background.setY(menuY);
		background.setFitWidth(menuWight);
		background.setFitHeight(menuHeight);
		root.getChildren().remove(background);
		root.getChildren().add(background);
		
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
