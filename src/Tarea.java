import java.time.LocalDate; //Importo LocalDate
import java.util.LinkedList;

public class Tarea {
//Tareas deben tener descripción, fecha máxima y prioridad (alta, media, baja)
//Tarea tambien tiene un STATUS = Pendiente o completado

//Atributos
    private String desc; //descripcion
    private LocalDate fecha;  //Fecha maxima
    private String prioridad;
    private String status; //Define el estado de la tarea

    //Constuctor
    public Tarea(String desc, LocalDate fecha, String prioridad){
        this.desc=desc;
        this.fecha=fecha;
        this.prioridad=prioridad;
        status="Pendiente"; //El estado de una tarea por defecto es "Pendiente"
    }

    //Metodos Getters y Setter

    public String getDesc(){return desc;    }
    public void setDesc(String desc){this.desc=desc;}

    public LocalDate getFecha(){return fecha;    }
    public void setFecha(LocalDate fecha){this.fecha=fecha;}

    public String getPrioridad(){return prioridad;    }
    public void setPrioridad(String prioridad){this.prioridad=prioridad;}

    public String getStatus(){return status;    }
    //No necesito crear un setEstado - no me conviene para este ejercicio


//METODO to String de tarea
    @Override
    public String toString(){
        String s= "Tarea: "+desc+ "\n-Prioridad: "+ prioridad+"\n-Fecha Maxima: "+ fecha+"\n-Status: "+status;

        return s;
    }

    //Metodo para cambiar status
    public void completarTarea(){
        this.status="Completado"; //Cambia el estado de la tarea
    }

    public void descompletarTarea(){
        this.status = "Pendiente";
    }


    //--------METODO PARA COMPARATOR / METODO VISTA DE TAREAS PENDIENTES---------
    public static void mostrarTareasPendientesOrdenadas(LinkedList<Tarea> listaTareas) {
        // Filtrar las pendientes
        LinkedList<Tarea> pendientes = new LinkedList<>();

        for (Tarea t : listaTareas) {
            if (t.getStatus().equalsIgnoreCase("Pendiente")) {
                pendientes.add(t);
            }
        }

        // Ordenar por prioridad usando ComparatorPrioridad
        pendientes.sort(new ComparatorPrioridad());

        // Mostrar
        System.out.println("\n--- TAREAS PENDIENTES ORDENADAS POR PRIORIDAD ---");
        if (pendientes.isEmpty()) {
            System.out.println("No hay tareas pendientes.");
            return; //Salir de Metodo
        }

        for (Tarea t : pendientes) {
            System.out.println("- " + t.getDesc() + " | Prioridad: " + t.getPrioridad() +
                    " | Fecha: " + t.getFecha());
        }
    }







}
