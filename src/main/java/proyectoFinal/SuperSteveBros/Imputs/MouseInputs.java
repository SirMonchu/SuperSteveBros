package proyectoFinal.SuperSteveBros.Imputs;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import proyectoFinal.SuperSteveBros.View.GamePanel;
import proyectoFinal.SuperSteveBros.gameStates.Gamestate;

public class MouseInputs {

	private GamePanel gamePanel;
    private double mouseX, mouseY;
    private static boolean mousePressed;
	private boolean mouseReleased;
	private boolean mouseClicked;
	private boolean mouseEntered;
	private boolean mouseExited;

    public MouseInputs(GamePanel gamePanel) {
    	this.gamePanel = gamePanel;
        addEventHandlers();
    }

    public static boolean isMousePressed() {
        return mousePressed;
    }

    public boolean isMouseReleased() {
        return mouseReleased;
    }

    public boolean isMouseClicked() {
        return mouseClicked;
    }

    public boolean isMouseEntered() {
        return mouseEntered;
    }

    public boolean isMouseExited() {
        return mouseExited;
    }

    public double getMouseX() {
        return mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    public void reset() {
        mousePressed = false;
        mouseReleased = false;
        mouseClicked = false;
        mouseEntered = false;
        mouseExited = false;
    }

    private void addEventHandlers() {
        gamePanel.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	switch (Gamestate.state) {
				case MENU:
					gamePanel.getGame().getMenu().mousePressed(event);
					break;
				case PLAYING:
					gamePanel.getGame().getPlaying().mousePressed(event);
					break;
				default:
					break;
				}
            }
        });

        gamePanel.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	switch (Gamestate.state) {
				case MENU:
					gamePanel.getGame().getMenu().mouseReleased(event);
					break;
				case PLAYING:
					gamePanel.getGame().getPlaying().mouseReleased(event);
					break;
				default:
					break;
				}
            }
        });

        gamePanel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseClicked = true;
                mouseX = event.getX();
                mouseY = event.getY();
            }
        });

        gamePanel.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseEntered = true;
            }
        });

        gamePanel.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseExited = true;
            }
        });
        
        gamePanel.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	switch (Gamestate.state) {
				case MENU:
					gamePanel.getGame().getMenu().mouseMoved(event);
					break;
				case PLAYING:
					gamePanel.getGame().getPlaying().mouseMoved(event);
					break;
				default:
					break;
				}
            }
        });
        
        gamePanel.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				switch (Gamestate.state) {
				case PLAYING:
					gamePanel.getGame().getPlaying().mouseDragged(event);
					break;
				default:
					break;
				}
			}
        });
    }
}

