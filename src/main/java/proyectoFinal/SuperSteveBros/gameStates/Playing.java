package proyectoFinal.SuperSteveBros.gameStates;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import proyectoFinal.SuperSteveBros.Game;
import proyectoFinal.SuperSteveBros.Ui.GameOverOverlay;
import proyectoFinal.SuperSteveBros.Ui.PauseOverlay;
import proyectoFinal.SuperSteveBros.entities.EnemyManager;
import proyectoFinal.SuperSteveBros.entities.Player;
import proyectoFinal.SuperSteveBros.levels.LevelManager;
import proyectoFinal.SuperSteveBros.utilz.LoadSave;
import javafx.scene.shape.Rectangle;

public class Playing extends State implements StateMethods{
	
	private Player player;
	private LevelManager levelManager;
	private EnemyManager enemyManager;
	private GameOverOverlay gameOverOverlay;
	private boolean paused = false;
	private PauseOverlay pauseOverlay;
	private int xLvlOffset;
	private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
	private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
	private int lvlTilesWide = LoadSave.GetLevelData()[0].length;
	private int maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
	private int maxLvlOffsetX = maxTilesOffset * Game.TILES_SIZE;
	private Rectangle rectangle;
	private ImageView game_bg;
	private BufferedImage backgroundImg;
	private boolean gameOver;
	
	public Playing(Game game) {
		super(game);
		initClasses();
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.GAME_BG);
		game_bg = LoadSave.convertToFxImageView(backgroundImg);
//		bigCloud = LoadSave.GetSpriteAtlas(LoadSave.BIG_CLOUDS); 
//		big_clouds = LoadSave.convertToFxImageView(bigCloud);
	}
	
	private void initClasses() {
		levelManager = new LevelManager(game);
		enemyManager = new EnemyManager(this);
		player = new Player(200, 200, (int) (36 * Game.SCALE), (int) (36 * Game.SCALE), this);
		player.loadLvlData(levelManager.getLevel().getLvlData());
		pauseOverlay = new PauseOverlay(this);
		gameOverOverlay = new GameOverOverlay(this);
	}

	@Override
	public void update() {
		if (!paused && !gameOver) {
			levelManager.update();
			player.update();
			enemyManager.update(levelManager.getLevel().getLvlData(), player);
			checkCloseToBorder();
		} else {
			pauseOverlay.update();
		}
	}

	private void checkCloseToBorder() {
		int playerX = (int) player.getHitbox().x;
		int diff = playerX - xLvlOffset;
		
		if (diff > rightBorder) {
			xLvlOffset += diff - rightBorder;
		} else if (diff < leftBorder) {
			xLvlOffset += diff - leftBorder;
		}
		if (xLvlOffset > maxLvlOffsetX) {
			xLvlOffset = maxLvlOffsetX;
		} else if (xLvlOffset < 0) {
			xLvlOffset = 0;
		}
	}

	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		enemyManager.checkEnemyHit(attackBox);
	}
	
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	@Override
	public void draw(Pane root) {
		
		// BACKGROUND
		game_bg.setX(0);
		game_bg.setY(0);
		game_bg.setFitWidth(Game.GAME_WIDTH);
		game_bg.setFitHeight(Game.GAME_HEIGHT);
		root.getChildren().remove(game_bg);
		root.getChildren().add(game_bg);

		// IN GAME
		
		levelManager.draw(root, xLvlOffset);
		player.render(root, xLvlOffset);
		enemyManager.draw(root, xLvlOffset);
		root.getChildren().remove(rectangle);
		if (paused) {
			rectangle = new Rectangle(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
	        rectangle.setFill(Color.rgb(0, 0, 0, 0.6));
	        root.getChildren().add(rectangle);
			pauseOverlay.draw(root);
		} else if (gameOver) {
			gameOverOverlay.draw(root);
		}
	}
	

	public void resetAll() {
		gameOver = false;
		paused = false;
		player.resetAll();
		enemyManager.resetAllEnemies();
	}

	@Override
	public void keyPressed(KeyEvent event) {
			game.getGamePanel().getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
	            @Override
	            public void handle(KeyEvent event) {
					if (gameOver) {
						gameOverOverlay.keyPressed(event);
					} else {
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
		                	paused = !paused;
		                	break;
		                }
					}
	            }
	        });
		}

	@Override
	public void keyReleased(KeyEvent event) {
		if (!gameOver) {
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
	}
	
	public Player getPlayer() {
		return player;
	}

	public void windowFocusLost() {
		player.resetDirBooleans();
	}

	@Override
	public void mousePressed(MouseEvent event) {
		if (!gameOver) {
			if (paused) {
				pauseOverlay.mousePressed(event);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		if (!gameOver) {
			if (paused) {
				pauseOverlay.mouseReleased(event);
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		if (!gameOver) {
			if (paused) {
				pauseOverlay.mouseMoved(event);
			}
		}
	}

	public void mouseDragged(MouseEvent event) {
		if (!gameOver) {
			if (paused) {
				pauseOverlay.mouseDragged(event);
			}
		}
	}
	
	public void unPause() {
		paused = false;
	}

}
