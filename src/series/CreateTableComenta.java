package series;

import java.sql.*;

public class CreateTableComenta implements DataBaseTask {
    @Override
    public void run(Connection conn, String data) throws SeriesException {
        String sql = "CREATE TABLE IF NOT EXISTS comenta (" +
                "usuario_id INT NOT NULL, " +
                "capitulo_id INT NOT NULL, " +
                "texto TEXT NOT NULL, " +
                "fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "PRIMARY KEY (usuario_id, capitulo_id), " +
                "FOREIGN KEY (usuario_id) REFERENCES usuario(id), " +
                "FOREIGN KEY (capitulo_id) REFERENCES capitulo(id)" +
                ")";

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new SeriesException(e, "CreateTableComenta");
        }
    }
}