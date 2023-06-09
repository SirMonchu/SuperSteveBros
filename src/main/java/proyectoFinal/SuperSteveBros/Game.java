package proyectoFinal.SuperSteveBros;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import proyectoFinal.SuperSteveBros.Imputs.KeyBoardInputs;
import proyectoFinal.SuperSteveBros.View.GamePanel;
import proyectoFinal.SuperSteveBros.entities.Player;
import proyectoFinal.SuperSteveBros.gameStates.Gamestate;
import proyectoFinal.SuperSteveBros.gameStates.Menu;
import proyectoFinal.SuperSteveBros.gameStates.Options;
import proyectoFinal.SuperSteveBros.gameStates.Playing;
import javafx.stage.Stage;

public class Game implements Runnable {
	
	private GamePanel gamePanel;
	private KeyBoardInputs keyBoardInputs;
	private Thread gameThread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;

	private Playing playing;
	private Menu menu;
	private Options options;
	proyectoFinal.SuperSteveBros.model.Player player;
	private Stage stage;
	
	public final static int TILES_DEFAULT_SIZE = 32;
	public final static float SCALE = 2f;
	public final static int TILES_IN_WIDTH = 26;
	public final static int TILES_IN_HEIGHT = 14;
	public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
	
	public Game(proyectoFinal.SuperSteveBros.model.Player player, Stage stage) {
		this.player = player;
		this.stage = stage;
		initClasses();
		gamePanel = new GamePanel(this);
		gamePanel.requestFocus();
		startGameLoop();
	}
	
	private void initClasses() {
		menu = new Menu(this);
		playing = new Playing(this);
		options = new Options(this);
	}

	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void stopGameLoop() {
	    Platform.runLater(() -> {
			stage.close();
	    });
		gameThread.stop();
		if (Player.getCuentaThread() != null) {
			Player.getCuentaThread().stop();
		} 
		Gamestate.state = Gamestate.MENU;	
	}
	
	public GamePanel getGamePanel() {
		return this.gamePanel;
	}
	
	public void update() {
		switch (Gamestate.state) {
		case MENU:
			menu.update();
			break;
		case PLAYING:
			playing.update();
			break;
		case OPTIONS:
			options.update();
			break;
		case QUIT:
		default:
			stopGameLoop();
			break;
		}
	}
	
	public void render(Pane root) {
		switch (Gamestate.state) {
		case MENU:
			menu.draw(root);
			break;
		case PLAYING:
			playing.draw(root);
			break;
		case OPTIONS:
			options.draw(root);
			break;
		default:
			break;
		}
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
				Platform.runLater(() -> gamePanel.refresh());
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
	
	public void windowFocusLost() {
		if (Gamestate.state == Gamestate.PLAYING) {
			playing.getPlayer().resetDirBooleans();
		}
	}
	
	public Menu getMenu() {
		return menu;
	}
	
	public Playing getPlaying() {
		return playing;
	}
	
	public Options getOptions() {
		return options;
	}
	
	public proyectoFinal.SuperSteveBros.model.Player getPlayer() {
		return this.player;
		
	}
}
