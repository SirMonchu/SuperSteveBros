package proyectoFinal.SuperSteveBros;

import proyectoFinal.SuperSteveBros.View.GamePanel;
import javafx.animation.AnimationTimer;
import proyectoFinal.SuperSteveBros.Imputs.KeyBoardInputs;

public class Game implements Runnable {
	
	private GamePanel gamePanel;
	private KeyBoardInputs keyBoardInputs;
	private Thread gameThread;
	private final int FPS_SET = 120;
	
	public Game() {
		gamePanel = new GamePanel();
		gamePanel.requestFocus();
		keyBoardInputs = new KeyBoardInputs(gamePanel);
		startGameLoop();
	}
	
	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void stopGameLoop() {
		gameThread.stop();
	}
	
	public GamePanel getGamePanel() {
		return this.gamePanel;
	}

	@Override
	public void run() {
		
		double timePerFrame = 1000000000.0 / FPS_SET;
		long lastFrame = System.nanoTime();
		long now = System.nanoTime();
	    int frames = 0;
	    long lastCheck = System.currentTimeMillis();
		
		while (true) {
			
			now = System.nanoTime();
			if (now - lastFrame >= timePerFrame) {
				
				gamePanel.refresh();
				lastFrame = now;
				frames++;
			}
			
			
        	if (System.currentTimeMillis() - lastCheck >=1000) {
        		lastCheck = System.currentTimeMillis();
        		System.out.println("FPS: " + frames);
        		frames = 0;
			
        	}
		
		}
	}
}
