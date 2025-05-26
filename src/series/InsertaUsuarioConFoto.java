package series;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Clase que inserta un nuevo usuario en la base de datos, incluyendo su
 * fotografía.
 * Implementa la interfaz DataBaseTask.
 */
public class InsertaUsuarioConFoto implements DataBaseTask {

    @Override
    public void run(Connection conn, String data) throws SeriesException {
        // Se espera que data tenga el formato: id,nombre,apellido1,apellido2,rutaFoto
        String[] tokens = data.split(",");

        if (tokens.length != 5) {
            throw new SeriesException(
                    new IllegalArgumentException("Formato de datos incorrecto"),
                    "insertando usuario");
        }

        try (
                // Preparar la sentencia SQL para insertar el usuario
                PreparedStatement pst = conn.prepareStatement(
                        "INSERT INTO usuario (id_usuario, nombre, apellido1, apellido2, fotografia) VALUES (?, ?, ?, ?, ?)");
                // Abrir el archivo de la fotografía como flujo binario
                InputStream fotoStream = new FileInputStream(tokens[4])) {
            // Extraer y convertir los datos de entrada
            int idUsuario = Integer.parseInt(tokens[0]);
            String nombre = tokens[1];
            String apellido1 = tokens[2];
            String apellido2 = tokens[3];

            // Asignar valores a los parámetros de la sentencia
            pst.setInt(1, idUsuario);
            pst.setString(2, nombre);
            pst.setString(3, apellido1);
            pst.setString(4, apellido2);
            pst.setBinaryStream(5, fotoStream); // Se inserta la foto como binario

            // Ejecutar la inserción
            pst.executeUpdate();

        } catch (Exception e) {
            // Excepcion SQL
            throw new SeriesException(e, "insertando usuario");
        }
    }
}
