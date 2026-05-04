package cursos;

import java.sql.*;
import java.util.*;

public class ConsultaConFiltro extends ConsultaConResultado<Properties> {
    /**
     * Obtiene los profesores que imparten un modulo cuyo titulo
     * contiene la cadena dada.
     *
     * @throws BBDDException, cuando data este vacia. Se debe fijar
     *                        when a "filtro vacio"
     * @throws SQLException,  cuando se produzca la misma al ejecutar
     *                        modificar la tabla.
     */
    @Override
    public void run(Connection conn, String data) throws BBDDException, SQLException {
        resultado = new ArrayList<>();

        if (data == null || data.trim().isEmpty()) {
            throw new BBDDException(null, "filtro vacio");
        }

        String sql = "SELECT p.nombre, p.apellido1, p.apellido2, i.curso_id, m.titulo " +
                "FROM profesor p " +
                "JOIN imparte i ON p.id = i.profesor_id " +
                "JOIN modulo m ON i.curso_id = m.curso_id AND i.n_modulo = m.n_modulo " +
                "WHERE LOWER(m.titulo) LIKE LOWER(?) " +
                "ORDER BY p.apellido1 ASC";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, "%" + data.trim() + "%");
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Properties p = new Properties(
                            rs.getString("nombre"),
                            rs.getString("apellido1"),
                            rs.getString("apellido2"),
                            rs.getInt("curso_id") + "-" + rs.getString("titulo"));
                    resultado.add(p);
                }
            }
        }
    }
}