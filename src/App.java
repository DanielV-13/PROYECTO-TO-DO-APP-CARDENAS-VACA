import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner; //importar el scanner para ingreso de datos
import java.time.LocalDate;
import java.io.*;  // Import para MANEJO DE ARCHIVOOS


public class App {
    //------Atributos de la clase---------

    private LinkedList<ToDo> listaToDos; //Linked List que guarda ToDos
    private Scanner sc; //Objeto de tipo Scanner
    private Historial historial;
    private HistorialCompletadas historialCompletadas; //Historial de las tareas Completadas

    //--------Constructor-----------
    public App(){
        listaToDos= new LinkedList<>();  //Creamos la nueva linkedList de To-Dos
        sc = new Scanner(System.in); //Iniciamos el Scanner
        historial = new Historial();
        historialCompletadas = new HistorialCompletadas();
        System.out.println("-------APLICACION INICIADA-------");
        //CARGAR DATOS desde ARCHIVO AL INICIAR
        cargarDatos(); // Se carga el estado inicial de la listaToDos

        // Guardamos el estado inicial como punto de partida en el historial
        historial.guardarEstado(listaToDos);
        // Ahora la pilaHistorial contiene el estado inicial

    }


    //------METODOS DE AUXILIARES PARA LA APP-----
//Metodos Private que se usaran para otros metodos

    //*** 1) BUSCAR TO-Do POR NOMBRE
    //Retorna el To-Do ,o  NULL si no existe
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


    //*** 2) Mostrar SubMenu de Prioridades de las Tareas

     //Muestra un menú para seleccionar una prioridad y valida la entrada.
     //Sigue preguntando hasta que el usuario ingrese una opción válida (1, 2, o 3).
     // RETORNO:  El String "Alta", "Media", o "Baja"

    private String seleccionarPrioridad() {
        int opcionPrioridad;

        // Usamos un bucle infinito que solo se romperá cuando haya un 'return' valido
        while (true) {
            System.out.println("Seleccione la prioridad:");
            System.out.println("  1. Alta");
            System.out.println("  2. Media");
            System.out.println("  3. Baja");
            System.out.print("Ingrese opción (1-3): ");

            try {
                // 1. Intentar leer un número
                opcionPrioridad = sc.nextInt();
                sc.nextLine(); // Limpiar el buffer

                // 2. Validar el rango del número
                switch (opcionPrioridad) {
                    case 1:
                        return "Alta";  // Opción válida, salimos del bucle
                    case 2:
                        return "Media"; // Opción válida, salimos del bucle
                    case 3:
                        return "Baja";  // Opción válida, salimos del bucle
                    default:
                        // Si es un número, pero no es 1, 2, o 3
                        System.out.println("\n¡Opción no válida! Por favor, ingrese 1, 2, o 3.\n");
                }

            } catch (java.util.InputMismatchException e) {  // Capturar el error si el usuario NO ingresó un dato Int

                System.out.println("\n Debe ingresar un NÚMERO (1, 2, o 3).\n");
                sc.nextLine(); // Limpiar el buffer de la entrada incorrecta
            }
        } // El bucle 'while' se repite si no hubo un return valido

    }


    //*** 3) Reconstruye el historial de completadas desde cero basado en el estado actual de listaToDos.
    //Debe llamarse después de una operación de "Deshacer"

    private void resincronizarHistorialCompletadas() {
        // 1) Limpiamos el historial actual creando uno nuevo
        this.historialCompletadas = new HistorialCompletadas();

        // 2) Recorremos el estado actual de listaToDos (que fue restaurado por deshacer)
        ListIterator<ToDo> itToDos = listaToDos.listIterator();
        while (itToDos.hasNext()) {
            ToDo todo = itToDos.next();
            LinkedList<Tarea> tareas = todo.getTareas(); //getTareas accede a la LinkedList de Tareas de cada TO-D0
            for (Tarea tarea : tareas) {
                // 3. Si una tarea está completada, la añadimos al nuevo historial
                if (tarea.getStatus().equals("Completado") && tarea.getFechaCompletada() != null) {
                    this.historialCompletadas.agregarCompletada(tarea);
                }
            }
        }
    }


