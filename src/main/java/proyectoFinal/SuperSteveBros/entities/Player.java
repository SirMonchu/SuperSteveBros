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

import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import proyectoFinal.SuperSteveBros.Game;
import proyectoFinal.SuperSteveBros.gameStates.Playing;
import proyectoFinal.SuperSteveBros.utilz.LoadSave;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Player extends Entity {

    private Image[][] animations;
    private int aniTick, aniIndex, aniSpeed = 11;
    private int playerAction = IDLE;
    private boolean moving = false;
    private boolean running = false;
    private float speed = 1 * Game.SCALE;
    private boolean sneaking = false;
    private BufferedImage img;
    private boolean left, up, right, down, jump;
    public static int [][] lvlData;
    private float xDrawOffset = 10 * Game.SCALE;
    private float yDrawOffset = 4 * Game.SCALE;
    private ImageView imageView;
    private static Thread cuentaThread;
    private static int segundos;
    private Text text;
    private Font customFont;
    private int score;
    
    //JUMPING // GRAVITY
    
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float JumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;
    
    //PLAYER BAR
    
    private BufferedImage playerBarImg;
    private ImageView playerUI;
    private Rectangle fxRect;

	private int statusBarWidth = (int) (192 * Game.SCALE);
	private int statusBarHeight = (int) (58 * Game.SCALE);
	private int statusBarX = (int) (10 * Game.SCALE);
	private int statusBarY = (int) (10 * Game.SCALE);

	private int healthBarWidth = (int) (150 * Game.SCALE);
	private int healthBarHeight = (int) (4 * Game.SCALE);
	private int healthBarXStart = (int) (34 * Game.SCALE);
	private int healthBarYStart = (int) (14 * Game.SCALE);

	private int maxHealth = 10;
	private int currentHealth = maxHealth;
	private int healthWidth = healthBarWidth;
	private Text displayText;

	
	// ATTACK
	
	private Rectangle2D.Float attackBox;
	private Rectangle aRect;
	private boolean attackChecked;
	
	
	private Playing playing;
	private boolean firstMove = true;
    
	public Player(float x, float y, int width, int height, Playing playing) {
		super(x, y, width, height);
		this.playing = playing;
		imageView = new ImageView();
		imageView.setFitWidth(width);
		imageView.setFitHeight(height);
		loadAnimations();
		playerUI = new ImageView();
		playerUI = LoadSave.convertToFxImageView(playerBarImg);
		playerUI.setFitWidth(statusBarWidth);
		playerUI.setFitHeight(statusBarHeight);
		fxRect = new Rectangle(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
		fxRect.setFill(Color.RED);
		iniHitbox(x, y, (int) (17 * Game.SCALE), (int) (29 * Game.SCALE));
		initAttackBox();
		text = new Text();
		customFont = Font.loadFont(getClass().getResourceAsStream("/proyectoFinal/SuperSteveBros/Extrude.ttf"), 70);
		score = 60;
	}
	
	public void setSpawn(Point spawn) {
		this.x = spawn.x;
		this.y = spawn.y;
		hitBox.x = x;
		hitBox.y = y;
	}
	
	private void initAttackBox() {
		attackBox = new Rectangle2D.Float(x, y, (int) 8 * Game.SCALE, (int) 3 * Game.SCALE);
		aRect = new Rectangle(attackBox.x, attackBox.y, attackBox.width, attackBox.height);
		aRect.setFill(Color.TRANSPARENT);
		aRect.setStroke(Color.RED);
	}

	public void update() {
		checkFirstMove();
		updateHealthBar();
		if (currentHealth <= 0) {
			playing.setGameOver(true);
			return;
		}
		updateAttackBox(); 
		updateCharacterOnAnimation();
		updatePos();
		if (isFalling()) {
			checkAttack();
		}
        updateAniTick();
        setAnimation();
	}

	private void checkAttack() {
		if (attackChecked) {
			return;
		}
		attackChecked = true;
		playing.checkEnemyHit(attackBox);
	}

	private void updateAttackBox() {
		attackBox.x = hitBox.x + 9;
		attackBox.y = hitBox.y + hitBox.height - attackBox.height;
	}

	private void updateHealthBar() {
		healthWidth = (int) ((currentHealth / (float) maxHealth) * healthBarWidth);
	}

	public void render(Pane root, int lvlOffset) {
		    imageView.setImage(animations[playerAction][aniIndex]);
		    imageView.setX((int) (hitBox.x - xDrawOffset) - lvlOffset);
		    imageView.setY((int) (hitBox.y - yDrawOffset));
		    root.getChildren().remove(imageView);
		    root.getChildren().add(imageView);
//		    drawHitbox(root, lvlOffset);
//		    drawAttackBox(root, lvlOffset);
		    drawUI(root);
	}
	
    private void drawAttackBox(Pane root, int lvlOffset) {
    	aRect.setX(attackBox.x - lvlOffset);
    	aRect.setY(attackBox.y);
		root.getChildren().remove(aRect);
		root.getChildren().add(aRect);
	}

	private void drawUI(Pane root) {
		
		// PLAYERUI
		
    	playerUI.setX(statusBarX);
    	playerUI.setY(statusBarY);
    	root.getChildren().remove(playerUI);
	    root.getChildren().add(playerUI);
	    
	    // HITBOX
	    
	    fxRect.setWidth(healthWidth);
	    fxRect.setHeight(healthBarHeight);
		root.getChildren().remove(fxRect);
		root.getChildren().add(fxRect);
		
		// TIMER
		
		text.setX(780 * Game.SCALE);
		text.setY(statusBarY * 3.5);
		text.setFont(customFont);
		root.getChildren().remove(text);
		root.getChildren().add(text);
	}
    
    public void changeHealth(int value) {
    	currentHealth += value;
    	
    	if (currentHealth <= 0) {
    		currentHealth = 0;
    		//gameOver();
    	} else if (currentHealth >= maxHealth){
    		currentHealth = maxHealth;
    	}
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
    	playerBarImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_BAR);
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
			yDrawOffset = 4 * Game.SCALE;
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
	
	private void checkFirstMove() {
		if (moving && firstMove && !inAir) {
			reiniciarCuentaAtras(60);
			firstMove = false;
		}
	}
    	
    private void updatePos() {
        moving = false;

        if (jump)
            jump();
        if (!inAir) {
        	if ((!left && !right) || (left && right)) {
        		return;
        	}
        }

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
                    attackChecked = false;
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
        
        public void iniciarCuentaAtras(int duracionSegundos) {
            segundos = duracionSegundos;
            setCuentaThread(new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int i = segundos; i >= 0; i--) {
                        	text.setText(String.valueOf(i));
                        	changeScore(-1);
                            Thread.sleep(1000); // Pausar el hilo durante 1 segundo
                        }
                        playing.setGameOver(true);
                    } catch (InterruptedException e) {
                        System.out.println("Cuenta atrás interrumpida.");
                    }
                }
            }));
            getCuentaThread().start();
        }

        public static void pausarCuentaAtras() {
            if (getCuentaThread() != null && getCuentaThread().isAlive()) {
                getCuentaThread().interrupt();
                System.out.println("Cuenta atrás pausada.");
            }
        }

        public void reanudarCuentaAtras() {
            if (getCuentaThread() != null && !getCuentaThread().isAlive()) {
                iniciarCuentaAtras(segundos);
                System.out.println("Cuenta atrás reanudada.");
            }
        }

        public void reiniciarCuentaAtras(int duracionSegundos) {
            pausarCuentaAtras();
            segundos = duracionSegundos;
            score = 60;
            iniciarCuentaAtras(segundos);
            System.out.println("Cuenta atrás reiniciada a " + duracionSegundos + " segundos.");
        }
        
        public boolean isFalling() {
            return inAir && airSpeed > 0;
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

	public void resetAll() {
		resetDirBooleans();
		inAir = false;
		moving = false;
		playerAction = IDLE;
		currentHealth = maxHealth;
		firstMove = true;
		
		hitBox.x = x;
		hitBox.y = y;
		
		if (!IsEntityOnFloor(hitBox, lvlData))
            inAir = true;
	}

	public static Thread getCuentaThread() {
		return cuentaThread;
	}

	public static void setCuentaThread(Thread cuentaThread) {
		Player.cuentaThread = cuentaThread;
	}
	
    public void changeScore(int value) {
        score += value;
    } 
    
    public int getScore() {
    	return this.score;
    }
    
    public void resetScore() {
    	score = 60;
    }
	
}
