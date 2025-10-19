import java.time.LocalDate;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {

        //Para ingresar la fecha se tiene que usar el Metodo LocalDate.of de la clase LocalDate
        //LocalDate.of (Año, Mes, Dia)

        Tarea t= new Tarea("Sacar perro", LocalDate.of(2025, 10, 31), "Alta");

        System.out.println(t);

        System.out.println("----COMPLETANDO TAREA---");
        t.completarTarea();

        System.out.println(t);

        App aaplicacion= new App();
        aaplicacion.ejecutar();




    }
}