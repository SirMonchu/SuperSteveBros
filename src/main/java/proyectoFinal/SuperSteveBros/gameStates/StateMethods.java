package proyectoFinal.SuperSteveBros.gameStates;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import proyectoFinal.SuperSteveBros.View.GamePanel;

public interface StateMethods {
	public void update();
	public void draw(Pane root);
	public void keyPressed(KeyEvent event);
	public void keyReleased(KeyEvent event);
	public void mousePressed(MouseEvent event);
	public void mouseReleased(MouseEvent event);
	public void mouseMoved(MouseEvent event);
}
