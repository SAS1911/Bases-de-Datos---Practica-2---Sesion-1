package series;

import java.sql.*;

public class CreateTableValora implements DataBaseTask {
    @Override
    public void run(Connection conn, String data) throws SeriesException {
    	String sql = "CREATE TABLE IF NOT EXISTS valora (" +
    	        "id_usuario INT NOT NULL, " +
    	        "id_serie INT NOT NULL, " +
    	        "puntuacion INT NOT NULL, " +
    	        "PRIMARY KEY (id_usuario, id_serie), " +
    	        "FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario), " +
    	        "FOREIGN KEY (id_serie) REFERENCES serie(id_serie)" +
    	        ")";

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new SeriesException(e, "CreateTableValora");
        }
    }
}
