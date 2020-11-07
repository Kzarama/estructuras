/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: Lista.java,v 1.18 2008/09/30 16:07:00 alf-mora Exp $
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Framework: Cupi2Collections
 * Autor: Jorge Villalobos - Abr 13, 2006
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.cupi2.collections.lista;

import java.io.Serializable;

import uniandes.cupi2.collections.iterador.Iterador;
import uniandes.cupi2.collections.iterador.IteradorException;
import uniandes.cupi2.collections.iterador.IteradorSimple;

/**
 * Representa una lista modelada con un arreglo dinámico
 * @param <T> Tipo de datos a almacenar en la lista. Los objetos de tipo T deben tener bien definidos el método <b>equals</b>
 */
public class Lista<T> implements Serializable
{
    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------
	
    /**
	 * Constante para la serialización
	 */
	private static final long serialVersionUID = 1L;	
	
    /**
     * Tamaño inicial del arreglo
     */
    private final static int INIT = 20;

    /**
     * Número de posiciones a agregar al crecer el arreglo
     */
    private final static int DELTA = 20;

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Elementos de la lista
     */
    protected T[] elems;

    /**
     * Número de elementos actualmente en la lista
     */
    protected int numElems;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Constructor por defecto de la lista. El tamaño inicial es de 20<br>
     * <b>post: </b> Se construyó una lista vacía con tamaño inicial 20.<br>
     */
    public Lista( )
    {
        this( INIT );
    }

