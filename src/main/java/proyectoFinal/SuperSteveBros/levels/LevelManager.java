package proyectoFinal.SuperSteveBros.levels;

import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import proyectoFinal.SuperSteveBros.Game;
import proyectoFinal.SuperSteveBros.Ui.Ranking;
import proyectoFinal.SuperSteveBros.gameStates.Gamestate;
import proyectoFinal.SuperSteveBros.utilz.LoadSave;

public class LevelManager {
	
	private Game game;
	private BufferedImage [] levelSprite;
	private Image[] image = new Image[48];
	private ArrayList<Level> levels;
	private int lvlIndex = 0;
	private ImageView imageView;
	Ranking ranking;
	private boolean last = true;
	
	public LevelManager(Game game) {
		this.game = game;
		importOutsideSprites();
		levels = new ArrayList<>();
		buildAllLevels();
		ranking = new Ranking();
	}
	
	private void buildAllLevels() {
		BufferedImage[] allLevels = LoadSave.GetAllLevels();
		for (BufferedImage img : allLevels) {
			levels.add(new Level(img));
		}
	}
	

	public void loadNextLevel() {
	    lvlIndex++;
	    if (lvlIndex >= levels.size()) {
	        lvlIndex = 0;
	        Gamestate.state = Gamestate.MENU;
	    }
	    Level newLevel = levels.get(lvlIndex);
	    game.getPlaying().getEnemyManager().loadEnemies(newLevel);
	    game.getPlaying().getPlayer().loadLvlData(newLevel.getLvlData());
	    game.getPlaying().setMaxLvlOffset(newLevel.getLvlOffset());
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

	public void draw(Pane root, int lvlOffset) {
		for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
			for (int i = 0; i < levels.get(lvlIndex).getLvlData()[0].length; i++) {
				 int index = levels.get(lvlIndex).getSpriteIndex(i, j);
				 imageView = new ImageView(image[index]);
				 imageView.setFitWidth(Game.TILES_SIZE);
				 imageView.setFitHeight(Game.TILES_SIZE);
				 imageView.setX(Game.TILES_SIZE*i - lvlOffset);
				 imageView.setY(Game.TILES_SIZE*j);
				 root.getChildren().remove(imageView);
				 root.getChildren().add(imageView);
			}
		}
	}
	
	public void update() {
		
	}
	
	public Level getLevel() {
		return levels.get(lvlIndex);
	}
	
	public int getLevelId() {
		return lvlIndex;
	}
	
	public String getLevelName() {
		String lvlName;
		lvlName = "Nivel " + lvlIndex;
		return lvlName;
	}
	
	public String getLevelDifficulty() {
		String lvlDiff;
		if (game.getPlaying().getEnemyManager().getEnemySize() < 2) {
			lvlDiff = "facil";
		} else if (game.getPlaying().getEnemyManager().getEnemySize() >= 2 && game.getPlaying().getEnemyManager().getEnemySize() < 4) {
			lvlDiff = "media";
		} else {
			lvlDiff = "dificil";
		}
		return lvlDiff;
	}
	
	public int getAmountOfLevels() {
		return levels.size();
	}

}
