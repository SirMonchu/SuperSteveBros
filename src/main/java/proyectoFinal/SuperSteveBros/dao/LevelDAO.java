package proyectoFinal.SuperSteveBros.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import proyectoFinal.SuperSteveBros.model.Level;
import proyectoFinal.SuperSteveBros.utils.ConnectionData;
import proyectoFinal.SuperSteveBros.utils.Connect;

public class LevelDAO implements DAO<Level> {

    private final static String FINDALL = "SELECT * FROM niveles";
    private final static String FINDBYID = "SELECT * FROM niveles WHERE id=?";
    private final static String FINDBYUSERNAME = "SELECT * FROM niveles WHERE username=?";
    private final static String INSERT = "INSERT INTO niveles (username, password) VALUES (?,?)";
    private final static String UPDATE = "UPDATE niveles SET username=?, password=? WHERE id=?";
    
    private Connection conn;
    ConnectionData connectionData;
    
    public LevelDAO(Connection conn) {
    	this.conn = conn;
        connectionData = new ConnectionData(
                "jdbc:mysql://localhost:3306",
                "superstevebros",
                "root",
                ""
        );
    }
    
    public LevelDAO(ConnectionData connectionData) {
        Connect connect = new Connect(connectionData);  // Crear instancia de Connect
        try {
            this.conn = connect.getConnection();
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos");
            e.printStackTrace();
        }
    }
    
	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Level> findAll() throws SQLException {
        List<Level> result = new ArrayList<>();
        try (PreparedStatement pst = this.conn.prepareStatement(FINDALL)) {
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                Level l = new Level(res.getInt("Id"), res.getString("nombre"), res.getString("dificultad"), res.getInt("tiempo_limite"));
                l.setId(res.getInt("Id"));
                result.add(l);
            }
        }
        return result;
	}

	@Override
	public Level findById(int id) throws SQLException {
		Level result = null;
        try (PreparedStatement pst = this.conn.prepareStatement(FINDBYID)) {
            pst.setInt(1, id);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    result = new Level(res.getInt("Id"), res.getString("nombre"), res.getString("dificultad"), res.getInt("tiempo_limite"));
                }
            }
        }
        return result;
	}

	@Override
	public Level save(Level entity) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String entity) throws SQLException {
		// TODO Auto-generated method stub
		
	}

}
