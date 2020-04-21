import java.io.Serializable;
public class ColaPrioridad<T extends Comparable<? super T>, E> implements Serializable
{
	private static final long serialVersionUID = 1L;	
    protected NodoColaPrioridad<T, E> primero;

    protected NodoColaPrioridad<T, E> ultimo;

    protected int numElems;

    public ColaPrioridad( )
    {
        primero = null;
        ultimo = null;
        numElems = 0;
    }

    public int darLongitud( )
    {
        return numElems;
    }

    public E tomarElemento( ) throws ColaVaciaException
    {
        if( primero == null )
            throw new ColaVaciaException( "No hay elementos en la cola" );
        else
        {
            NodoColaPrioridad<T, E> p = primero;
            primero = primero.desconectarPrimero( );
            if( primero == null )
                ultimo = null;
            numElems--;
            return p.darElemento( );
        }
    }

    public void insertar( T prioridad, E elem )
    {
        NodoColaPrioridad<T, E> nodo = new NodoColaPrioridad<T, E>( prioridad, elem );
        if( primero == null )
        {
            primero = nodo;
            ultimo = nodo;
        }
        else if( primero.darPrioridad( ).compareTo( prioridad ) < 0 )
        {
            nodo.insertarDespues( primero );
            primero = nodo;
        }
        else
        {
            boolean inserto = false;
            for( NodoColaPrioridad<T, E> p = primero; !inserto && p.darSiguiente( ) != null; p = p.darSiguiente( ) )
            {
                if( p.darSiguiente( ).darPrioridad( ).compareTo( prioridad ) < 0 )
                {
                    nodo.insertarDespues( p.darSiguiente( ) );
                    p.insertarDespues( nodo );
                    inserto = true;
                }
            }
            if( !inserto )
            {
                ultimo = ultimo.insertarDespues( nodo );
            }
        }
        numElems++;
    }

    public NodoColaPrioridad<T, E> darPrimero( )
    {
        return primero;
    }

    public NodoColaPrioridad<T, E> darUltimo( )
    {
        return ultimo;
    }

    public boolean estaVacia( )
    {
        return primero == null;
    }

    @Override
    public String toString( )
    {
        String resp = "[" + numElems + "]:";
        for( NodoColaPrioridad<T, E> p = primero; p != null; p = p.darSiguiente( ) )
        {
            resp += "->" + p.toString( );
        }
        return resp;
    }

}
