package series;

import java.sql.*;

public class ConnectionManager {

    protected final String user;
    protected final String password;
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
            conn = DriverManager.getConnection(this.url, this.user, this.password);
            for (int i = 0; i < tasks.length; i++) {
                try {
                    tasks[i].run(conn, dataArray[i]);
                } catch (SeriesException e) {
                    return "Task:" + e.when() + "\t" + e.getMessage();
                }
            }
            return "OK";
        } catch (SQLException e) {
            return "SQL:" + e.getMessage();
        } catch (Exception e) {
            return "Otro:" + e.getMessage();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ignore) {
                    // Si falla el close, no interrumpe el flujo
                }
            }
        }
    }
}