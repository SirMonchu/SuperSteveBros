package proyectoFinal.SuperSteveBros.View;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import proyectoFinal.SuperSteveBros.Game;
import proyectoFinal.SuperSteveBros.Imputs.KeyBoardInputs;
import proyectoFinal.SuperSteveBros.Imputs.MouseInputs;

import static proyectoFinal.SuperSteveBros.Game.GAME_HEIGHT;
import static proyectoFinal.SuperSteveBros.Game.GAME_WIDTH;

public class GamePanel extends Pane {

    private KeyBoardInputs keyBoardInputs;
    private MouseInputs mouseInputs;
    private static Scene scene;
    private Game game;

    public GamePanel(Game game) {
        this.game = game;
        scene = new Scene(this, GAME_WIDTH, GAME_HEIGHT, Color.CYAN);
        keyBoardInputs = new KeyBoardInputs(scene, this); // pasar una referencia a esta instancia de GamePanel
        mouseInputs = new MouseInputs(this);
        this.setFocusTraversable(true);
        System.out.println("Size: " + GAME_WIDTH + " x " + GAME_HEIGHT);
    }

    public void updateGame() {

    }

    public void refresh() {
        this.getChildren().clear();
        game.render(this);
    }

    public Game getGame() {
        return game;
    }
}
