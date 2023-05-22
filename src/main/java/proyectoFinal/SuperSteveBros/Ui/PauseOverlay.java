package proyectoFinal.SuperSteveBros.Ui;

import static proyectoFinal.SuperSteveBros.utilz.Constants.UI.PauseButtons.SOUND_SIZE;
import static proyectoFinal.SuperSteveBros.utilz.Constants.UI.ControlButtons.*;
import static proyectoFinal.SuperSteveBros.utilz.Constants.UI.VolumeButton.*;

import java.awt.image.BufferedImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import proyectoFinal.SuperSteveBros.Game;
import proyectoFinal.SuperSteveBros.entities.Player;
import proyectoFinal.SuperSteveBros.gameStates.Gamestate;
import proyectoFinal.SuperSteveBros.gameStates.Playing;
import proyectoFinal.SuperSteveBros.gameStates.StateMethods;
import proyectoFinal.SuperSteveBros.utilz.LoadSave;
import javafx.scene.image.ImageView;

public class PauseOverlay implements StateMethods {
	
	private Playing playing;
	private BufferedImage backgroundImg;
	private int bgX, bgY, bgWight, bgHeight;
	private ImageView background;
	private SoundButton musicButton, sfxButton;
	private ControlButton menuB, replayB, unPauseB;
	private VolumeButton volumeButton;
	
	public PauseOverlay(Playing playing) {
		this.playing = playing;
		loadBackground();
		createSoundButtons();
		createControlButtons();
		createVolumeButton();
	}

	private void createVolumeButton() {
		int vX = (int) (309 * Game.SCALE);
		int vY = (int) (278 * Game.SCALE);
		volumeButton = new VolumeButton(vX, vY, SLIDER_WIDTH, VOLUME_HEIGHT);
	}

	private void createControlButtons() {
		int menuX = (int) (313 * Game.SCALE);
		int replayX = (int) (387 * Game.SCALE);
		int unPauseX = (int) (462 * Game.SCALE);
		int bY = (int) (325 * Game.SCALE);
		
		menuB = new ControlButton(menuX, bY, CONTROL_SIZE, CONTROL_SIZE, 2);
		replayB = new ControlButton(replayX, bY, CONTROL_SIZE, CONTROL_SIZE, 1);
		unPauseB = new ControlButton(unPauseX, bY, CONTROL_SIZE, CONTROL_SIZE, 0);
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
		
		menuB.update();
		replayB.update();
		unPauseB.update();
		
		volumeButton.update();
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
		
		// CONTROL BUTTONS
		
		menuB.draw(root);
		replayB.draw(root);
		unPauseB.draw(root);
		
		//VOLUME BUTTONS
		
		volumeButton.draw(root);
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
		if (volumeButton.isMousePressed()) {
			volumeButton.changeX((int) event.getX());
		}
	}
	
	@Override
	public void mousePressed(MouseEvent event) {
		if (isIn(event, musicButton)) {
			musicButton.setMousePressed(true);
		} else if (isIn(event, sfxButton)){
			sfxButton.setMousePressed(true);
		} else if (isIn(event, menuB)) {
			menuB.setMousePressed(true);
		} else if (isIn(event, replayB)) {
			replayB.setMousePressed(true);
		} else if (isIn(event, unPauseB)) {
			unPauseB.setMousePressed(true);
		} else if (isIn(event, volumeButton)) {
			volumeButton.setMousePressed(true);
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
	    } else if (isIn(event, menuB)) {
	    	if (menuB.isMousePressed()) {
	            Gamestate.state = Gamestate.MENU;
	            playing.unPause();
	            playing.resetAll();
	    	}
	    } else if (isIn(event, replayB)) {
	    	if (replayB.isMousePressed()) {
	    		playing.resetAll();
	    	}
	    } else if (isIn(event, unPauseB)) {
	    	if (unPauseB.isMousePressed()) {
	            playing.unPause();
	    	}
	    }
	    musicButton.resetBools();
	    sfxButton.resetBools();
	    menuB.resetBools();
	    replayB.resetBools();
	    unPauseB.resetBools();
	    volumeButton.resetBools();
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		musicButton.setMouseOver(false);
		sfxButton.setMouseOver(false);
		menuB.setMouseOver(false);
		replayB.setMouseOver(false);
		unPauseB.setMouseOver(false);
		volumeButton.setMouseOver(false);
		
		if (isIn(event, musicButton)) {
			musicButton.setMouseOver(true);
		} else if (isIn(event, sfxButton)){
			sfxButton.setMouseOver(true);
		} else if (isIn(event, menuB)){
			menuB.setMouseOver(true);
		} else if (isIn(event, replayB)){
			replayB.setMouseOver(true);
		} else if (isIn(event, unPauseB)){
			unPauseB.setMouseOver(true);
		} else if (isIn(event, volumeButton)){
			volumeButton.setMouseOver(true);
		}
	}
	
	private boolean isIn(MouseEvent e, PauseButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}
}
