package proyectoFinal.SuperSteveBros.controllers;


import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;
import proyectoFinal.SuperSteveBros.Imputs.KeyBoardListener;
import proyectoFinal.SuperSteveBros.View.GamePanel;

public class MovementController {
    private static final int HERO_STEP = 2;
    private final ImageView hero;
    private final KeyBoardListener keyBoardListener;
    private int frames;
    private long lastCheck = 0;

    public MovementController(ImageView hero, KeyBoardListener keyBoardListener) {
        this.hero = hero;
        this.keyBoardListener = keyBoardListener;
    }

    public void moverHeroe() {

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle (long now) {
                int dx = 0, dy = 0;

                if (keyBoardListener.isGoNorth()) dy -= 10;
                if (keyBoardListener.isGoSouth()) dy += 10;
                if (keyBoardListener.isGoEast()) dx += 10;
                if (keyBoardListener.isGoWest()) dx -= 10;
                if (keyBoardListener.isRunning()) {
                    dx *= HERO_STEP;
                    dy *= HERO_STEP;
                }
            	frames++;
            	if (System.currentTimeMillis() - lastCheck >=1000) {
            		lastCheck = System.currentTimeMillis();
            		System.out.println("FPS: " + frames);
            		frames = 0;
            	}
                moveHeroBy(dx, dy);
            }
        };
        timer.start();
    }

    private void moveHeroBy(int dx, int dy) {
        GamePanel gamePanel = (GamePanel) hero.getParent();
        gamePanel.moveHeroBy(dx, dy);
    }
}