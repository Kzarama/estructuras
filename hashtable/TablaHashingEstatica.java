package estructuras;

import java.io.Serializable;

import uniandes.cupi2.collections.listaEncadenada.ListaEncadenada;
import uniandes.cupi2.collections.listaEncadenada.NoExisteException;
import uniandes.cupi2.collections.tablaHashing.EntradaTabla;
import uniandes.cupi2.collections.tablaHashing.ITablaHashing;
import uniandes.cupi2.collections.tablaHashing.LlaveInvalidaException;

/**
 * Clase que representa una hash table para el almacenamiento de elementos de tipo V y asociados con llave de tipo L. Los elementos del tipo L deben tener los métodos <b>
 * equals</b> y <b>hashCode </b> definidos correctamente
 * @param <L> Tipo de las llaves asociada con los elementos
 * @param <V> Tipo de los elementos guardados en la tabla
 */
public class TablaHashingEstatica<L, V> implements Serializable, ITablaHashing<L, V>
{	
    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------
	
    /**
	 * Constante para la serialización
	 */
	private static final long serialVersionUID = 1L;	

    /**
     * Tamaño inicial del arreglo de listas encadenadas
     */
    public static final int TAM_TABLA_HASHING = 11;

    /**
     * Factor de carga inicial de la tabla de hashing
     */
    public static final double FACTOR_CARGA = 0.75;

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Entradas de la tabla
     */
    private ListaEncadenada<EntradaTabla<L,V>>[] entradasTabla;

    /**
     * Factor de carga de la tabla
     */
    private double factorCarga;

