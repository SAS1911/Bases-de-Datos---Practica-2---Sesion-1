package series;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Clase que realiza una consulta para obtener las temporadas de series
 * cuya sinopsis coincide con un patrón específico.
 * 
 * Hereda de ConsultaConResultado<Temporada> para manejar el resultado como una
 * lista
 * de objetos Temporada, ordenados por fecha de estreno ascendente.
 * 
 * Utiliza PreparedStatement para prevenir inyección SQL y manejar parámetros de
 * búsqueda.
 */
public class ConsultaTemporadasDeSerie extends ConsultaConResultado<Temporada> {

    @Override
    public void run(Connection conn, String data) throws SeriesException {
        // Consulta SQL con JOIN entre temporada y serie
        // Filtra por sinopsis que coincida con el patrón (data)
        // Ordena por fecha de estreno ascendente
        String sql = "SELECT t.id_serie, t.n_temporada, t.n_capitulos, t.fecha_estreno " +
                "FROM temporada t " +
                "JOIN serie s ON t.id_serie = s.id_serie " +
                "WHERE s.sinopsis LIKE ? " +
                "ORDER BY t.fecha_estreno ASC";

        // Inicializa la lista de resultados (atributo heredado)
        resultado = new ArrayList<>();

        try (
                // PreparedStatement para prevenir inyección SQL
                PreparedStatement pst = conn.prepareStatement(sql)) {
            // Establece el parámetro del patrón de búsqueda
            pst.setString(1, data);

            try (
                    // Ejecuta la consulta y obtiene el ResultSet
                    ResultSet rs = pst.executeQuery()) {
                // Procesa cada fila del resultado
                while (rs.next()) {
                    // Extrae los valores de las columnas
                    int idSerie = rs.getInt("id_serie");
                    int nTemporada = rs.getInt("n_temporada");
                    int nCapitulos = rs.getInt("n_capitulos");
                    java.sql.Date fechaEstreno = rs.getDate("fecha_estreno");

                    // Crea y almacena el objeto Temporada
                    resultado.add(new Temporada(idSerie, nTemporada, nCapitulos, fechaEstreno));
                }
            }
        } catch (SQLException e) {
            // Ocurre un error SQL al moemnto de consultar temporadas de serie
            throw new SeriesException(e, "ConsultaTemporadasDeSerie");
        }
    }
}