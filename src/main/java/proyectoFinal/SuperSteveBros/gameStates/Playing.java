package proyectoFinal.SuperSteveBros.gameStates;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import proyectoFinal.SuperSteveBros.Game;
import proyectoFinal.SuperSteveBros.Ui.PauseOverlay;
import proyectoFinal.SuperSteveBros.View.GamePanel;
import proyectoFinal.SuperSteveBros.entities.Player;
import proyectoFinal.SuperSteveBros.levels.LevelManager;

public class Playing extends State implements StateMethods{
	
	private Player player;
	private LevelManager levelManager;
	private Scene scene;
	private boolean paused = true;
	private PauseOverlay pauseOverlay;
	
	public Playing(Game game) {
		super(game);
		initClasses();
		
	}
	
	private void initClasses() {
		levelManager = new LevelManager(game);
		player = new Player(200, 200, (int) (36 * Game.SCALE), (int) (36 * Game.SCALE));
		player.loadLvlData(levelManager.getLevel().getLvlData());
		pauseOverlay = new PauseOverlay();
	}

	@Override
	public void update() {
		levelManager.update();
		player.update();
		pauseOverlay.update();
	}

	@Override
	public void draw(Pane root) {
		levelManager.draw(root);
		player.render(root);
		pauseOverlay.draw(root);
	}

	@Override
	public void keyPressed(KeyEvent event) {
		game.getGamePanel().getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                case A:
                	player.setLeft(true);
                	break;
                case D:
                	player.setRight(true);
                	break;
                case CONTROL:
                	player.setSneaking(true);
                   	break;
                case SHIFT:
                	player.setRunning(true);
                    break;
                case SPACE:
                	player.setJump(true);
                  	break;
                case ESCAPE:
                	Gamestate.state = Gamestate.MENU;
                	break;
                }
            }
        });
	}

	@Override
	public void keyReleased(KeyEvent event) {
    	game.getGamePanel().getScene().setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case A:
                    	player.setLeft(false);
                    	break;
                    case D:
                    	player.setRight(false);
                    	break;
                    case CONTROL:
                    	player.setSneaking(false);
                    	break;
                    case SHIFT:
                    	player.setRunning(false);
                        break;
                    case SPACE:
                    	player.setJump(false);
                        break;
                }
            }
        });
	}
	
	public Player getPlayer() {
		return player;
	}

	public void windowFocusLost() {
		player.resetDirBooleans();
	}

	@Override
	public void mousePressed(MouseEvent event) {
		if (paused) {
			pauseOverlay.mousePressed(event);
		}
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		if (paused) {
			pauseOverlay.mouseReleased(event);
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		if (paused) {
			pauseOverlay.mouseMoved(event);
		}
		
	}
}
