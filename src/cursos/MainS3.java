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

        System.out.println("--- ConsultaSimple ASC ---");
        StringWriter result2 = cm.run(
                new DataBaseTask[] { new ConsultaSimple() },
                new String[] { "ASC" },
                true);
        System.out.println(result2);

        System.out.println("--- ConsultaConFiltro 'java' ---");
        StringWriter result3 = cm.run(
                new DataBaseTask[] { new ConsultaConFiltro() },
                new String[] { "java" },
                true);
        System.out.println(result3);
    }

    public static void main(String[] args) {
        test();
    }
}
