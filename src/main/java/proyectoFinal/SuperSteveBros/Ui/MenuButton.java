package proyectoFinal.SuperSteveBros.Ui;

import java.awt.image.BufferedImage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import proyectoFinal.SuperSteveBros.utilz.LoadSave;
import proyectoFinal.SuperSteveBros.gameStates.Gamestate;

import static proyectoFinal.SuperSteveBros.utilz.Constants.UI.Buttons.*;

public class MenuButton {
	
	private int xPos, yPos, rowIndex, index;
	private int xOffsetCenter = BUTTONS_WIDTH / 2;
	private Gamestate state;
	private BufferedImage[] imgs;
	private Image[] img;
	private boolean mouseOver, mousePressed;
	private Rectangle bounds;
	private ImageView imageView;
	
	public MenuButton(int xPos, int yPos, int rowIndex, Gamestate state) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.rowIndex = rowIndex;
		this.state = state;
		loadImgs();
		initBounds();
		imageView = new ImageView();
	}


	private void initBounds() {
		bounds = new Rectangle(xPos - xOffsetCenter, yPos, BUTTONS_WIDTH, BUTTONS_HEIGHT);
		
	}


	private void loadImgs() {
	    imgs = new BufferedImage[3];
	    img = new Image[3];
	    BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.MENU_BUTTONS);
	    for (int i = 0; i < imgs.length; i++) {
	        imgs[i] = temp.getSubimage(i * BUTTONS_WIDTH_DEF, rowIndex * BUTTONS_HEIGHT_DEF, BUTTONS_WIDTH_DEF, BUTTONS_HEIGHT_DEF);
	        img[i] = LoadSave.convertToFxImage(imgs[i]);
	    }
	}

	
	public void draw(Pane root) {
	    	imageView.setImage(img[index]);
	    	imageView.setX(xPos - xOffsetCenter);
	    	imageView.setY(yPos);
	    	imageView.setFitWidth(BUTTONS_WIDTH);
	    	imageView.setFitHeight(BUTTONS_HEIGHT);
	        root.getChildren().remove(imageView);
	        root.getChildren().add(imageView);
	}

	
	public void update() {
		index = 0;
		if (mouseOver) {
			index = 1;
		}
		if (mousePressed) {
			index = 2;
		}
	}

	/**
	 * @return the mouseOver
	 */
	public boolean isMouseOver() {
		return mouseOver;
	}


	/**
	 * @param mouseOver the mouseOver to set
	 */
	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}


	/**
	 * @return the mousePressed
	 */
	public boolean isMousePressed() {
		return mousePressed;
	}


	/**
	 * @param mousePressed the mousePressed to set
	 */
	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public void applyGamestate() {
		Gamestate.state = state;
	}
	
	public void resetBools() {
		mouseOver = false;
		mousePressed = false;
	}
}
