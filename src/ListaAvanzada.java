//Definir algoritmo generico para buscar
//CLASE GENERICA <E>

//EN ESTA CLASE SE VAN A AÑADIR TODOS LOS METODOS GENERICOS PARA COMPARAR ENTRE LISTAS
//Aqui se añaden funciones genericas para la Sustentacion
//Hacer funciones genericas

//FUNCION GENERICA QUE VA SI O SI:  METODO GENERICO PARA HACER INTERSECCION ENTRE LISTAS.

//EJEMPPLO PARA SUSTENTACION:
// -Interseccion de 2 Listas de Tareas por fecha
//-Interseccion de 2 Listas de Tareas por Estado
//-Interseccion de 2 Listas de Tareas por Prioridad


import java.util.Comparator;
import java.util.LinkedList;

public class ListaAvanzada <E> {

    //Atributos
    LinkedList<E> L;

    //Constructor-- se le ingresa una LINKED LIST
    public ListaAvanzada(LinkedList<E> L) {
        this.L = L;
    }

    //Metodo para BUSCAR
    //Devuelve una LinkedList
    //Parametros: UN COMPARATOR <E> y un dato de tipo E
    //El dato tipo E es una PLANTILLA

    //El proposito de este metodo es mostrar las tareas que COINCIDAN con algun valor del objeto plantilla
    public LinkedList<E> buscar(Comparator<E> c, E valor) {
        LinkedList<E> resultado = new LinkedList<>();
        for (E elemento : L) {
            if (c.compare(elemento, valor) == 0) {   // Si retorna 0 = COINCIDEN
                resultado.add(elemento);
            }
        }
        return resultado; //Retorna solo los valores que coincidieron con la plantilla
    }
}