    /**
     * El número de elementos que hay en la tabla
     */
    private int numeroElementos;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye una tabla con 11 entradas y factor de carga 0.75. <br>
     * <b>post: </b> Se construyó una tabla vacía con 11 entradas y factor de carga 0.75.
     */
    @SuppressWarnings("unchecked")
    public TablaHashingEstatica( )
    {
        entradasTabla = new ListaEncadenada[TAM_TABLA_HASHING];
        factorCarga = FACTOR_CARGA;
        numeroElementos = 0;

        // Inicializar los buckets (listas)
        inicializarListas( );
    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------
    
    /* (non-Javadoc)
     * @see uniandes.cupi2.collections.tablaHashing.ITablaHashing#agregar(java.lang.Object, java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public V agregar( L llave, V elemento )
    {
        if( llave != null )
        {
            V elementoAnterior = null;
            int indice = obtenerIndiceHash( llave );
            EntradaTabla<L, V> entrada = buscarLugarInsercion( indice, llave );

            if( entrada == null ) // Si la entrada es null la llave no se encuentra asociada con otro elemento
            {
                EntradaTabla<L, V> nuevaEntrada = new EntradaTabla( llave, elemento );
                entradasTabla[ indice ].insertarCola( nuevaEntrada );
                numeroElementos++;
                rehash( );
            }
            else
            // la llave se encuentra asociada con un elemento
            {
                elementoAnterior = entrada.darElemento( );
                entrada.cambiarElemento( elemento );
            }
            return elementoAnterior;
        }
        else
        {
            throw new LlaveInvalidaException( "La llave para agregar un elemento no puede ser null" );
        }

    }

    
    /* (non-Javadoc)
     * @see uniandes.cupi2.collections.tablaHashing.ITablaHashing#dar(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public V dar( L llave )
    {
        if( llave != null )
        {
            int indice = obtenerIndiceHash( llave );
            ListaEncadenada<EntradaTabla<L,V>> lista = entradasTabla[ indice ];
            V elemento = null;
            EntradaTabla entrada = new EntradaTabla( llave );
            entrada = lista.buscar( entrada );

            if( entrada != null )
            {
                elemento = ( V )entrada.darElemento( );
            }

            return elemento;
        }
        else
        {
            throw new LlaveInvalidaException( "La llave para consultar un elemento no puede ser null" );
        }
    }

    
    /* (non-Javadoc)
     * @see uniandes.cupi2.collections.tablaHashing.ITablaHashing#eliminar(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public V eliminar( L llave )
    {
        if( llave != null )
        {
            int indice = obtenerIndiceHash( llave );

            ListaEncadenada<EntradaTabla<L,V>> lista = entradasTabla[ indice ];
            V elemento = null;
            EntradaTabla entrada = new EntradaTabla( llave );

            try
            {
                entrada = ( EntradaTabla )lista.eliminar( entrada );
                elemento = ( V )entrada.darElemento( );
                numeroElementos--;
                return elemento;
            }
            catch( NoExisteException e )
            {
                return null;
            }

        }
        else
        {
            throw new LlaveInvalidaException( "La llave para eliminar un elemento no puede ser null" );
        }

    }

    /**
     * Realiza el rehash si el factor de carga es mayor o igual a factorCarga. <br>
     * <b>post: </b> Se realizó el reshash de la tabla.<br>
     */
    @SuppressWarnings("unchecked")
    protected void rehash( )
    {
        int tam = entradasTabla.length;
        double factor = numeroElementos / tam;

        if( factor >= factorCarga )
        {
            ListaEncadenada<EntradaTabla<L,V>>[] entradasAnteriores = entradasTabla;
            int nuevoTam = obtenerPrimo( tam * 2 );
            entradasTabla = new ListaEncadenada[nuevoTam];
            inicializarListas( );
            numeroElementos = 0;
            // Adicionar todos los elementos existentes
            for( int cont = 0; cont < tam; cont++ )
            {
                ListaEncadenada<EntradaTabla<L,V>> lista = entradasAnteriores[ cont ];

                for( int cont2 = 0; cont2 < lista.darLongitud( ); cont2++ )
                {
                    EntradaTabla<L, V> entrada = lista.dar( cont2 );
                    agregar( entrada.darLlave( ), entrada.darElemento( ) );

                }
            }
        }
    }

    
    /* (non-Javadoc)
     * @see uniandes.cupi2.collections.tablaHashing.ITablaHashing#darNumeroElementos()
     */
    public int darNumeroElementos( )
    {
        return numeroElementos;
    }

    /**
     * Retorna el tamaño de la tabla. <br>
     * <b>post: </b> Se retornó el tamaño de la tabla (capacidad).
     * @return El tamaño de la tabla (capacidad)
     */
    public int darTamanhoTabla( )
    {
        return entradasTabla.length;
    }

    // -----------------------------------------------------------------
    // Métodos auxiliares
    // -----------------------------------------------------------------
    /**
     * Busca el lugar en donde se debe situar una nueva entrada con la llave especificada dentro del bucket dado. <br>
     * <b>pre: </b> indice!=null, indice>=0, llave!=null. <br>
     * <b>post: </b> Se retorno la entrada en la que debe insertarse el elemento con la lleve especificada.
     * @param indice Indice del bucket al que corresponde la llave
     * @param llave La llave a la que se le va a localizar el lugar de inserción
     * @return Null si no existe una entrada asociada con la llave especificada o la entrada en la que debe adicionarse el elemento asociado con la llave dada
     */
    @SuppressWarnings("unchecked")
    private EntradaTabla buscarLugarInsercion( int indice, L llave )
    {
        EntradaTabla<L, V> lugarInsercion = new EntradaTabla( llave );
        ListaEncadenada<EntradaTabla<L,V>> lista = entradasTabla[ indice ];
        lugarInsercion = lista.buscar( lugarInsercion );
        return lugarInsercion;
    }

    /**
     * Obtiene el indice hash de la llave especificada. <br>
     * <b>post: </b> Se retornó el indice correspondiente a la llave especificada.
     * @param llave La llave de la que se desea el código hash
     * @return El índice correspondiente a la llave
     */
    private int obtenerIndiceHash( L llave )
    {

        int tamTabla = entradasTabla.length;
        int codigoHash = llave.hashCode( );
        int indice = codigoHash % tamTabla;

        if( indice < 0 )
        {
            indice += tamTabla;
        }

        return indice;
    }

    /**
     * Obtiene el número primo más cercano al valor especificado. <br>
     * <b>post: </b> Se retornó el número entero primo más cercano a numero.
     * @param numero El valor desde donde se va a calcular un número primo
     * @return El número primo más cercano a numero
     */
    private int obtenerPrimo( int numero )
    {
        int primo = numero + 1;
        while( !esPrimo( primo ) )
        {
            primo++;
        }
        return primo;
    }

    /**
     * Indica si el número especificado es primo<br>
     * <b>post: </b> Se retornó true si el número especificado es primo o false de lo contrario<br>
     * @param numero El número a corroborar si es primo
     * @return True si el número es primo o false de lo contrario
     */
    private boolean esPrimo( int numero )
    {
        boolean primo = true;

        if( numero % 2 == 0 && numero != 2 )
        {
            primo = false;
        }
        else
        {
            int raiz = ( int )Math.sqrt( numero );

            for( int cont = 3; cont <= raiz && primo; cont += 2 )
            {
                if( numero % cont == 0 )
                {
                    primo = false;
                }
            }
        }
        return primo;
    }

    /**
     * Inicializa las listas que representan los buckets en la tabla de hashing. <br>
     * <b>pre: </b> entradasTabla!=null. <br>
     * <b>post: </b> Se inicializaron las lista que representan los buckets.
     */
    @SuppressWarnings("unchecked")
    private void inicializarListas( )
    {
        for( int cont = 0; cont < entradasTabla.length; cont++ )
        {
            entradasTabla[ cont ] = new ListaEncadenada( );
        }
    }
}
