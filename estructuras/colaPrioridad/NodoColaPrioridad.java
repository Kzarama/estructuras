import java.io.Serializable;

public class NodoColaPrioridad<T extends Comparable<? super T>, E> implements Serializable
{
	private static final long serialVersionUID = 1L;
    private E elemento;

    private T prioridad;

    private NodoColaPrioridad<T, E> sigNodo;

    public NodoColaPrioridad( T pPrioridad, E pElemento )
    {
        elemento = pElemento;
        prioridad = pPrioridad;
        sigNodo = null;
    }

    public E darElemento( )
    {
        return elemento;
    }

    public T darPrioridad( )
    {
        return prioridad;
    }

    public NodoColaPrioridad<T, E> desconectarPrimero( )
    {
        NodoColaPrioridad<T, E> p = sigNodo;
        sigNodo = null;
        return p;
    }

    public NodoColaPrioridad<T, E> insertarDespues( NodoColaPrioridad<T, E> nodo )
    {
        sigNodo = nodo;
        return nodo;
    }

    public NodoColaPrioridad<T, E> darSiguiente( )
    {
        return sigNodo;
    }

    @Override
    public String toString( )
    {
        return "(" + prioridad.toString( ) + "," + elemento.toString( ) + ")";
    }
}
