import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner; //importar el scanner para ingreso de datos
import java.time.LocalDate;


public class App {
    //------Atributos de la clase---------

    private LinkedList<ToDo> listaToDos; //Linked List que guarda ToDos
    private Scanner sc; //Objeto de tipo Scanner

    //--------Constructor-----------
    public App(){
        listaToDos= new LinkedList<>();  //Creamos la nueva linkedList
        sc = new Scanner(System.in); //
        System.out.println("-------APLICACION INICIADA-------");
    }


    //------METODOS DE AUXILIARES PARA LA APP-----
//Metodos Private que se usaran para otros metodos

    //*** 1) BUSCAR TODO POR NOMBRE
    //Retorna el ToDo , null si no existe
    //Parametros: Nombre del ToDo

    private ToDo buscarToDoNombre(String nombre){
       //Crear Iterator para recorrer la lista
        ListIterator<ToDo> it= listaToDos.listIterator();

        //--Bucle--
        while(it.hasNext()){
            ToDo i = it.next(); //ToDo actual
            //Comparar nombres (ignorando mayusculas)
            if(i.getNameToDo().equalsIgnoreCase(nombre)){
                return i; //Devuelve el ToDo
            }
        }
        return null; //En caso que no se haya encontrado el nombre del ToDo
    }

    //*** 2) VERIFICAR SI EXISTE UN TODO CON ESE NOMBRE
    //Devuelve un booleano
    private boolean existeToDo(String nombre){
                        //buscarToDo devuelve un ToDo o un null
                        //ToDo != null == TRUE -- En caso de encontrarlo
                        //null != null == FALSE -- En caso de no encontrarlo
        boolean existe= buscarToDoNombre(nombre) !=null;
        return existe;
    }


    //------------METODOS PRINCIPALES---------
    //*** 1) Crear un Nuevo ToDo
    public void crearNuevoToDo(){
        System.out.println("\nIngrese nombre del nuevo ToDo: ");
        String nombre= sc.nextLine();

        //Verificar si ya existe un todo con ese nombre
        if(existeToDo(nombre)){
            System.out.println("Ya existe un ToDo con ese nombre");
            return; //Salir del metodo
        }

        //Else - no existe un ToDo con ese nombre
        ToDo nuevoToDo= new ToDo(nombre);

        listaToDos.add(nuevoToDo); //Agregar al final de la lista

        System.out.println("-ToDo " + nombre + " creado exitosamente");

    }

    //*** 2) VER TODOS LOS TODOS
        public void verToDos(){
            System.out.println("\n----ToDos Actuales----");

            //Caso 1 : No hay ToDo
            if(listaToDos.isEmpty()){
                System.out.println(" No hay ningun ToDo creado ");
                System.out.println("----------------\n");
                return; //Salir del metodo
            }
            //Caso 2: Recorrer la lista de ToDos con iterador

            ListIterator<ToDo> it = listaToDos.listIterator();
            int contador=1; //Contador para enumerar

            //--Bucle--

            while(it.hasNext()){
                ToDo actual= it.next(); //ToDo actual
                System.out.println(contador + ") " + actual.getNameToDo() +
                        " (" + actual.getSize() + " tareas)");

                contador++; //Aumentar el contador
            }

            System.out.println("----------------------\n");
        }

        //*** 3) CREAR Y AGREGAR UNA TAREA A UN TODO EN LA APP
        public void crearTarea(){
            //Paso 1: Verificar que haya ToDos disponibles
            if(listaToDos.isEmpty()){
                System.out.println("No hay nigun ToDo al cual agregar Tareas\n");
                System.out.println("Cree un ToDo primero\n");
                return; //Sale del metodo
            }

            //Paso 2: Mostrar ToDos disponibles para agregar tareas
            verToDos();

            //Paso 3: Seleccionar el ToDo donde agregar la tarea
            System.out.println("Ingrese el nombre del ToDo donde quiere ingresar una Tarea: ");
            String nombreToDo =sc.nextLine();

            //Buscar si existe un ToDo con ese nombre en la App
            if(existeToDo(nombreToDo)==false){
                System.out.println("Porfavor ingrese un nombre de ToDo valido\n");
                return; //Sale del metodo
            }

            //Como ya verifique que exisita el ToDo
            //Puedo buscarlo por nombre
            ToDo toDoselect = buscarToDoNombre(nombreToDo);

            //En caso de que el ToDo existe, pedir datos de la Tarea a ingresar
            System.out.println("\nProporcione datos de la Tarea a agregar-");

            // Pedir descripción
            System.out.print("\nIngrese la descripción de la tarea: ");
            String desc = sc.nextLine();

            // Pedir fecha (año, mes, día)
            System.out.println("\nIngrese la FECHA MAXIMA de vencimiento de la tarea");
            System.out.print("Ingrese el año (ej: 2025): ");
            int año = sc.nextInt();
            System.out.print("Ingrese el mes (1-12): ");
            int mes = sc.nextInt();
            System.out.print("Ingrese el día (1-31): ");
            int dia = sc.nextInt();
            sc.nextLine(); // Limpiar buffer para evitar errores

            LocalDate fecha = LocalDate.of(año, mes, dia);

            // Pedir prioridad
            System.out.print("Ingrese la prioridad (Alta/Media/Baja): ");
            String prioridad = sc.nextLine();

            //Crear nueva Tarea y agregarla
            Tarea nuevaTarea=new Tarea(desc,fecha,prioridad);
            toDoselect.addTarea(nuevaTarea);

            System.out.println("Tarea- " + desc + " -agregada exitosamente a- " + nombreToDo + "");
        }



