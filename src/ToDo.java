import java.util.LinkedList;
import java.time.LocalDate;
import java.util.ListIterator; //Importar list iterator
import java.util.PriorityQueue; //Importar la Priority Queue para Ordenar por prioridad


//No necesito que hacer ToD0 <Tarea> porque no estoy trabajando con Genericos
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


    //--------------USAR COMPARATOR EJEMPLO EN CLASE-------------
    //3) Generar una vista de las Tareas pendientes (ORDENADAS CON PRIORITYQUEUE)
    public LinkedList<Tarea> verPendientes(){
        System.out.println("------Tareas Pendientes (Ordenadas por Prioridad) -----\n");  //Variable local para almacenar tareas pendientes

        //Creo la lista que va a guardar las tareas pendientes en Desorden
            LinkedList<Tarea> pendientesDesordenados;

            //RECORDAR: "lista" es el atributo de la clase ToD0 que guarda la lista de Tareas
            ListaAvanzada<Tarea> listaAvanzada = new ListaAvanzada<>(lista);  //Una lista avanzada, que encapsula la lista de Tareas

            //Tarea PLANTILLA
            Tarea plantilla= new Tarea(null,null,null);
            plantilla.setStatus("Pendiente"); //Aunque por defecto, el status ya es "Pendiente"

            //1) Buscamos todas las tareas pendientes
            pendientesDesordenados= listaAvanzada.buscar(new ComparatorStatus(), plantilla);


            //----- 2) Ahora ordenamos la lista de pendientes, usando una COLA DE PRIORIDAD-----
            PriorityQueue<Tarea> colaOrdenada = new PriorityQueue<>(new ComparatorPrioridadOrden()); //Recibe el ComparadorPrioridadOrden

            //Añadir tareas pendientes desordenadas a la Cola
            for(Tarea E:pendientesDesordenados){
                colaOrdenada.add(E);  //La cola va organizando las tareas internamente con el COMPARADORPrioridadOrdenar
            }

            //LinkedList que va a retornar el Metodo
            LinkedList<Tarea> pendientesOrdenados= new LinkedList<>();

            //Vaciamos la cola y ponemos las tareas ordenadas en la LinkedList
            while(!colaOrdenada.isEmpty()){
                pendientesOrdenados.add(colaOrdenada.poll());
            }

            return pendientesOrdenados; //Retorno lista con pendientes Ordenados
    }

    //4) Generar una vista de las Tareas Completadas
    public LinkedList<Tarea> verCompletadas(){
        System.out.println("------Tareas Completadas-----\n");  //Variable local para almacenar tareas pendientes

        //Creo la lista que va a devolver el metodo
        LinkedList<Tarea> completadas;
        //RECORDAR: "lista" es el atributo de la clase ToD0 que guarda la lista de Tareas
        ListaAvanzada<Tarea> listaAvanzada = new ListaAvanzada<>(lista);  //Una lista avanzada, que encapsula la lista de Tareas

        //Tarea PLANTILLA
        Tarea plantilla= new Tarea(null,null,null);
        plantilla.setStatus("Completado");

        completadas= listaAvanzada.buscar(new ComparatorStatus(), plantilla);
        return completadas;

    }

    //5) Generar una vista de las Tareas de una Prioridad dada
    public LinkedList<Tarea> verPrioridad(String prioridadBuscada){

        //Creo la lista que va a devolver el metodo
        LinkedList<Tarea> resultado;
        //RECORDAR: "lista" es el atributo de la clase ToDo que guarda la lista de Tareas
        ListaAvanzada<Tarea> listaAvanzada = new ListaAvanzada<>(lista);  //Una lista avanzada, que encapsula la lista de Tareas

        //Tarea PLANTILLA
        Tarea plantilla= new Tarea(null,null,null);
        plantilla.setPrioridad(prioridadBuscada);

        resultado= listaAvanzada.buscar(new ComparatorPrioridad(), plantilla);
        return resultado;

    }

    public void eliminarCompletadas(){
        //Creo la lista con las tareas completadas del To-Do
        LinkedList<Tarea> completadas = this.verCompletadas();

        // Crear el iterator para recorrer el To-Do original
        ListIterator<Tarea> original = lista.listIterator();

        // Recorrer la lista de tareas con el iterator
        while(original.hasNext()){
            Tarea t = original.next(); // La tarea en cuestion

            for(Tarea completada: completadas) {
                if(t == completada){
                    original.remove();
                    break; // Agregar break para salir del for después de eliminar
                }
            }
        }
    }

}
