package cursos;

import java.sql.*;

import java.util.*;

public class ConsultaSimple extends ConsultaConResultado<Properties> {
    /**
     * Obtiene los profesores ordenados por apellido1
     *
     * @param conn La conexion ya abierta
     * @param data o bien ASC o bien DESC (debe ser case-insentive)
     *
     * @throws BBDDException, cuando `data` sea distinto de ACS y DESC.
     * @throws SQLException,  cuando se produzca la misma al ejecutar
     *                        modificar la tabla.
     */
    @Override
    public void run(Connection conn, String data) throws BBDDException, SQLException {
        if (data == null || !(data.equalsIgnoreCase("ASC") || data.equalsIgnoreCase("DESC"))) {
            throw new BBDDException(null, "ordenando");
        }

        String orden = data.equalsIgnoreCase("ASC") ? "ASC" : "DESC";
        String sql = "SELECT nombre, apellido1, apellido2 FROM profesor ORDER BY apellido1 " + orden;
        resultado = new ArrayList<>();

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String apellido1 = rs.getString("apellido1");
                String apellido2 = rs.getString("apellido2");
                resultado.add(new Properties(nombre, apellido1, apellido2));
            }
        }
    }
}