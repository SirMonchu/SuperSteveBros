package proyectoFinal.SuperSteveBros.entities;

import static proyectoFinal.SuperSteveBros.utilz.Constants.Directions.LEFT;
import static proyectoFinal.SuperSteveBros.utilz.Constants.EnemyConstants.*;
import static proyectoFinal.SuperSteveBros.utilz.HelpMethods.CanMoveHere;
import static proyectoFinal.SuperSteveBros.utilz.HelpMethods.GetEntityYPosUnderRoofOrAboveFloor;
import static proyectoFinal.SuperSteveBros.utilz.HelpMethods.IsEntityOnFloor;
import static proyectoFinal.SuperSteveBros.utilz.HelpMethods.isFloor;

import proyectoFinal.SuperSteveBros.Game;

public class Zombie extends Enemy {

	public Zombie(float x, float y) {
		super(x, y, ZOMBIE_WIDTH, ZOMBIE_HEIGHT, ZOMBIE);
		iniHitbox(x, y, (int) (17 * Game.SCALE), (int) (29 * Game.SCALE));
		this.enemyState = IDLE_LEFT;
	}
	
	public void update(int[][] lvlData) {
		updateMove(lvlData);
		updateAnimationTick();
	}
	
	private void updateMove(int[][] lvlData) {
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
            	move(lvlData);
                break;
	        }
		}
	}
	
}
