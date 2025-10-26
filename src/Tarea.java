import java.time.LocalDate; //Importo LocalDate

public class Tarea {
//Tareas deben tener descripción, fecha máxima y prioridad (alta, media, baja)
//Tarea tambien tiene un STATUS = Pendiente o completado

//Atributos
    private String desc; //descripcion
    private LocalDate fecha;  //Fecha maxima
    private String prioridad;
    private String status; //Define el estado de la tarea
    private LocalDate fechaCompletada;

    //Constuctor
    public Tarea(String desc, LocalDate fecha, String prioridad){
        this.desc=desc;
        this.fecha=fecha;
        this.prioridad=prioridad;
        this.fechaCompletada=null;
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
    public void setStatus(String status){this.status=status;}

    public LocalDate getFechaCompletada(){return fechaCompletada;    }
    public void setFechaCompletada(LocalDate fechaCompletada) {this.fechaCompletada = fechaCompletada;}

//METODO to String de tarea
    @Override
    public String toString(){
        String s= "Tarea: "+desc+ "\n-Prioridad: "+ prioridad+"\n-Fecha Maxima: "+ fecha+"\n-Status: "+status;

        // --EN CASO DE QUE LA TAREA ESTE COMPLETADA---
        // Si la tarea está "Completado" Y la fechadeCompletacion no es null, añadimos la fecha
        if (status.equals("Completado") && fechaCompletada != null) {
            s += "\n-Completada el: " + fechaCompletada;
        }

        s += "\n"; //Salto de linea al final

        return s;
    }

    //Metodo para cambiar status (COMPLETAR TAREA)
    public void completarTarea(){
        this.status="Completado"; //Cambia el estado de la tarea
        this.fechaCompletada= LocalDate.now();
    }

    public void descompletarTarea(){
        this.status = "Pendiente";
        this.fechaCompletada=null;
    }

}
