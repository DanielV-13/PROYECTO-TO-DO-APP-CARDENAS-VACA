//MAIN PARA PROBAR COMPARATOOOOR por prioridad

import java.time.LocalDate;
import java.util.LinkedList;

public class Main3 {
    public static void main (String[]args) {

        ToDo universidad = new ToDo("Universidad");

        Tarea t1 = new Tarea("Deber1", LocalDate.of(2025,10,10), "Alta");
        Tarea t2 = new Tarea("Deber2", LocalDate.of(2025,10,10), "Alta");
        Tarea t3 = new Tarea("Deber3", LocalDate.of(2025,10,10), "Alta");
        Tarea t4 = new Tarea("Deber4", LocalDate.of(2025,10,10), "Alta");
        Tarea t5 = new Tarea("Deber5", LocalDate.of(2025,10,10), "Alta");


        universidad.addTarea(t1);
        universidad.addTarea(t2);
        universidad.addTarea(t3);
        universidad.addTarea(t4);
        universidad.addTarea(t5);

        t1.completarTarea();
        t3.completarTarea();
        t5.completarTarea();

        LinkedList<Tarea> pendientesUniversidad= universidad.verPendientes();

        for(Tarea t: pendientesUniversidad){
            System.out.println(t);
        }




    }
}
