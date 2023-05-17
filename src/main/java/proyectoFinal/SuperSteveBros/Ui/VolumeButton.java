package proyectoFinal.SuperSteveBros.Ui;

import static proyectoFinal.SuperSteveBros.utilz.Constants.UI.VolumeButton.*;

import java.awt.image.BufferedImage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import proyectoFinal.SuperSteveBros.utilz.LoadSave;

public class VolumeButton extends PauseButton{

	private BufferedImage[] imgs;
	private Image Bimg[];
	private BufferedImage slider;
	private ImageView imageView, slide;
	private int index = 0;
	private boolean mouseOver, mousePressed;
	private int buttonX, minX, maxX;
	
	public VolumeButton(int xPos, int yPos, int widht, int height) {
		super(xPos + widht / 2, yPos, VOLUME_WIDTH, height);
		bounds.x -= VOLUME_WIDTH / 2;
		buttonX = xPos + widht / 2;
		this.xPos = xPos;
		this.widht = widht;
		minX = xPos + VOLUME_WIDTH / 2;
		maxX = xPos + widht - VOLUME_WIDTH / 2;
		loadImgs();
		imageView = new ImageView();
	}

	private void loadImgs() {
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.VOLUME_BUTTONS);
		imgs = new BufferedImage[3];
		Bimg = new Image[3];
		slide = new ImageView();
		for (int i = 0; i < imgs.length; i++) {
			imgs[i] = temp.getSubimage(i * VOLUME_WIDTH_DEF, 0, VOLUME_WIDTH_DEF, VOLUME_HEIGHT_DEF);
			slider = temp.getSubimage(3 * VOLUME_WIDTH_DEF, 0, SLIDER_WIDTH_DEF, VOLUME_HEIGHT_DEF);
			Bimg[i] = LoadSave.convertToFxImage(imgs[i]);
			slide = LoadSave.convertToFxImageView(slider);
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
		// SLIDER
    	slide.setX(xPos);
    	slide.setY(yPos);
    	slide.setFitWidth(widht);
    	slide.setFitHeight(height);
        root.getChildren().remove(slide);
        root.getChildren().add(slide);
		// BUTTONS
    	imageView.setImage(Bimg[index]);
    	imageView.setX(buttonX - VOLUME_WIDTH / 2);
    	imageView.setY(yPos);
    	imageView.setFitWidth(VOLUME_WIDTH);
    	imageView.setFitHeight(height);
        root.getChildren().remove(imageView);
        root.getChildren().add(imageView);
	}
	
	public void changeX(int xPos) {
		if (xPos < minX) {
			buttonX = minX;
		} else if (xPos > maxX) {
			buttonX = maxX;
		} else {
			buttonX = xPos;
		}
		bounds.x = buttonX - VOLUME_WIDTH / 2;
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
