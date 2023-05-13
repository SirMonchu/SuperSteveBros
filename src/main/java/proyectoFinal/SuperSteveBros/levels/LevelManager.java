package proyectoFinal.SuperSteveBros.levels;

import java.awt.image.BufferedImage;
import javafx.scene.image.ImageView;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import proyectoFinal.SuperSteveBros.Game;
import proyectoFinal.SuperSteveBros.View.GamePanel;
import proyectoFinal.SuperSteveBros.utilz.LoadSave;

public class LevelManager {
	
	private Game game;
	private BufferedImage [] levelSprite;
	private Image[] image = new Image[48];
	private Level levelOne;
	private ImageView imageView;
	
	public LevelManager(Game game) {
		this.game = game;
		importOutsideSprites();
		levelOne = new Level(LoadSave.GetLevelData());
		imageView = new ImageView();
	}
	
	private void importOutsideSprites() {
	    BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
	    levelSprite = new BufferedImage[48];
	    for (int j = 0; j < 4; j++) {
	        for (int i = 0; i < 12; i++) {
	            int index = j * 12 + i;
	            levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
	            image[index] = LoadSave.convertToFxImage(levelSprite[index]);
	        }
	    }
	}

	public void draw(GamePanel gamePanel) {
		for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
			for (int i = 0; i < Game.TILES_IN_WIDTH; i++) {
				 int index = levelOne.getSpriteIndex(i, j);
				 gamePanel.getCanvas().getGraphicsContext2D().drawImage(image[index], Game.TILES_SIZE*i, Game.TILES_SIZE*j, Game.TILES_SIZE, Game.TILES_SIZE);
			}
		}
	}
	
	public void update() {
		
	}
	
	public Level getLevel() {
		return levelOne;
	}
}
