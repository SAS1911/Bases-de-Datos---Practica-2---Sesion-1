package series;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTableValora implements DataBaseTask {

    @Override
    public void run(Connection conn, String data) throws SeriesException {
        String sql = ""
                + "CREATE TABLE valora ("
                + "  id_usuario   INT,"
                + "  id_serie     INT,"
                + "  n_temporada  INT,"
                + "  n_orden      INT,"
                + "  valor        INT,"
                + "  fecha        DATE,"
                + "  PRIMARY KEY (id_usuario, id_serie, n_temporada, n_orden, fecha),"
                + "  FOREIGN KEY (id_usuario) "
                + "    REFERENCES usuario(id_usuario) "
                + "    ON DELETE CASCADE ON UPDATE CASCADE,"
                + "  FOREIGN KEY (id_serie, n_temporada, n_orden) "
                + "    REFERENCES capitulo(id_serie, n_temporada, n_orden) "
                + "    ON DELETE CASCADE ON UPDATE CASCADE"
                + ")";
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new SeriesException(e, "CreateTableValora");
        }
    }
}
