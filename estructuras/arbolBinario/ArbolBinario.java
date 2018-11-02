import java.io.Serializable;

public class ArbolBinario<T> implements Serializable, IArbol<T>{

	private static final long serialVersionUID = 1L;
    private NodoArbolBinario<T> raiz;
    public ArbolBinario( ) {
        raiz = null;
    }
    public NodoArbolBinario<T> darRaiz( )
    {
        return raiz;
    }

    public void definirRaiz( NodoArbolBinario<T> nodo )
    {
        raiz = nodo;
    }

    public int darAltura( )
    {
        return ( raiz != null ) ? raiz.darAltura( ) : 0;
    }

    public int darPeso( )
    {
        return ( raiz != null ) ? raiz.darPeso( ) : 0;
    }

    public int contarHojas( )
    {
        return ( raiz != null ) ? raiz.contarHojas( ) : 0;
    }

    public int darTamanoNivel( int nivel )
    {
        return ( raiz != null ) ? raiz.darTamanoNivel( nivel ) : 0;
    }

    public boolean estaLleno( )
    {
        return ( raiz != null ) ? raiz.estaLleno( raiz.darAltura( ) ) : true;
    }

    public boolean estaCasiLleno( )
    {
        return ( raiz != null ) ? raiz.estaCasiLleno( raiz.darAltura( ) ) : true;
    }

    public boolean estaBalanceadoPorAltura( )
    {
        return ( raiz != null ) ? raiz.estaBalanceadoPorAltura( ) : false;
    }

    public boolean estaBalanceadoPorPeso( )
    {
        return ( raiz != null ) ? raiz.estaBalanceadoPorPeso( ) : false;
    }

    public boolean esCompleto( )
    {
        return ( raiz != null ) ? raiz.esCompleto( ) : false;
    }

    public T buscar( T modelo )
    {
        return ( raiz != null ) ? raiz.buscar( modelo ) : null;
    }

    public Iterador<T> darInordenIterativo( )
    {
        IteradorSimple<T> resultado = new IteradorSimple<T>( darPeso( ) );
        if( raiz != null )
        {
            raiz.inordenIterativo( resultado );
        }
        return resultado;
    }

    public Iterador<T> darPreordenIterativo( )
    {
        IteradorSimple<T> resultado = new IteradorSimple<T>( darPeso( ) );
        if( raiz != null )
        {
            raiz.preordenIterativo( resultado );
        }
        return resultado;
    }

    public Iterador<T> darPostordenIterativo( )
    {
        IteradorSimple<T> resultado = new IteradorSimple<T>( darPeso( ) );
        if( raiz != null )
        {
            raiz.postordenIterativo( resultado );
        }
        return resultado;
    }

    public Iterador<T> darInorden( )
    {
        IteradorSimple<T> resultado = new IteradorSimple<T>( darPeso( ) );
        if( raiz != null )
        {
            raiz.inorden( resultado );
        }
        return resultado;
    }

    public Iterador<T> darPreorden( )
    {
        IteradorSimple<T> resultado = new IteradorSimple<T>( darPeso( ) );
        if( raiz != null )
        {
            raiz.preorden( resultado );
        }
        return resultado;
    }

    public Iterador<T> darPostorden( )
    {
        IteradorSimple<T> resultado = new IteradorSimple<T>( darPeso( ) );
        if( raiz != null )
        {
            raiz.postorden( resultado );
        }
        return resultado;
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

    public Iterador<T> darCamino( T elem ) throws ElementoNoExisteException
    {
        IteradorSimple<T> resultado = new IteradorSimple<T>( darAltura( ) );
        if( raiz != null )
        {
            raiz.darCamino( elem, resultado );
        }
        return resultado;
    }

    public Iterador<T> darNivel( int nivel )
    {
        IteradorSimple<T> resultado = new IteradorSimple<T>( darPeso( ) );
        if( raiz != null )
        {
            raiz.darNivel( nivel, resultado );
        }
        return resultado;
    }

    public T darSiguienteInorden( T elem ) throws ElementoNoExisteException
    {
        if( raiz != null )
        {
            return raiz.darSiguienteInorden( elem );
        }
        throw new ElementoNoExisteException( "Elemento no encontrado" );
    }

    public Iterador<T> darRamaMasLarga( )
    {
        int altura = darAltura( );
        IteradorSimple<T> resultado = new IteradorSimple<T>( altura );
        if( raiz != null )
        {
            raiz.darRamaMasLarga( altura, resultado );
        }
        return resultado;
    }
}
