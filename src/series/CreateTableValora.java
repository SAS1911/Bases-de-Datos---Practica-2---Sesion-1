package series;

import java.sql.*;

public class CreateTableValora implements DataBaseTask {
    @Override
    public void run(Connection conn, String data) throws SeriesException {
        String sql = "CREATE TABLE IF NOT EXISTS valora (" +
                "usuario_id INT NOT NULL, " +
                "serie_id INT NOT NULL, " +
                "puntuacion INT NOT NULL, " +
                "PRIMARY KEY (usuario_id, serie_id), " +
                "FOREIGN KEY (usuario_id) REFERENCES usuario(id), " +
                "FOREIGN KEY (serie_id) REFERENCES serie(id)" +
                ")";

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new SeriesException(e, "CreateTableValora");
        }
    }
}