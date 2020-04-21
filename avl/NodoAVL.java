import java.io.Serializable;

public class NodoAVL<T extends Comparable<? super T>> implements Serializable
{
	private static final long serialVersionUID = 1L;

    private static final int BIZQ = 1;


    private static final int BAL = 0;


    private static final int BDER = -1;

    private T elem;

    private NodoAVL<T> derNodo;

    private NodoAVL<T> izqNodo;

    private int balance;

    public NodoAVL( T pElemento )
    {
        elem = pElemento;
        derNodo = null;
        izqNodo = null;
    }

    public T darRaiz( )
    {
        return elem;
    }

    public NodoAVL<T> darHijoDerecho( )
    {
        return derNodo;
    }

    public NodoAVL<T> darHijoIzquierdo( )
    {
        return izqNodo;
    }

    public NodoAVL<T> insertar( T pElemento ) throws ElementoExisteException
    {
        Retorno retorno = new Retorno( null, false );
        auxInsertar( pElemento, retorno );
        return retorno.respuesta;
    }

    public NodoAVL<T> eliminar( T pElemento ) throws ElementoNoExisteException
    {
        Retorno retorno = new Retorno( null, false );
        auxEliminar( pElemento, retorno );
        return retorno.respuesta;
    }

    public T buscar( T modelo )
    {
        int resultado = elem.compareTo( modelo );
        if( resultado == 0 )
        {
            return elem;
        }
        else if( resultado > 0 )
        {
            return ( izqNodo != null ) ? izqNodo.buscar( modelo ) : null;
        }
        else
        {
            return ( derNodo != null ) ? derNodo.buscar( modelo ) : null;
        }
    }

    public void inorden( IteradorSimple<T> resultado )
    {
        if( izqNodo != null )
        {
            izqNodo.inorden( resultado );
        }

        try
        {
            resultado.agregar( elem );
        }
        catch( IteradorException e )
        {
        }

        if( derNodo != null )
        {
            derNodo.inorden( resultado );
        }
    }

    public int darAltura( )
    {
        int a1 = ( izqNodo == null ) ? 0 : izqNodo.darAltura( );
        int a2 = ( derNodo == null ) ? 0 : derNodo.darAltura( );
        return ( a1 >= a2 ) ? a1 + 1 : a2 + 1;
    }

    public T darMayor( )
    {
        NodoAVL<T> nodo = mayorElemento( );
        return ( nodo == null ) ? null : nodo.darRaiz( );
    }

    public T darMenor( )
    {
        NodoAVL<T> nodo = menorElemento( );
        return ( nodo == null ) ? null : nodo.darRaiz( );
    }

    private void auxInsertar( T pElemento, Retorno retorno ) throws ElementoExisteException
    {
        int resultado = elem.compareTo( pElemento );
        if( resultado == 0 )
        {
            throw new ElementoExisteException( "El elemento ya existe en el árbol" );
        }
        else if( resultado > 0 )
        {
            if( izqNodo == null )
            {
                izqNodo = new NodoAVL<T>( pElemento );
                retorno.respuesta = this;
                if( derNodo == null )
                {
                    balance = BIZQ;
                    retorno.diferenciaAltura = true;
                }
                else
                {
                    balance = BAL;
                    retorno.diferenciaAltura = false;
                }
            }
            else
            {
                izqNodo.auxInsertar( pElemento, retorno );
                izqNodo = retorno.respuesta;

                if( retorno.diferenciaAltura )
                {
                    switch( balance )
                    {
                        case BIZQ:
                            retorno.diferenciaAltura = false;
                            retorno.respuesta = balanceaIzq( );
                            break;
                        case BAL:
                            balance = BIZQ;
                            retorno.respuesta = this;
                            break;
                        case BDER:
                            balance = BAL;
                            retorno.diferenciaAltura = false;
                            retorno.respuesta = this;
                            break;
                    }
                }
                else
                {
                    retorno.respuesta = this;
                }
            }
        }
        else
        {
            if( derNodo == null )
            {
                derNodo = new NodoAVL<T>( pElemento );
                retorno.respuesta = this;
                if( izqNodo == null )
                {
                    balance = BDER;
                    retorno.diferenciaAltura = true;
                }
                else
                {
                    balance = BAL;
                    retorno.diferenciaAltura = false;
                }
            }
            else
            {
                derNodo.auxInsertar( pElemento, retorno );
                derNodo = retorno.respuesta;

                if( retorno.diferenciaAltura )
                {
                    switch( balance )
                    {
                        case BIZQ:
                            balance = BAL;
                            retorno.diferenciaAltura = false;
                            retorno.respuesta = this;
                            break;
                        case BAL:
                            balance = BDER;
                            retorno.respuesta = this;
                            break;
                        case BDER:
                            retorno.diferenciaAltura = false;
                            retorno.respuesta = balanceaDer( );
                            break;
                    }
                }
                else
                {
                    retorno.respuesta = this;
                }
            }
        }
    }

