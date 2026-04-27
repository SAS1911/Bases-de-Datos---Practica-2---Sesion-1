package cursos;

import java.sql.*;

public class BBDDManager {

    protected final String user;
    protected final String password;
    private final String url;

    public BBDDManager(String user, String password) {
        this.user = user;
        this.password = password;
        this.url = "jdbc:mysql://localhost:3306/cursos_db";
    }

    public String url() {
        return this.url;
    }

    public StringWriter run(DataBaseTask[] tasks, String[] dataArray, boolean autoCommit) {
        StringWriter result = new StringWriter();
        try (Connection conn = DriverManager.getConnection(this.url, this.user, this.password)) {
            conn.setAutoCommit(autoCommit);
            for (int i = 0; i < tasks.length; i++) {
                try {
                    tasks[i].run(conn, dataArray[i]);
                    if (!autoCommit) {
                        conn.commit();
                    }
                } catch (BBDDException e) {
                    result.add("Task:" + e.when() + ";" + e.getMessage() + ";");
                    if (!autoCommit) {
                        conn.commit();
                    }
                } catch (SQLException e) {
                    result.add("SQL:" + e.getMessage() + ";");
                    if (!autoCommit) {
                        conn.rollback();
                    }
                }
            }
        } catch (SQLException e) {
            result.add("Connection:" + e.getMessage() + ";");
        } catch (Exception e) {
            result.add("Otro:" + e.getMessage() + ";");
        }
        result.add("fin");
        return result;
    }
}
