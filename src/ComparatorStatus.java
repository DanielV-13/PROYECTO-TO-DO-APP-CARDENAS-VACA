import java.util.Comparator;

public class ComparatorStatus implements Comparator<Tarea> {

    @Override
    public int compare(Tarea t1, Tarea t2) {
        if (t1.getStatus().equals(t2.getStatus()) ) {  return 0; //Si son iguales en Status retorne 0
        } else {
            return 1;  //Si son diferentes en Prioridad retorne 1

        }
    }
}