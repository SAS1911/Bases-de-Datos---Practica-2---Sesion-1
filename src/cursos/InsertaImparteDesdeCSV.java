package cursos;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class InsertaImparteDesdeCSV implements DataBaseTask {

    @Override
    public void run(Connection conn, String data) throws BBDDException, SQLException {
        String sql = "INSERT INTO imparte (profesor_id, curso_id, n_modulo, aula_id, fecha) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pst = conn.prepareStatement(sql);
                Scanner scanner = new Scanner(new FileInputStream(data))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty())
                    continue;
                String[] parts = line.split(",");
                int profesorId = Integer.parseInt(parts[0].trim());
                int cursoId = Integer.parseInt(parts[1].trim());
                int nModulo = Integer.parseInt(parts[2].trim());
                int aulaId = Integer.parseInt(parts[3].trim());
                String fechaStr = parts[4].trim();
                String[] fechaParts = fechaStr.split("-");
                int anho = Integer.parseInt(fechaParts[0]);
                int mes = Integer.parseInt(fechaParts[1]);
                int dia = Integer.parseInt(fechaParts[2]);
                LocalDate localDate = LocalDate.of(anho, mes, dia);
                Date fecha = Date.valueOf(localDate);

                pst.setInt(1, profesorId);
                pst.setInt(2, cursoId);
                pst.setInt(3, nModulo);
                pst.setInt(4, aulaId);
                pst.setDate(5, fecha);
                int filas = pst.executeUpdate();
                if (filas != 1) {
                    throw new SQLException("Se esperaba 1 fila insertada, pero se insertaron " + filas);
                }
            }
        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw new BBDDException(e, "Insertando");
        }
    }
}