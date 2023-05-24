package proyectoFinal.SuperSteveBros.FXMLcontrollers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import proyectoFinal.SuperSteveBros.MainClass;
import proyectoFinal.SuperSteveBros.dao.PlayerDAO;
import proyectoFinal.SuperSteveBros.model.Player;
import proyectoFinal.SuperSteveBros.utils.Connect;
import proyectoFinal.SuperSteveBros.utils.ConnectionData;

public class LoginController {
	
	private Stage stage; // Referencia al Stage de la ventana de inicio de sesión
    private Player currentPlayer; // Referencia al jugador actual
    private static LoginController instance;
	
	public LoginController() {
		
	}
	
	public static LoginController getInstance() {
        if (instance == null) {
            instance = new LoginController();
        }
        return instance;
    }

    @FXML
    private TextField loginUsername;

    @FXML
    private PasswordField loginPassword;

    @FXML
    private Button loginButton;

    @FXML
    private Button signupButton;

    

    @FXML
    private void initialize() {
    	currentPlayer = null;
    }

    @FXML
    private void handleLoginButton() {
        String username = loginUsername.getText();
        String password = loginPassword.getText();
        // Realizar consulta a la base de datos para verificar la existencia del usuario
        boolean userExists = checkUserExistence(username, password);

        if (userExists) {
            // Usuario válido, continuar con la lógica de tu aplicación
            currentPlayer = getPlayer(username);
            System.out.println("Current Player: " + currentPlayer.toString());
            try {
                MainClass.startGame(new Stage(), currentPlayer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Usuario no válido, mostrar mensaje de error o realizar acciones adicionales
            System.out.println("Inicio de sesión fallido");
        }
    }

    @FXML
    private void handleSignUpButton(ActionEvent event) {
        try {
            // Obtener el Stage actual
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            // Cargar y mostrar el nuevo Stage para SignUp
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/proyectoFinal/SuperSteveBros/SingUp.fxml"));
            Parent root = loader.load();
            Scene secondaryScene = new Scene(root, 640, 480);
            Stage secondaryStage = new Stage();
            secondaryStage.setScene(secondaryScene);
            secondaryStage.setTitle("SIGN UP");

            // Pasar una referencia de este controlador al controlador de SignUp
            SignUpController signUpController = loader.getController();
            signUpController.setLoginController(this); // Establecer la referencia
            signUpController.setStage(secondaryStage); // Establecer la referencia al Stage

            secondaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para verificar la existencia del usuario en la base de datos (simulado)
    private boolean checkUserExistence(String username, String password) {
        // Crear una instancia de ConnectionData con la información de conexión
        ConnectionData connectionData = new ConnectionData(
                "jdbc:mysql://localhost:3306",
                "superstevebros",
                "root",
                ""
        );

        // Crear una instancia de Connect y obtener la conexión
        Connect connect = new Connect(connectionData);
        try (Connection connection = connect.getConnection()) {
            // Consulta base de datos
            PlayerDAO playerDAO = new PlayerDAO(connection);
            List<Player> players = playerDAO.findAll();
            for (Player player : players) {
                if (player.getUsername().equals(username) && player.getPassword().equals(password)) {
                    return true; // El usuario existe en la base de datos
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos");
            e.printStackTrace();
        }

        // El usuario no existe en la base de datos
        return false;
    }

    // Método para obtener el jugador actual
    public Player getPlayer() {
        return currentPlayer;
    }

    // Método para obtener el jugador por nombre de usuario
    private Player getPlayer(String username) {
        // Crear una instancia de ConnectionData con la información de conexión
        ConnectionData connectionData = new ConnectionData(
                "jdbc:mysql://localhost:3306",
                "superstevebros",
                "root",
                ""
        );

        // Crear una instancia de Connect y obtener la conexión
        Connect connect = new Connect(connectionData);
        try (Connection connection = connect.getConnection()) {
            // Consulta base de datos
            PlayerDAO playerDAO = new PlayerDAO(connection);
            return playerDAO.findByUsername(username);
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos");
            e.printStackTrace();
        }

        return null;
    }

    // Setter para establecer la referencia al Stage de la ventana de inicio de sesión
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Getter para obtener la referencia al Stage de la ventana de inicio de sesión
    public Stage getStage() {
        return stage;
    }
}
