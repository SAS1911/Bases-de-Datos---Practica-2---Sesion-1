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
        if (data == null || data.trim().isEmpty()) {
            throw new BBDDException(null, "filtro vacio");
        }

        String sql = "SELECT p.nombre, p.apellido1, p.apellido2, i.curso_id, c.titulo " +
                "FROM profesor p " +
                "JOIN imparte i ON p.profesor_id = i.profesor_id " +
                "JOIN curso c ON i.curso_id = c.curso_id " +
                "WHERE c.titulo LIKE ? " +
                "ORDER BY p.apellido1 ASC";

        resultado = new ArrayList<>();

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, "%" + data.trim() + "%");
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String apellido1 = rs.getString("apellido1");
                    String apellido2 = rs.getString("apellido2");
                    String cursoId = String.valueOf(rs.getInt("curso_id"));
                    String titulo = rs.getString("titulo");
                    String extra = cursoId + "-" + titulo;
                    resultado.add(new Properties(nombre, apellido1, apellido2, extra));
                }
            }
        }
    }
}