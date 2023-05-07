package proyectoFinal.SuperSteveBros.View;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import proyectoFinal.SuperSteveBros.Imputs.KeyBoardListener;

public class GamePanel extends Pane {
    private static final double W = 1920, H = 1080;
    private BufferedImage img;
    private ImageView hero;
    private BufferedImage[] moveAni;
    private KeyBoardListener keyBoardListener;

    public GamePanel() {
    	inport();
    	loadAnimations();
//    	this.hero = convertToFxImage(imegeCrop(img));
    	int i = 0;
    	do {
    		this.hero = convertToFxImage(moveAni[i]);
    		i++;
		} while (keyBoardListener.isGoEast());
//    	this.hero = convertToFxImage(moveAni[0]);
        getChildren().add(hero);
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
    	File file = new File("C:\\Users\\ramon\\eclipse-workspace\\SuperSteveBros\\src\\main\\resources\\proyectoFinal\\SuperSteveBros\\Stevemvm.png"); 
    	InputStream is = null;
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			this.img = ImageIO.read(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public ImageView importImg() {
    	File file = new File("C:\\Users\\ramon\\eclipse-workspace\\SuperSteveBros\\src\\main\\resources\\proyectoFinal\\SuperSteveBros\\Stevemvm.png");
    	Image image = new Image(file.toURI().toString());
    	ImageView imageView = new ImageView(image);
		return imageView;
    }
  
    private void loadAnimations() {
    	moveAni = new BufferedImage[7];
    	for (int i = 0; i < moveAni.length; i++) {
    		moveAni[i] = img.getSubimage(i*112, 0, 112, 139);
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