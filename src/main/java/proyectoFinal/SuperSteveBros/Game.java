package proyectoFinal.SuperSteveBros;

public class Game {

    private GameWindow gameWindow;

    public Game(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }

    public void endGame() {
        gameWindow.closeWindow();
    }
}
