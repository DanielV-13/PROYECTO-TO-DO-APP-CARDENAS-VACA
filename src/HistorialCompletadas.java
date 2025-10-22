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
        long diasDiferencia = ChronoUnit.DAYS.between(t.getFechaCompletada(), LocalDate.now());
        if(diasDiferencia > 30){
            mes.add(t);
        } else if(diasDiferencia > 15){
            dias.add(t);
        } else if(diasDiferencia > 7){
            semana.add(t);
        }
    }

    public void eliminarCompletada(Tarea t) {
        if (t.getFechaCompletada() == null) {
            semana.remove(t);
            dias.remove(t);
            mes.remove(t);
        }
    }
}
