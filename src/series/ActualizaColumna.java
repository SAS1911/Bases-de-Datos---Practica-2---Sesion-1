package series;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ActualizaColumna implements DataBaseTask {

    @Override
    public void run(Connection conn, String data) throws SeriesException {
        String sql = "ALTER TABLE capitulo MODIFY COLUMN titulo VARCHAR(100);";

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new SeriesException(e, "Actualizar Columna");
        }
    }
}