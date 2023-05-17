package proyectoFinal.SuperSteveBros.Ui;

import static proyectoFinal.SuperSteveBros.utilz.Constants.UI.PauseButtons.*;

import java.awt.image.BufferedImage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import proyectoFinal.SuperSteveBros.gameStates.StateMethods;
import proyectoFinal.SuperSteveBros.utilz.LoadSave;

public class SoundButton extends PauseButton implements StateMethods{
	
	private BufferedImage[][] soundImgs;
	private Image[][] img;
	private ImageView imageView;
	private boolean mouseOver, mousePressed, mouseDragged;
	private boolean muted;
	private int rowIndex, colIndex;
	
	public SoundButton(int xPos, int yPos, int widht, int height) {
		super(xPos, yPos, widht, height);
		loadSoundImages();
		imageView = new ImageView();
	}

	private void loadSoundImages() {
	    BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.SOUND_BUTTONS);
		soundImgs = new BufferedImage[2][3];
	    img = new Image[2][3];
	    for (int j = 0; j < soundImgs.length; j++) {
	    	for (int i = 0; i < soundImgs[j].length; i++) {
	    	soundImgs[j][i] = temp.getSubimage(i * SOUND_SIZE_DEF, j * SOUND_SIZE_DEF, SOUND_SIZE_DEF, SOUND_SIZE_DEF);
	        img[j][i] = LoadSave.convertToFxImage(soundImgs[j][i]);	
	    	}
	    }
	}

	@Override
	public void update() {
		if (muted) {
			rowIndex = 1;
		} else {
			rowIndex = 0;
		}
		colIndex = 0;
		if(mouseOver) {
			colIndex = 1;
		}
		if(mousePressed) {
			colIndex = 2;
		}
		
	}
	
	public void resetBools() {
		mouseOver = false;
		mousePressed = false;
	}

	@Override
	public void draw(Pane root) {
    	imageView.setImage(img[rowIndex][colIndex]);
    	imageView.setX(xPos);
    	imageView.setY(yPos);
    	imageView.setFitWidth(widht);
    	imageView.setFitHeight(height);
        root.getChildren().remove(imageView);
        root.getChildren().add(imageView);
	}

	@Override
	public void keyPressed(KeyEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		// TODO Auto-generated method stub
		
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

	/**
	 * @return the muted
	 */
	public boolean isMuted() {
		return muted;
	}

	/**
	 * @param muted the muted to set
	 */
	public void setMuted(boolean muted) {
		this.muted = muted;
	}
	
	
}
