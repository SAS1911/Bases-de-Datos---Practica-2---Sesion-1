package series;

import java.sql.*;

public class ConnectionManager {
    private final String user;
    private final String password;
    private final String url;

    public ConnectionManager(String user, String password) {
        this.user = user;
        this.password = password;
        this.url = "jdbc:mysql://localhost:3306/series";
    }

    public String url() {
        return this.url;
    }

    public String runTask(DataBaseTask[] tasks, String[] dataArray) {
        Connection conn = null;
        try {
            // Conexión básica sin parámetros adicionales
            conn = DriverManager.getConnection(url, user, password);

            // Ejecutar tareas
            for (int i = 0; i < tasks.length; i++) {
                String data = (i < dataArray.length) ? dataArray[i] : "";
                tasks[i].run(conn, data);
            }
            return "OK";
        } catch (SQLException e) {
            return "SQL:" + e.getMessage();
        } catch (SeriesException e) {
            return "Task:" + e.when() + ":" + e.getMessage();
        } catch (Exception e) {
            return "Otro:" + e.getMessage();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Error al cerrar conexión: " + e.getMessage());
                }
            }
        }
    }
}