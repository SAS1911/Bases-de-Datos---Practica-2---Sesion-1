package cursos;

import java.sql.*;

public class CreateTable implements DataBaseTask {

    @Override
    public void run(Connection conn, String data) throws BBDDException, SQLException {
        try {
            String sql = "CREATE TABLE imparte (" +
                    "profesor_id INT, " +
                    "curso_id INT, " +
                    "n_modulo INT, " +
                    "aula_id INT, " +
                    "fecha DATE, " +
                    "PRIMARY KEY (profesor_id, curso_id, n_modulo, aula_id, fecha)" +
                    ")";

            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            throw new BBDDException(e, "CreateTable");
        }
    }
}
