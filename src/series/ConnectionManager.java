package series;

import java.sql.*;

public class ConnectionManager {
    private final String user;
    private final String password;
    private final String url;

    public ConnectionManager(String user, String password) {
        this.user = user;
        this.password = password;
        this.url = "jdbc:mysql://localhost:3306/Series?useSSL=false&serverTimezone=UTC";
    }

    public String url() {
        return this.url;
    }

    public String runTask(DataBaseTask[] tasks, String[] dataArray) {
        Connection conn = null;
        try {
            // Registrar el driver (necesario para versiones recientes)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 1) Abrir conexión con parámetros adicionales para MySQL 8+
            conn = DriverManager.getConnection(url(), user, password);

            // 2) Ejecutar todas las tareas
            for (int i = 0; i < tasks.length; i++) {
                if (i < dataArray.length) {
                    tasks[i].run(conn, dataArray[i]);
                } else {
                    tasks[i].run(conn, "");
                }
            }

            return "OK";
        } catch (ClassNotFoundException e) {
            return "Otro:Driver no encontrado: " + e.getMessage();
        } catch (SQLException e) {
            return "SQL:" + e.getMessage();
        } catch (SeriesException e) {
            return "Task:" + e.when() + ":" + e.getMessage();
        } catch (Exception e) {
            return "Otro:" + e.getMessage();
        } finally {
            // 3) Cerrar conexión
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    // Ignorar error al cerrar
                }
            }
        }
    }
}
