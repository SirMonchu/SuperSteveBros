package proyectoFinal.SuperSteveBros.utilz;

import static proyectoFinal.SuperSteveBros.utilz.Constants.EnemyConstants.ZOMBIE;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javafx.scene.image.ImageView;
import proyectoFinal.SuperSteveBros.Game;
import proyectoFinal.SuperSteveBros.entities.Zombie;

public class HelpMethods {
	public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
		if (!IsSolid(x, y, lvlData))
			if (!IsSolid(x + width, y + height, lvlData))
				if (!IsSolid(x + width, y, lvlData))
					if (!IsSolid(x, y + height, lvlData))
						return true;
		return false;
	}

	private static boolean IsSolid(float x, float y, int[][] lvlData) {
		int maxWidht = lvlData[0].length * Game.TILES_SIZE; 
		if (x < 0 || x >= maxWidht)
			return true;
		if (y < 0 || y >= Game.GAME_HEIGHT)
			return true;

		float xIndex = x / Game.TILES_SIZE;
		float yIndex = y / Game.TILES_SIZE;
		
		return IsTileSolid((int) xIndex, (int) yIndex, lvlData);
	}
	
	private static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData) {
		int value = lvlData[yTile][xTile];

		if (value >= 48 || value < 0 || value != 11)
			return true;
		return false;
	}

	public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
		int currentTile = (int) (hitbox.x / Game.TILES_SIZE);
		if (xSpeed > 0) {
			// Right
			int tileXPos = currentTile * Game.TILES_SIZE;
			int xOffset = (int) (Game.TILES_SIZE - hitbox.width);
			return tileXPos + xOffset - 1;
		} else
			// Left
			return currentTile * Game.TILES_SIZE;
	}

	public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
		int currentTile = (int) (hitbox.y / Game.TILES_SIZE);
		if (airSpeed > 0) {
			// Falling - touching floor
			int tileYPos = currentTile * Game.TILES_SIZE;
			int yOffset = (int) (Game.TILES_SIZE - hitbox.height);
			return tileYPos + yOffset - 1;
		} else
			// Jumping
			return currentTile * Game.TILES_SIZE;

	}

	public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
		// Check the pixel below bottomleft and bottomright
		if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
			if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
				return false;

		return true;

	}
	
	public static boolean isFloor(Rectangle2D.Float hitBox, float xSpeed, int[][] lvlData) {
		if (xSpeed > 0)
			return IsSolid(hitBox.x + hitBox.width + xSpeed, hitBox.y + hitBox.height + 1, lvlData);
		else
			return IsSolid(hitBox.x + xSpeed, hitBox.y + hitBox.height + 1, lvlData);
	}
	
	public static boolean IsAllTileWalkable(int xSart, int xEnd, int y, int[][] lvlData) {
		for (int i = 0; i < xEnd - xSart; i++) {
			if (IsTileSolid(xSart + i , y, lvlData)) {
				return false;
			}
			if (!IsTileSolid(xSart + i , y + 1, lvlData)) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean IsSightClear(int[][] lvlData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int yTile) {
		
		int firstXTile = (int) (firstHitbox.x / Game.TILES_SIZE);
		int secondXTile = (int) (secondHitbox.x / Game.TILES_SIZE);
		
		if (firstXTile > secondXTile) {
			return IsAllTileWalkable(secondXTile, firstXTile, yTile, lvlData);
		} else {
			return IsAllTileWalkable(firstXTile, secondXTile, yTile, lvlData);
		}
	}
	
	public static int[][] GetLevelData(BufferedImage img) {
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
	
	public static ArrayList<Zombie> GetZombies(BufferedImage img){
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
	
	public static Point GetPlayerSpawn(BufferedImage img) {
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if (value == 100) {
					return new Point(i * Game.TILES_SIZE, j * Game.TILES_SIZE);
				}
			}
		return new Point(1 * Game.TILES_SIZE, 1 * Game.TILES_SIZE);
	}
}
