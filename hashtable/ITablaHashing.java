/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad de los Andes (Bogotá - Colombia)
 * $Id: ITablaHashing.java,v 1.1 2008/03/27 16:36:47 jua-gome Exp $
 * Departamento de Ingeniería de Sistemas y Computación 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Framework: Cupi2Collections
 * Autor: Juan Erasmo Gómez - Marzo 12, 2008
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package uniandes.cupi2.collections.tablaHashing;

/**
 * Interfaz que representa una hash table para el almacenamiento de elementos de tipo <code>V</code> y asociados con llave de tipo <code>L</code>. Los elementos del tipo
 * <code>L</code> deben tener los métodos <code> equals</code> y <code>hashCode</code> definidos correctamente
 * @param <L> Tipo de las llaves asociada con los elementos
 * @param <V> Tipo de los elementos guardados en la tabla
 */
public interface ITablaHashing<L, V>
{

    /**
     * Agrega un entrada a la tabla con el elemento y llave especificados. En caso que ya exista un elemento con esa llave, será reemplazado.
     * @param llave La llave asociada con el elemento.
     * @param elemento El elemento a ser agregado.
     * @return El antiguo elemento asociado con la llave o <code>null</code> si no tenía ningún elemento asociado.
     */
    public V agregar( L llave, V elemento );

    /**
     * Retorna el elemento asociado con la llave especificada.
     * @param llave La llave del elemento que se desea recuperar.
     * @return El elemento asociado con la llave o <code>null</code> si no existe tal asociación.
     */
    public V dar( L llave );

    /**
     * Elimina el elemento asociado con la llave especificada.
     * @param llave Llave del elemento que se desea eliminar.
     * @return El elemento eliminado o <code>null</code> si la llave no tiene ningún elemento asociado en la tabla.
     */
    public V eliminar( L llave );

    /**
     * Retorna el número de elementos que hay en la tabla
     * @return El número de elementos que hay en la tabla
     */
    public int darNumeroElementos( );

}
