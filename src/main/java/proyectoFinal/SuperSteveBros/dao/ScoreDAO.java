package proyectoFinal.SuperSteveBros.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;

import proyectoFinal.SuperSteveBros.model.Level;
import proyectoFinal.SuperSteveBros.model.Player;
import proyectoFinal.SuperSteveBros.model.Score;
import proyectoFinal.SuperSteveBros.utils.Connect;
import proyectoFinal.SuperSteveBros.utils.ConnectionData;

public class ScoreDAO implements DAO<Score> {

    private final static String FINDALL = "SELECT * FROM puntuajes";
    private final static String FINDBYID = "SELECT puntuacion FROM puntuajes WHERE id_jugador = ? AND id_nivel = ?";
    private final static String INSERT = "INSERT INTO puntuajes (id_jugador, id_nivel, puntuacion) VALUES (?,?,?)";
    private final static String UPDATE = "UPDATE puntuajes SET puntuacion=? WHERE id_jugador=? AND id_nivel = ?";
    
    private Connection conn;
    ConnectionData connectionData;
    LevelDAO lD;
    PlayerDAO pD;
    
    public ScoreDAO(Connection conn) {
        this.conn = conn;
        connectionData = new ConnectionData(
                "jdbc:mysql://localhost:3306",
                "superstevebros",
                "root",
                ""
        );
    }
    
    public ScoreDAO(ConnectionData connectionData) {
    	lD = new LevelDAO(connectionData);
    	pD = new PlayerDAO(connectionData);
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
	public List<Score> findAll() throws SQLException {
        List<Score> result = new ArrayList<>();
        try (PreparedStatement pst = this.conn.prepareStatement(FINDALL)) {
            ResultSet res = pst.executeQuery();
            while (res.next()) {
            	Score s = new Score(pD.findById(res.getInt("id_jugador")), lD.findById(res.getInt("id_nivel")), res.getInt("puntuacion"));
                result.add(s);
            }
        }
        return result;
	}


	// Método para obtener un puntuaje por su ID
    public Score findByIds(int id_jugador, int id_nivel) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(FINDBYID)) {
            statement.setInt(1, id_jugador);
            statement.setInt(2, id_nivel);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int puntuacion = resultSet.getInt("puntuacion");
                    // Obtener el jugador y el nivel correspondiente a partir de sus IDs
                    Player jugador = pD.findById(id_jugador);
                    Level nivel = lD.findById(id_nivel);

                    return new Score(jugador, nivel, puntuacion);
                } else {
                    return null; // El puntuaje no existe
                }
            }
        }
    }

	@Override
	public Score save(Score entity) throws SQLException {
        if (entity != null) {
        	Score existingScore = findByIds(entity.getPlayer().getId(), entity.getLevel().getId());
            if (existingScore == null || (existingScore.getPlayer().getId() == entity.getPlayer().getId() && !(existingScore.getLevel().getId() == entity.getLevel().getId())) || !(existingScore.getPlayer().getId() == entity.getPlayer().getId() && (existingScore.getLevel().getId() == entity.getLevel().getId()))) {
                    // INSERT
                    try (PreparedStatement pst = this.conn.prepareStatement(INSERT)) {
                        pst.setInt(1, entity.getPlayer().getId());
                        pst.setInt(2, entity.getLevel().getId());
                        pst.setInt(3, entity.getScore());
                        pst.executeUpdate();
                    }
                } else {
                    // UPDATE
                    try (PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
                        pst.setInt(1, entity.getScore());
                        pst.setInt(2, existingScore.getPlayer().getId());
                        pst.setInt(3, existingScore.getLevel().getId());
                        pst.executeUpdate();
                    }
                }
        }
        return entity;
	}

	@Override
	public void delete(Score entity) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Score findById(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
