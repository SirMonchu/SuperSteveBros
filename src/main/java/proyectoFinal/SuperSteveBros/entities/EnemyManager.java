package proyectoFinal.SuperSteveBros.entities;

import static proyectoFinal.SuperSteveBros.utilz.Constants.EnemyConstants.*;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import proyectoFinal.SuperSteveBros.gameStates.Playing;
import proyectoFinal.SuperSteveBros.levels.Level;
import proyectoFinal.SuperSteveBros.utilz.LoadSave;

public class EnemyManager {

	private Playing playing;
	private ImageView[][] zombieArray;
	private ArrayList<Zombie> zombies = new ArrayList<>();
	private ImageView zombieImageView;
	protected Rectangle fxRect;
	
	public EnemyManager(Playing playing) {
		this.playing = playing;
		loadEnemyImgs();
	}
	
	public void loadEnemies(Level level) {
		zombies = level.getZombies();
		System.out.println("Size off Zombies: " + zombies.size());
	}

	public void update(int[][] lvlData, Player player) {
		boolean isAnyActive = false;
		for (Zombie z : zombies)
			if (z.isActive()) {
				z.update(lvlData, player);
				isAnyActive = true;
			}
		if(!isAnyActive)
			playing.setLevelCompleted(true);
	}
	
	public void draw(Pane root, int xLvlOffset) {
		drawZombies(root, xLvlOffset);
	}

	private void drawZombies(Pane root, int xLvlOffset) {
		for (Zombie z : zombies) {
			if (z.isActive()) {
				ImageView zombieImageView = zombieArray[z.getEnemyState()][z.getAniIndex()];
	            zombieImageView.setX(z.getHitbox().x - xLvlOffset - 18);
	            zombieImageView.setY(z.getHitbox().y - 18);
	            zombieImageView.setFitWidth(ZOMBIE_WIDTH - 6);
	            zombieImageView.setFitHeight(ZOMBIE_HEIGHT - 22);
	            root.getChildren().remove(zombieImageView);
	            root.getChildren().add(zombieImageView);
	            z.drawAttackBox(root, xLvlOffset);
	            z.drawHitbox(root, xLvlOffset);
			}
        }
	}
	
	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		for (Zombie z : zombies) {
			if (z.isActive()) {
				if (attackBox.intersects(z.getHitbox())) {
					z.hurt(1);
					return;
				}
			}
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
    
	protected void drawHitbox(Pane root, int xLvlOffset) {
		for (Zombie z : zombies) {
		fxRect = new Rectangle((int) z.getHitbox().x, (int) z.getHitbox().y - 18, (int) z.getHitbox().width, (int) z.getHitbox().height);
		fxRect.setFill(Color.TRANSPARENT);
		fxRect.setStroke(Color.RED);
		fxRect.setX(z.getHitbox().x - xLvlOffset);
		fxRect.setY(z.getHitbox().y);
		root.getChildren().remove(fxRect);
		root.getChildren().add(fxRect);
		}
	}

	public void resetAllEnemies() {
		for (Zombie z : zombies) {
			z.resetEnemy();
		}
	}
}
