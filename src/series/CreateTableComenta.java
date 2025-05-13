package series;

import java.sql.*;

public class CreateTableComenta implements DataBaseTask {
    @Override
    public void run(Connection conn, String data) throws SeriesException {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            // Sentencia SQL optimizada para MySQL 8.4+
            String sql = "CREATE TABLE IF NOT EXISTS Comenta (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "usuario_id INT NOT NULL," +
                    "capitulo_id INT NOT NULL," +
                    "texto TEXT NOT NULL," +
                    "fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP," +
                    "CONSTRAINT fk_comenta_usuario FOREIGN KEY (usuario_id) " +
                    "REFERENCES Usuario(id) ON DELETE CASCADE," +
                    "CONSTRAINT fk_comenta_capitulo FOREIGN KEY (capitulo_id) " +
                    "REFERENCES Capitulo(id) ON DELETE CASCADE," +
                    "CONSTRAINT uc_comenta_usuario_capitulo UNIQUE (usuario_id, capitulo_id)" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";

            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new SeriesException(e, "CreateTableComenta");
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
