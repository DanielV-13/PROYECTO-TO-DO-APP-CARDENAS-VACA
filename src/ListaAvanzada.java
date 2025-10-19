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

    //Constructor
    public ListaAvanzada(LinkedList<E> L) {
        this.L = L;
    }

    //Metodo para BUSCAR
    //Devuelve una LinkedList
    //Parametros: UN COMPARATOR <E> y un dato de tipo E
    public LinkedList<E> buscar(Comparator<E> c, E valor) {
        LinkedList<E> resultado = new LinkedList<>();
        for (E elemento : L) {
            if (c.compare(elemento, valor) == 0) {
                resultado.add(elemento);
            }
        }
        return resultado;
    }
}

