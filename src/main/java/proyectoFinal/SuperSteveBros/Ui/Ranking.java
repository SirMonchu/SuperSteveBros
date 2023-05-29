package proyectoFinal.SuperSteveBros.Ui;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import proyectoFinal.SuperSteveBros.Game;
import proyectoFinal.SuperSteveBros.dao.LevelDAO;
import proyectoFinal.SuperSteveBros.dao.PlayerDAO;
import proyectoFinal.SuperSteveBros.dao.ScoreDAO;
import proyectoFinal.SuperSteveBros.model.Level;
import proyectoFinal.SuperSteveBros.model.Player;
import proyectoFinal.SuperSteveBros.model.Score;
import proyectoFinal.SuperSteveBros.utils.ConnectionData;
import proyectoFinal.SuperSteveBros.utilz.LoadSave;
import javafx.scene.layout.VBox;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class Ranking {
    private ScoreDAO scoreDAO;
    private PlayerDAO playerDAO;
    private LevelDAO levelDAO;
    ConnectionData connectionData;
    private ImageView bgIView;
    private int bgX, bgY, bgWight, bgHeight;
    private Text[] scoreTexts; // Array de Text para los scores

    public Ranking() {
        connectionData = new ConnectionData("jdbc:mysql://localhost:3306", "superstevebros", "root", "");
        playerDAO = new PlayerDAO(connectionData);
        scoreDAO = new ScoreDAO(connectionData);
        levelDAO = new LevelDAO(connectionData);
        loadImg();
        initializeScoreTexts();
    }

    private void loadImg() {
        BufferedImage background = LoadSave.GetSpriteAtlas(LoadSave.RANKING_BACKGROUND);
        bgIView = new ImageView();
        bgWight = (int) (background.getWidth() * Game.SCALE);
        bgHeight = (int) (background.getHeight() * Game.SCALE);
        bgX = Game.GAME_WIDTH / 2 - bgWight / 2;
        bgY = (int) (25 * Game.SCALE);
        bgIView = LoadSave.convertToFxImageView(background);
    }

    private void initializeScoreTexts() {
        scoreTexts = new Text[9];
        Font customFont = Font.loadFont(getClass().getResourceAsStream("/proyectoFinal/SuperSteveBros/Extrude.ttf"), 36);
        for (int i = 0; i < scoreTexts.length; i++) {
            scoreTexts[i] = new Text();
            scoreTexts[i].setFont(customFont);
        }
    }

    public void draw(Pane root) throws SQLException {
        bgIView.setX(bgX);
        bgIView.setY(bgY);
        bgIView.setFitWidth(bgWight);
        bgIView.setFitHeight(bgHeight);

        root.getChildren().remove(bgIView);
        root.getChildren().add(bgIView);

        VBox scoresList = new VBox(); // Crear VBox para la lista de scores
        scoresList.setSpacing(10); // Espacio entre cada score en la lista

        List<Score> scores = scoreDAO.findAll();
        Collections.sort(scores, Collections.reverseOrder()); // Ordenar los scores de mayor a menor

        int count = 0; // Contador para limitar el número de resultados

        for (Score score : scores) {
            if (count >= 9) { // Limitar a máximo 9 resultados
                break;
            }

            Player player = playerDAO.findById(score.getPlayer().getId());
            Level level = levelDAO.findById(score.getLevel().getId());

            String username = player.getUsername();
            String levelName = level.getName();
            int scoreValue = score.getScore();

            // Actualizar el Text correspondiente en el array
            Text text = scoreTexts[count];
            text.setText(username + " - " + levelName + ": " + scoreValue);

            scoresList.getChildren().add(text); // Añadir el texto a la lista

            count++;
        }

        scoresList.setLayoutX(bgX + 70);
        scoresList.setLayoutY(bgY + 200);

        // Añadir la lista al Pane root
        root.getChildren().remove(scoresList);
        root.getChildren().add(scoresList);
    }

    public void update() {
        // TODO Auto-generated method stub
    }
}


