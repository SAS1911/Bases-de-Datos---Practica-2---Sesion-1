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
        return 0;
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

    }
}