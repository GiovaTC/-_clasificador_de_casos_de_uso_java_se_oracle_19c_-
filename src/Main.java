import service.ClasificadorService;

public class Main {

    public static void main(String[] args) {

        ClasificadorService service = new ClasificadorService();

        try {
            service.procesarArchivo("casos_uso_app1.txt");
            service.procesarArchivo("casos_uso_app2.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}