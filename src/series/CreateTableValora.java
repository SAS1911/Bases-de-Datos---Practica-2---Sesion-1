package series;

import java.sql.*;

public class CreateTableValora implements DataBaseTask {
    @Override
    public void run(Connection conn, String data) throws SeriesException {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            // Sentencia SQL más compatible con MySQL 8+
            String sql = "CREATE TABLE IF NOT EXISTS Valora (" +
                    "usuario_id INT NOT NULL," +
                    "serie_id INT NOT NULL," +
                    "puntuacion INT NOT NULL CHECK (puntuacion BETWEEN 1 AND 5)," +
                    "fecha_valoracion TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "PRIMARY KEY (usuario_id, serie_id)," +
                    "CONSTRAINT fk_valora_usuario FOREIGN KEY (usuario_id) " +
                    "REFERENCES Usuario(id) ON DELETE CASCADE," +
                    "CONSTRAINT fk_valora_serie FOREIGN KEY (serie_id) " +
                    "REFERENCES Serie(id) ON DELETE CASCADE" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";

            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new SeriesException(e, "CreateTableValora");
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    // Ignorar error al cerrar
                }
            }
        }
    }
}
