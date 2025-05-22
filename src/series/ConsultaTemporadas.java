package series;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Clase que realiza una consulta para obtener todas las temporadas de la base
 * de datos,
 * ordenadas por fecha de estreno en orden ascendente.
 * 
 * Hereda de ConsultaConResultado<Temporada> para manejar el resultado como una
 * lista
 * de objetos Temporada.
 */
public class ConsultaTemporadas extends ConsultaConResultado<Temporada> {

    @Override
    public void run(Connection conn, String data) throws SeriesException {
        // Consulta SQL para obtener todas las temporadas ordenadas por fecha de estreno
        String sql = "SELECT * FROM temporada ORDER BY fecha_estreno ASC";

        // Inicializamos la lista de resultados (hereda de ConsultaConResultado)
        resultado = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            // Procesamos cada fila del ResultSet
            while (rs.next()) {
                // Extraemos los valores de cada columna
                int idSerie = rs.getInt("id_serie");
                int nTemporada = rs.getInt("n_temporada");
                int nCapitulos = rs.getInt("n_capitulos");
                java.sql.Date fechaEstreno = rs.getDate("fecha_estreno");

                // Creamos un objeto Temporada con los datos obtenidos
                Temporada temp = new Temporada(idSerie, nTemporada, nCapitulos, fechaEstreno);

                // Añadimos la temporada a la lista de resultados
                resultado.add(temp);
            }

        } catch (SQLException e) {
            // Ocurre un error SQL al moemnto de consultar temporadas
            throw new SeriesException(e, "ConsultaTemporadas");
        }
    }
}