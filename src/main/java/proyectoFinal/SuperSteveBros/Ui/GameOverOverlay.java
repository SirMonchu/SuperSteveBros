package proyectoFinal.SuperSteveBros.Ui;

import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import proyectoFinal.SuperSteveBros.Game;
import proyectoFinal.SuperSteveBros.gameStates.Gamestate;
import proyectoFinal.SuperSteveBros.gameStates.Playing;
import proyectoFinal.SuperSteveBros.utilz.LoadSave;

public class GameOverOverlay {
	
	Playing playing;
	private ImageView gameOver;
	
	public GameOverOverlay(Playing playing) {
		this.playing = playing;
		gameOver = new ImageView();
		gameOver = LoadSave.convertToFxImageView(LoadSave.GetSpriteAtlas(LoadSave.GAME_OVER));
	}
	
	public void draw(Pane root) {
		gameOver.setFitWidth(Game.GAME_WIDTH);
		gameOver.setFitHeight(Game.GAME_HEIGHT);
		root.getChildren().remove(gameOver);
		root.getChildren().add(gameOver);
	}
	
	public void keyPressed(KeyEvent event) {
		if (event.getCode() == event.getCode().ENTER) {
	        playing.resetAll();
	        Gamestate.state = Gamestate.MENU;
		}
	}	
}
