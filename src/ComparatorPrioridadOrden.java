import java.util.Comparator;

//Estructura de un comparator cualquiera
// t1< t2 -- returns -1 o negativo
// t1 == t2 -- returns 0
// t1 > t2 -- returns 1 o positivo

//COMPARATOR POR PRIORIDAD, ahora SIRVE PARA ORDENAR
// Este comparador SÍ sirve para ORDENAR,
// definiendo una jerarquía (Alta=1, Media=2, Baja=3)

//Este comparator va a comparar <TAREAS>
public class ComparatorPrioridadOrden implements Comparator<Tarea> {


     // Asigna un valor numérico a cada prioridad para poder compararlas.
     //Un número más bajo significa mayor prioridad.

    private int getPrioridadValor(String prioridad) {
        // Como se valida la entrada de prioridad en App.java, podemos confiar
        // en que el String será siempre "Alta", "Media", o "Baja".
        switch (prioridad) {
            case "Alta":
                return 1;
            case "Media":
                return 2;
            case "Baja":
                return 3;
            default:
                return 4; //Nunca se dara el caso que sea 4
        }
    }

    @Override
    public int compare(Tarea t1, Tarea t2) {
        // Obtenemos el valor numérico de cada tarea
        int prioridadT1 = getPrioridadValor(t1.getPrioridad());
        int prioridadT2 = getPrioridadValor(t2.getPrioridad());

        if (prioridadT1 < prioridadT2) {
            return -1; // a<b = -1
        } else if (prioridadT1 > prioridadT2) {
            return 1; // a>b = 1
        } else {  //a = b  --0
            return 0; // Son iguales
        }
    }
}