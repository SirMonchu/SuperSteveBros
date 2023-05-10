package proyectoFinal.SuperSteveBros;

import proyectoFinal.SuperSteveBros.View.GamePanel;
import proyectoFinal.SuperSteveBros.entities.Player;
import javafx.scene.canvas.Canvas;
import proyectoFinal.SuperSteveBros.Imputs.KeyBoardInputs;

public class Game implements Runnable {
	
	private GamePanel gamePanel;
	private KeyBoardInputs keyBoardInputs;
	private Thread gameThread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;
	
	private Player player;
	
	public Game() {
		initClasses();
		gamePanel = new GamePanel(this);
		gamePanel.requestFocus();
		keyBoardInputs = new KeyBoardInputs(gamePanel);
		startGameLoop();
	}
	
	private void initClasses() {
		player = new Player(200, 200);
		
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
	
	public void update() {
		player.update();
	}
	
	public void render(Canvas canvas) {
		player.render(canvas);
	}

	@Override
	public void run() {
		
		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;
	    int frames = 0;
	    long lastCheck = System.currentTimeMillis();
	    int updates = 0;
	    long previousTime = System.nanoTime();
	    double deltaU = 0;
	    double deltaF = 0;
		
		while (true) {

			long currentTime = System.nanoTime();
			
			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;
			
			if (deltaU >= 1) {
				update();
				updates++;
				deltaU--;
			}
			
			if (deltaF >= 1) {
				gamePanel.refresh();
				frames++;
				deltaF--;
			}
						
        	if (System.currentTimeMillis() - lastCheck >=1000) {
        		lastCheck = System.currentTimeMillis();
        		System.out.println("FPS: " + frames + " || " + "UPS: " + updates);
        		frames = 0;
        		updates = 0;
        	}
		
		}
	}
	
	public Player getPlayer() {
		return player;
	}

	public void windowFocusLost() {
		player.resetDirBooleans();
	}
}
