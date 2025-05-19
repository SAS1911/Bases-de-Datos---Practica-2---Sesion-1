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
        String[] campos = linea.split(",");
        int idSerie = Integer.parseInt(campos[0].trim());
        int nTemporada = Integer.parseInt(campos[1].trim());
        int nCapitulos = Integer.parseInt(campos[2].trim());
        String[] fechaParts = campos[3].trim().split("-");
        LocalDate fecha = LocalDate.of(
                Integer.parseInt(fechaParts[2]),
                Integer.parseInt(fechaParts[1]),
                Integer.parseInt(fechaParts[0]));

        pst.setInt(1, idSerie);
        pst.setInt(2, nTemporada);
        pst.setInt(3, nCapitulos);
        pst.setDate(4, Date.valueOf(fecha));

        return pst.executeUpdate();
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
        String sql = "INSERT INTO temporada (id_serie, n_temporada, n_capitulos, fecha_estreno) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pst = conn.prepareStatement(sql);
                Scanner sc = new Scanner(new FileInputStream(data))) {

            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                if (!linea.isEmpty()) {
                    insertaUnaTemporada(pst, linea);
                }
            }

        } catch (SQLException e) {
            throw new SeriesException(e, "Insertando");
        } catch (Exception e) {
            throw new SeriesException(e, "Insertando");
        }
    }
}