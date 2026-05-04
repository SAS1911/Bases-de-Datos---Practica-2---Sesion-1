package cursos;

public class MainS3 {

    public static void test() {
        BBDDManager cm = new BBDDManager("alumno", "bbdd-upm");

        System.out.println("--- AddColumn ---");
        StringWriter result1 = cm.run(
                new DataBaseTask[] { new AddColumn() },
                new String[] { "" },
                true);
        System.out.println(result1);

        System.out.println("--- ConsultaSimple ---");
        ConsultaSimple consultaSimple = new ConsultaSimple();
        StringWriter result2 = cm.run(
                new DataBaseTask[] { consultaSimple },
                new String[] { "ASC" },
                true);
        System.out.println(result2);
        System.out.println(consultaSimple.get());

        System.out.println("--- ConsultaConFiltro ---");
        ConsultaConFiltro consultaFiltro = new ConsultaConFiltro();
        StringWriter result3 = cm.run(
                new DataBaseTask[] { consultaFiltro },
                new String[] { "java" },
                true);
        System.out.println(result3);
        System.out.println(consultaFiltro.get());
    }

    public static void main(String[] args) {
        test();
    }
}
