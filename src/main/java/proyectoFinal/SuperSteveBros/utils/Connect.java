package proyectoFinal.SuperSteveBros.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306";

    private ConnectionData connectionData;

    public Connect(ConnectionData connectionData) {
        this.connectionData = connectionData;
    }

    public Connection getConnection() throws SQLException {
        Connection connection = null;

        try {
            // Cargar el driver JDBC
            Class.forName(JDBC_DRIVER);

            // Establecer la conexión con la base de datos
            connection = DriverManager.getConnection(
                    DB_URL + "/" + connectionData.getDatabase(),
                    connectionData.getUsername(),
                    connectionData.getPassword()
            );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
