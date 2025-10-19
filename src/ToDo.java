import java.util.LinkedList;
import java.time.LocalDate;
import java.util.ListIterator; //Importar list iterator


//No necesito que hacer ToDo <Tarea> porque no estoy trabajando con Genericos
//Sino que estoy trabajando EXCLUSIVAMENTE con la clase TAREA

public class ToDo {
    //Atributos
    private String nameToDo;
    private LocalDate fechaCreacionL;
    private LinkedList<Tarea> lista; //LinkedList que guarda las Tareas

    //Getters y Setters
    public String getNameToDo(){return nameToDo;}
    public void setNameToDo(String nameToDo){this.nameToDo=nameToDo;}

    public LocalDate getFechaCreacionL(){return fechaCreacionL;}
   //No necesito un Setter para fechaCreacion, eso lo setea el constructor

    public int getSize(){return lista.size();}

    public LinkedList<Tarea> getTareas(){return lista;}  //Getter para acceder a la linkedList del ToDo


    //Constructor - Crea una LinkedList de tareas
    //Se crea una LinkedList vacia
    public ToDo(String nameToDo){
        this.nameToDo=nameToDo;
        lista=new LinkedList<>(); //La lista de tareas empieza vacia
        fechaCreacionL= LocalDate.now(); //Fecha de creacion es igual a la fecha ACTUAL

    }

    //----METODO TO STRING CON LIST ITERATOR-----
    @Override
    public String toString(){
        String s="";
        s= "Nombre de la lista: "+nameToDo+"\n# de Tareas en la lista: "+ lista.size()+"    - Fecha Creacion: "+fechaCreacionL+"\n"+"\n";

        //Creo el iterator
        ListIterator<Tarea> it = lista.listIterator();    //"lista" es el nombre del LinkedList de la clase ToDo

        while(it.hasNext()){
            Tarea t= it.next();   //Al inicio el iterador esta DETRAS del primer elemento

            //s = s +.. CONCATENA lo que habia antes en "s" con lo nuevo
            s= s + t.toString()+"\n ---------------\n";   //Despues de cada tarea saldra esa linea divisora
        }
        return s;
    }

    //-----METODOS-----
    //1) Añadir Tarea
     public void addTarea(Tarea t){
        if(t !=null) {   //Nos aseguramos que la Tarea no sea null
            lista.add(t);
        }
     }

     //2) Marcar todas las tareas como hechas
    public void completarTodo(){
        //Caso1- La lista esta vacia
        if(lista.isEmpty()){
            return; //No hacer nada

        }else{
            //Creo el iterator
            ListIterator<Tarea> it = lista.listIterator();

            while(it.hasNext()){
                Tarea t= it.next(); //La tarea en cuestion
                t.completarTarea(); //Completa la tarea
            }
        }
    }


 /*   //3) Generar una vista de las Tareas pendientes
    public void verPendientes(){
        String s="------Tareas Pendientes-----\n";  //Variable local para almacenar tareas pendientes

        boolean hayPendientes=false; //Variable local para verificar si hayPendientes

        //Caso1- La lista esta vacia
        if(lista.isEmpty()){
            System.out.println("No hay tareas en la Lista");
            return ; //No hacer nada y salir del metodo

        // Caso 2 - La lista no esta vacia
        }else{
            //Creo el iterator
            ListIterator<Tarea> it = lista.listIterator();

            while(it.hasNext()){
                Tarea t= it.next(); //La tarea en cuestion
                if(t.getStatus().equals("Pendiente")){
                    s= s+"-"+t.getDesc()+"\n FechaMaxima: "+t.getFecha()+
                            "\n Prioridad: "+t.getPrioridad()+
                            "\n----------------------------\n";

                    hayPendientes=true; //Cambia el estado de hayPendientes
                }
            }
            if (hayPendientes==false){
                //Esto es distinto de que la lista este vacia
                System.out.println("No hay tareas pendientes");
            }else{
                System.out.println(s);
            }
        }
    }


  */

    //--------------USAR COMPARATOR EJEMPLO EN CLASE-------------
    //3) Generar una vista de las Tareas pendientes
    public LinkedList<Tarea> verPendientes(){
        System.out.println("------Tareas Pendientes-----\n");  //Variable local para almacenar tareas pendientes

            //Creo el iterator
            LinkedList<Tarea> pendientes;
            ListaAvanzada<Tarea> listaAvanzada = new ListaAvanzada<>(lista);  //Una lista avanzada, que encapsula la lista de Tareas

            pendientes= listaAvanzada.buscar(new ComparatorPrioridad(), new Tarea (null,LocalDate.of(2025,10,6),null));
            return pendientes;

    }




























    // 4) Eliminar Tareas ya realizadas - CON ITERATOR
    public void eliminarCompletadas(){
        // Crear el iterator
        ListIterator<Tarea> it = lista.listIterator();

        // Recorrer la lista con el iterator
        while(it.hasNext()){
            Tarea t = it.next(); // La tarea en cuestion

            // Si la tarea está completada
            if(t.getStatus().equals("Completado")){
                it.remove(); // Eliminar usando el metodo remove del iterador
            }
        }
    }

}
