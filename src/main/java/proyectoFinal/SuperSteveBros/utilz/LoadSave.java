package proyectoFinal.SuperSteveBros.utilz;

import static proyectoFinal.SuperSteveBros.utilz.Constants.EnemyConstants.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import proyectoFinal.SuperSteveBros.Game;
import proyectoFinal.SuperSteveBros.entities.Zombie;

import java.awt.Color;

public class LoadSave {
	
	public static final String PLAYER_ATLAS = "SteveAnimated.png";
	public static final String LEVEL_ATLAS = "outside_sprites.png";
//	public static final String LEVEL_DATA = "level_one_data.png";
	public static final String LEVEL_DATA = "level_one_data_long.png";
	public static final String MENU_BUTTONS = "button_atlas.png";
	public static final String MENU_BACKGROUND = "menu_background.png";
	public static final String MENU_PAUSE = "pause_menu.png";
	public static final String SOUND_BUTTONS = "sound_button.png";
	public static final String CONTROL_BUTTONS = "urm_buttons.png";
	public static final String VOLUME_BUTTONS = "volume_buttons.png";
	public static final String MENU_BG = "BG.jpg";
	public static final String GAME_BG = "3-bg-full.png";
	public static final String BIG_CLOUDS = "big_clouds.png";
//	public static final String ZOMBIE_ATLAS = "zomibieSprite.png";
	public static final String ZOMBIE_ATLAS = "zomibieSprite1.png";
	public static final String PLAYER_BAR = "health_power_bar.png";
	public static final String GAME_OVER = "game-over.jpg";
	
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
	
	public static ArrayList<Zombie> GetZombies(){
		BufferedImage img = GetSpriteAtlas(LEVEL_DATA);
		ArrayList<Zombie> List = new ArrayList<>();
		
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if (value == ZOMBIE) {
					List.add(new Zombie(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
				}
			}
		return List;
	}
	
	public static ImageView convertToFxImageView(BufferedImage image) {
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
	
	public static int[][] GetLevelData() {
		BufferedImage img = GetSpriteAtlas(LEVEL_DATA);
		int[][] lvlData = new int[img.getHeight()][img.getWidth()];
		
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
