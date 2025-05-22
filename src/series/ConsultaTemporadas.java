package series;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ConsultaTemporadas extends ConsultaConResultado<Temporada> {

    @Override
    public void run(Connection conn, String data) throws SeriesException {
        String sql = "SELECT * FROM temporada ORDER BY fecha_estreno ASC";
        resultado = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int idSerie = rs.getInt("id_serie");
                int nTemporada = rs.getInt("n_temporada");
                int nCapitulos = rs.getInt("n_capitulos");
                java.sql.Date fechaEstreno = rs.getDate("fecha_estreno");

                Temporada temp = new Temporada(idSerie, nTemporada, nCapitulos, fechaEstreno);
                resultado.add(temp);
            }

        } catch (SQLException e) {
            throw new SeriesException(e, "ConsultaTemporadas");
        }
    }
}