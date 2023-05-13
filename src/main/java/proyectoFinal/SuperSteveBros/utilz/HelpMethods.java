package proyectoFinal.SuperSteveBros.utilz;

import java.awt.geom.Rectangle2D;
import static proyectoFinal.SuperSteveBros.entities.Player.lvlData;
import proyectoFinal.SuperSteveBros.Game;

public class HelpMethods {

	public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
	    int leftTileIndex = (int) (x / Game.TILES_SIZE);
	    int rightTileIndex = (int) ((x + width) / Game.TILES_SIZE);
	    int topTileIndex = (int) (y / Game.TILES_SIZE);
	    int bottomTileIndex = (int) ((y + height) / Game.TILES_SIZE);

	    if (IsSolid(leftTileIndex, topTileIndex, lvlData) ||
	        IsSolid(leftTileIndex, bottomTileIndex, lvlData) ||
	        IsSolid(rightTileIndex, topTileIndex, lvlData) ||
	        IsSolid(rightTileIndex, bottomTileIndex, lvlData)) {
	        return false;
	    }

	    return true;
	}

	private static boolean IsSolid(int tileX, int tileY, int[][] lvlData) {
	    if (tileX < 0 || tileX >= lvlData[0].length) {
	        return true;
	    }

	    if (tileY < 0 || tileY >= lvlData.length) {
	        return true;
	    }

	    if (lvlData[tileY][tileX] >= 48 || lvlData[tileY][tileX] < 0 || lvlData[tileY][tileX] != 11) {
	    	return true;
	    }
	    return false;
	}

	public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
		int currentLeftTile = (int) (hitbox.x / Game.TILES_SIZE);
		int currentRightTile = (int) ((hitbox.x + hitbox.width) / Game.TILES_SIZE);
		if (xSpeed > 0) {
		    // Moving right
		    int nextRightTile = currentRightTile + 1;
		    if (!IsSolid(nextRightTile, (int) hitbox.y / Game.TILES_SIZE, lvlData ) &&
		        !IsSolid(nextRightTile, (int) (hitbox.y + hitbox.height) / Game.TILES_SIZE, lvlData)) {
		        // There is no collision with the tile to the right, so the hitbox can be placed on that tile
		        return (nextRightTile * Game.TILES_SIZE) - hitbox.width;
		    }
		} else if (xSpeed < 0) {
		    // Moving left
		    int nextLeftTile = currentLeftTile - 1;
		    if (!IsSolid(nextLeftTile, (int) hitbox.y / Game.TILES_SIZE, lvlData) &&
		        !IsSolid(nextLeftTile, (int) (hitbox.y + hitbox.height) / Game.TILES_SIZE, lvlData)) {
		        // There is no collision with the tile to the left, so the hitbox can be placed on that tile
		        return nextLeftTile * Game.TILES_SIZE;
		    }
		}

		// No tile available to place the hitbox on, return the current position
		return hitbox.x;
	}

	public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
		int currentTopTile = (int) (hitbox.y / Game.TILES_SIZE);
		int currentBottomTile = (int) ((hitbox.y + hitbox.height) / Game.TILES_SIZE);
		if (airSpeed > 0) {
		    // Falling - touching floor
		    int nextBottomTile = currentBottomTile + 1;
		    if (!IsSolid((int) hitbox.x / Game.TILES_SIZE, nextBottomTile, lvlData) &&
		        !IsSolid((int) (hitbox.x + hitbox.width) / Game.TILES_SIZE, nextBottomTile, lvlData)) {
		        // There is no collision with the tile below, so the hitbox can be placed on that tile
		        return (nextBottomTile * Game.TILES_SIZE) - hitbox.height;
		    }
		} else if (airSpeed < 0) {
		    // Jumping
		    int nextTopTile = currentTopTile - 1;
		    if (!IsSolid((int) hitbox.x / Game.TILES_SIZE, nextTopTile, lvlData) &&
		        !IsSolid((int) (hitbox.x + hitbox.width) / Game.TILES_SIZE, nextTopTile, lvlData)) {
		        // There is no collision with the tile above, so the hitbox can be placed on that tile
		        return nextTopTile * Game.TILES_SIZE;
		    }
		}

		// No tile available to place the hitbox on, return the current position
		return hitbox.y;
	}

	public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
		int bottomTileIndex = (int) ((hitbox.y + hitbox.height) / Game.TILES_SIZE);
		// Check the pixel below bottomleft and bottomright
		if (IsSolid((int) (hitbox.x / Game.TILES_SIZE), bottomTileIndex, lvlData) ||
		    IsSolid((int) ((hitbox.x + hitbox.width) / Game.TILES_SIZE), bottomTileIndex, lvlData)) {
		    return true;
		}

		return false;
	}

}
