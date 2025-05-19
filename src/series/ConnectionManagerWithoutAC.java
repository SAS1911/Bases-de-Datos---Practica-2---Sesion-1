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

	/* *
	* Construye un gestor de conexion .
	*
	* @param user , el usuario a emplear en la conexion
	* @param password , su password
	*/

    public ConnectionManagerWithoutAC(String user, String password) {
        super(user, password); // Para construir ConnectionManager
    }

    /* *
    * Ejecuta una secuencia de tareas dada por tasks utilizando
    1
    * para cada una los datos en dataArray .
    *
    * PERO con AC a false .
    *
    * 1) Si cualquiera de las tareas falla ( sea por el motivo que sea ) se debe hacer
    * un rollback de todas ellas .
    * 2) Cuando se encuentra un error en una tarea se cancela la ejecucion del resto .
    * 3) Solamente se hace commit al finalizar todas las tareas correctamente .
    * 3) El String retornado debe ser " OK " cuando se completen todas las tareas correctamente y
    * no se haya producido ningun tipo de error .
    * 4) Se debe retornar el mensaje del error de cualquier Exception que se haya producido .
    * Pero no es necesario introducir ningun texto adicional . Si el error es SeriesException
    * si es necesario retornar el mensaje y ` when `.
    */
    
    @Override
    public String runTask(DataBaseTask[] tasks, String[] dataArray) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url(), super.user, super.password);
            connection.setAutoCommit(false);
            for (int i = 0; i < tasks.length; i++) {
                try {
                    tasks[i].run(connection, dataArray[i]);
                } catch (SeriesException e) {
                    connection.rollback();
                    return e.when() + " " + e.getMessage();
                } catch (Exception e) {
                    connection.rollback();
                    return e.getMessage();
                }
            }

            connection.commit();
            return "OK";

        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {

            }
            return e.getMessage();
        } finally {

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {

                }
            }
        }
    }
}

