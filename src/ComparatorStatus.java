import java.util.Comparator;
//Estructura de un comparator cualquiera
// t1< t2 -- returns -1 o negativo
// t1 == t2 -- returns 0
// t1 > t2 -- returns 1 o positivo

//COMPARATOR POR STATUS
// En este caso no importa si se retorna -1 o 1 para ordenar,
// porque NO estamos ordenando, solo queremos buscar COINCIDENCIA con una plantilla.
// Solo importa el 0 para indicar coincidencia.
public class ComparatorStatus implements Comparator<Tarea> {

    @Override
    public int compare(Tarea t1, Tarea t2){
        // Si tienen el mismo status--- retorna 0 (COINCIDEN)
        if(t1.getStatus().equals(t2.getStatus())){
            return 0;
        }
        // Si son diferentes -- retorna 1 (NO COINCIDEN)
        return 1;
    }
}
