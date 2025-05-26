package series;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class InsertaUsuarioConFoto implements DataBaseTask {

    @Override
    public void run(Connection conn, String data) throws SeriesException {
        String[] tokens = data.split(",");

        if (tokens.length != 5) {
            throw new SeriesException(new IllegalArgumentException("Formato de datos incorrecto"), "insertando usuario");
        }

        try (
            PreparedStatement pst = conn.prepareStatement(
                "INSERT INTO usuario (id_usuario, nombre, apellido1, apellido2, fotografia) VALUES (?, ?, ?, ?, ?)");
            InputStream is = new FileInputStream(tokens[4])
        ) {
            int idUsuario = Integer.parseInt(tokens[0]);
            String nombre = tokens[1];
            String apellido1 = tokens[2];
            String apellido2 = tokens[3];

            pst.setInt(1, idUsuario);
            pst.setString(2, nombre);
            pst.setString(3, apellido1);
            pst.setString(4, apellido2);
            pst.setBinaryStream(5, is);

            pst.executeUpdate();

        } catch (Exception e) {
            throw new SeriesException(e, "insertando usuario");
        }
    }
}