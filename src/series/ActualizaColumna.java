package series;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase que implementa la tarea de actualizar el tipo de dato de una columna
 * en la tabla 'capitulo' de la base de datos.
 * 
 * Esta clase modifica la columna 'titulo' para cambiar su tipo a VARCHAR(100).
 * Implementa la interfaz DataBaseTask para poder ser ejecutada por
 * ConnectionManager.
 */
public class ActualizaColumna implements DataBaseTask {

    @Override
    public void run(Connection conn, String data) throws SeriesException {
        // Sentencia SQL para modificar la columna 'titulo' de la tabla 'capitulo'
        // Cambia el tipo de dato a VARCHAR con longitud máxima de 100 caracteres
        String sql = "ALTER TABLE capitulo MODIFY COLUMN titulo VARCHAR(100);";

        try (Statement stmt = conn.createStatement()) {
            // Ejecutamos la sentencia ALTER TABLE
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            // Ocurre un error SQL al moemnto de actualozar columna
            throw new SeriesException(e, "Actualizar Columna");
        }
    }
}