    //------------METODOS PRINCIPALES---------
    //*** 1) Crear un Nuevo ToD0
    public void crearNuevoToDo(){
        System.out.println("\nIngrese nombre del nuevo ToDo: ");
        String nombre= sc.nextLine();

        ToDo toDoSeleccionado = buscarToDoNombre(nombre);

        if(toDoSeleccionado != null){
            System.out.println("Ya existe un ToDo con ese nombre");
            return;
        }

        //Else - no existe un To-Do con ese nombre
        ToDo nuevoToDo= new ToDo(nombre);

        listaToDos.add(nuevoToDo); //Agregar al final de la lista

        System.out.println("-ToDo " + nombre + " creado exitosamente");

        historial.guardarEstado(listaToDos);

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


        //*** 3) CREAR Y AGREGAR UNA TAREA A UN TOD0 EN LA APP
        public void crearTarea(){
            //Paso 1: Verificar que haya ToDos disponibles
            if(listaToDos.isEmpty()){
                System.out.println("No hay nigun ToDo al cual agregar Tareas\n");
                System.out.println("Cree un ToDo primero\n");
                return; //Sale del metodo
            }

            //Paso 2: Mostrar ToDos disponibles para agregar tareas
            verToDos();

            //Paso 3: Seleccionar el ToD0 donde agregar la tarea
            System.out.println("Ingrese el nombre del ToDo donde quiere ingresar una Tarea: ");
            String nombreToDo =sc.nextLine();

            //Buscar si existe un ToD0 con ese nombre en la App
            ToDo toDoselect = buscarToDoNombre(nombreToDo);

            if(toDoselect == null){
                System.out.println("No existe un ToDo con ese nombre");
                return; //sale del metodo
            }

            //Como ya verifique que exisita el ToD0
            //Puedo proceder a agregar la tarea

            //En caso de que el ToD0 existe, pedir datos de la Tarea a ingresar
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

            // Pedir prioridad - (CON EL METODO AUXILIAR "seleccionarPrioridad() )
            String prioridad = seleccionarPrioridad();

            //Crear nueva Tarea y agregarla
            Tarea nuevaTarea=new Tarea(desc,fecha,prioridad);
            toDoselect.addTarea(nuevaTarea);

            System.out.println("Tarea- " + desc + " -agregada exitosamente a- " + nombreToDo + "");

            historial.guardarEstado(listaToDos);
        }


    //*** 4) Ver todas las tareas de un To-Do específico - Ordenadas por Prioridad
    public void verTareasDeToDo(){
        // Verificar que haya ToDos
        if(listaToDos.isEmpty()){
            System.out.println("\nNo hay listas de ToDo disponibles.");
            return; //Sale del Metodo
        }
        // Mostrar ToDos disponibles
        verToDos();

        // Pedir cuál To-Do ver
        System.out.print("Ingrese el nombre del ToDo a visualizar: ");
        String nombreToDo = sc.nextLine();

        ToDo toDoselect = buscarToDoNombre(nombreToDo);

        if(toDoselect == null){
            System.out.println("No existe un ToDo con ese nombre");
            System.out.println("Porfavor ingrese un nombre de ToDo valido\n");
            return; //Sale del metodo
        }

        //Como ya verifique el To-Do existia, puedo ahora mostrar las Tareas

        System.out.println("\n---------------------------------------------------");
        System.out.println("   Mostrando tareas de: " + toDoselect.getNameToDo());
        System.out.println("-----------------------------------------------------");

        // MOSTRAR TAREAS PENDIENTES (En ORden por Prioridad)
        LinkedList<Tarea> pendientes = toDoselect.verPendientes();

        if (pendientes.isEmpty()) {
            System.out.println("  No hay tareas pendientes en este ToDo.");
        } else {
            // Iteramos la lista ordenada y la mostramos
            for (Tarea t : pendientes) {
                System.out.println(t); // toString() de Tarea
                System.out.println(" ---------------");
            }
        }

        // MOSTRAR TAREAS COMPLETADAS
        LinkedList<Tarea> completadas = toDoselect.verCompletadas();

        if (completadas.isEmpty()) {
            System.out.println("\n  No hay tareas completadas en este ToDo.");
        } else {
            for (Tarea t : completadas) {
                System.out.println(t); //toString() de Tarea
                System.out.println(" ---------------");
            }
        }
        System.out.println("--------------------------\n");

    }


    //*** 5) MÉTOD0 OBLIGATORIO - Recuperar solo las tareas de una prioridad dada de todos los ToDos
    //Buscar todas las tareas de una cierta prioridad en un Lista de ToDos
    public void verTareasPorPrioridad(){
        System.out.print("\nIngrese la prioridad a buscar (Alta/Media/Baja): ");
        String prioridadBuscada = seleccionarPrioridad(); //USA EL METODO AUXILIAR "seleccionarPrioridad()"

        System.out.println("\n------ TAREAS CON PRIORIDAD: " + prioridadBuscada + " ----------");

        //Caso 0 : Verificar que halla ToDos en la APP
        if(listaToDos.isEmpty()){
            System.out.println(" No hay ningun ToDo creado ");
            System.out.println(" Volviendo al menu principal ");
            System.out.println("----------------\n");
            return; //Salir del metodo
        }

        //Variable local booleana para verificar si encuentra ToDos con esa prioridad en la APP en general
        boolean encontroPrioridad=false;

        //Ahora que verificamos que si hay ToDos en la APP :
        // Recorrer TODOS los ToDos con iterator
        ListIterator<ToDo> itToDos = listaToDos.listIterator();

        //------------------------------
        //BUCLE WHILE PARA RECORRER TODOS LOS TO-DOs
        while(itToDos.hasNext()){
            ToDo toDo = itToDos.next(); // To-Do actual

            //Ver las tareaas de esa prioridad del To-Do actual
            LinkedList<Tarea> resultado= toDo.verPrioridad(prioridadBuscada);

            if (resultado.isEmpty()) {
                System.out.println("No hay tareas con esa Prioridad en el ToDo: "+toDo.getNameToDo());
            }else{
                System.out.println("Tareas con Prioridad: "+ prioridadBuscada+" en el ToDo "+ toDo.getNameToDo());
                encontroPrioridad=true; //Variable local pasa a ser True

                //Imprimir cada Tarea de la lista resultante
                for(Tarea t:resultado){
                    System.out.println(t);
                }
            }
        }  //SALIENDO DEL WHILE

        if (encontroPrioridad==false){
            System.out.println("NO SE ENCONTRO NINGUNA TAREA CON PRIORIDAD -"+prioridadBuscada+"- EN LA APP");
        }

        System.out.println("----------------------------------------------------\n");
    }


    //*** 6) MÉTOD0 OBLIGATORIO - Recuperar todas las tareas marcadas como hechas de todos los To-DO
    //Buscar todas las tareas cumplidas en un Lista de ToDos
    public void verTodasLasCompletadas(){
        System.out.println("\n-------TODAS LAS TAREAS COMPLETADAS-----------");

        //Caso 0 : Verificar que halla ToDos en la APP
        if(listaToDos.isEmpty()){
            System.out.println(" No hay ningun ToDo creado ");
            System.out.println(" Volviendo al menu principal ");
            System.out.println("----------------\n");
            return; //Salir del metodo
        }
        //Como ya verificamos que si hay ToDos, seguimos:

        boolean encontroCompletadas = false; // Variable bandera para ver si hay completadas en la APP EN GENERAL

        // Recorrer TODOS los ToDos con iterator
        ListIterator<ToDo> itToDos = listaToDos.listIterator();

        //----BUCLE WHILE----
        while(itToDos.hasNext()) {
            ToDo toDo = itToDos.next(); // To-Do actual

            LinkedList<Tarea> resultado = toDo.verCompletadas();

            if (resultado.isEmpty()) {
                System.out.println("No hay tareas COMPLETADAS en el ToDo: " + toDo.getNameToDo());
            } else {
                System.out.println("Tareas COMPLETADAS en el ToDo " + toDo.getNameToDo());
                encontroCompletadas = true; //Variable local pasa a ser True

                //Imprimir cada Tarea de la lista resultante
                for (Tarea t : resultado) {
                    System.out.println(t);
                }
            }
        }
        //Saliendo del While
        if (encontroCompletadas==false){
            System.out.println("NO SE ENCONTRARON TAREAS COMPLETADAS EN LA APP");
        }
        System.out.println("---------------------------------------\n");
    }


    //*** 7) Completar una tarea específica (Selección por Número) ***
    public void completarTareaEspecifica(){
        // --- 1. Seleccionar el ToD0 ---
        if(listaToDos.isEmpty()){
            System.out.println("\nNo hay listas de ToDo disponibles.");
            return;
        }
        verToDos();
        System.out.print("Ingrese el nombre del ToDo donde está la tarea a completar: ");
        String nombreToDo = sc.nextLine();
        ToDo toDoSeleccionado = buscarToDoNombre(nombreToDo);

        if(toDoSeleccionado == null){
            System.out.println("No existe un ToDo con ese nombre.");
            return;
        }

        // --- 2. Obtener y mostrar TAREAS PENDIENTES numeradas ---
        LinkedList<Tarea> pendientes = toDoSeleccionado.verPendientes();

        if(pendientes.isEmpty()){
            System.out.println("\nNo hay tareas pendientes en este ToDo para completar.");
            return;
        }

        // Mostramos las tareas pendientes numeradas
        System.out.println("\n--- TAREAS PENDIENTES en '" + toDoSeleccionado.getNameToDo() + "' ---");
        int contador = 1;
        for(Tarea t: pendientes){
            System.out.println("----- Tarea #" + contador + " -----"); // Número de tarea
            System.out.println(t); // Muestra la tarea
            contador++;
        }

        // --- 3. Seleccionar la tarea por NÚMERO ---
        int numeroTareaACompletar = -1; //Inicializamos la variable en -1
        Tarea tareaACompletar = null;  //La variable donde se guardara la tarea a Eliminar

        boolean numeroValido = false;
        while (!numeroValido) { //Mientras el numero sea invalido
            System.out.print("\nIngrese el NÚMERO de la tarea PENDIENTE que desea completar (1-" + pendientes.size() + "): ");
            try {
                numeroTareaACompletar = sc.nextInt();
                sc.nextLine(); // Limpiar buffer

                // Validar que el número esté en el rango correcto
                if (numeroTareaACompletar >= 1 && numeroTareaACompletar <= pendientes.size()) {
                    // Obtener la tarea correcta de la lista (índice es numero - 1)
                    tareaACompletar = pendientes.get(numeroTareaACompletar - 1);
                    numeroValido = true; // Salir del bucle
                } else {
                    System.out.println("Número fuera de rango. Intente de nuevo.");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Entrada inválida. Debe ingresar un número.");
                sc.nextLine(); // Limpiar buffer de entrada incorrecta
            }
        } // Fin del while de validación

        // --- Ahora hay que completar la Tarea Seleccionada---
        if (tareaACompletar != null) {
            // Llama al métod0 completarTarea() del objeto Tarea
            tareaACompletar.completarTarea();
            System.out.println("\nTarea '" + tareaACompletar.getDesc() + "' completada exitosamente");

            // Guardar estado para deshacer
            historial.guardarEstado(listaToDos);

            // Agregar al historial de completadas
            historialCompletadas.agregarCompletada(tareaACompletar);
        }
    }



    //------FUNCIONALIDAD EXTRA--------
    //Regresar una Tarea Completada a la Lista de Pendientes
     //También la elimina del historial de completadas.

    public void regresarTareaAPendiente() {
        // --- 1. Seleccionar el ToD0 ---
        if (listaToDos.isEmpty()) {
            System.out.println("\nNo hay listas de ToDo disponibles.");
            return;
        }
        verToDos();
        System.out.print("Ingrese el nombre del ToDo donde está la tarea a regresar: ");
        String nombreToDo = sc.nextLine();
        ToDo toDoSeleccionado = buscarToDoNombre(nombreToDo);

        if (toDoSeleccionado == null) {
            System.out.println("No existe un ToDo con ese nombre.");
            return;
        }

        // --- 2. Obtener y mostrar TAREAS COMPLETADAS de ese ToD0 ---
        // Usamos ListaAvanzada para obtener solo las completadas
        ListaAvanzada<Tarea> listaAvanzada = new ListaAvanzada<>(toDoSeleccionado.getTareas());
        Tarea plantillaCompletada = new Tarea(null, null, null);
        plantillaCompletada.setStatus("Completado");

        LinkedList<Tarea> completadas = listaAvanzada.buscar(new ComparatorStatus(), plantillaCompletada);

        if (completadas.isEmpty()) {
            System.out.println("\nNo hay tareas completadas en este ToDo para regresar.");
            return;
        }

        System.out.println("\n--- TAREAS COMPLETADAS en '" + toDoSeleccionado.getNameToDo() + "' ---");
        for (Tarea t : completadas) {
            System.out.println(t); // Muestra la tarea (con su fecha de completación)
            System.out.println(" ---------------");
        }

        // --- 3. Seleccionar la tarea a regresar ---
        System.out.print("\nIngrese la descripción EXACTA de la tarea a regresar a Pendiente: ");
        String descBuscada = sc.nextLine();

        // --- 4. Buscar la tarea y ejecutar la acción ---
        ListIterator<Tarea> it = completadas.listIterator(); // Iteramos sobre la lista de completadas
        boolean encontrada = false;
        Tarea tareaARegresar = null; // Guardamos la tarea encontrada

        while (it.hasNext()) {
            Tarea t = it.next();
            if (t.getDesc().equalsIgnoreCase(descBuscada)) { // Buscamos por descripción (ignorando mayúsculas)
                tareaARegresar = t;
                encontrada = true;
                break;
            }
        }

        // --- 5. Ejecutar los cambios si se encontró ---
        if (encontrada) {
            // Cambiar estado y borrar fecha en la Tarea
            tareaARegresar.descompletarTarea(); // Pone Status="Pendiente" y fechaCompletada=null

            // Eliminar del historial de completadas
            historialCompletadas.eliminarCompletada(tareaARegresar);

            // Guardar el estado general para el "Deshacer"
            historial.guardarEstado(listaToDos);

            System.out.println("\n La tarea '" + tareaARegresar.getDesc() + "' ha sido regresada al estado Pendiente.");
        } else {
            System.out.println("\nNo se encontró una tarea completada con esa descripción en este ToDo.");
        }
    }


//------FUNCIONALIDAD EXTRA--------
    // Editar detalles de una tarea PENDIENTE (Selección por Número)

    public void editarTareaPendiente() {
        // --- 1. Seleccionar el ToD0 ---
        if (listaToDos.isEmpty()) {
            System.out.println("\nNo hay listas de ToDo disponibles.");
            return;
        }
        verToDos();
        System.out.print("Ingrese el nombre del ToDo donde está la tarea a editar: ");
        String nombreToDo = sc.nextLine();
        ToDo toDoSeleccionado = buscarToDoNombre(nombreToDo);

        if (toDoSeleccionado == null) {
            System.out.println("No existe un ToDo con ese nombre.");
            return;
        }

        // --- 2. Obtener y mostrar TAREAS PENDIENTES numeradas ---
        LinkedList<Tarea> pendientes = toDoSeleccionado.verPendientes(); // Ya vienen ordenadas

        if (pendientes.isEmpty()) {
            System.out.println("\nNo hay tareas pendientes en este ToDo para editar.");
            return;
        }

        // Mostramos las tareas pendientes numeradas
        System.out.println("\n--- TAREAS PENDIENTES en '" + toDoSeleccionado.getNameToDo() + "' ---");
        int contador = 1;
        for (Tarea t : pendientes) {
            System.out.println("----- Tarea #" + contador + " -----"); // Número de tarea
            System.out.println(t); // Muestra la tarea
            contador++;
        }

        // --- 3. Seleccionar la tarea por NÚMERO ---
        int numeroTareaAEditar = -1; // Inicia con -1, que es un valor invalido
        Tarea tareaAEditar = null;   // Variable para guardar la tarea seleccionada

        // Bucle para validar la entrada del número
        boolean numeroValido = false;
        while (!numeroValido) {  //Mientras sea False la variable booleana
            System.out.print("\nIngrese el NÚMERO de la tarea PENDIENTE que desea editar (1-" + pendientes.size() + "): ");

            try {
                numeroTareaAEditar = sc.nextInt();
                sc.nextLine(); // Limpiar buffer

                // Validar que el número esté en el rango correcto
                if (numeroTareaAEditar >= 1 && numeroTareaAEditar <= pendientes.size()) {
                    // Obtener la tarea correcta de la lista (índice es = numero - 1)
                    tareaAEditar = pendientes.get(numeroTareaAEditar - 1);
                    numeroValido = true; // Salir del bucle

                } else {
                    System.out.println("Número fuera de rango. Intente de nuevo.");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Entrada inválida. Debe ingresar un número.");
                sc.nextLine(); // Limpiar buffer

            }
        } // Fin del while que valida la seleccion de la Tarea por numero

        // --- 4) Si se seleccionó una tarea válida, pedir qué editar ---
        if (tareaAEditar != null) {
            System.out.println("\nEditando Tarea #" + numeroTareaAEditar + ": \n" + tareaAEditar);
            System.out.println("¿Qué detalle desea editar?");
            System.out.println("  1. Descripción");
            System.out.println("  2. Fecha Máxima");
            System.out.println("  3. Prioridad");
            System.out.println("  0. Cancelar");
            System.out.print("Ingrese opción: ");

            int opcionEditar;
            try {
                opcionEditar = sc.nextInt();
                sc.nextLine(); // Limpiar buffer
            } catch (java.util.InputMismatchException e) {
                System.out.println("Debe ingresar un número.");
                sc.nextLine(); // Limpiar buffer
                return;
            }

            boolean cambioRealizado = false;  //Variable Bandera

            switch (opcionEditar) {
                case 1: // Editar Descripción
                    System.out.print("Ingrese la nueva descripción: ");
                    String nuevaDesc = sc.nextLine();
                    tareaAEditar.setDesc(nuevaDesc);
                    System.out.println("Descripción actualizada.");
                    cambioRealizado = true;
                    break;

                case 2: // Editar Fecha Máxima
                    System.out.println("Ingrese la NUEVA FECHA MAXIMA:");
                    System.out.print("Ingrese el año (ej: 2025): ");
                    int año = sc.nextInt();
                    System.out.print("Ingrese el mes (1-12): ");
                    int mes = sc.nextInt();
                    System.out.print("Ingrese el día (1-31): ");
                    int dia = sc.nextInt();
                    sc.nextLine(); // Limpiar buffer

                    try {
                        LocalDate nuevaFecha = LocalDate.of(año, mes, dia);
                        tareaAEditar.setFecha(nuevaFecha);
                        System.out.println("Fecha máxima actualizada.");
                        cambioRealizado = true;

                    } catch (java.time.DateTimeException e) {
                        System.out.println("Fecha inválida. No se realizaron cambios.");
                    }
                    break;

                case 3: // Editar Prioridad
                    System.out.println("Seleccione la NUEVA PRIORIDAD:");
                    String nuevaPrioridad = seleccionarPrioridad();
                    tareaAEditar.setPrioridad(nuevaPrioridad);
                    System.out.println("Prioridad actualizada.");
                    cambioRealizado = true;
                    break;

                case 0: // Cancelar
                    System.out.println("Edición cancelada.");
                    break;

                default:
                    System.out.println("Opción inválida. No se realizaron cambios.");
                    break;
            }

            // --- 5. Guardar estado si hubo cambios ---
            if (cambioRealizado) {
                historial.guardarEstado(listaToDos);
            }
        }
    }


    //------FUNCIONALIDAD EXTRA--------
    // Remover una tarea específica de una lista (sea pendiente o completada)

    public void removerTarea() {
        // --- 1. Seleccionar el ToD0 ---
        if (listaToDos.isEmpty()) {
            System.out.println("\nNo hay listas de ToDo disponibles.");
            return;
        }
        verToDos();
        System.out.print("Ingrese el nombre del ToDo de donde desea remover una tarea: ");
        String nombreToDo = sc.nextLine();
        ToDo toDoSeleccionado = buscarToDoNombre(nombreToDo);

        if (toDoSeleccionado == null) {
            System.out.println("No existe un ToDo con ese nombre.");
            return;
        }

        // --- 2. Obtener y mostrar TODAS las tareas numeradas ---
        LinkedList<Tarea> tareasDelToDo = toDoSeleccionado.getTareas(); // Obtenemos la lista interna

        if (tareasDelToDo.isEmpty()) {
            System.out.println("\nEste ToDo ('" + toDoSeleccionado.getNameToDo() + "') no tiene tareas para remover");
            return;
        }

        System.out.println("\n--- TAREAS en '" + toDoSeleccionado.getNameToDo() + "' ---");
        int contador = 1;
        // Creamos una lista separada solo para mostrar, para no modificar la original aún
        LinkedList<Tarea> tareasParaMostrar = new LinkedList<>(tareasDelToDo);
        for (Tarea t : tareasParaMostrar) {
            System.out.println("----- Tarea #" + contador + " -----");
            System.out.println(t); // Muestra la tarea
            contador++;
        }

        // --- 3. Seleccionar la tarea por NÚMERO ---
        int numeroTareaARemover = -1; //Inicializamos con -1 que es un valor no valido
        Tarea tareaARemover = null; //Variable para la tarea que se va a remover

        boolean numeroValido = false;
        while (!numeroValido) {  //Mientras se ingrese un numero invalido
            System.out.print("\nIngrese el NÚMERO de la tarea que desea remover (1-" + tareasParaMostrar.size() + "): ");
            try {
                numeroTareaARemover = sc.nextInt();
                sc.nextLine(); // Limpiar buffer

                if (numeroTareaARemover >= 1 && numeroTareaARemover <= tareasParaMostrar.size()) {
                    // Obtenemos la tarea correspondiente de la lista *para mostrar*
                    //Solo idenficiamos la tarea que vamos a remover
                    tareaARemover = tareasParaMostrar.get(numeroTareaARemover - 1); //numero - 1, nos da el indice de la tarea
                    numeroValido = true;
                } else {
                    System.out.println("Número fuera de rango. Intente de nuevo");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Entrada inválida Debe ingresar un número");
                sc.nextLine();
            }
        }

        // --- 4.Proceder a eliminar la tarea seleccionada ---
        if (tareaARemover != null) {
            boolean removidoExitosamente = false;

            // Usamos un ListIterator sobre la lista ORIGINAL del ToD0 para eliminar de forma segura
            ListIterator<Tarea> it = tareasDelToDo.listIterator();
            while (it.hasNext()) {
                Tarea tareaActual = it.next();
                // Comparamos el objeto Tarea directamente
                if (tareaActual == tareaARemover) {
                    it.remove(); // Eliminamos la tarea usando el iterador
                    removidoExitosamente = true;
                    System.out.println("\nTarea '" + tareaARemover.getDesc() + "' removida de la lista");
                    break; // Salimos del bucle una vez eliminada
                }
            }

            // --- 5. Limpiar historiales y guardar estado si se eliminó ---
            if (removidoExitosamente) {
                // Si la tarea estaba completada, eliminarla también del historial de completadas
                if (tareaARemover.getStatus().equals("Completado")) {
                    historialCompletadas.eliminarCompletada(tareaARemover); // Llama al métod0
                }
                // Guardar el estado para el "Deshacer"
                historial.guardarEstado(listaToDos); //
            }
        }
    }









    //*** 8) Buscar tareas por texto en la descripción
    public void buscarTareasPorTexto(){
        System.out.print("\nIngrese el texto a buscar: ");
        String textoBuscado = sc.nextLine().toLowerCase();

        System.out.println("\n--------- RESULTADOS DE LA BÚSQUEDA -------");

        boolean encontro = false; //Variable bandera para todos los TO-Dos

        // Recorrer TODOS los ToDos con iterator
        ListIterator<ToDo> itToDos = listaToDos.listIterator();

        while(itToDos.hasNext()){
            ToDo todo = itToDos.next();
            System.out.println("\n--- ToDo: " + todo.getNameToDo() + " ---");

            boolean hayEnEsteLista = false; //Variable bandera, para este To-Do especifico

            // Obtener tareas y recorrer con iterator
            LinkedList<Tarea> tareas = todo.getTareas();
            ListIterator<Tarea> itTareas = tareas.listIterator();

            while(itTareas.hasNext()){
                Tarea tarea = itTareas.next();
                // Buscar si contiene el texto
                if(tarea.getDesc().toLowerCase().contains(textoBuscado)){
                    System.out.println(tarea);
                    hayEnEsteLista = true;
                    encontro = true;
                }
            }

            if(hayEnEsteLista==false){
                System.out.println("  No se encontraron coincidencias en este ToDo.");
            }
        } //Saliendo del While que recorre los To-Do

        if(encontro==false){
            System.out.println("\nNo se encontraron tareas que contengan '" + textoBuscado + "'");
        }

        System.out.println("=============================================\n");
    }


    //*** 9) Eliminar un ToD0 completo
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

        ToDo toDoselect = buscarToDoNombre(nombreToDo);

        if(toDoselect == null){
            System.out.println("No existe un ToDo con ese nombre");
            System.out.println("Porfavor ingrese un nombre de ToDo valido para eliminar\n");
            return; //Sale del metodo
        }

        // Eliminar to-Do con iterator
        ListIterator<ToDo> it = listaToDos.listIterator();

        while (it.hasNext()) {
            ToDo todo = it.next();

            if (todo.getNameToDo().equalsIgnoreCase(nombreToDo)) {
                it.remove(); // Eliminar con iterator
                System.out.println("ToDo- '" + nombreToDo + "' eliminado exitosamente.");
                historial.guardarEstado(listaToDos);

                break;  //Si lo encuentra hace un break
            }
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
        System.out.println("    7. Regresar tarea completada a Pendiente");
        System.out.println("    8. Editar tarea pendiente");
        System.out.println("    9. Remover tarea específica");
        System.out.println("");
        System.out.println("  BÚSQUEDA Y FILTROS:");
        System.out.println("   10. Ver tareas por prioridad (TODOS)");
        System.out.println("   11. Ver Histórico de tareas completadas");
        System.out.println("   12. Buscar tareas por texto");
        System.out.println("");
        System.out.println("  SISTEMA:");
        System.out.println("   13. Deshacer ultima accion");
        System.out.println("   14. Limpiar historial de cambios");
        System.out.println("   15. Guardar datos manualmente");
        System.out.println("");
        System.out.println("    0. Salir");
        System.out.println("---------------------------------------------");
        System.out.print("Seleccione una opción: ");
    }


    //*** 11) Ejecutar la aplicación
    public void ejecutar(){
        int opcion;

        //Estructura do - while

        do {   //SIEMPRE VA A HACER ESTO
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
                    regresarTareaAPendiente();
                    break;
                case 8:
                    editarTareaPendiente();
                    break;
                case 9:
                    removerTarea();
                    break;
                case 10:
                    verTareasPorPrioridad();
                    break;
                case 11:
                    System.out.println(historialCompletadas);
                    break;
                case 12:
                    buscarTareasPorTexto();
                    break;
                case 13:
                    LinkedList<ToDo> estadoAnterior = historial.deshacer();
                    if (estadoAnterior != null) { listaToDos = estadoAnterior;
                        resincronizarHistorialCompletadas(); //Actualiza el Historial de Completadas luego de un Deshacer
                    }
                    break;
                case 14:
                    historial.limpiarHistorial();
                    break;
                case 15:
                    guardarDatos();
                    break;
                // --- CASE OCULTO (numero de acceso 100) ---
                case 100:
                    completarTareaConFechaManual();
                    break;

                case 0:
                    System.out.println("\n Guardando datos en archivo...");
                    guardarDatos();
                    System.out.println("CERRANDO APLICACION..........");
                    break;
                default:
                    System.out.println("\n Opción inválida. Intente nuevamente.");
            }

        } while(opcion != 0);   //Volver a repetir el bucle si no se marca 0

        sc.close();
    }


    //-----------METODOS PARA GUARDAR EN ARCHIVOOOOOOOSSS----------------


    // 1) Métod0 para GUARDAR todos los datos en archivo
    public void guardarDatos(){
        try {
            // Crear archivo (se crea en la carpeta del proyecto)
            FileWriter fw = new FileWriter("datos/ToDos.txt");
            //Archivo de nombre "ToDos" de tipo txt en la carpeta "datos"
            BufferedWriter bw = new BufferedWriter(fw);

            // Recorrer todos los ToDos
            ListIterator<ToDo> itToDos = listaToDos.listIterator();

            //RECORRER TODOS LOS TO-DO con iterator
            while(itToDos.hasNext()){
                ToDo todo = itToDos.next();

                // Guardar información del To-Do
                //getFechaCreacionL --- getter para acceder a la fecha de creacion del TO-Do
                bw.write("TO-DO|" + todo.getNameToDo() + "|" + todo.getFechaCreacionL());
                bw.newLine();  //Salto de Linea

                // Guardar todas las tareas de este To-Do
                LinkedList<Tarea> tareas = todo.getTareas();

                for(Tarea t : tareas){
                    //Comprobar que la fechadeCompletacion no sea NULL
                    String fechaCompletacionStr;

                    if (t.getFechaCompletada() == null) {
                        fechaCompletacionStr = "-";
                    } else {
                        fechaCompletacionStr = t.getFechaCompletada().toString();
                    }

                    bw.write("TAREA|" +
                            t.getDesc() + "|" +
                            t.getPrioridad() + "|" +
                            t.getFecha() + "|" +
                            t.getStatus() + "|" +  // Nuevo delimitador
                            fechaCompletacionStr);

                    bw.newLine(); //Salto de Linea
                }

                // Separador entre ToDos
                bw.write("---");
                bw.newLine();
            }

            bw.close(); //Cierro BufferedWriter
            fw.close(); //Cierro FileWriter

            System.out.println(" Datos guardados exitosamente en 'datos/todos.txt'");


            //CATCH en caso de error
        } catch (IOException e) {
            System.out.println("Error al guardar datos en archivo: " + e.getMessage());
        }
    }



    // 2) Métod0 para CARGAR datos desde el archivo
    public void cargarDatos(){
        try {
            File archivo = new File("datos/todos.txt");

            // Si el archivo no existe, no hacer nada
            if(!archivo.exists()){
                System.out.println("No hay datos guardados previos.");
                return;
            }

            //Inicio FileReader y BufferedReader
            FileReader fr = new FileReader(archivo);
            BufferedReader br = new BufferedReader(fr);

            String linea;
            ToDo todoActual = null;


            //While que recorre todas las lineas del archivo
            //Condicion, que la linea NO SEA NULL
            while((linea = br.readLine()) != null) {

                // Ignorar líneas vacías o separadores
                // si "---" OR ""
                if (linea.equals("---") || linea.trim().isEmpty()) {
                    continue;
                }

                //---SI LA LINEA NO ESTA VACIA--
                // Separar la línea por el delimitador |
                //Array de las partes de la linea
                String[] partes = linea.split("\\|");

                // Si es un To-Do
                //TO-DO | nombreTo-Do | fecha
                if (partes[0].equals("TO-DO")) {
                    String nombre = partes[1];
                    // Crear nuevo To-Do
                    todoActual = new ToDo(nombre);
                    listaToDos.add(todoActual); //Añade el nuevo ToD0 a la lista de la App


                // SI ES UNA TAREA
                // "TAREA|"  partes [0]
                // t.getDesc()  partes [1]
                // t.getPrioridad() partes [2]
                // t.getFecha()  partes [3]
                // t.getStatus() partes [4]

            }else if(partes[0].equals("TAREA") && todoActual != null){
                    String desc = partes[1];
                    String prioridad = partes[2];
                    LocalDate fecha = LocalDate.parse(partes[3]); //Convertir String en LocalDate
                    String status = partes[4];

                    // --- Comprobamos la fecha de Completacion del archivo---
                    // Comprobamos si el archivo ya tiene el campo extra (partes.length > 5)
                    String fechaCompStr;
                    if (partes.length > 5) {
                        fechaCompStr = partes[5]; // Si existe, lo leemos
                    } else {
                        fechaCompStr = "-"; // Si no tiene el campo fechaCompletada, es null
                    }


                    // Crear tarea
                    Tarea tarea = new Tarea(desc, fecha, prioridad);
                    tarea.setStatus(status);  // Restaurar el status de la Tarea (que no sea Pendiente por Defecto)

                    // Restaurar la fecha de completación de la Tarea si no es "null"
                    if (!fechaCompStr.equals("-")) {
                        tarea.setFechaCompletada(LocalDate.parse(fechaCompStr)); //Pasamos de String a Date
                    }


                    // Agregar al To-Do actual
                    todoActual.addTarea(tarea);

                    // --- HISTORIAL DE COMPLETADAS ---
                    // Si la tarea cargada estaba completada, añadirla al historial de tareas completadas
                    if (tarea.getStatus().equals("Completado") && tarea.getFechaCompletada() != null) {
                        historialCompletadas.agregarCompletada(tarea);
                    }

                }
            }

            br.close();  //Cierro BufferedReader
            fr.close(); //Cierro FileReader

            System.out.println(" Datos cargados exitosamente desde 'datos/todos.txt'");

            //EXCEPCION EN CASO DE ERROR
        } catch (IOException e) {
            System.out.println(" Error al cargar datos: " + e.getMessage());
        }
    }



    //--------METODO OCULTO SOLO PARA ADMINISTRADORES (PARA PROBAR HISTORIALCOMPLETADAS)-----

    private void completarTareaConFechaManual() {
        // --- SELECCIÓN DE TAREA ---
        if(listaToDos.isEmpty()){
            System.out.println("\nNo hay listas de ToDo disponibles.");
            return;
        }
        verToDos();
        System.out.print("Ingrese el nombre del ToDo: ");
        String nombreToDo = sc.nextLine();
        ToDo toDoSeleccionado = buscarToDoNombre(nombreToDo);

        if(toDoSeleccionado == null){
            System.out.println("No existe un ToDo con ese nombre");
            return;
        }

        LinkedList<Tarea> pendientes = toDoSeleccionado.verPendientes();
        if(pendientes.isEmpty()){
            System.out.println("\nNo hay tareas pendientes en este ToDo");
            return;
        }

        System.out.println("---TAREAS PENDIENTES EN EL TO-DO "+ toDoSeleccionado.getNameToDo()+"---");
        for(Tarea t: pendientes){
            System.out.println(t);
        }
        System.out.print("\nIngrese la descripción de la tarea a completar manualmente: ");
        String descBuscada = sc.nextLine();

        // --- BÚSQUEDA Y MODIFICACIÓN DE FECHA ---
        ListIterator<Tarea> it = pendientes.listIterator();
        boolean encontrada = false;

        while(it.hasNext()){
            Tarea t= it.next();

            if(t.getDesc().equals(descBuscada) ) {
                // --- INICIO DE LA COMPLETACION MANUAL DE LA FECHA ---
                System.out.println("\nIngrese la FECHA DE COMPLETACIÓN MANUAL para '" + t.getDesc() + "'");
                System.out.print("Ingrese el año (ej: 2025): ");
                int año = sc.nextInt();
                System.out.print("Ingrese el mes (1-12): ");
                int mes = sc.nextInt();
                System.out.print("Ingrese el día (1-31): ");
                int dia = sc.nextInt();
                sc.nextLine(); // Limpiar buffer

                LocalDate fechaCompletacionManual = LocalDate.of(año, mes, dia);

                // Seteamos el status y la fecha manualmente
                t.setStatus("Completado");
                t.setFechaCompletada(fechaCompletacionManual); // Usamos el setter


                System.out.println("Tarea '" + t.getDesc() + "' marcada como completada el " + fechaCompletacionManual + ".");
                encontrada = true;
                historial.guardarEstado(listaToDos);

                // --- ACTUALIZAR EL HISTORIAL DE COMPLETADAS ---
                // Añadimos la Tarea con la nueva fecha (la lógica interna la clasificará)
                historialCompletadas.agregarCompletada(t);
                // ----------------------------------------------
                break;
            }
        }

        if(encontrada==false){
            System.out.println(" No se encontró una tarea pendiente con esa descripción.");
        }
    }










}