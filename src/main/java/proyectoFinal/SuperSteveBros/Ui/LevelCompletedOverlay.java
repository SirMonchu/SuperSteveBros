package proyectoFinal.SuperSteveBros.Ui;

import static proyectoFinal.SuperSteveBros.utilz.Constants.UI.ControlButtons.*;

import java.awt.image.BufferedImage;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import proyectoFinal.SuperSteveBros.Game;
import proyectoFinal.SuperSteveBros.gameStates.Gamestate;
import proyectoFinal.SuperSteveBros.gameStates.Playing;
import proyectoFinal.SuperSteveBros.utilz.LoadSave;

public class LevelCompletedOverlay {
	
	private Playing playing;
	private ControlButton menu, next;
	private BufferedImage img;
	private ImageView imageView;
	private int bgX, bgY, bgW, bgH;
	
	public LevelCompletedOverlay(Playing playing) {
		this.playing = playing;
		initImg();
		initButtons();
	}

	private void initImg() {
		imageView = new ImageView();
		img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_COMPLETED);
		imageView = LoadSave.convertToFxImageView(img);
		bgW = (int) (img.getWidth() * Game.SCALE);
		bgH = (int) (img.getHeight() * Game.SCALE);
		bgX = Game.GAME_WIDTH / 2 - bgW / 2;
		bgY = (int) (75 * Game.SCALE);
	}
	
	private void initButtons() {
		int menuX = (int) (330 * Game.SCALE);
		int nextX = (int) (445 * Game.SCALE);
		int y = (int) (195 * Game.SCALE);
		next = new ControlButton(nextX, y, CONTROL_SIZE, CONTROL_SIZE, 0);
		menu = new ControlButton(menuX, y, CONTROL_SIZE, CONTROL_SIZE, 2);
	}

	public void draw(Pane root) {
		imageView.setX(bgX);
		imageView.setY(bgY);
		imageView.setFitWidth(bgW);
		imageView.setFitHeight(bgH);
		root.getChildren().remove(imageView);
		root.getChildren().add(imageView);
		next.draw(root);
		menu.draw(root);
	}
	
	public void update() {
		next.update();
		menu.update();
	}
	
	private boolean isIn(ControlButton c, MouseEvent event) {
		return c.getBounds().contains(event.getX(), event.getY());
	}
	
	public void mouseMoved(MouseEvent event) {
		next.setMouseOver(false);
		menu.setMouseOver(false);
		
		if (isIn(menu, event)) {
			menu.setMouseOver(true);
		} else if (isIn(next, event)) {
			next.setMouseOver(true);
		}
	}
	
	public void mouseReleased(MouseEvent event) {
		if (isIn(menu, event)) {
			if (menu.isMousePressed()) {
				playing.resetAll();
				Gamestate.state = Gamestate.MENU;
			}
		} else if (isIn(next, event)) {
			if (next.isMousePressed()) {
				playing.loadNextLevel();
			}
		}
		menu.resetBools();
		next.resetBools();
	}
	
	public void mousePressed(MouseEvent event) {
		if (isIn(menu, event)) {
			menu.setMousePressed(true);
		} else if (isIn(next, event)) {
			next.setMousePressed(true);
		}
	}
	
}
