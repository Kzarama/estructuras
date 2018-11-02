import java.io.Serializable;

public class ArbolAVL<T extends Comparable<? super T>> implements Serializable, IArbolOrdenado<T>
{
	private static final long serialVersionUID = 1L;

    private NodoAVL<T> raiz;

    private int peso;

    public ArbolAVL( )
    {
        raiz = null;
        peso = 0;
    }

    public ArbolAVL( NodoAVL<T> r, int p )
    {
        raiz = r;
        peso = p;
    }

    public NodoAVL<T> darRaiz( )
    {
        return raiz;
    }

    public void insertar( T elemento ) throws ElementoExisteException
    {
        if( raiz == null )
        {
            raiz = new NodoAVL<T>( elemento );
        }
        else
        {
            raiz = raiz.insertar( elemento );
        }
        peso++;
    }

    public void eliminar( T elemento ) throws ElementoNoExisteException
    {
        if( raiz != null )
        {
            raiz = raiz.eliminar( elemento );
            peso--;
        }
        else
        {
            throw new ElementoNoExisteException( "El elemento especificado no existe en el árbol" );
        }
    }

    public T buscar( T modelo )
    {
        return ( raiz != null ) ? raiz.buscar( modelo ) : null;
    }

    public Iterador<T> inorden( )
    {
        IteradorSimple<T> resultado = new IteradorSimple<T>( peso );
        if( raiz != null )
        {
            raiz.inorden( resultado );
        }
        return resultado;
    }

    public int darAltura( )
    {
        return ( raiz != null ) ? raiz.darAltura( ) : 0;
    }

    public int darPeso( )
    {
        return peso;
    }

    public T darMayor( )
    {
        return ( raiz != null ) ? raiz.darMayor( ) : null;
    }

    public T darMenor( )
    {
        return ( raiz != null ) ? raiz.darMenor( ) : null;
    }

    public Iterador<T> darRecorridoNiveles( )
    {
        IteradorSimple<T> resultado = new IteradorSimple<T>( darPeso( ) );
        if( raiz != null )
        {
            raiz.darRecorridoNiveles( resultado );
        }
        return resultado;
    }
}
