package proyectoFinal.SuperSteveBros.Ui;

import static proyectoFinal.SuperSteveBros.utilz.Constants.UI.PauseButtons.SOUND_SIZE;

import java.awt.image.BufferedImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import proyectoFinal.SuperSteveBros.Game;
import proyectoFinal.SuperSteveBros.gameStates.StateMethods;
import proyectoFinal.SuperSteveBros.utilz.LoadSave;
import javafx.scene.image.ImageView;

public class PauseOverlay implements StateMethods {
	
	private BufferedImage backgroundImg;
	private int bgX, bgY, bgWight, bgHeight;
	private ImageView background;
	private SoundButton musicButton, sfxButton;
	
	public PauseOverlay() {
		loadBackground();
		createSoundButtons();
	}

	private void createSoundButtons() {
		int soundX = (int) (450 * Game.SCALE);
		int musicY = (int) (140 * Game.SCALE);
		int sfxY = (int) (186 * Game.SCALE);
		musicButton = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
		sfxButton = new SoundButton(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
	}

	private void loadBackground() {
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_PAUSE);
		bgWight = (int) (backgroundImg.getWidth() * Game.SCALE);
		bgHeight = (int) (backgroundImg.getHeight() * Game.SCALE);
		bgX = Game.GAME_WIDTH / 2 - bgWight / 2;
		bgY = (int) (25 * Game.SCALE);
		background = LoadSave.convertToFxImageView(backgroundImg);
	}

	@Override
	public void update() {
		musicButton.update();
		sfxButton.update();
	}

	@Override
	public void draw(Pane root) {
		
		// BACKGROUND
		
		background.setX(bgX);
		background.setY(bgY);
		background.setFitWidth(bgWight);
		background.setFitHeight(bgHeight);
		root.getChildren().remove(background);
		root.getChildren().add(background);
		
		// SOUND BUTTONS
		
		musicButton.draw(root);
		sfxButton.draw(root);
	}

	@Override
	public void keyPressed(KeyEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent event) {
		// TODO Auto-generated method stub
		
	}

	public void mouseDragged(MouseEvent event) {
		
	}
	
	@Override
	public void mousePressed(MouseEvent event) {
		if (isIn(event, musicButton)) {
			musicButton.setMousePressed(true);
		} else if (isIn(event, sfxButton)){
			sfxButton.setMousePressed(true);
		}
	}

	@Override
	public void mouseReleased(MouseEvent event) {
	    if (isIn(event, musicButton)) {
	        if (musicButton.isMousePressed()) {
	            musicButton.setMuted(!musicButton.isMuted());
	        }
	    } else if (isIn(event, sfxButton)){
	        if (sfxButton.isMousePressed()) {
	            sfxButton.setMuted(!sfxButton.isMuted());
	        }
	    }
	    musicButton.resetBools();
	    sfxButton.resetBools();
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		musicButton.setMouseOver(false);
		sfxButton.setMouseOver(false);
		
		if (isIn(event, musicButton)) {
			musicButton.setMouseOver(true);
		} else if (isIn(event, sfxButton)){
			sfxButton.setMouseOver(true);
		}
	}
	
	private boolean isIn(MouseEvent e, PauseButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}
}
