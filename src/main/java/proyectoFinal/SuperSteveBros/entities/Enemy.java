package proyectoFinal.SuperSteveBros.entities;

import static proyectoFinal.SuperSteveBros.utilz.Constants.EnemyConstants.*;
import static proyectoFinal.SuperSteveBros.utilz.HelpMethods.*;
import static proyectoFinal.SuperSteveBros.utilz.Constants.Directions.*;

import proyectoFinal.SuperSteveBros.Game;

public abstract class Enemy extends Entity{

	protected int aniIndex;
	protected int enemyState;
	protected int enemyType;
	protected int aniTick, aniSpeed = 25;
	protected boolean firstUpdate = true;
	protected boolean inAir;
	protected float fallSpeed;
	protected float gravity = 0.04f * Game.SCALE;
	protected float walkSpeed = 0.5f * Game.SCALE;
	protected int walkDir = LEFT;
	
	public Enemy(float x, float y, int width, int height, int enemyType) {
		super(x, y, width, height);
		this.enemyType = enemyType;
		iniHitbox(x, y, width, height);
	}
	
	protected void firstUpdateCheck(int[][] lvlData) {
		if(!IsEntityOnFloor(hitBox, lvlData)) {
			inAir = true;
		}
		firstUpdate = false;
	}
	
	protected void updateInAir(int[][] lvlData) {
		if (CanMoveHere(hitBox.x, hitBox.y + fallSpeed, hitBox.width, hitBox.height, lvlData)) {
			hitBox.y += fallSpeed;
			fallSpeed += gravity;
		} else {
			inAir = false;
			hitBox.y = GetEntityYPosUnderRoofOrAboveFloor(hitBox, fallSpeed);
		}
	}
	
	protected void move(int[][] lvlData) {
        float xSpeed = 0;
        
        if (walkDir == LEFT) {
            xSpeed = -walkSpeed;
            if (enemyState != MOVING_LEFT) {
                enemyState = MOVING_LEFT;
                aniIndex = 0; // Reiniciar el índice de animación
            }
        } else {
            xSpeed = walkSpeed;
            if (enemyState != MOVING_RIGHT) {
                enemyState = MOVING_RIGHT;
                aniIndex = 0; // Reiniciar el índice de animación
            }
        }
        
        if (CanMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, lvlData)) {
            if (isFloor(hitBox, xSpeed, lvlData)) {
                hitBox.x += xSpeed;
                return;
            }
        }
        changeWalkDir();
	}
	
	protected void canSeePlayer(int[][] lvlData, Player player) {
		int playerTileY = (int) (player.getHitbox().y / Game.TILES_SIZE);
	}
	
	protected void newState(int enemyState) {
		this.enemyState = enemyState;
		aniTick = 0;
		aniIndex = 0;
	}
	
	protected void updateAnimationTick() {
		  aniTick++;
          if (aniTick >= aniSpeed) {
              aniTick = 0;
              aniIndex++;
              if (aniIndex >= GetSpriteAmount(enemyType, enemyState)) {
                  aniIndex = 0;
              }
          }
	}
	
	protected void changeWalkDir() {
		if (walkDir == LEFT) {
			walkDir = RIGHT;
		} else {
			walkDir = LEFT;
		}
	}

	public int getAniIndex() {
		return aniIndex;
	}
	
	public int getEnemyState() {
		return enemyState;
	}
}