    private void auxEliminar( T pElemento, Retorno retorno ) throws ElementoNoExisteException
    {
        int resultado = elem.compareTo( pElemento );
        if( resultado == 0 )
        {
            if( izqNodo == null & derNodo == null )
            {
                retorno.diferenciaAltura = true;
                retorno.respuesta = null;
            }
            else if( izqNodo == null )
            {
                retorno.respuesta = derNodo;
                retorno.diferenciaAltura = true;
            }
            else
            {
                NodoAVL<T> reemplazo = izqNodo.mayorElemento( );
                elem = reemplazo.elem;
                izqNodo.auxEliminar( reemplazo.elem, retorno );
                izqNodo = retorno.respuesta;

                if( retorno.diferenciaAltura )
                {
                    balanElimDer( retorno );
                }
                else
                {
                    retorno.respuesta = this;
                }
            }
        }
        else if( resultado > 0 )
        {
            if( izqNodo == null )
            {
                throw new ElementoNoExisteException( "El elemento no se encuentra en el árbol" );
            }
            izqNodo.auxEliminar( pElemento, retorno );
            izqNodo = retorno.respuesta;

            if( retorno.diferenciaAltura )
            {
                balanElimDer( retorno );
            }
            else
            {
                retorno.respuesta = this;
            }
        }
        else
        {
            if( derNodo == null )
            {
                throw new ElementoNoExisteException( "El elemento no se encuentra en el árbol" );
            }
            derNodo.auxEliminar( pElemento, retorno );
            derNodo = retorno.respuesta;

            if( retorno.diferenciaAltura )
            {
                balanElimIzq( retorno );
            }
            else
            {
                retorno.respuesta = this;
            }
        }
    }

    private NodoAVL<T> balanceaIzq( )
    {
        if( izqNodo.balance == BIZQ )
        {
            balance = BAL;
            izqNodo.balance = BAL;
            return roteDer( );
        }
        else
        {
            switch( izqNodo.derNodo.balance )
            {
                case BIZQ:
                    balance = BDER;
                    izqNodo.balance = BAL;
                    break;
                case BAL:
                    balance = BAL;
                    izqNodo.balance = BAL;
                    break;
                case BDER:
                    balance = BAL;
                    izqNodo.balance = BIZQ;
                    break;
            }
            izqNodo.derNodo.balance = BAL;
            return roteIzqDer( );
        }
    }

    private NodoAVL<T> balanceaDer( )
    {
        if( derNodo.balance == BDER )
        {
            balance = BAL;
            derNodo.balance = BAL;
            return roteIzq( );
        }
        else
        {
            switch( derNodo.izqNodo.balance )
            {
                case BIZQ:
                    balance = BAL;
                    derNodo.balance = BDER;
                    break;
                case BAL:
                    balance = BAL;
                    derNodo.balance = BAL;
                    break;
                case BDER:
                    balance = BIZQ;
                    derNodo.balance = BAL;
                    break;
            }
            derNodo.izqNodo.balance = BAL;
            return roteDerIzq( );
        }
    }

    private NodoAVL<T> roteIzq( )
    {
        NodoAVL<T> temp = derNodo;
        derNodo = temp.izqNodo;
        temp.izqNodo = this;
        return temp;
    }

    private NodoAVL<T> roteDer( )
    {
        NodoAVL<T> temp = izqNodo;
        izqNodo = temp.derNodo;
        temp.derNodo = this;
        return temp;
    }

