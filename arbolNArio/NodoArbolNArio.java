import java.io.Serializable;
public class NodoArbolNArio<T> implements Serializable
{
	private static final long serialVersionUID = 1L;
    private T elem;

    private Lista<NodoArbolNArio<T>> hijos;

    public NodoArbolNArio( T pElem )
    {
        elem = pElem;
        hijos = null;
    }
    public void agregarHijo( NodoArbolNArio<T> nodo )
    {
        if( hijos == null )
            hijos = new Lista<NodoArbolNArio<T>>( );
        hijos.agregar( nodo );
    }

    public T darElemento( )
    {
        return elem;
    }
    public Lista<NodoArbolNArio<T>> darHijos( )
    {
        return hijos;
    }

    public void asignarElemento( T pElem )
    {
        elem = pElem;
    }

    public boolean esHoja( )
    {
        return hijos == null;
    }

    public int darAltura( )
    {
        if( esHoja( ) )
            return 1;
        else
        {
            int maxAltura = 0;
            for( int i = 0; i < hijos.darLongitud( ); i++ )
            {
                NodoArbolNArio<T> hijo = hijos.darElemento( i );
                int auxAltura = hijo.darAltura( );
                if( auxAltura > maxAltura )
                    maxAltura = auxAltura;
            }
            return maxAltura + 1;
        }
    }

    public int darPeso( )
    {
        if( esHoja( ) )
            return 1;
        else
        {
            int peso = 0;
            for( int i = 0; i < hijos.darLongitud( ); i++ )
            {
                peso += hijos.darElemento( i ).darPeso( );
            }
            return peso + 1;
        }
    }

    public int contarHojas( )
    {
        if( esHoja( ) )
            return 1;
        else
        {
            int numHojas = 0;
            for( int i = 0; i < hijos.darLongitud( ); i++ )
            {
                numHojas += hijos.darElemento( i ).contarHojas( );
            }
            return numHojas;
        }
    }

    public T buscar( T modelo )
    {
        if( modelo.equals( elem ) )
            return elem;
        else if( esHoja( ) )
            return null;
        else
        {
            for( int i = 0; i < hijos.darLongitud( ); i++ )
            {
                T aux = hijos.darElemento( i ).buscar( modelo );
                if( aux != null )
                    return aux;
            }
            return null;
        }
    }

    public void inorden( IteradorSimple<T> resultado )
    {
        if( hijos == null )
        {
            try
            {
                resultado.agregar( elem );
            }
            catch( IteradorException e )
            {
            }
        }
        else
        {
            hijos.darElemento( 0 ).inorden( resultado );
            try
            {
                resultado.agregar( elem );
            }
            catch( IteradorException e )
            {
            }
            for( int i = 1; i < hijos.darLongitud( ); i++ )
            {
                hijos.darElemento( i ).inorden( resultado );
            }
        }
    }

    public void preorden( IteradorSimple<T> resultado )
    {
        try
        {
            resultado.agregar( elem );
        }
        catch( IteradorException e )
        {
        }
        for( int i = 0; hijos != null && i < hijos.darLongitud( ); i++ )
        {
            hijos.darElemento( i ).preorden( resultado );
        }
    }

    public void postorden( IteradorSimple<T> resultado )
    {
        for( int i = 0; hijos != null && i < hijos.darLongitud( ); i++ )
        {
            hijos.darElemento( i ).postorden( resultado );
        }
        try
        {
            resultado.agregar( elem );
        }
        catch( IteradorException e )
        {
        }
    }
    public void darRecorridoNiveles( IteradorSimple<T> resultado )
    {
        ColaEncadenada<NodoArbolNArio<T>> cola = new ColaEncadenada<NodoArbolNArio<T>>( );
        cola.insertar( this );
        while( cola.darLongitud( ) != 0 )
        {
            NodoArbolNArio<T> nodo = null;
            try
            {
                nodo = cola.tomarElemento( );
            }
            catch( ColaVaciaException e )
            {
            }
            try
            {
                resultado.agregar( nodo.elem );
            }
            catch( IteradorException e )
            {
            }

            for( int i = 0; nodo.hijos != null && i < nodo.hijos.darLongitud( ); i++ )
            {
                cola.insertar( nodo.hijos.darElemento( i ) );
            }
        }

    }

    public int darTamanoNivel( int nivel )
    {
        if( nivel == 0 )
        {
            return 1;
        }

        int cantidadElementos = 0;
        for( int i = 0; hijos != null && i < hijos.darLongitud( ); i++ )
        {
            cantidadElementos += hijos.darElemento( i ).darTamanoNivel( nivel - 1 );
        }

        return cantidadElementos;
    }

    public void darNivel( int nivel, IteradorSimple<T> resultado )
    {
        if( nivel == 0 )
        {
            try
            {
                resultado.agregar( elem );
            }
            catch( IteradorException e )
            {
            }
        }
        else
        {
            for( int i = 0; hijos != null && i < hijos.darLongitud( ); i++ )
            {
                hijos.darElemento( i ).darNivel( nivel - 1, resultado );
            }
        }
    }
}
