import java.util.Comparator;

// COMPARATOR POR DESCRIPCIÓN
// Busca coincidencias parciales en la descripción de las tareas
// (si la descripción de t1 contiene alguna palabra de t2)

public class ComparatorDescripcion implements Comparator<Tarea> {

    @Override
    public int compare(Tarea t1, Tarea t2) {
        // Convertir ambas descripciones a minúsculas
        String desc1 = t1.getDesc().toLowerCase();
        String desc2 = t2.getDesc().toLowerCase();

        // Si son exactamente iguales, retorna 0
        if (desc1.equals(desc2)) {
            return 0;
        }

        // Si una descripción contiene a la otra, también es coincidencia
        if (desc1.contains(desc2) || desc2.contains(desc1)) {
            return 0;
        }

        // Si son diferentes, retorna 1
        return 1;
    }
}