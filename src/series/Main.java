package series;

public class Main {

    // Comprobar
    public static void test() {
        // Crea el gestor de conexiones
        ConnectionManager cm = new ConnectionManager("root","bases2005");

        // Crear las tareas
        DataBaseTask[] tasks = {
            // Descomenta esta linea.
            new InsertaUsuarioConFoto(),
            new ConsultaTemporadasIncompletas()
        };
        String[] data = {
            "1,Juan,Boadilla,del Monte,C:\\Users\\Usuario\\OneDrive\\Documentos\\Prac2\\series\\data\\foto.jpg", "" };

        // Llamar a run:
        String ok = cm.runTask(tasks, data);
        System.out.println(ok);

        ConsultaTemporadasIncompletas c = (ConsultaTemporadasIncompletas) tasks[1];
        System.out.println(c.get());
    }

    public static void main(String[] args) {
        test();
    }
}


