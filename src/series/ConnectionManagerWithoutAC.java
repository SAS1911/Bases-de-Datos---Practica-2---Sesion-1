package series;

import java.sql.*;

/* *
* Clase para gestionar las conexiones sin AC .
* Hereda de ConnectionManager .
* Los atributos y getUrl deberian ser los mismos .
* Pero , en general , sera necesario que los atributos sean ` protected `.
* No se puede poner ningun metodo adiccional en la clase ConnectionManager .
*/

public class ConnectionManagerWithoutAC extends ConnectionManager {

    /*
     * *
     * Construye un gestor de conexion .
     *
     * @param user , el usuario a emplear en la conexion
     * 
     * @param password , su password
     */

    public ConnectionManagerWithoutAC(String user, String password) {
        super(user, password); // Para construir ConnectionManager
    }

    /*
     * *
     * Ejecuta una secuencia de tareas dada por tasks utilizando
     * 1
     * para cada una los datos en dataArray .
     *
     * PERO con AC a false .
     *
     * 1) Si cualquiera de las tareas falla ( sea por el motivo que sea ) se debe
     * hacer
     * un rollback de todas ellas .
     * 2) Cuando se encuentra un error en una tarea se cancela la ejecucion del
     * resto .
     * 3) Solamente se hace commit al finalizar todas las tareas correctamente .
     * 3) El String retornado debe ser " OK " cuando se completen todas las tareas
     * correctamente y
     * no se haya producido ningun tipo de error .
     * 4) Se debe retornar el mensaje del error de cualquier Exception que se haya
     * producido .
     * Pero no es necesario introducir ningun texto adicional . Si el error es
     * SeriesException
     * si es necesario retornar el mensaje y ` when `.
     */

    @Override
    public String runTask(DataBaseTask[] tasks, String[] dataArray) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url(), user, password);
            conn.setAutoCommit(false);

            for (int i = 0; i < tasks.length; i++) {
                tasks[i].run(conn, dataArray[i]);
            }

            conn.commit();
            return "OK";
        } catch (SQLException e) {
            try {
                if (conn != null)
                    conn.rollback();
            } catch (SQLException ex) {
                return "Rollback failed: " + ex.getMessage();
            }

            if (e.getErrorCode() == 1452) { // Código para violación de FK
                return "Error de clave foránea: " + e.getMessage() +
                        "\nAsegúrate que las series referenciadas existen";
            }
            return e.getMessage();
        } catch (SeriesException e) {
            return e.when() + ":" + e.getMessage();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Error al cerrar conexión: " + e.getMessage());
                }
            }
        }
    }
}