    //*** 4) Ver todas las tareas de un ToDo específico
    public void verTareasDeToDo(){
        // Verificar que haya ToDos
        if(listaToDos.isEmpty()){
            System.out.println("\nNo hay listas de ToDo disponibles.");
            return; //Sale del Metodo
        }

        // Mostrar ToDos disponibles
        verToDos();

        // Pedir cuál ToDo ver
        System.out.print("Ingrese el nombre del ToDo a visualizar: ");
        String nombreToDo = sc.nextLine();

        //Buscar si existe un ToDo con ese nombre en la App
        if(existeToDo(nombreToDo)==false){
            System.out.println("Porfavor ingrese un nombre de ToDo valido\n");
            return; //Sale del metodo
        }

        //Como existe el ToDo puedo buscarlo por nombre
        ToDo toDoselect = buscarToDoNombre(nombreToDo);

        // Mostrar el ToDo completo
        System.out.println("\n" + toDoselect.toString());
    }


    //*** 5) MÉTODO OBLIGATORIO - Recuperar solo las tareas de una prioridad dada de todos los ToDos
    public void verTareasPorPrioridad(){
        System.out.print("\nIngrese la prioridad a buscar (Alta/Media/Baja): ");
        String prioridadBuscada = sc.nextLine();

        System.out.println("\n------ TAREAS CON PRIORIDAD: " + prioridadBuscada + " ----------");

        boolean encontroTareas = false; // Variable local para verificar si hay TAREAS en algun ToDO



        // Recorrer TODOS los ToDos con iterator
        ListIterator<ToDo> itToDos = listaToDos.listIterator();

        while(itToDos.hasNext()){
            ToDo toDo = itToDos.next(); // ToDo actual

            System.out.println("\n--- Lista: " + toDo.getNameToDo() + " ---");

            boolean hayEnEsteLista = false;  //Variable local para ver si hay tareas en ese ToDo

            // Obtener las tareas(LinkedList) de este ToDo
            LinkedList<Tarea> tareas = toDo.getTareas(); //getTareas, obtiene la LinkedList del toDo

            tareas.sort(new ComparatorPrioridad()); // Ordena por prioridad antes de mostrar

            //Iterador para recorrer las tareas
            ListIterator<Tarea> itTareas = tareas.listIterator();

            // Recorrer todas las tareas de este ToDo
            while(itTareas.hasNext()){
                Tarea tarea = itTareas.next();

                // Comparar prioridad de cada tarea
                if(tarea.getPrioridad().equalsIgnoreCase(prioridadBuscada)){
                    System.out.println("  - " + tarea.getDesc());
                    System.out.println("    Fecha: " + tarea.getFecha() +
                            " | Status: " + tarea.getStatus());
                    hayEnEsteLista = true;  //Tarea con prioridad buscada encontrada
                    encontroTareas = true;  //Tareas en general encontradaas
                }
            }

            // Si no hay tareas con esa prioridad en esta lista
            if(!hayEnEsteLista){
                System.out.println("  No hay tareas con esa prioridad en esta lista.");
            }
        }

        //SALIENDO DEL WHILE
        // Si no se encontraron tareas en ningún ToDo
        if(!encontroTareas){
            System.out.println("\nNo se encontraron tareas con la prioridad '" + prioridadBuscada + " en nigun ToDO");
        }

        System.out.println("----------------------------------------------------\n");
    }




