package proyectoFinal.SuperSteveBros.gameStates;

import java.awt.image.BufferedImage;
import java.sql.SQLException;

import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import proyectoFinal.SuperSteveBros.Game;
import proyectoFinal.SuperSteveBros.Ui.ControlButton;
import proyectoFinal.SuperSteveBros.Ui.PauseButton;
import proyectoFinal.SuperSteveBros.dao.PlayerDAO;
import proyectoFinal.SuperSteveBros.utils.ConnectionData;
import proyectoFinal.SuperSteveBros.utilz.LoadSave;

import static proyectoFinal.SuperSteveBros.utilz.Constants.UI.ControlButtons.*;

public class Options extends State implements StateMethods {

	private BufferedImage backgroundImg, optionsBackgroundImg;
	private int bgX, bgY, bgW, bgH;
	private ControlButton menuB;
	private ImageView bgIView, optIView;
	private ImageView[] deleteButton;
	private BufferedImage[] dB;
	private int index;
	private boolean mouseOver;
	private boolean mousePressed;
	private Rectangle bounds;
	private int menuX, menuY;
	PlayerDAO playerDao;
	ConnectionData connectionData;
	
	public Options(Game game) {
		super(game);
		loadImgs();
		loadButtons();
		initBounds();
		loadDeleteButton();
		connectionData = new ConnectionData("jdbc:mysql://localhost:3306", "superstevebros", "root", "");
		playerDao = new PlayerDAO(connectionData);
	}

	private void initBounds() {
		bounds = new Rectangle(menuX + 150, menuY, 240, CONTROL_SIZE);
	}

	private void loadButtons() {
		menuX = (int) (317 * Game.SCALE);
		menuY = (int) (325 * Game.SCALE);

		menuB = new ControlButton(menuX, menuY, CONTROL_SIZE, CONTROL_SIZE, 2);
	}

	private void loadImgs() {
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BG);
		optionsBackgroundImg = LoadSave.GetSpriteAtlas(LoadSave.OPTIONS_BACKGROUND);
		
		
		bgIView = LoadSave.convertToFxImageView(backgroundImg);
		optIView = LoadSave.convertToFxImageView(optionsBackgroundImg);

		
		bgW = (int) (optionsBackgroundImg.getWidth() * Game.SCALE);
		bgH = (int) (optionsBackgroundImg.getHeight() * Game.SCALE);
		bgX = Game.GAME_WIDTH / 2 - bgW / 2;
		bgY = (int) (33 * Game.SCALE);
	}
	
	private void loadDeleteButton() {
	    dB = new BufferedImage[3];
	    deleteButton = new ImageView[3];
	    BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.DELETE_ACCOUNT);
	    int buttonWidth = temp.getWidth();
	    int buttonHeight = temp.getHeight() / dB.length;
	    for (int i = 0; i < dB.length; i++) {
	        dB[i] = temp.getSubimage(0, i * buttonHeight, buttonWidth, buttonHeight);
	        deleteButton[i] = LoadSave.convertToFxImageView(dB[i]);
	    }
	}



	@Override
	public void update() {
		menuB.update();
		index = 0;
		if (mouseOver) {
			index = 1;
		}
		if (mousePressed) {
			index = 2;
		}
	}

	@Override
	public void draw(Pane root) {
		optIView.setX(bgX);
		optIView.setY(bgY);
		optIView.setFitWidth(bgW);
		optIView.setFitHeight(bgH);
		root.getChildren().remove(bgIView);
		root.getChildren().add(bgIView);
		root.getChildren().remove(optIView);
		root.getChildren().add(optIView);
		menuB.draw(root);
		deleteButton[index].setX(menuX + 150);
		deleteButton[index].setY(menuY);
		deleteButton[index].setFitWidth(240);
		deleteButton[index].setFitHeight(CONTROL_SIZE);
		root.getChildren().remove(deleteButton[index]);
		root.getChildren().add(deleteButton[index]);
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if (event.getCode() == event.getCode().ESCAPE)
			Gamestate.state = Gamestate.MENU;
	}

	@Override
	public void keyReleased(KeyEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent event) {
		
		 mousePressed = false;
		 
		if (isIn(event, menuB)) {
			menuB.setMousePressed(true);
		}
		if (isInDelete(event)) {
			mousePressed = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		if (isIn(event, menuB)) {
			if (menuB.isMousePressed())
				Gamestate.state = Gamestate.MENU;
		}

		if (isInDelete(event) ) {
			if (mousePressed) {
				try {
					playerDao.delete(game.getPlayer());
					game.stopGameLoop();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		index = 0;
		menuB.resetBools();		
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		menuB.setMouseOver(false);
		mouseOver = false;
		
		if (isIn(event, menuB))
			menuB.setMouseOver(true);
		if (isInDelete(event))
			mouseOver = true;
	}
	

	private boolean isIn(MouseEvent event, PauseButton b) {
		return b.getBounds().contains(event.getX(), event.getY());
	}
	
	private boolean isInDelete(MouseEvent event) {
		return bounds.contains(event.getX(), event.getY());
	}

}
