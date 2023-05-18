package proyectoFinal.SuperSteveBros.entities;

import static proyectoFinal.SuperSteveBros.utilz.Constants.EnemyConstants.*;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import proyectoFinal.SuperSteveBros.gameStates.Playing;
import proyectoFinal.SuperSteveBros.utilz.LoadSave;

public class EnemyManager {

	private Playing playing;
	private ImageView[][] zombieArray;
	private ArrayList<Zombie> zombies = new ArrayList<>();
	private ImageView zombieImageView;
	
	public EnemyManager(Playing playing) {
		this.playing = playing;
		loadEnemyImgs();
		addEnemies();
	}
	
	private void addEnemies() {
		zombies = LoadSave.GetZombies();
		System.out.println("Size off Zombies: " + zombies.size());
	}

	public void update() {
		for (Zombie z : zombies) {
			z.update();
		}
	}
	
	public void draw(Pane root, int xLvlOffset) {
		drawCrabs(root, xLvlOffset);
	}

	private void drawCrabs(Pane root, int xLvlOffset) {
		for (Zombie z : zombies) {
			ImageView zombieImageView = zombieArray[z.getEnemyState()][z.getAniIndex()];
            zombieImageView.setX(z.getHitbox().x - xLvlOffset);
            zombieImageView.setY(z.getHitbox().y);
            zombieImageView.setFitWidth(ZOMBIE_WIDTH);
            zombieImageView.setFitHeight(ZOMBIE_HEIGHT);
            root.getChildren().remove(zombieImageView);
            root.getChildren().add(zombieImageView);
        }
	}

    private void loadEnemyImgs() {
        zombieArray = new ImageView[8][14];
        Image temp = LoadSave.convertToFxImage(LoadSave.GetSpriteAtlas(LoadSave.ZOMBIE_ATLAS));
        for (int j = 0; j < zombieArray.length; j++) {
            for (int i = 0; i < zombieArray[j].length; i++) {
            	zombieArray[j][i] = new ImageView(temp);
            	zombieArray[j][i].setViewport(
                        new javafx.geometry.Rectangle2D(i * ZOMBIE_WIDTH_DEF, j * ZOMBIE_HEIGHT_DEF,
                        		ZOMBIE_WIDTH_DEF, ZOMBIE_HEIGHT_DEF));
            }
        }
    }
}
