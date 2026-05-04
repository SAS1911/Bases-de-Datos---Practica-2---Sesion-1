package cursos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsultaBlob implements DataBaseTask {
    /**
     * Realiza una consulta que recupera una imagen desde una bbdd.
     *
     * @param conn La conexion ya abierta
     * @param data el nombre del edificio (exacto)
     *
     * @throws SQLException,             cuando se produzca la misma al ejecutar
     *                                   los comandos sql.
     * @throws BBDDException,            cuando se produzca una IOException,
     *                                   fija `when` a "error archivo"
     * @throws IllegalArgumentException, cuando el edificio no exista
     *                                   en la base de datos.
     */
    @Override
    public void run(Connection conn, String data) throws BBDDException, SQLException {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("no existe");
        }

        String sql = "SELECT foto FROM edificio WHERE nombre = ? ORDER BY id ASC LIMIT 1";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, data);
            try (ResultSet rs = pst.executeQuery()) {
                if (!rs.next()) {
                    throw new IllegalArgumentException("no existe");
                }
                byte[] foto = rs.getBytes("foto");
                Path fichero = Paths.get(data + ".jpg");
                try {
                    Files.write(fichero, foto);
                } catch (IOException e) {
                    throw new BBDDException(e, "error archivo");
                }
            }
        }
    }
}