    /**
     * Constructor de la lista con la capacidad inicial dada. <br>
     * <b>pre: </b> capacidad!=null, capacidad>0. <br>
     * <b>post: </b> Se construyó la lista con la capacidad inicial especificada.<br>
     * @param capacidad Capacidad inicial para la lista. Entero positivo mayor a cero.<br>
     */
    @SuppressWarnings("unchecked")
    public Lista( int capacidad )
    {
        elems = ( T[] )new Object[capacidad];
        numElems = 0;
    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Devuelve el elemento en la posición especificada. <br>
     * <b>post</b>: Se retornó el elemento en la posición especificada.<br>
     * @param pos Posición del elemento.<br>
     * @return Elemento en la posición especificada. Puede ser null en caso que no exista un elemento en la posición dada.<br>
     * @throws IndiceFueraDeRangoException Lanzada cuando la posición es menor a cero o mayor al tamaño de la lista menos uno.<br>
     */
    public T darElemento( int pos )
    {
        if( pos < 0 || pos > numElems )
            throw new IndiceFueraDeRangoException( pos );
        return elems[ pos ];
    }

    /**
     * Agrega el elemento al final lista. <br>
     * <b>post: </b> Se agregó el elemento especificado al final de la lista.<br>
     * Si con la adición se excede la capacidad de la lista, la nueva capacidad de la lista es elem.length+DELTA. <br>
     * @param elem Elemento a agregar. Diferente de null.<br>
     */
    @SuppressWarnings("unchecked")
    public void agregar( T elem )
    {
        // Verifica si hay que aumentar el tamaño de la representación
        if( numElems == elems.length )
        {
            T viejo[] = elems;
            elems = ( T[] )new Object[elems.length + DELTA];
            System.arraycopy( viejo, 0, elems, 0, viejo.length );
        }
        elems[ numElems++ ] = elem;
    }

    /**
     * Inserta el elemento en la posición especificada. <br>
     * <b>post: </b> Se insertó el elemento en la posición especificada desplazando los elementos que se encuentran desde pos una posición adelante.<br>
     * Si con la adición se excede la capacidad de la lista, la nueva capacidad de la lista es elem.length+DELTA. <br>
     * @param elem Elemento a insertar<br>
     * @param pos Posición en la que se va a insertar el elemento<br>
     * @throws IndiceFueraDeRangoException Lanzada cuando la posición donde se quiere insertar el elemento es negativa o más grande que el tamaño de la lista menos uno.<br>
     */
    @SuppressWarnings("unchecked")
    public void insertar( T elem, int pos )
    {
        if( pos < 0 || pos > numElems )
        {
            throw new IndiceFueraDeRangoException( pos );
        }
        // Verifica si hay que aumentar el tamaño de la representación
        if( numElems == elems.length )
        {
            T viejo[] = elems;
            elems = ( T[] )new Object[elems.length + DELTA];
            System.arraycopy( viejo, 0, elems, 0, viejo.length );
        }
        // Abre espacio para el nuevo elemento
        for( int i = numElems - 1; i >= pos; i-- )
        {
            elems[ i + 1 ] = elems[ i ];
        }
        // Incrementa el número de elementos
        numElems++;
        // Inserta el nuevo elemento
        elems[ pos ] = elem;
    }

    /**
     * Elimina el elemento en la posición especificada. <br>
     * <b>post: </b> Se eliminó el elemento en la posición especificada desplazando todos los elementos que se encuentran desde pos+1 una posición hacia atrás. <br>
     * @param pos Posición del elemento a eliminar.
     * @return Elemento eliminado. En el caso de que no se elimine ningún elemento se retorna null.
     * @throws IndiceFueraDeRangoException Lanzada cuando la posición donde se quiere eliminar el elemento es negativa o más grande que el tamaño de la lista menos uno.<br>
     */
    public T eliminar( int pos )
    {
        if( pos < 0 || pos >= numElems )
        {
            throw new IndiceFueraDeRangoException( pos );
        }
        // Saca el elemento a eliminar para ser retornado.
        T resp = elems[ pos ];
        // Desplaza los elementos
        for( int i = pos; i < numElems - 1; i++ )
        {
            elems[ i ] = elems[ i + 1 ];
        }
        // Se vuelve null el último elemento
        elems[ numElems - 1 ] = null;
        // Se reduce la cantidad de elementos
        numElems--;
        // Se retorna el elemento eliminado
        return resp;
    }

    /**
     * Elimina el elemento especificado de la lista. <br>
     * <b>post: </b> Se eliminó el elemento especificado desplazando todos los elementos que se encuentran después de este elemento una posición hacia adelante.<br>
     * Si el elemento no existe se retorna null.<br>
     * @param elem Elemento a eliminar. Diferente de null<br>
     * @return Elemento eliminado o null si el elemento no existe.<br>
     */
    public T eliminar( T elem )
    {
        int pos = 0;
        // Se busca la posición del elemento a eliminar
        for( ; pos < numElems && !elem.equals( elems[ pos ] ); pos++ )
            ;

        T eliminado = null;
        // Si se encuentra la posición y es menor que la cantidad de elementos se llama el método eliminar por posición
        if( pos < numElems )
            eliminado = eliminar( pos );
        // Se retorna el elemento eliminado o null en caso que no exista
        return eliminado;
    }

    /**
     * Busca el elemento especificado. <br>
     * <b>post:</b> Se retornó la posición del elemento buscado dentro de la lista o -1 si no existe. <br>
     * @param elem Elemento a buscar. Diferente de null.<br>
     * @return Posición del elemento buscado dentro de la lista o -1 si no existe.<br>
     */
    public int buscar( T elem )
    {
        int pos = 0;
        for( ; pos < numElems && !elem.equals( elems[ pos ] ); pos++ )
            ;
        return pos == numElems ? -1 : pos;
    }

    /**
     * Devuelve la longitud de la lista. <br>
     * <b>post:</b> Se retornó la longitud de la lista (número de elementos).
     * @return Longitud de la lista. Entero mayor a cero o cero.
     */
    public int darLongitud( )
    {
        return numElems;
    }

    /**
     * Asigna a la posición especificada al elemento dado, reemplazando el elemento que se encuentra en esa posición <br>
     * <b>post:</b> El nuevo elemento en la posición pos es elem.<br>
     * @param elem Elemento a asignar<br>
     * @param pos Posición en la que se va a realizar la asignación<br>
     * @throws IndiceFueraDeRangoException Lanzada cuando la posición donde se quiere asignar el elemento es menor a cero o igual a la cantidad de elementos de la lista.
     *         También es lanzada cuando la lista no tiene posiciones.<br>
     */
    public void asignar( T elem, int pos )
    {
        if( pos < 0 || pos > numElems || numElems == 0 )
            throw new IndiceFueraDeRangoException( pos );
        elems[ pos ] = elem;
    }

    /**
     * Devuelve un iterador con los elementos de la lista. <br>
     * <b>post:</b> Se retornó iterador con los elementos de la lista.<br>
     * @return Iterador con los elementos de la lista, puede ser vacío pero no null.<br>
     */
    public Iterador<T> darIterador( )
    {
        IteradorSimple<T> respuesta = new IteradorSimple<T>( numElems );
        for( int i = 0; i < numElems; i++ )
        {
            try
            {
                respuesta.agregar( elems[ i ] );
            }
            catch( IteradorException e )
            {
                // Nunca debería ocurrir esta excepción
            }
        }
        return respuesta;
    }

    /**
     * Vacía la lista.<br>
     * <b>post:</b> La lista se encuentra vacía y la cantidad de elementos es cero.<br>
     */
    public void vaciar( )
    {
        // Se borran las referencias a los objetos presentes en la lista, para permitir el adecuado
        // trabajo del recolector de basura
        for( int i = 0; i < numElems; i++ )
            elems[ i ] = null;
        numElems = 0;
    }

    /**
     * Evalúa si el elemento dado se encuentra o no en la lista<br>
     * <b>post:</b> Se retornó true en caso que el elemento buscado se encuentre en la lista, false de lo contrario. <br>
     * @param elem Elemento a buscar. Diferente de null.<br>
     * @return True en caso que el elemento buscado se encuentre en la lista, false de lo contrario.<br>
     */
    public boolean contiene( T elem )
    {
        return buscar( elem ) != -1;
    }

    /**
     * Evalúa si la lista es vacía.
     * @return True en caso que la lista no tenga elementos, false de lo contrario.
     */
    public boolean esVacio( )
    {
        return darLongitud( ) == 0;
    }

    /**
     * Convierte la lista a un String. <br>
     * <b>post: </b> Se retornó la representación en String de la lista.<br>
     * El String tiene el formato "[numeroElementos]: e1-e2-e3..-en", donde e1, e2, ..., en son los elementos que contiene la lista y numeroElementos su longitud.<br>
     * @return La representación en String de la lista
     */
    @Override
    public String toString( )
    {
        String resp = "[" + numElems + "]:";
        for( int i = 0; i < numElems; i++ )
        {
            resp += elems[ i ] + "-";
        }
        return resp;
    }
}