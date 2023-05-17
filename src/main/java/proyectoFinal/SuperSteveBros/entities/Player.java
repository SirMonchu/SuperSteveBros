package proyectoFinal.SuperSteveBros.entities;

import static proyectoFinal.SuperSteveBros.utilz.HelpMethods.*;
import static proyectoFinal.SuperSteveBros.utilz.Constants.PlayerConstants.GetSpriteAmount;
import static proyectoFinal.SuperSteveBros.utilz.Constants.PlayerConstants.IDLE;
import static proyectoFinal.SuperSteveBros.utilz.Constants.PlayerConstants.RUNNING_LEFT;
import static proyectoFinal.SuperSteveBros.utilz.Constants.PlayerConstants.RUNNING_RIGHT;
import static proyectoFinal.SuperSteveBros.utilz.Constants.PlayerConstants.SNEAKING_LEFT;
import static proyectoFinal.SuperSteveBros.utilz.Constants.PlayerConstants.SNEAKING_RIGHT;
import static proyectoFinal.SuperSteveBros.utilz.Constants.PlayerConstants.WALKING_LEFT;
import static proyectoFinal.SuperSteveBros.utilz.Constants.PlayerConstants.WALKING_RIGHT;

import java.awt.image.BufferedImage;
import javafx.scene.image.Image;
import proyectoFinal.SuperSteveBros.Game;
import proyectoFinal.SuperSteveBros.utilz.LoadSave;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Player extends Entity {

    private Image[][] animations;
    private int aniTick, aniIndex, aniSpeed = 11;
    private int playerAction = IDLE;
    private boolean moving = false;
    private boolean running = false;
    private float speed = 2;
    private boolean sneaking = false;
    private BufferedImage img;
    private boolean left, up, right, down, jump;
    public static int [][] lvlData;
    private float xDrawOffset = 10 * Game.SCALE;
    private float yDrawOffset = 4 * Game.SCALE;
    private ImageView imageView;
    
    //JUMPING // GRAVITY
    
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float JumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;
    
	public Player(float x, float y, int width, int height) {
		super(x, y, width, height);
		imageView = new ImageView();
		imageView.setFitWidth(width);
		imageView.setFitHeight(height);
		loadAnimations();
		iniHitbox(x, y, 17 * Game.SCALE, 29 * Game.SCALE);
	}
	
	public void update() {
		updateCharacterOnAnimation();
		updatePos();
        updateAniTick();
        setAnimation();     
	}

	public void render(Pane root) {
		    imageView.setImage(animations[playerAction][aniIndex]);
		    imageView.setX((int) (hitBox.x - xDrawOffset));
		    imageView.setY((int) (hitBox.y - yDrawOffset));
		    root.getChildren().remove(imageView);
		    root.getChildren().add(imageView);
		    drawHitbox(root);
	}
	
    private void loadAnimations() {

    	 img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
    	
            animations = new Image[8][11];
            for (int j = 0; j < animations.length; j++) {
                for (int i = 0; i < animations[j].length; i++) {
                    BufferedImage subimage = img.getSubimage(i*181, j*200, 181, 200);
                    animations[j][i] = LoadSave.convertToFxImage(subimage);
                }
            }
    }
    
    public void loadLvlData(int [][] lvlData) {
    	this.lvlData = lvlData;
    	if (!IsEntityOnFloor(hitBox, lvlData)) {
    		inAir = true;
    	}
    }
    
	
	private void updateCharacterOnAnimation() {
		speed = 2;
		yDrawOffset = 4 * Game.SCALE;
		xDrawOffset = 10 * Game.SCALE;
		imageView.setFitWidth(width);
		imageView.setFitHeight(height);
		if ((right || left) && running && !sneaking) {
			speed *= 1.5;
			yDrawOffset = 6 * Game.SCALE;
			imageView.setFitWidth(width * 1.1);
			imageView.setFitHeight(height * 1.1);
		} else if ((right || left) && sneaking && !running) {
			speed *= 0.5;
			yDrawOffset = 6 * Game.SCALE;
			imageView.setFitWidth(width * 1.2);
			imageView.setFitHeight(height * 1.2);
		} else if ((right || left) && !sneaking && !running) {
			yDrawOffset = 7 * Game.SCALE;
			xDrawOffset = 15 * Game.SCALE;
			imageView.setFitWidth(width * 1.2);
			imageView.setFitHeight(height * 1.2);
		}
	}
    	
    private void updatePos() {
        moving = false;

        if (jump)
            jump();
        if (!left && !right && !inAir)
            return;

        float xSpeed = 0;
        
        if (left)
            xSpeed -= speed;
        if (right)
            xSpeed += speed;

        if (!inAir)
            if (!IsEntityOnFloor(hitBox, lvlData))
                inAir = true;

        if (inAir) {
            if (CanMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, lvlData)) {
                hitBox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                hitBox.y = GetEntityYPosUnderRoofOrAboveFloor(hitBox, airSpeed);
                if (airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedAfterCollision;
                updateXPos(xSpeed);
            }

        } else {
            updateXPos(xSpeed);
        }
        moving = true;
    }


	private void jump() {
		if (inAir)
			return;
		inAir = true;
		airSpeed = JumpSpeed;

	}

	private void resetInAir() {
		inAir = false;
		airSpeed = 0;

	}

	private void updateXPos(float xSpeed) {
		if (CanMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, lvlData)) {
		    hitBox.x += xSpeed;
		} else {
			hitBox.x = GetEntityXPosNextToWall(hitBox, xSpeed);
		}
	}

		private void updateAniTick() {
            aniTick++;
            if (aniTick >= aniSpeed) {
                aniTick = 0;
                aniIndex++;
                if (aniIndex >= GetSpriteAmount(playerAction)) {
                    aniIndex = 0;
                }
            }
        }
        
        private void setAnimation() {
    		if (moving && right && running == false && sneaking == false) {
    			playerAction = WALKING_RIGHT;
    		} else if (moving && left && running == false && sneaking == false){
    			playerAction = WALKING_LEFT;
    		} else if (moving && right && running) {
    			playerAction = RUNNING_RIGHT;
    		} else if (moving && left && running) {	
    			playerAction = RUNNING_LEFT;	
    		} else if (moving && right && sneaking) {
    			playerAction = SNEAKING_RIGHT;
    		} else if (moving && left && sneaking) {
    			playerAction = SNEAKING_LEFT;
    		} else {
    			playerAction = IDLE;
    		}
    	}
    	
        
        public void setRunning(boolean running) {
        	this.running = running;
        }
        
        public void setSneaking(boolean sneaking) {
        	this.sneaking = sneaking;
        }

		/**
		 * @return the left
		 */
		public boolean isLeft() {
			return left;
		}

		/**
		 * @param left the left to set
		 */
		public void setLeft(boolean left) {
			this.left = left;
		}

		/**
		 * @return the up
		 */
		public boolean isUp() {
			return up;
		}

		/**
		 * @param up the up to set
		 */
		public void setUp(boolean up) {
			this.up = up;
		}

		/**
		 * @return the right
		 */
		public boolean isRight() {
			return right;
		}

		/**
		 * @param right the right to set
		 */
		public void setRight(boolean right) {
			this.right = right;
		}

		/**
		 * @return the down
		 */
		public boolean isDown() {
			return down;
		}

		/**
		 * @param down the down to set
		 */
		public void setDown(boolean down) {
			this.down = down;
		}

		public void resetDirBooleans() {
			left = false;
			right = false;
			up = false;
			down = false;
			running = false;
			sneaking = false;
		}
        
       public void setJump(boolean jump) {
    	   this.jump = jump;
       }
}
