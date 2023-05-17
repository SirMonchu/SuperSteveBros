package proyectoFinal.SuperSteveBros.Ui;

import static proyectoFinal.SuperSteveBros.utilz.Constants.UI.ControlButtons.*;

import java.awt.image.BufferedImage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import proyectoFinal.SuperSteveBros.utilz.LoadSave;

public class ControlButton extends PauseButton{

	private BufferedImage[] imgs;
	private int rowIndex, index;
	private ImageView imageView;
	private Image[] img;
	private boolean mouseOver, mousePressed;
	
	public ControlButton(int xPos, int yPos, int widht, int height, int rowIndex) {
		super(xPos, yPos, widht, height);
		this.rowIndex = rowIndex;
		loadImgs();
		imageView = new ImageView();
	}

	private void loadImgs() {
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.CONTROL_BUTTONS);
		imgs = new BufferedImage[3];
		img = new Image[3];
		for (int i = 0; i < imgs.length; i++) {
			imgs[i] = temp.getSubimage(i * CONTROL_SIZE_DEF, rowIndex * CONTROL_SIZE_DEF, CONTROL_SIZE_DEF, CONTROL_SIZE_DEF);
			img[i] = LoadSave.convertToFxImage(imgs[i]);	
		}
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
	
	public void draw(Pane root) {
    	imageView.setImage(img[index]);
    	imageView.setX(xPos);
    	imageView.setY(yPos);
    	imageView.setFitWidth(CONTROL_SIZE);
    	imageView.setFitHeight(CONTROL_SIZE);
        root.getChildren().remove(imageView);
        root.getChildren().add(imageView);
	}
	
	public void resetBools() {
		mouseOver = false;
		mousePressed = false;
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
	
	
}
