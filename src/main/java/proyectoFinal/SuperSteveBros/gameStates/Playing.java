package proyectoFinal.SuperSteveBros.gameStates;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.sql.SQLException;

import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import proyectoFinal.SuperSteveBros.Game;
import proyectoFinal.SuperSteveBros.FXMLcontrollers.LoginController;
import proyectoFinal.SuperSteveBros.Ui.GameOverOverlay;
import proyectoFinal.SuperSteveBros.Ui.LevelCompletedOverlay;
import proyectoFinal.SuperSteveBros.Ui.PauseOverlay;
import proyectoFinal.SuperSteveBros.dao.ScoreDAO;
import proyectoFinal.SuperSteveBros.entities.EnemyManager;
import proyectoFinal.SuperSteveBros.entities.Player;
import proyectoFinal.SuperSteveBros.levels.LevelManager;
import proyectoFinal.SuperSteveBros.model.Score;
import proyectoFinal.SuperSteveBros.utils.ConnectionData;
import proyectoFinal.SuperSteveBros.model.Level;
import proyectoFinal.SuperSteveBros.utilz.LoadSave;
import javafx.scene.shape.Rectangle;

public class Playing extends State implements StateMethods{
	
	private Player player;
	private LevelManager levelManager;
	private EnemyManager enemyManager;
	private GameOverOverlay gameOverOverlay;
	private LevelCompletedOverlay levlCompletedOverlay;
	private boolean paused = false;
	private PauseOverlay pauseOverlay;
	private int xLvlOffset;
	private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
	private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
	private int maxLvlOffsetX;
	private Rectangle rectangle;
	private ImageView game_bg;
	private BufferedImage backgroundImg;
	private boolean gameOver;
	private boolean lvlCompleted = false;
	ScoreDAO scoreDao;
	ConnectionData connectionData;
	private proyectoFinal.SuperSteveBros.model.Player currentPlayer;
	
	public Playing(Game game) {
		super(game);
		initClasses();
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.GAME_BG);
		game_bg = LoadSave.convertToFxImageView(backgroundImg);
		calcLvlOffset();
		loadStartLevel();
		connectionData = new ConnectionData("jdbc:mysql://localhost:3306", "superstevebros", "root", "");
		scoreDao = new ScoreDAO(connectionData);
	}
	
	public void loadNextLevel() {
		resetAll();
		levelManager.loadNextLevel();
		player.setSpawn(levelManager.getLevel().getPlayerSpawn());
	}
	
	private void loadStartLevel() {
		enemyManager.loadEnemies(levelManager.getLevel());
	}

	private void calcLvlOffset() {
		maxLvlOffsetX = levelManager.getLevel().getLvlOffset();
	}

	private void initClasses() {
		levelManager = new LevelManager(game);
		enemyManager = new EnemyManager(this);
		
		player = new Player(200, 200, (int) (36 * Game.SCALE), (int) (36 * Game.SCALE), this);
		player.loadLvlData(levelManager.getLevel().getLvlData());
		player.setSpawn(levelManager.getLevel().getPlayerSpawn());
		
		pauseOverlay = new PauseOverlay(this);
		gameOverOverlay = new GameOverOverlay(this);
		levlCompletedOverlay = new LevelCompletedOverlay(this);
	}

	@Override
	public void update() {
		
		if (paused) {
			pauseOverlay.update();
		} else if (lvlCompleted) {
			levlCompletedOverlay.update();
		} else if (!gameOver){
			levelManager.update();
			player.update();
			enemyManager.update(levelManager.getLevel().getLvlData(), player);
			checkCloseToBorder();
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
		} else if (lvlCompleted) {
			levlCompletedOverlay.draw(root);
		}
	}
	

	public void resetAll() {
		gameOver = false;
		paused = false;
		lvlCompleted = false;
		player.resetAll();
		enemyManager.resetAllEnemies();
		player.reanudarCuentaAtras();
		player.reiniciarCuentaAtras(60);
	}
	public void setMaxLvlOffset(int lvlOffset) {
		this.maxLvlOffsetX = lvlOffset;
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
		                	player.pausarCuentaAtras();
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
			} else if (lvlCompleted) {
				levlCompletedOverlay.mousePressed(event);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		if (!gameOver) {
			if (paused) {
				pauseOverlay.mouseReleased(event);
			} else if (lvlCompleted) {
				levlCompletedOverlay.mouseReleased(event);
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		if (!gameOver) {
			if (paused) {
				pauseOverlay.mouseMoved(event);
			} else if (lvlCompleted) {
				levlCompletedOverlay.mouseMoved(event);
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
		player.reanudarCuentaAtras();
	}
	
	public EnemyManager getEnemyManager() {
		return enemyManager;
		
	}
	
	public boolean isLvlCompleted() {
        return lvlCompleted;
    }	

	public void setLevelCompleted(boolean lvlCompleted) {
		this.lvlCompleted = lvlCompleted;
		if (lvlCompleted) {
			player.pausarCuentaAtras();
            saveScore();
            player.resetScore();
        }
	}
	
	private void saveScore() {
		Level level = new Level(levelManager.getLevelId(), levelManager.getLevelName(), levelManager.getLevelDifficulty(), 60);
		currentPlayer = game.getPlayer();
		Score score = new Score(currentPlayer, level, player.getScore());
		if (currentPlayer == null) {
			System.out.println("Es null");
		} else {
			System.out.println(currentPlayer.toString());
		}
		try {
			scoreDao.save(score);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
