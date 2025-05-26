package series;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * La clase ConsultaTemporadasIncompletas hereda de ConsultaConResultado<String>
 * y sobrescribe el método run. Realiza una consulta SQL que obtiene el título y
 * número de temporada de series cuya cantidad real de capítulos no coincide con
 * la esperada. Cada resultado se guarda como un String en el formato
 * "titulo:n_temporada".
 */
public class ConsultaTemporadasIncompletas extends ConsultaConResultado<String> {

    @Override
    public void run(Connection conn, String data) throws SeriesException {
        // Inicializa la lista de resultados (atributo heredado)
        resultado = new ArrayList<>();

        String sql = "SELECT s.titulo, t.n_temporada " +
                "FROM temporada t " +
                "JOIN serie s ON t.id_serie = s.id_serie " +
                "JOIN capitulo c ON t.id_serie = c.id_serie AND t.n_temporada = c.n_temporada " +
                "GROUP BY s.titulo, t.n_temporada, t.n_capitulos " +
                "HAVING COUNT(*) <> t.n_capitulos";

        try (
                Statement stmt = conn.createStatement();

                // Ejecuta la consulta y obtiene resultados
                ResultSet rs = stmt.executeQuery(sql)) {
            // Procesa cada fila del resultado
            while (rs.next()) {
                // Obtiene datos de la fila actual
                String titulo = rs.getString("titulo");
                int nTemporada = rs.getInt("n_temporada");

                // Formatea resultado y lo añade a la lista
                resultado.add(titulo + ":" + nTemporada);
            }
        } catch (SQLException e) {
            // Excepcion en SQL
            throw new SeriesException(e, "ConsultaTemporadasIncompletas");
        }
    }
}