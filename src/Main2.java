import java.time.LocalDate;
import java.util.*;

public class Main2 {
    public static void main(String [] args){

        Tarea t1= new Tarea("Sacar perro", LocalDate.of(2025, 10, 31), "Alta");
        Tarea t2= new Tarea("Limpiar cocina", LocalDate.of(2025, 10, 5), "Baja");
        Tarea t3= new Tarea("Arreglar Carro", LocalDate.of(2025, 10, 15), "Media");

        ToDo Casa= new ToDo("Casa");

        Casa.addTarea(t1);
        Casa.addTarea(t2);
        Casa.addTarea(t3);

        System.out.println(Casa);

     /*   Casa.completarTodo();
        //Casa.eliminarCompletadas();


        Casa.addTarea(new Tarea("Pintar pared", LocalDate.of(2025,10,8), "Alta"));


        Casa.verPendientes();
*/
    }
}
