package series;

import java.sql.*;

public class ConnectionManagerWithoutAC extends ConnectionManager {

    public ConnectionManagerWithoutAC(String user, String password) {
        super(user, password);
    }

    @Override
    public String runTask(DataBaseTask[] tasks, String[] dataArray) {
        Connection connection = null;
        try {
            // 1) Abrir una conexión con auto-commit desactivado
            connection = DriverManager.getConnection(url(), super.user, super.password);
            connection.setAutoCommit(false);

            // 2) Ejecutar todas las tareas en una transacción
            for (int i = 0; i < tasks.length; i++) {
                try {
                    tasks[i].run(connection, dataArray[i]);
                } catch (SeriesException e) {
                    // Rollback si hay error en alguna tarea
                    connection.rollback();
                    return e.when() + " " + e.getMessage();
                } catch (Exception e) {
                    // Rollback para cualquier otro error
                    connection.rollback();
                    return e.getMessage();
                }
            }

            // 3) Commit si todo fue bien
            connection.commit();
            return "OK";

        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                // Si falla el rollback, devolvemos el error original
            }
            return e.getMessage();
        } finally {
            // 4) Cerrar la conexión si se abrió
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // No hacemos nada si falla el cierre
                }
            }
        }
    }
}