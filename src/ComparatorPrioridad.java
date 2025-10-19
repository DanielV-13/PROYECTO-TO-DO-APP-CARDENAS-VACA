import java.util.Comparator;

//COMPARATOR POR PRIORIDAD
// En este caso no importa si se retorna -1 o 1 para ordenar,
// porque NO estamos ordenando, solo queremos buscar COINCIDENCIA con una plantilla.
// Solo importa el 0 para indicar coincidencia.

public class ComparatorPrioridad implements Comparator<Tarea> {

    @Override
    public int compare(Tarea t1, Tarea t2) {
        // Buscar coincidencia
       if( t1.getPrioridad().equals(t2.getPrioridad() ) ){
           return 0;   //Retorna 0 si son iguales en prioridad
       } else{
           return 1; //Retorna 1 si son distintos en prioridad
       }

    }
 }

