package series;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConsultaTemporadasDeSerie extends ConsultaConResultado<Temporada> {

    @Override
    public void run(Connection conn, String data) throws SeriesException {
        String sql = "SELECT t.id_serie, t.n_temporada, t.n_capitulos, t.fecha_estreno " +
                "FROM temporada t " +
                "JOIN serie s ON t.id_serie = s.id_serie " +
                "WHERE s.sinopsis LIKE ? " +
                "ORDER BY t.fecha_estreno ASC";

        resultado = new ArrayList<>();

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, data);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    int idSerie = rs.getInt("id_serie");
                    int nTemporada = rs.getInt("n_temporada");
                    int nCapitulos = rs.getInt("n_capitulos");
                    java.sql.Date fechaEstreno = rs.getDate("fecha_estreno");

                    Temporada temp = new Temporada(idSerie, nTemporada, nCapitulos, fechaEstreno);
                    resultado.add(temp);
                }
            }
        } catch (SQLException e) {
            throw new SeriesException(e, "ConsultaTemporadasDeSerie");
        }
    }
}