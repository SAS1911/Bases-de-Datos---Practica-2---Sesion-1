package cursos;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConsultaCompleja extends ConsultaConResultado<Properties> {
    /**
     * Realiza una consulta
     *
     * @param conn La conexion ya abierta
     * @param data un entero que indica un porcentaje
     *
     * @throws BBDDException, cuando `data` no se pueda convertir
     *                        en un entero
     * @throws SQLException,  cuando se produzca la misma al ejecutar
     *                        los comandos sql.
     */
    @Override
    public void run(Connection conn, String data) throws BBDDException, SQLException {
        resultado = new ArrayList<>();

        int porcentaje;
        try {
            porcentaje = Integer.parseInt(data.trim());
        } catch (Exception e) {
            throw new BBDDException(e, "not int");
        }

        String sql = "SELECT p.nombre, p.apellido1, p.apellido2, ip.curso_id, " +
                "(SUM(m.horas) * 100.0 / total.total_horas) AS porcentaje " +
                "FROM (SELECT DISTINCT profesor_id, curso_id, n_modulo FROM imparte) ip " +
                "JOIN modulo m ON ip.curso_id = m.curso_id AND ip.n_modulo = m.n_modulo " +
                "JOIN profesor p ON ip.profesor_id = p.id " +
                "JOIN (SELECT curso_id, SUM(horas) AS total_horas FROM modulo GROUP BY curso_id) total " +
                "ON ip.curso_id = total.curso_id " +
                "GROUP BY p.id, ip.curso_id, p.nombre, p.apellido1, p.apellido2, total.total_horas " +
                "HAVING SUM(m.horas) * 100 >= ? * total.total_horas " +
                "ORDER BY p.apellido1 ASC";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, porcentaje);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    BigDecimal porcentajeObtenido = rs.getBigDecimal("porcentaje");
                    Properties p = new Properties(
                            rs.getString("nombre"),
                            rs.getString("apellido1"),
                            rs.getString("apellido2"),
                            "curso:" + rs.getInt("curso_id") + ":" + porcentajeObtenido.toString());
                    resultado.add(p);
                }
            }
        }
    }
}