package cursos;

public class MainS4 {

    public static void test() {
        BBDDManager cm = new BBDDManager("root", "19112004$Ss");

        System.out.println("--- ConsultaBlob ---");
        StringWriter resultBlob = cm.run(
                new DataBaseTask[] { new ConsultaBlob() },
                new String[] { "Edificio Central" },
                true);
        System.out.println(resultBlob);

        System.out.println("--- ConsultaCompleja ---");
        ConsultaCompleja consulta = new ConsultaCompleja();
        StringWriter resultCompleja = cm.run(
                new DataBaseTask[] { consulta },
                new String[] { "30" },
                true);
        System.out.println(resultCompleja);
        System.out.println(consulta.get());
    }

    public static void main(String[] args) {
        test();
    }
}
