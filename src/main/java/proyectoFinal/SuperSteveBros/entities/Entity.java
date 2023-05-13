package proyectoFinal.SuperSteveBros.entities;

import java.awt.geom.Rectangle2D;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import proyectoFinal.SuperSteveBros.View.GamePanel;

public abstract class Entity {
	
	protected float x, y;
	protected int width, height;
	protected Rectangle2D.Float hitBox;
	protected Rectangle fxRect;
	
	public Entity(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	protected void drawHitbox(GamePanel gamePanel) {
//		gc.strokeRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
		fxRect.setX(hitBox.x);
		fxRect.setY(hitBox.y);
		gamePanel.getChildren().remove(fxRect);
		gamePanel.getChildren().add(fxRect);
	}

	protected void iniHitbox(float x, float y, float width, float height) {
		hitBox = new Rectangle2D.Float(x, y, width, height); 
		fxRect = new Rectangle((int) hitBox.x, (int) hitBox.y, (int) hitBox.width, (int) hitBox.height);
		fxRect.setFill(Color.TRANSPARENT);
		fxRect.setStroke(Color.RED);
	}
	
//	protected void updateHitBox() {
//		hitBox.x = (int) x;
//		hitBox.y = (int) y;
//	}
	
	public Rectangle2D.Float getHitbox() {
		return hitBox;
		
	}
	
}
