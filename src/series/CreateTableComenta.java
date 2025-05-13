package series;

import java.sql.*;

public class CreateTableComenta implements DataBaseTask {
    @Override
    public void run(Connection conn, String data) throws SeriesException {
        String sql = "CREATE TABLE IF NOT EXISTS comenta (" +
                "id_usuario INT NOT NULL, " +
                "id_serie INT NOT NULL, " +
                "texto TEXT NOT NULL, " +
                "fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "PRIMARY KEY (id_usuario, id_serie), " +
                "FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario), " +
                "FOREIGN KEY (id_serie) REFERENCES capitulo(id_serie)" +
                ")";

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new SeriesException(e, "CreateTableComenta");
        }
    }
}