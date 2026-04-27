package cursos;

import java.sql.*;

public class CreateTable implements DataBaseTask {

    @Override
    public void run(Connection conn, String data) throws BBDDException, SQLException {
        try {
            String sql = "CREATE TABLE imparte (" +
                    "id_aula INT, " +
                    "id_profesor INT, " +
                    "curso_id INT, " +
                    "n_modulo INT, " +
                    "fecha DATE, " +
                    "PRIMARY KEY (id_profesor, id_aula, n_modulo, fecha), " +
                    "FOREIGN KEY (id_aula) REFERENCES aula(id) ON DELETE CASCADE ON UPDATE CASCADE, " +
                    "FOREIGN KEY (id_profesor) REFERENCES profesor(id) ON DELETE CASCADE ON UPDATE CASCADE, " +
                    "FOREIGN KEY (curso_id, n_modulo) REFERENCES modulo(curso_id, n_modulo) ON DELETE CASCADE ON UPDATE CASCADE "
                    +
                    ");";
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            throw new BBDDException(e, "CreateTable");
        }
    }
}
