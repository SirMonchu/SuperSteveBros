package proyectoFinal.SuperSteveBros.entities;

import static proyectoFinal.SuperSteveBros.utilz.Constants.Directions.DOWN;
import static proyectoFinal.SuperSteveBros.utilz.Constants.Directions.LEFT;
import static proyectoFinal.SuperSteveBros.utilz.Constants.Directions.RIGHT;
import static proyectoFinal.SuperSteveBros.utilz.Constants.Directions.UP;
import static proyectoFinal.SuperSteveBros.utilz.Constants.PlayerConstants.GetSpriteAmount;
import static proyectoFinal.SuperSteveBros.utilz.Constants.PlayerConstants.IDLE;
import static proyectoFinal.SuperSteveBros.utilz.Constants.PlayerConstants.RUNNING_LEFT;
import static proyectoFinal.SuperSteveBros.utilz.Constants.PlayerConstants.RUNNING_RIGHT;
import static proyectoFinal.SuperSteveBros.utilz.Constants.PlayerConstants.SNEAKING_LEFT;
import static proyectoFinal.SuperSteveBros.utilz.Constants.PlayerConstants.SNEAKING_RIGHT;
import static proyectoFinal.SuperSteveBros.utilz.Constants.PlayerConstants.WALKING_LEFT;
import static proyectoFinal.SuperSteveBros.utilz.Constants.PlayerConstants.WALKING_RIGHT;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class Player extends Entity {

    private Image[][] animations;
    private int aniTick, aniIndex, aniSpeed = 11;
    private int playerAction = IDLE;
    private boolean moving = false;
    private boolean running = false;
    private double speed = 3;
    private boolean sneaking = false;
    private BufferedImage img;
    private boolean left, up, right, down;
	
	public Player(float x, float y) {
		super(x, y);
		loadAnimations();
	}
	
	public void update() {
        updateAniTick();
        setAnimation();
        updatePos();
	}
	
	public void render(Canvas canvas) {
    	canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        canvas.getGraphicsContext2D().drawImage(animations[playerAction][aniIndex], x, y, img.getWidth() / 15, img.getHeight() / 11);
	}
	
    private void loadAnimations() {
    	
        File file = new File("src/main/resources/proyectoFinal/SuperSteveBros/SteveAnimated.png");
        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
        	img = ImageIO.read(is);
            
            animations = new Image[8][11];
            for (int j = 0; j < animations.length; j++) {
                for (int i = 0; i < animations[j].length; i++) {
                    BufferedImage subimage = img.getSubimage(i*181, j*200, 181, 200);
                    animations[j][i] = convertToFxImage(subimage);
                }
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    	private static Image convertToFxImage(BufferedImage image) {
            WritableImage wr = null;
            if (image != null) {
                wr = new WritableImage(image.getWidth(), image.getHeight());
                PixelWriter pw = wr.getPixelWriter();
                for (int x = 0; x < image.getWidth(); x++) {
                    for (int y = 0; y < image.getHeight(); y++) {
                        pw.setArgb(x, y, image.getRGB(x, y));
                    }
                }
            }

            return new ImageView(wr).getImage();
        }
    	
        private void updatePos() {
        	
        	moving = false;
        	
            	if (running) {
            		speed = 2;
            	} else if (sneaking) {
            		speed = 0.5;
            	} else {
            		speed = 1;
            	}
            	if (left && !right) {
            		x -= 3 * speed;
            		moving = true;
            	} else if (right && !left) {
            		x += 3 * speed;
            		moving = true;
            	}
            	if (up && !down) {
            		y -= 3 * speed;
            		moving = true;
            	} else if (down && !up) {
            		y += 3 * speed;
            		moving = true;
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
        
        
}