    //*** 6) MÉTODO OBLIGATORIO - Recuperar todas las tareas marcadas como hechas de todos los ToDO
    public void verTodasLasCompletadas(){
        System.out.println("\n-------TODAS LAS TAREAS COMPLETADAS-----------");

        boolean hayCompletadas = false; // Variable bandera

        // Recorrer TODOS los ToDos con iterator
        ListIterator<ToDo> itToDos = listaToDos.listIterator();

        while(itToDos.hasNext()){
            ToDo toDo = itToDos.next(); // ToDo actual

            System.out.println("\n--- Lista: " + toDo.getNameToDo() + " ---");

            boolean hayEnEsteLista = false;

            // Obtener las tareas de este ToDo
            LinkedList<Tarea> tareas = toDo.getTareas();
            tareas.sort(new ComparatorStatus()); // Ordenar por status antes de recorrer
            ListIterator<Tarea> itTareas = tareas.listIterator();

            // Recorrer todas las tareas buscando completadas
            while(itTareas.hasNext()){
                Tarea tarea = itTareas.next();

                // Si la tarea está completada
                if(tarea.getStatus().equals("Completado")){

                    System.out.println("  - " + tarea.getDesc());
                    System.out.println("    Prioridad: " + tarea.getPrioridad() +
                            " | Fecha: " + tarea.getFecha());
                    hayEnEsteLista = true;
                    hayCompletadas = true;
                }
            }

            // Si no hay completadas en esta lista
            if(!hayEnEsteLista){
                System.out.println("  No hay tareas completadas en esta lista.");
            }
        }

        // Si no hay completadas en ningún ToDo
        if(!hayCompletadas){
            System.out.println("\nNo hay tareas completadas en ningún ToDo.");
        }

        System.out.println("---------------------------------------\n");
    }


    //*** 7) Completar una tarea específica
    public void completarTareaEspecifica(){
        // Verificar que haya ToDos
        if(listaToDos.isEmpty()){
            System.out.println("\nNo hay listas de ToDo disponibles.");
            return;
        }

        // Mostrar ToDos
        verToDos();

        // Seleccionar ToDo
        System.out.print("Ingrese el nombre del ToDo: ");
        String nombreToDo = sc.nextLine();

        ToDo toDoSeleccionado = buscarToDoNombre(nombreToDo);

        if(toDoSeleccionado == null){
            System.out.println("No existe un ToDo con ese nombre");
            return;
        }

        // Obtener las tareas de este ToDo
        LinkedList<Tarea> tareas = toDoSeleccionado.getTareas();

        // Verificar que haya tareas
        if(tareas.isEmpty()){
            System.out.println("\nNo hay tareas en esta lista.");
            return;
        }

        // Mostrar tareas pendientes con iterator
        System.out.println("\n--- Tareas Pendientes ---");

        //Iterador de tareaas
        ListIterator<Tarea> it = tareas.listIterator();

        boolean hayPendientes = false;
        int contador = 1;

        //Recorrer Tareas
        while(it.hasNext()){
            Tarea tarea = it.next();
            if(tarea.getStatus().equals("Pendiente")){
                System.out.println(contador + ") " + tarea.getDesc() +
                        " | Prioridad: " + tarea.getPrioridad());

                hayPendientes = true;
                contador++;
            }
        }

        // Si no hay pendientes
        if(!hayPendientes){
            System.out.println("No hay tareas pendientes en esta lista.");
            return; //Sale del Metodo
        }

        // Pedir cuál completar
        System.out.print("\nIngrese la descripción de la tarea a completar: ");
        String descBuscada = sc.nextLine();

        // Buscar y completar con iterator
        ListIterator<Tarea> itBuscar = tareas.listIterator();
        boolean encontrada = false;

        //Volver a Iterar las tareas para buscar
        while(itBuscar.hasNext()){
            Tarea tarea = itBuscar.next();

            if(tarea.getDesc().equalsIgnoreCase(descBuscada) && tarea.getStatus().equals("Pendiente")){
                tarea.completarTarea();
                System.out.println(" Tarea '" + tarea.getDesc() + "' marcada como completada.");
                encontrada = true;
                break;
            }
        }

        if(!encontrada){
            System.out.println(" No se encontró una tarea pendiente con esa descripción.");
        }
    }


