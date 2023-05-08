package proyectoFinal.SuperSteveBros.View;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import static proyectoFinal.SuperSteveBros.utils.Contants.PlayerConstants.*;
import static proyectoFinal.SuperSteveBros.utils.Contants.Directions.*;

public class GamePanel extends Pane {
    private static final double W = 1920, H = 1080;
    private BufferedImage img;
    private ImageView hero;
    private BufferedImage[] [] animations;
    private int aniTick, aniIndex, aniSpeed = 5;
    private int playerAction;
    private int playerDir = -1;
    private boolean moving = false;
    private boolean moving_right = false;
    private boolean moving_left = false;

    public GamePanel() {
        inport();
        loadAnimations();
        this.hero = convertToFxImage(animations[playerAction][aniIndex]);
//        this.hero = convertToFxImage(animations [1][5]);
        getChildren().add(hero);

        // Crear una animación para actualizar la imagen del héroe
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000/60), e -> {
            updateAniTick();
            setAnimation();
            hero.setImage(convertToFxImage(animations[playerAction][aniIndex]).getImage());
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        
    }
    
    private void setAnimation() {
    	
    	if (moving_right) {
    		playerAction = RUNNING_RIGHT;
    	} else {
    		playerAction = RUNNING_LEFT;
    	}
    	
    }
    
    public void setDirection(int direction) {
        this.playerDir = direction;
        moving = true;
        if (playerDir == RIGHT) {
            moving_right = true;
            moving_left = false;
        } else if (playerDir == LEFT) {
            moving_left = true;
            moving_right = false;
        }
        if (moving_right) {
            playerAction = RUNNING_RIGHT;
        } else if (moving_left) {
            playerAction = RUNNING_LEFT;
        } else {
  //          playerAction = IDLE;
        }
    }
    
    public void setMoving(boolean moving) {
        this.moving = moving;
        moving_right = false;
        moving_left = false;
        if (moving && playerDir == RIGHT) {
            moving_right = true;
            playerAction = RUNNING_RIGHT;
        } else if (moving && playerDir == LEFT) {
            moving_left = true;
            playerAction = RUNNING_LEFT;
        } else if (!moving) {
      //      playerAction = IDLE;
        }
    }
    
	public void Setmoving_left(boolean moving_left) {
		this.moving_left = moving_left;	
	}

	public void Setmoving_right(boolean moving_right) {
		this.moving_right = moving_right;
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

    private static ImageView convertToFxImage(BufferedImage image) {
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

        return new ImageView(wr);
    }

    private BufferedImage imegeCrop(BufferedImage image) {
        BufferedImage img = image.getSubimage(0, 0, 120, 120);
        return img;
    }

    private void inport() {
        File file = new File("src/main/resources/proyectoFinal/SuperSteveBros/Stevemvm.png");
        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            this.img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ImageView importImg() {
        File file = new File("src/main/resources/proyectoFinal/SuperSteveBros/Stevemvm.png");
        Image image = new Image(file.toURI().toString());
        ImageView imageView = new ImageView(image);
        return imageView;
    }

    private void loadAnimations() {
        animations = new BufferedImage[2][7];
        
        for (int j = 0; j < animations.length; j++) {
	        for (int i = 0; i < animations[j].length; i++) {
	            animations[j][i] = img.getSubimage(i*112, j*139, 112, 139);
	        }
        }    
    }

    public void dibujar() {
        moveHeroTo(W / 2, H / 2);
        setPrefSize(W, H);
    }

    public void moveHeroBy(int dx, int dy) {
        if (dx == 0 && dy == 0) return;

        final double cx = hero.getBoundsInLocal().getWidth() / 2;
        final double cy = hero.getBoundsInLocal().getHeight() / 2;

        double x = cx + hero.getLayoutX() + dx;
        double y = cy + hero.getLayoutY() + dy;

        moveHeroTo(x, y);
    }

    private void moveHeroTo(double x, double y) {
        final double cx = hero.getBoundsInLocal().getWidth() / 2;
        final double cy = hero.getBoundsInLocal().getHeight() / 2;

        if (x - cx >= 0 &&
                x + cx <= W &&
                y - cy >= 0 &&
                y + cy <= H) {
            hero.relocate(x - cx, y - cy);
        }
    }

    public ImageView getHero() {
        return hero;
    }


}