package proyectoFinal.SuperSteveBros.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import proyectoFinal.SuperSteveBros.FXMLcontrollers.SignUpController;
import proyectoFinal.SuperSteveBros.model.Player;
import proyectoFinal.SuperSteveBros.utils.Connect;
import proyectoFinal.SuperSteveBros.utils.ConnectionData;

public class PlayerDAO implements DAO<Player> {
    private final static String FINDALL = "SELECT * FROM jugadores";
    private final static String FINDBYID = "SELECT * FROM jugadores WHERE id=?";
    private final static String FINDBYUSERNAME = "SELECT * FROM jugadores WHERE username=?";
    private final static String INSERT = "INSERT INTO jugadores (username, password) VALUES (?,?)";
    private final static String UPDATE = "UPDATE jugadores SET username=?, password=? WHERE id=?";

    private Connection conn;
    ConnectionData connectionData;
    
    public PlayerDAO(Connection conn) {
        this.conn = conn;
        connectionData = new ConnectionData(
                "jdbc:mysql://localhost:3306",
                "superstevebros",
                "root",
                ""
        );
    }

    public PlayerDAO() {
        Connect connect = new Connect(connectionData);  // Crear instancia de Connect
        try {
            this.conn = connect.getConnection();
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos");
            e.printStackTrace();
        }
    }

    public void close() throws Exception {
        if (conn != null) {
            conn.close();
        }
    }

    @Override
    public List<Player> findAll() throws SQLException {
        List<Player> result = new ArrayList<>();
        try (PreparedStatement pst = this.conn.prepareStatement(FINDALL)) {
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                Player p = new Player(res.getString("username"), res.getString("password"));
                p.setId(res.getInt("id"));
                result.add(p);
            }
        }
        return result;
    }

    @Override
    public Player findById(int id) throws SQLException {
        Player result = null;
        try (PreparedStatement pst = this.conn.prepareStatement(FINDBYID)) {
            pst.setInt(1, id);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    result = new Player(res.getString("username"), res.getString("password"));
                    result.setId(res.getInt("id"));
                }
            }
        }
        return result;
    }

    @Override
    public Player save(Player entity) throws SQLException {
        if (entity != null) {
            Player existingPlayer = findByUsername(entity.getUsername());
            if (existingPlayer == null || existingPlayer.getId() == entity.getId()) {
            	SignUpController.setError(false);
                if (entity.getId() == 0) {
                    // INSERT
                    try (PreparedStatement pst = this.conn.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
                        pst.setString(1, entity.getUsername());
                        pst.setString(2, entity.getPassword());
                        pst.executeUpdate();

                        // Obtener el ID generado para el nuevo jugador
                        ResultSet generatedKeys = pst.getGeneratedKeys();
                        if (generatedKeys.next()) {
                            entity.setId(generatedKeys.getInt(1));
                        }
                        generatedKeys.close();
                    }
                } else {
                    // UPDATE
                    try (PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
                        pst.setString(1, entity.getUsername());
                        pst.setString(2, entity.getPassword());
                        pst.setInt(3, entity.getId());
                        pst.executeUpdate();
                    }
                }
            } else {
                // Ya existe otro jugador con el mismo nombre de usuario
            	SignUpController.setError(true);
                System.out.println("Ya existe un jugador con el mismo nombre de usuario");
            }
        }
        return entity;
    }

    private Player findByUsername(String username) throws SQLException {
        Player result = null;
        try (PreparedStatement pst = this.conn.prepareStatement(FINDBYUSERNAME)) {
            pst.setString(1, username);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    result = new Player(res.getString("username"), res.getString("password"));
                    result.setId(res.getInt("id"));
                }
            }
        }
        return result;
    }

	@Override
	public void delete(String entity) throws SQLException {
		// TODO Auto-generated method stub
		
	}
}
