package series;

import java.io.FileInputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class InsertaTemporadas implements DataBaseTask {
    /*
     * *
     * Rellena el pst con los datos de una linea del CSV y lo ejecuta .
     *
     * ATENCION : para poner la fecha se recomienda usar :
     * - java . time . LocalDate . of
     * Para crear una LocalDate con year , month , day
     * - java . sql . Date . valueof
     * Para transformarla en Date
     *
     * Para descomponer una cadena en ` tokens ` se recomienda :
     *
     * - String . split
     * Indicando como argumento el caracter que separa .
     *
     * @param pst PreparedStatement con el INSERT
     * 
     * @param linea Una linea del archivo csv
     * 
     * @return el resultado de ejecutar el pst
     * 
     * @throws SQLException
     */
    public int insertaUnaTemporada(PreparedStatement pst, String linea) throws SQLException {
        String[] tokens = linea.split(",");
        if (tokens.length != 4) {
            throw new SQLException("Formato de línea incorrecto. Se esperaban 4 valores separados por comas");
        }

        try {
            int idSerie = Integer.parseInt(tokens[0].trim());

            // Verificar si la serie existe
            try (PreparedStatement checkStmt = pst.getConnection().prepareStatement(
                    "SELECT 1 FROM serie WHERE id_serie = ?")) {
                checkStmt.setInt(1, idSerie);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (!rs.next()) {
                        throw new SQLException("La serie con ID " + idSerie + " no existe");
                    }
                }
            }

            // Resto del código para insertar la temporada...
            int numTemporada = Integer.parseInt(tokens[1].trim());
            int numCapitulos = Integer.parseInt(tokens[2].trim());

            String[] fechaParts = tokens[3].trim().split("-");
            if (fechaParts.length != 3) {
                throw new SQLException("Formato de fecha incorrecto. Se esperaba dd-mm-yyyy");
            }

            LocalDate localDate = LocalDate.of(
                    Integer.parseInt(fechaParts[2]),
                    Integer.parseInt(fechaParts[1]),
                    Integer.parseInt(fechaParts[0]));

            pst.setInt(1, idSerie);
            pst.setInt(2, numTemporada);
            pst.setInt(3, numCapitulos);
            pst.setDate(4, Date.valueOf(localDate));

            return pst.executeUpdate();
        } catch (NumberFormatException e) {
            throw new SQLException("Error al parsear números: " + e.getMessage());
        }
    }

    /*
     * *
     * Lee de un CSV con datos de ` temporada ` y los inserta en la BD .
     *
     * Crea un PreparedStament y lee linea a linea el archivo
     * para insertarlas en la ` temporada `.
     *
     * - El archivo CSV no tiene etiquetas de columnas , las columnas son :
     * id_serie , n_temporada , n_capitulos , fecha_estreno .
     * - Las columnas se separan por comas .
     * - El formato de la fecha es : dd - mm - yyyy ( dia - mes - anho ).
     *
     * Si falla la insercion de cualquier linea no debe continuar .
     *
     * @param conn La conexion ya abierta
     * 
     * @param data El nombre ( ruta ) del archivo CSV
     * 
     * @throws SeriesException Se debe fijar ` when ` a " Insertando "
     */
    @Override
    public void run(Connection conn, String data) throws SeriesException {
        PreparedStatement pst = null;
        Scanner scanner = null;

        try {
            // Preparar statement SQL
            String sql = "INSERT INTO temporada (id_serie, n_temporada, n_capitulos, fecha_estreno) VALUES (?, ?, ?, ?)";
            pst = conn.prepareStatement(sql);

            // Leer archivo CSV
            scanner = new Scanner(new FileInputStream(data));

            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine().trim();
                if (!linea.isEmpty()) { // Ignorar líneas vacías
                    insertaUnaTemporada(pst, linea);
                }
            }
        } catch (SQLException e) {
            throw new SeriesException(e, "Insertando");
        } catch (Exception e) {
            throw new SeriesException(e, "Insertando");
        } finally {
            // Cerrar recursos
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    System.err.println("Error al cerrar PreparedStatement: " + e.getMessage());
                }
            }
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}