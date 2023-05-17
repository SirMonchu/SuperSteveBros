package proyectoFinal.SuperSteveBros.Ui;

import java.awt.Rectangle;

public class PauseButton {
	
	protected int xPos, yPos, widht, height;
	protected Rectangle bounds;

	
	public PauseButton(int xPos, int yPos, int widht, int height) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.widht = widht;
		this.height = height;
		createBounds();
	}

	private void createBounds() {
		bounds = new Rectangle(xPos, yPos, widht, height);
	}

	/**
	 * @return the xPos
	 */
	public int getxPos() {
		return xPos;
	}

	/**
	 * @param xPos the xPos to set
	 */
	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	/**
	 * @return the yPos
	 */
	public int getyPos() {
		return yPos;
	}

	/**
	 * @param yPos the yPos to set
	 */
	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	/**
	 * @return the widht
	 */
	public int getWidht() {
		return widht;
	}

	/**
	 * @param widht the widht to set
	 */
	public void setWidht(int widht) {
		this.widht = widht;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the bounds
	 */
	public Rectangle getBounds() {
		return bounds;
	}

	/**
	 * @param bounds the bounds to set
	 */
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	
}
