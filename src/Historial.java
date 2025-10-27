import java.util.Stack;
import java.util.LinkedList;

public class Historial {
    private Stack<LinkedList<ToDo>> pilaHistorial;
    private Stack<LinkedList<ToDo>> pilaRehacer;
    private int limite;

    public Historial(){
        this.pilaHistorial = new Stack<>();
        this.pilaRehacer = new Stack<>();
        this.limite = 20;
    }

    public void guardarEstado(LinkedList<ToDo> listaToDos) {
        LinkedList<ToDo> copia = copiarLista(listaToDos);
        pilaHistorial.push(copia);
        if (pilaHistorial.size() > limite) {
            pilaHistorial.remove(0);
        }
    }

    public LinkedList<ToDo> deshacer() {
        if (pilaHistorial.size() <= 1) {
            System.out.println("No hay cambios para deshacer");
            return null;
        }
        pilaRehacer.push(pilaHistorial.pop());
        LinkedList<ToDo> estadoAnterior = pilaHistorial.peek();
        System.out.println("Cambio deshecho (quedan " + pilaHistorial.size() + " en historial)");
        return copiarLista(estadoAnterior);
    }

    public LinkedList<ToDo> rehacer() {
        if (pilaRehacer.size() == 0) {
            System.out.println("No hay cambios para rehacer");
            return null;
        }
        LinkedList<ToDo> estadoSiguiente = pilaRehacer.pop();
        pilaHistorial.push(estadoSiguiente);
        System.out.println("Cambio rehecho");
        return copiarLista(estadoSiguiente);
    }

    public void limpiarHistorial() {
        pilaHistorial.clear();
        System.out.println("Historial limpiado");
    }

    private LinkedList<ToDo> copiarLista(LinkedList<ToDo> original) {
        LinkedList<ToDo> copia = new LinkedList<>();
        for (ToDo todo : original) {
            ToDo todoCopia = new ToDo(todo.getNameToDo());
            for (Tarea tarea : todo.getTareas()) {
                Tarea tareaCopia = new Tarea(tarea.getDesc(), tarea.getFecha(), tarea.getPrioridad());
                tareaCopia.setStatus(tarea.getStatus());
                todoCopia.addTarea(tareaCopia);
            }
            copia.add(todoCopia);
        }
        return copia;
    }
}
