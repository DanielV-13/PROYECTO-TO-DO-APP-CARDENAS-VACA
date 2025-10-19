import java.util.Comparator;

public class ComparatorPrioridad implements Comparator<Tarea> {

    @Override
    public int compare(Tarea t1, Tarea t2) {
        // Ordenar por prioridad: Alta -> Media -> Baja
        int p1 = obtenerValorPrioridad(t1.getPrioridad());
        int p2 = obtenerValorPrioridad(t2.getPrioridad());

        return Integer.compare(p1, p2);
    }

    private int obtenerValorPrioridad(String prioridad) {
        switch (prioridad.toLowerCase()) {
            case "alta": return 1;
            case "media": return 2;
            case "baja": return 3;
            default: return 4;
        }
    }
}
