import java.time.LocalDate;
import java.util.LinkedList;
import java.time.temporal.ChronoUnit;

public class HistorialCompletadas {
    private LinkedList<Tarea> semana;
    private LinkedList<Tarea> dias;
    private LinkedList<Tarea> mes;

    public HistorialCompletadas(){
        this.semana = new LinkedList<>();
        this.dias = new LinkedList<>();
        this.mes = new LinkedList<>();
    }

    public void agregarCompletada(Tarea t){

        // Asegurarse de que la fecha de Completacion no sea null
        if (t.getFechaCompletada() == null) {
            return; // No se puede procesar si no tiene fecha
        }

        long diasDiferencia = ChronoUnit.DAYS.between(t.getFechaCompletada(), LocalDate.now());

        // --- Ver en que Rango de Completo la tarea---
        // Se comprueba desde el rango más pequeño al más grande.

        if (diasDiferencia <= 7) {
            semana.add(t); // Tareas de 0-7 días
        } else if (diasDiferencia <= 15) {
            dias.add(t);   // Tareas de 8-15 días
        } else if (diasDiferencia <= 30) {
            mes.add(t);    // Tareas de 16-30 días
        }
        // --Las tareas de más de 30 días no se añaden a ninguna lista--
    }



     //Elimina una tarea de las listas del historial (semana, dias, mes).
     //Este métod0 debe llamarse DESPUÉS de que la tarea haya sido marcada
     //como "Pendiente" nuevamente (es decir, después de llamar a t.descompletarTarea()).
    public void eliminarCompletada(Tarea t) {

        boolean removidoSemana = semana.remove(t);  // El métod0 .remove() no da error si el elemento no existe.
        boolean removidoDias = dias.remove(t);
        boolean removidoMes = mes.remove(t);

    }

    @Override
    public String toString() {
        String texto = "===== HISTORIAL DE TAREAS COMPLETADAS =====\n";
        texto += "\n-- Última semana --\n";
        if (semana.isEmpty()) {
            texto += "No hay tareas completadas en la última semana.\n";
        } else {
            for (Tarea t : semana) {
                texto += t + "\n";
            }
        }
        texto += "\n-- Últimos 15 días --\n";
        if (dias.isEmpty()) {
            texto += "No hay tareas completadas en los últimos 15 días.\n";
        } else {
            for (Tarea t : dias) {
                texto += t + "\n";
            }
        }
        texto += "\n-- Último mes --\n";
        if (mes.isEmpty()) {
            texto += "No hay tareas completadas en el último mes.\n";
        } else {
            for (Tarea t : mes) {
                texto += t + "\n";
            }
        }
        return texto;
    }
}
