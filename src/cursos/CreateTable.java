package cursos;

import java.sql.*;

public class CreateTable implements DataBaseTask {

    @Override
    public void run(Connection conn, String data) throws BBDDException, SQLException {
        try {
            String sql = "CREATE TABLE imparte (" +
                    "id_curso INT, " +
                    "id_profesor INT, " +
                    "PRIMARY KEY (id_curso, id_profesor)" +
                    ")";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            throw new BBDDException(e, "CreateTable");
        }
    }
}
