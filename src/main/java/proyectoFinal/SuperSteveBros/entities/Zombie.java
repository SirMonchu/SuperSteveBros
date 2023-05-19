package proyectoFinal.SuperSteveBros.entities;

import static proyectoFinal.SuperSteveBros.utilz.Constants.Directions.*;
import static proyectoFinal.SuperSteveBros.utilz.Constants.EnemyConstants.*;
import proyectoFinal.SuperSteveBros.Game;

public class Zombie extends Enemy {

	public Zombie(float x, float y) {
		super(x, y, ZOMBIE_WIDTH, ZOMBIE_HEIGHT, ZOMBIE);
		iniHitbox(x, y, (int) (17 * Game.SCALE), (int) (29 * Game.SCALE));
		this.enemyState = IDLE_LEFT;
	}
	
	public void update(int[][] lvlData, Player player) {
		updateMove(lvlData, player);
		updateAnimationTick();
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
	        }
		}
	}
	
}
