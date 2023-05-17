package proyectoFinal.SuperSteveBros.levels;

import java.awt.image.BufferedImage;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import proyectoFinal.SuperSteveBros.Game;
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

	public void draw(Pane root) {
		for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
			for (int i = 0; i < Game.TILES_IN_WIDTH; i++) {
				 int index = levelOne.getSpriteIndex(i, j);
//				 imageView.setImage();
				 imageView = new ImageView(image[index]);
				 imageView.setFitWidth(Game.TILES_SIZE);
				 imageView.setFitHeight(Game.TILES_SIZE);
				 imageView.setX(Game.TILES_SIZE*i);
				 imageView.setY(Game.TILES_SIZE*j);
				 root.getChildren().remove(imageView);
				 root.getChildren().add(imageView);
			}
		}
	}
	
	public void update() {
		
	}
	
	public Level getLevel() {
		return levelOne;
	}
}
