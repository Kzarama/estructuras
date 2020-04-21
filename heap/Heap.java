import java.io.Serializable;

public class Heap<T extends Comparable<? super T>> implements Serializable
{
	private static final long serialVersionUID = 1L;	
	
    private final static int INIT = 20;

    private final static int DELTA = 20;

    private Elemento<T>[] elementos;

    private int peso;

    public Heap( )
    {
        this( INIT );
    }

    @SuppressWarnings("unchecked")
	public Heap( int capacidad )
    {
        elementos = new Elemento[capacidad];
        peso = 0;
    }

    public T darRaiz( )
    {
        T raiz = null;
        if( peso > 0 )
        {
            raiz = elementos[ 0 ].darElemento( );
        }
        return raiz;
    }

    @SuppressWarnings("unchecked")
    public void insertar( T elemento )
    {
        if( peso == elementos.length )
        {
            Elemento viejo[] = elementos;
            elementos = new Elemento[elementos.length + DELTA];
            System.arraycopy( viejo, 0, elementos, 0, viejo.length );
        }

        Elemento<T> aux = new Elemento( elemento );
        peso++;
        int nuevo = peso - 1; // Indice inicial del nuevo elemento
        int padre = ( ( nuevo + 1 ) / 2 ) - 1; // Indice del padre del nuevo elemento

        if( peso == 1 )
        {
            padre = 0;
        }

        while( padre >= 0 && nuevo > 0 && aux.compareTo( elementos[ padre ] ) < 0 )
        {
            elementos[ nuevo ] = elementos[ padre ];
            nuevo = padre;
            padre = ( ( nuevo + 1 ) / 2 ) - 1;
        }

        elementos[ nuevo ] = aux;
    }

    public T eliminar( T elemento )
    {
        T eliminado = null;

        if( peso > 0 )
        {
            Elemento<T> aux = new Elemento<T>( elemento );
            int posEliminado = -1;
            int cont = 0;

            while( posEliminado == -1 && cont < peso )
            {
                if( elementos[ cont ].compareTo( aux ) == 0 )
                {
                    posEliminado = cont;
                }
                cont++;
            }

            if( posEliminado != -1 )
            {
                eliminado = elementos[ posEliminado ].darElemento( );
                if( posEliminado < peso - 1 )
                {
                    elementos[ posEliminado ] = elementos[ peso - 1 ];
                }
                elementos[ peso - 1 ] = null;
                peso--;

                reheap( posEliminado );
            }
        }
        return eliminado;
    }

    public T buscar( T modelo )
    {
        int cont = 0;
        T elemento = null;
        Elemento<T> aux = new Elemento<T>( modelo );
        while( cont < peso && elemento == null )
        {
            if( aux.compareTo( elementos[ cont ] ) == 0 )
            {
                elemento = elementos[ cont ].darElemento( );
            }

            cont++;
        }
        return elemento;
    }
    public Iterador<T> darRecorridoNiveles( )
    {
        IteradorSimple<T> resultado = new IteradorSimple<T>( peso );
        try
        {
            for( int cont = 0; cont < peso; cont++ )
            {
                resultado.agregar( elementos[ cont ].darElemento( ) );
            }

        }
        catch( IteradorException e )
        {
            e.printStackTrace( );
        }

        return resultado;
    }

    public int darAltura( )
    {
        int altura = 0;

        int cont = 0;

        while( Math.pow( 2, cont ) <= peso )
        {
            cont++;
        }

        altura = cont;

        return altura;
    }

    public int darPeso( )
    {
        return peso;
    }

    public T darMayor( )
    {
        T mayor = null;

        if( peso > 0 )
        {

            Elemento<T> aux = elementos[ 0 ];

            for( int cont = 1; cont < peso; cont++ )
            {
                if( elementos[ cont ].compareTo( aux ) > 0 )
                {
                    aux = elementos[ cont ];
                }
            }

            mayor = aux.darElemento( );
        }

        return mayor;
    }

    public T darMenor( )
    {
        T menor = null;

        if( peso > 0 )
        {
            menor = elementos[ 0 ].darElemento( );
        }
        return menor;
    }

    public T eliminarMenor( )
    {
        T elemento = null;
        if( peso > 0 )
        {
            elemento = eliminar( elementos[ 0 ].darElemento( ) );
        }
        return elemento;
    }

    public void vaciar( )
    {
        for( int i = 0; i < peso; i++ )
            elementos[ i ] = null;
        peso = 0;
    }

    public Elemento<T>[] darElementos( )
    {
        @SuppressWarnings("unused")
        Elemento<T>[] elems = null;

        if( peso > 0 )
        {
            elems = elementos;
        }

        return elementos;
    }

    private void reheap( int indice )
    {
        boolean terminado = false;

        Elemento<T> elemento = elementos[ indice ];
        int menor = ( 2 * ( indice + 1 ) ) - 1;

        while( !terminado && menor < peso )
        {
            int menorDerecho = menor + 1;

            if( menorDerecho < peso && elementos[ menorDerecho ].compareTo( elementos[ menor ] ) < 0 )
            {
                menor = menorDerecho;
            }

            if( elemento.compareTo( elementos[ menor ] ) > 0 )
            {
                elementos[ indice ] = elementos[ menor ];
                indice = menor;
                menor = ( 2 * ( indice + 1 ) ) - 1;
            }
            else
            {
                terminado = true;
            }
        }
        elementos[ indice ] = elemento;
    }
}