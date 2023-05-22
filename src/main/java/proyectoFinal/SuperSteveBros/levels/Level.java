package proyectoFinal.SuperSteveBros.levels;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import proyectoFinal.SuperSteveBros.Game;
import proyectoFinal.SuperSteveBros.entities.Zombie;
import static proyectoFinal.SuperSteveBros.utilz.HelpMethods.GetLevelData;
import static proyectoFinal.SuperSteveBros.utilz.HelpMethods.GetZombies;
import static proyectoFinal.SuperSteveBros.utilz.HelpMethods.GetPlayerSpawn;

public class Level {
	
	private BufferedImage img;
	private int [][] lvlData;
	private ArrayList<Zombie> zombies;
	private int lvlTilesWide;
	private int maxTilesOffset;
	private int maxLvlOffsetX;
	private Point playerSpawn;
	
	public Level(BufferedImage img) {
		this.img = img;
		createLevelData();
		createEnemies();
		calcLvlOffsets();
		calcPlayerSpawn();
	}
	
	private void calcPlayerSpawn() {
		playerSpawn = GetPlayerSpawn(img);
	}

	private void calcLvlOffsets() {
		lvlTilesWide = img.getWidth();
		maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
		maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
	}

	private void createEnemies() {
		zombies = GetZombies(img);
	}

	private void createLevelData() {
		lvlData = GetLevelData(img);
	}

	public int getSpriteIndex(int x, int y) {
		return lvlData[y][x];
	}
	
	public int [][] getLvlData() {
		return lvlData;
	}
	
	public int getLvlOffset() {
		return maxLvlOffsetX;
	}
	
	public ArrayList<Zombie> getZombies() {
		return zombies;
		
	}
	
	public Point getPlayerSpawn() {
		return playerSpawn;
	}
}
