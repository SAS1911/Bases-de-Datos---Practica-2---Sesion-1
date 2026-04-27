package cursos;

public class MainS2 {

    // Comprobar
    public static void test() {
        // Crea el gestor de conexiones
        BBDDManager cm = new BBDDManager("root", "19112004$Ss");

        // Crear las tareas
        DataBaseTask[] tasks = {
                new InsertaUnaFilaImparte(),
                new InsertaImparteDesdeCSV()
        };
        String[] data = {
                "7, 3, 2, 4, 14/03/2025 ",
                "_imparte.csv"
        };

        // Llamar a run:
        StringWriter result = cm.run(tasks, data, true);
        System.out.println(result);
    }

    public static void main(String[] args) {
        test();
    }
}