    private NodoAVL<T> roteDerIzq( )
    {
        derNodo = derNodo.roteDer( );
        return roteIzq( );
    }

    private NodoAVL<T> roteIzqDer( )
    {
        izqNodo = izqNodo.roteIzq( );
        return roteDer( );
    }

    private void balanElimDer( Retorno retorno )
    {
        switch( balance )
        {
            case BIZQ:
                balance = BAL;
                retorno.respuesta = this;
                break;
            case BAL:
                balance = BDER;
                retorno.diferenciaAltura = false;
                retorno.respuesta = this;
                break;
            case BDER:
                if( derNodo.balance != BIZQ )
                {
                    retorno.respuesta = roteIzq( );
                    if( retorno.respuesta.balance == BAL )
                    {
                        retorno.respuesta.balance = BIZQ;
                        retorno.respuesta.izqNodo.balance = BDER;
                        retorno.diferenciaAltura = false;
                    }
                    else
                    {
                        retorno.respuesta.balance = BAL;
                        retorno.respuesta.izqNodo.balance = BAL;
                    }
                }
                else
                {
                    retorno.respuesta = roteDerIzq( );
                    if( retorno.respuesta.balance == BDER )
                    {
                        retorno.respuesta.izqNodo.balance = BIZQ;
                    }
                    else
                    {
                        retorno.respuesta.izqNodo.balance = BAL;
                    }
                    if( retorno.respuesta.balance == BIZQ )
                    {
                        retorno.respuesta.derNodo.balance = BDER;
                    }
                    else
                    {
                        retorno.respuesta.derNodo.balance = BAL;
                    }
                    retorno.respuesta.balance = BAL;
                }
                break;
        }
    }

    private void balanElimIzq( Retorno retorno )
    {
        switch( balance )
        {
            case BIZQ:
                if( izqNodo.balance != BDER )
                {
                    retorno.respuesta = roteDer( );
                    if( retorno.respuesta.balance == BAL )
                    {
                        retorno.respuesta.balance = BDER;
                        retorno.respuesta.derNodo.balance = BIZQ;
                        retorno.diferenciaAltura = false;
                    }
                    else
                    {
                        retorno.respuesta.balance = BAL;
                        retorno.respuesta.derNodo.balance = BAL;
                    }
                }
                else
                {
                    retorno.respuesta = roteIzqDer( );
                    if( retorno.respuesta.balance == BIZQ )
                    {
                        retorno.respuesta.derNodo.balance = BDER;
                    }
                    else
                    {
                        retorno.respuesta.derNodo.balance = BAL;
                    }
                    if( retorno.respuesta.balance == BDER )
                    {
                        retorno.respuesta.izqNodo.balance = BIZQ;
                    }
                    else
                    {
                        retorno.respuesta.izqNodo.balance = BAL;
                    }
                    retorno.respuesta.balance = BAL;
                }
                break;
            case BAL:
                balance = BIZQ;
                retorno.diferenciaAltura = false;
                retorno.respuesta = this;
                break;
            case BDER:
                balance = BAL;
                retorno.respuesta = this;
                break;
        }
    }

    private NodoAVL<T> mayorElemento( )
    {
        return ( derNodo == null ) ? this : derNodo.mayorElemento( );
    }

    private NodoAVL<T> menorElemento( )
    {
        return ( izqNodo == null ) ? this : izqNodo.menorElemento( );
    }

    public void darRecorridoNiveles( IteradorSimple<T> resultado )
    {
        ColaEncadenada<NodoAVL<T>> cola = new ColaEncadenada<NodoAVL<T>>( );
        cola.insertar( this );
        while( cola.darLongitud( ) != 0 )
        {
            NodoAVL<T> nodo = null;
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

            if( nodo.izqNodo != null )
            {
                cola.insertar( nodo.izqNodo );
            }
            if( nodo.derNodo != null )
            {
                cola.insertar( nodo.derNodo );
            }
        }
    }

    private class Retorno
    {
        private NodoAVL<T> respuesta;

        private boolean diferenciaAltura;

        private Retorno( NodoAVL<T> pRespuesta, boolean pDiferenciaAltura )
        {
            respuesta = pRespuesta;
            diferenciaAltura = pDiferenciaAltura;
        }
    }
}
