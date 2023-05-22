package proyectoFinal.SuperSteveBros.entities;

import static proyectoFinal.SuperSteveBros.utilz.Constants.Directions.*;
import static proyectoFinal.SuperSteveBros.utilz.Constants.EnemyConstants.*;

import java.awt.geom.Rectangle2D;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import proyectoFinal.SuperSteveBros.Game;

public class Zombie extends Enemy {
	
	// ATTACK
	
		private Rectangle2D.Float attackBox;
		private Rectangle aRect;
		private int attackBoxOffsetX;

	public Zombie(float x, float y) {
		super(x, y, ZOMBIE_WIDTH, ZOMBIE_HEIGHT, ZOMBIE);
		iniHitbox(x, y, (int) (17 * Game.SCALE), (int) (29 * Game.SCALE));
		this.enemyState = IDLE_LEFT;
		initAttackBox();
	}
	
	private void updateAttackBox() {
		if (walkDir == RIGHT) {
			attackBox.x = hitBox.x;
		} else if (walkDir == LEFT) {
			attackBox.x = hitBox.x - hitBox.width + 22;
		}
		attackBox.y = hitBox.y + 9;
	}

	private void initAttackBox() {
		attackBox = new Rectangle2D.Float(x, y, (int) hitBox.width + (6 * Game.SCALE), (int) (10 * Game.SCALE));
//		attackBoxOffsetX = (int) (Game.SCALE * 30);
		aRect = new Rectangle(attackBox.x, attackBox.y, attackBox.width, attackBox.height);
		aRect.setFill(Color.TRANSPARENT);
		aRect.setStroke(Color.RED);
	}
	
	public void drawAttackBox(Pane root, int lvlOffset) {
    	aRect.setX(attackBox.x - lvlOffset);
    	aRect.setY(attackBox.y);
		root.getChildren().remove(aRect);
		root.getChildren().add(aRect);
	}

	public void update(int[][] lvlData, Player player) {
		updateMove(lvlData, player);
		updateAnimationTick();
		updateAttackBox();
	}
	
	private void updateMove(int[][] lvlData, Player player) {
		if (firstUpdate) {
			firstUpdateCheck(lvlData);
		}
		if(inAir) {
			updateInAir(lvlData);
		} else {
	        switch (enemyState) {
            case IDLE_LEFT:
            	newState(MOVING_LEFT);
            	break;
            case IDLE_RIGHT:
            	newState(MOVING_RIGHT);
                break;
            case MOVING_LEFT:
            case MOVING_RIGHT:
            	
            	if(canSeePlayer(lvlData, player)) {
            		turnTowardsPlayer(player);
            	}
            	if (IsPlayerCloseForAttack(player)) {
            		newState(ATTACK_LEFT);
            		if(walkDir == RIGHT) {
            			newState(ATTACK_RIGHT);
            		} else {
            			newState(ATTACK_LEFT);
            		}
            	} else {
                	move(lvlData);
            	}
            	break;
            case ATTACK_RIGHT:
            case ATTACK_LEFT:
            	if (aniIndex == 0) {
            		attacChecked = false;
            	}
            	if (aniIndex == 3 && !attacChecked) {
            		checkPlayerHit(attackBox, player);
            	}
            	break;
	        }
		}
	}	
}