    //*** 8) Buscar tareas por texto en la descripción
    public void buscarTareasPorTexto(){
        System.out.print("\nIngrese el texto a buscar: ");
        String textoBuscado = sc.nextLine().toLowerCase();

        System.out.println("\n--------- RESULTADOS DE BÚSQUEDA: '" + textoBuscado + "' -----");

        boolean encontro = false;

        // Recorrer TODOS los ToDos con iterator
        ListIterator<ToDo> itToDos = listaToDos.listIterator();

        while(itToDos.hasNext()){
            ToDo todo = itToDos.next();

            System.out.println("\n--- Lista: " + todo.getNameToDo() + " ---");

            boolean hayEnEsteLista = false;

            // Obtener tareas y recorrer con iterator
            LinkedList<Tarea> tareas = todo.getTareas();
            ListIterator<Tarea> itTareas = tareas.listIterator();

            while(itTareas.hasNext()){
                Tarea tarea = itTareas.next();

                // Buscar si contiene el texto
                if(tarea.getDesc().toLowerCase().contains(textoBuscado)){
                    System.out.println("  - " + tarea.getDesc());
                    System.out.println("    Prioridad: " + tarea.getPrioridad() +
                            " | Status: " + tarea.getStatus() +
                            " | Fecha: " + tarea.getFecha());
                    hayEnEsteLista = true;
                    encontro = true;
                }
            }

            if(!hayEnEsteLista){
                System.out.println("  No se encontraron coincidencias en esta lista.");
            }
        }

        if(!encontro){
            System.out.println("\nNo se encontraron tareas que contengan '" + textoBuscado + "'");
        }

        System.out.println("=============================================\n");
    }


    //*** 9) Eliminar un ToDo completo
    public void eliminarToDo() {
        // Verificar que haya ToDos
        if (listaToDos.isEmpty()) {
            System.out.println("\nNo hay ToDos para eliminar.");
            return;
        }

        // Mostrar ToDos
        verToDos();

        // Pedir cuál eliminar
        System.out.print("Ingrese el nombre del ToDo a eliminar: ");
        String nombreToDo = sc.nextLine();

        // Buscar y eliminar con iterator
        ListIterator<ToDo> it = listaToDos.listIterator();
        boolean encontrado = false;

        while (it.hasNext()) {
            ToDo todo = it.next();

            if (todo.getNameToDo().equalsIgnoreCase(nombreToDo)) {

                it.remove(); // Eliminar con iterator
                System.out.println("ToDo- '" + nombreToDo + "' eliminado exitosamente.");

                encontrado = true;
                break;  //Si lo encuentra hace un break
            }
        }
            if (!encontrado) {
                System.out.println("No existe un ToDo con ese nombre.");
        }
    }


    //*** 10) Mostrar menú principal
    public void mostrarMenu(){
        System.out.println("\n-------------------------------------");
        System.out.println("      TODO LIST APP - MENU PRINCIPAL");
        System.out.println("---------------------------------------");
        System.out.println("  GESTIÓN DE LISTAS:");
        System.out.println("    1. Crear nuevo ToDo");
        System.out.println("    2. Ver todos los ToDos");
        System.out.println("    3. Ver tareas de un ToDo");
        System.out.println("    4. Eliminar un ToDo");
        System.out.println("");
        System.out.println("  GESTIÓN DE TAREAS:");
        System.out.println("    5. Crear nueva tarea");
        System.out.println("    6. Completar tarea específica");
        System.out.println("");
        System.out.println("  BÚSQUEDA Y FILTROS:");
        System.out.println("    7. Ver tareas por prioridad (TODOS)");
        System.out.println("    8. Ver todas las completadas (TODOS)");
        System.out.println("    9. Buscar tareas por texto");
        System.out.println("");
        System.out.println("    0. Salir");
        System.out.println("---------------------------------------------");
        System.out.print("Seleccione una opción: ");
    }


    //*** 11) Ejecutar la aplicación
    public void ejecutar(){
        int opcion;

        do {
            mostrarMenu();
            opcion = sc.nextInt();
            sc.nextLine(); // Limpiar buffer

            switch(opcion){
                case 1:
                    crearNuevoToDo();
                    break;
                case 2:
                    verToDos();
                    break;
                case 3:
                    verTareasDeToDo();
                    break;
                case 4:
                    eliminarToDo();
                    break;
                case 5:
                    crearTarea();
                    break;
                case 6:
                    completarTareaEspecifica();
                    break;
                case 7:
                    verTareasPorPrioridad();
                    break;
                case 8:
                    verTodasLasCompletadas();
                    break;
                case 9:
                    buscarTareasPorTexto();
                    break;
                case 0:
                    System.out.println("\n CERRANDO APLICACION..........");
                    break;
                default:
                    System.out.println("\n Opción inválida. Intente nuevamente.");
            }

        } while(opcion != 0);

        sc.close();
    }
}