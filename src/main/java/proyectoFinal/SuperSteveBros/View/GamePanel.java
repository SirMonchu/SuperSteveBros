package proyectoFinal.SuperSteveBros.View;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import proyectoFinal.SuperSteveBros.Game;
import proyectoFinal.SuperSteveBros.Imputs.KeyBoardInputs;
import static proyectoFinal.SuperSteveBros.utilz.Constants.PlayerConstants.*;
import static proyectoFinal.SuperSteveBros.utilz.Constants.Directions.*;

public class GamePanel extends Pane {

    private static final double W = 1280, H = 800;
    private Canvas canvas;
    private KeyBoardInputs keyBoardInputs;
    private static Scene scene;
    private Game game;

    public GamePanel(Game game) {
    	this.game = game;
        canvas = new Canvas(W, H);
        scene = new Scene(this, W, H, Color.CYAN);
        this.getChildren().add(canvas);
        keyBoardInputs = new KeyBoardInputs(this); // pasar una referencia a esta instancia de GamePanel
        this.setFocusTraversable(true);
    }

    
    public void updateGame() {

    }


    public void refresh() {
    	game.render(this.canvas);
	}
    
    public Game getGame() {
    	return game;
    }
}

