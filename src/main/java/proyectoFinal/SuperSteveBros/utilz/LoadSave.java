package proyectoFinal.SuperSteveBros.utilz;

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
import proyectoFinal.SuperSteveBros.Game;
import java.awt.Color;

public class LoadSave {
	
	public static final String PLAYER_ATLAS = "SteveAnimated.png";
	public static final String LEVEL_ATLAS = "outside_sprites.png";
	public static final String LEVEL_DATA = "level_one_data.png";
	
	public static BufferedImage GetSpriteAtlas(String fileName) {
		BufferedImage img = null;
        File file = new File("src/main/resources/proyectoFinal/SuperSteveBros/" + fileName);
        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
        	img = ImageIO.read(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return img;
	}
	
	public static Image convertToFxImage(BufferedImage image) {
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
	
	public static int[][] GetLevelData() {
		int[][] lvlData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
		BufferedImage img = GetSpriteAtlas(LEVEL_DATA);

		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getRed();
				if (value >= 48) {
					value = 0;
				}
				lvlData[j][i] = value;
			}
		return lvlData;

	}
}
