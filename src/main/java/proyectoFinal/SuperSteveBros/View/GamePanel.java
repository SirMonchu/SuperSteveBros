package proyectoFinal.SuperSteveBros.View;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import proyectoFinal.SuperSteveBros.Imputs.KeyBoardInputs;
import static proyectoFinal.SuperSteveBros.utilz.Constants.PlayerConstants.*;
import static proyectoFinal.SuperSteveBros.utilz.Constants.Directions.*;

public class GamePanel extends Pane {

    private static final double W = 1280, H = 800;
    private Canvas canvas;
    private KeyBoardInputs keyBoardInputs;
    private int cx = 0, cy = 0;
    private static Scene scene;
    private BufferedImage img;
    private Image[][] animations;
    private int aniTick, aniIndex, aniSpeed = 11;
    private int playerAction = IDLE;
    private int playerDir = -1;
    private boolean moving = false;
    private boolean running = false;
    private double speed = 5;
    private boolean sneaking = false;


    public GamePanel() {
        inport();
        loadAnimations();
        canvas = new Canvas(W, H);
        scene = new Scene(this, W, H, Color.CYAN);
        this.getChildren().add(canvas);
        keyBoardInputs = new KeyBoardInputs(this); // pasar una referencia a esta instancia de GamePanel
        this.setFocusTraversable(true);
    }

    private void loadAnimations() {
        this.animations = new Image[8][11];

        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                BufferedImage subimage = img.getSubimage(i*181, j*200, 181, 200);
                animations[j][i] = convertToFxImage(subimage);
            }
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
		if (moving && playerDir == RIGHT && running == false && sneaking == false) {
			playerAction = WALKING_RIGHT;
		} else if (moving && playerDir == LEFT && running == false && sneaking == false){
			playerAction = WALKING_LEFT;
		} else if (moving && playerDir == RIGHT && running) {
			playerAction = RUNNING_RIGHT;
		} else if (moving && playerDir == LEFT && running) {	
			playerAction = RUNNING_LEFT;	
		} else if (moving && playerDir == RIGHT && sneaking) {
			playerAction = SNEAKING_RIGHT;
		} else if (moving && playerDir == LEFT && sneaking) {
			playerAction = SNEAKING_LEFT;
		} else {
			playerAction = IDLE;
		}
		
	}
    
	
    private void updatePos() {
        if (moving) {
        	if (running) {
        		speed = 2;
        	} else if (sneaking) {
        		speed = 0.5;
        	} else {
        		speed = 1;
        	}
            switch (playerDir) {
                case LEFT:
                    cx -= 5 * speed;
                    break;
                case UP:
                    cy -= 5 * speed;
                    break;
                case RIGHT:
                    cx += 5 * speed;
                    break;
                case DOWN:
                    cy += 5 * speed;
                    break;
            }
        }
    }


    public void refresh() {
    	canvas.getGraphicsContext2D().clearRect(0, 0, W, H);
        updateAniTick();
        setAnimation();
        updatePos();
        canvas.getGraphicsContext2D().drawImage(animations[playerAction][aniIndex], cx, cy, img.getWidth() / 15, img.getHeight() / 11);
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
	
    private void inport() {
        File file = new File("src/main/resources/proyectoFinal/SuperSteveBros/SteveAnimated.png");
        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            this.img = ImageIO.read(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDirection(int direction) {
    	this.playerDir = direction;
    	moving = true;
    }
    
    public void setMoving(boolean moving) {
    	this.moving = moving;
    }
    
    public void setRunning(boolean running) {
    	this.running = running;
    }
    
    public void setSneaking(boolean sneaking) {
    	this.sneaking = sneaking;
    }
}

