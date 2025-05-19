package series;

import java.sql.*;

public class CreateTableComenta implements DataBaseTask {

    public void run(Connection conn, String data) throws SeriesException {
        try {
            String sql = "CREATE TABLE comenta (" +
                    "id_serie INT, " +
                    "n_temporada INT, " +
                    "id_usuario INT, " +
                    "fecha DATE, " +
                    "texto VARCHAR(200), " +
                    "PRIMARY KEY(id_serie, n_temporada, id_usuario, fecha), " +
                    "FOREIGN KEY(id_serie, n_temporada) REFERENCES temporada(id_serie, n_temporada) " +
                    "ON DELETE CASCADE ON UPDATE CASCADE, " +
                    "FOREIGN KEY(id_usuario) REFERENCES usuario(id_usuario) " + "  ON DELETE CASCADE ON UPDATE CASCADE);";

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            throw new SeriesException(e, "CreateTableComenta");
        }
    }
}