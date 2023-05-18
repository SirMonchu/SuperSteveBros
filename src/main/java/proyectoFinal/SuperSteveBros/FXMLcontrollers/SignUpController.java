package proyectoFinal.SuperSteveBros.FXMLcontrollers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import proyectoFinal.SuperSteveBros.dao.PlayerDAO;
import proyectoFinal.SuperSteveBros.model.Player;
import proyectoFinal.SuperSteveBros.utils.Connect;
import proyectoFinal.SuperSteveBros.utils.ConnectionData;

public class SignUpController {

    @FXML
    private TextField SingUpUserName;

    @FXML
    private PasswordField SignUpPassword;
    
    @FXML
    private Button backButton;
    
    @FXML
    private Label errorText;

    private LoginController loginController; // Referencia al controlador de Login
    private Stage stage; // Referencia al Stage de la ventana de registro
    private static boolean error = false;

    @FXML
    private void handleRegisterButton(ActionEvent event) throws IOException {
        String username = SingUpUserName.getText();
        String password = SignUpPassword.getText();

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
            // Crear una instancia de PlayerDAO
            PlayerDAO playerDAO = new PlayerDAO(connection);

            // Crear una nueva instancia de Player con los datos del formulario
            Player player = new Player(username, password);

            // Guardar el nuevo jugador en la base de datos
            playerDAO.save(player);
            if (!error) {
                System.out.println("Nuevo usuario registrado:");
                System.out.println("Nombre de usuario: " + username);
                System.out.println("Contraseña: " + password);
                handleBackButton(event);
            } else {
                // Mostrar mensaje de error temporalmente
                errorText.setOpacity(1); // Establecer la opacidad al 100%
                
                // Configurar una animación de desvanecimiento después de 3 segundos
                Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(3), e -> {
                        errorText.setOpacity(0); // Establecer la opacidad al 0 (ocultar el mensaje)
                    })
                );
                timeline.play();
            }
            
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos");
            e.printStackTrace();
        }
    }



    @FXML
    private void handleBackButton(ActionEvent event) throws IOException {
        // Obtener el Stage actual
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        try {
            // Cargar y mostrar el nuevo Stage para SignIn
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/proyectoFinal/SuperSteveBros/Login.fxml"));
            Parent root = loader.load();
            Scene secondaryScene = new Scene(root, 640, 480);
            Stage secondaryStage = new Stage();
            secondaryStage.setScene(secondaryScene);
            secondaryStage.setTitle("SIGN IN");

            // Pasar una referencia de este controlador al controlador de SignIn
            LoginController loginController = loader.getController();
            loginController.setStage(secondaryStage); // Establecer la referencia al Stage

            secondaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void setError(boolean e) {
    	error = e;
    }

    // Setter para establecer la referencia al controlador de Login
    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    // Setter para establecer la referencia al Stage de la ventana de registro
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
