import java.io.Serializable;

public class NodoArbolBinario<T> implements Serializable
{
	private static final long serialVersionUID = 1L;
	
    private T elem;

    private NodoArbolBinario<T> izqNodo;

    private NodoArbolBinario<T> derNodo;

    public NodoArbolBinario( T pElem )
    {
        elem = pElem;
        izqNodo = null;
        derNodo = null;
    }

    public T darElemento( )
    {
        return elem;
    }

    public NodoArbolBinario<T> darIzquierdo( )
    {
        return izqNodo;
    }

    public NodoArbolBinario<T> darDerecho( )
    {
        return derNodo;
    }

    public void encadenarIzquierdo( NodoArbolBinario<T> nodo )
    {
        izqNodo = nodo;
    }

    public void encadenarDerecho( NodoArbolBinario<T> nodo )
    {
        derNodo = nodo;
    }

    public void asignarElemento( T pElem )
    {
        elem = pElem;
    }

    public boolean esHoja( )
    {
        return izqNodo == null && derNodo == null;
    }

    public int darAltura( )
    {
        int a1 = ( izqNodo == null ) ? 0 : izqNodo.darAltura( );
        int a2 = ( derNodo == null ) ? 0 : derNodo.darAltura( );
        return ( a1 >= a2 ) ? a1 + 1 : a2 + 1;
    }

    public int darPeso( )
    {
        int p1 = ( izqNodo == null ) ? 0 : izqNodo.darPeso( );
        int p2 = ( derNodo == null ) ? 0 : derNodo.darPeso( );
        return p1 + p2 + 1;
    }

    public int contarHojas( )
    {
        if( esHoja( ) )
            return 1;
        else
        {
            int h1 = ( izqNodo == null ) ? 0 : izqNodo.contarHojas( );
            int h2 = ( derNodo == null ) ? 0 : derNodo.contarHojas( );
            return h1 + h2;
        }
    }


    public T buscar( T modelo )
    {
        if( modelo.equals( elem ) )
            return elem;
        else
        {
            T temp = ( izqNodo == null ) ? null : izqNodo.buscar( modelo );
            if( temp != null )
                return temp;
            else
                return ( derNodo == null ) ? null : derNodo.buscar( modelo );
        }
    }

    public void inordenIterativo( IteradorSimple<T> resultado )
    {
        PilaEncadenada<NodoArbolBinario<T>> pila = new PilaEncadenada<NodoArbolBinario<T>>( );
        NodoArbolBinario<T> nodo = this;
        while( nodo != null || pila.darLongitud( ) != 0 )
        {
            if( nodo == null )
            {
                try
                {
                    // Toma el primer árbol de la pila
                    nodo = pila.tomarElemento( );
                }
                catch( PilaVaciaException e )
                {
                    // Nunca debería aparecer esta excepción
                }
                // Recorre el nodo
                try
                {
                    resultado.agregar( nodo.elem );
                }
                catch( IteradorException e )
                {
                    // Nunca debería aparecer esta excepción
                }
                nodo = nodo.derNodo;
            }
            else if( nodo.izqNodo == null )
            {
                // Recorre el nodo
                try
                {
                    resultado.agregar( nodo.elem );
                }
                catch( IteradorException e )
                {
                    // Nunca debería aparecer esta excepción
                }
                nodo = nodo.derNodo;
            }
            else
            {
                pila.insertar( nodo );
                nodo = nodo.izqNodo;
            }
        }
    }
    public void preordenIterativo( IteradorSimple<T> resultado )
    {
        PilaEncadenada<NodoArbolBinario<T>> pila = new PilaEncadenada<NodoArbolBinario<T>>( );
        NodoArbolBinario<T> nodo = this;
        while( nodo != null || pila.darLongitud( ) != 0 )
        {
            if( nodo.izqNodo != null )
            {
                pila.insertar( nodo );
                try
                {
                    resultado.agregar( nodo.elem );
                }
                catch( IteradorException e )
                {
                    // No debería ocurrir esta excepción
                }
                nodo = nodo.izqNodo;
            }
            else
            {
                try
                {
                    resultado.agregar( nodo.elem );
                }
                catch( IteradorException e )
                {
                    // No debería ocurrir esta excepción
                }
                try
                {
                    nodo = pila.darLongitud( ) == 0 ? null : pila.tomarElemento( ).derNodo;
                }
                catch( PilaVaciaException e )
                {
                    // No debería ocurrir esta excepción
                }
            }
        }
    }
    public void postordenIterativo( IteradorSimple<T> resultado )
    {
        PilaEncadenada<NodoArbolBinario<T>> pila = new PilaEncadenada<NodoArbolBinario<T>>( );
        NodoArbolBinario<T> nodo = this;
        NodoArbolBinario<T> ultimoInsertado = null;
        while( nodo != null )
        {
            if( nodo.izqNodo != null && !nodo.izqNodo.equals( ultimoInsertado ) && !nodo.derNodo.equals( ultimoInsertado ) )
            {
                // Tiene hijo por la izquierda y ninguno de los hijos del nodo fue recientemente agregado a resultado
                pila.insertar( nodo );
                nodo = nodo.izqNodo;
            }
            else if( nodo.izqNodo == null && nodo.derNodo == null && !nodo.equals( ultimoInsertado ) )
            {
                // Es una hoja y no ha sido insertada
                try
                {
                    resultado.agregar( nodo.elem );
                    ultimoInsertado = nodo;
                    nodo = pila.darLongitud( ) != 0 ? pila.tomarElemento( ) : null;
                }
                catch( IteradorException e )
                {
                    // No debería ocurrir esta excepción
                }
                catch( PilaVaciaException e )
                {
                    // No debería ocurrir esta excepción
                }

            }
            else if( nodo.derNodo != null && !ultimoInsertado.equals( nodo.derNodo ) )
            {
                // Tiene hijo derecho y este hijo no ha sido insertado
                pila.insertar( nodo );
                nodo = nodo.derNodo;
            }
            else if( nodo.derNodo != null && ultimoInsertado.equals( nodo.derNodo ) )
            {
                try
                {
                    // Ya se han agregado los hijos izquierdo y derecho y es hora de agregar la raíz
                    resultado.agregar( nodo.elem );
                    ultimoInsertado = nodo;
                    nodo = pila.darLongitud( ) != 0 ? pila.tomarElemento( ) : null;
                }
                catch( IteradorException e )
                {
                    // No debería ocurrir esta excepción
                }
                catch( PilaVaciaException e )
                {
                    // No debería ocurrir esta excepción
                }
            }
        }
    }

    public void inorden( IteradorSimple<T> resultado )
    {
        // Agrega los elementos del subárbol izquierdo
        if( izqNodo != null )
        {
            izqNodo.inorden( resultado );
        }

        try
        {
            // Agrega el elemento que se encuentra en el nodo
            resultado.agregar( elem );
        }
        catch( IteradorException e )
        {
            // Nunca debería aparecer esta excepción
        }

        // Agrega los elementos del subárbol derecho
        if( derNodo != null )
        {
            derNodo.inorden( resultado );
        }
    }

    public void preorden( IteradorSimple<T> resultado )
    {
        try
        {
            // Agrega el elemento que se encuentra en el nodo
            resultado.agregar( elem );
        }
        catch( IteradorException e )
        {
            // Nunca debería aparecer esta excepción
        }

        // Agrega los elementos del subárbol izquierdo
        if( izqNodo != null )
        {
            izqNodo.preorden( resultado );
        }
        // Agrega los elementos del subárbol derecho
        if( derNodo != null )
        {
            derNodo.preorden( resultado );
        }
    }

    public void postorden( IteradorSimple<T> resultado )
    {
        // Agrega los elementos del subárbol izquierdo
        if( izqNodo != null )
        {
            izqNodo.postorden( resultado );
        }
        // Agrega los elementos del subárbol derecho
        if( derNodo != null )
        {
            derNodo.postorden( resultado );
        }

        try
        {
            // Agrega el elemento que se encuentra en el nodo
            resultado.agregar( elem );
        }
        catch( IteradorException e )
        {
            // Nunca debería aparecer esta excepción
        }
    }

    public void darRecorridoNiveles( IteradorSimple<T> resultado )
    {
        ColaEncadenada<NodoArbolBinario<T>> cola = new ColaEncadenada<NodoArbolBinario<T>>( );
        cola.insertar( this );
        while( cola.darLongitud( ) != 0 )
        {
            NodoArbolBinario<T> nodo = null;
            try
            {
                // Toma el primer árbol de la cola
                nodo = cola.tomarElemento( );
            }
            catch( ColaVaciaException e )
            {
                // Nunca debería aparecer esta excepción
            }
            try
            {
                resultado.agregar( nodo.elem );
            }
            catch( IteradorException e )
            {
                // Nunca debería aparecer esta excepción
            }

            // Agrega los dos subárboles (si no son vacíos) a la cola
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

    public int darTamanoNivel( int nivel )
    {
        if( nivel == 0 )
            return 1;
        else
        {
            int acum = 0;
            if( izqNodo != null )
                acum += izqNodo.darTamanoNivel( nivel - 1 );
            if( derNodo != null )
                acum += derNodo.darTamanoNivel( nivel - 1 );
            return acum;
        }
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
                // Nunca debería aparecer esta excepción
            }
        }
        else
        {
            if( izqNodo != null )
                izqNodo.darNivel( nivel - 1, resultado );
            if( derNodo != null )
                derNodo.darNivel( nivel - 1, resultado );
        }
    }

    public boolean estaBalanceadoPorAltura( )
    {
        if( esHoja( ) )
            return true;
        else if( izqNodo == null )
            return derNodo.esHoja( );
        else if( derNodo == null )
            return izqNodo.esHoja( );
        else
            return Math.abs( izqNodo.darAltura( ) - derNodo.darAltura( ) ) <= 1 && izqNodo.estaBalanceadoPorAltura( ) && derNodo.estaBalanceadoPorAltura( );
    }

    public boolean estaBalanceadoPorPeso( )
    {
        if( esHoja( ) )
            return true;
        else if( izqNodo == null )
            return derNodo.esHoja( );
        else if( derNodo == null )
            return izqNodo.esHoja( );
        else
            return Math.abs( izqNodo.darPeso( ) - derNodo.darPeso( ) ) <= 1 && izqNodo.estaBalanceadoPorPeso( ) && derNodo.estaBalanceadoPorPeso( );
    }

    public boolean esCompleto( )
    {
        if( esHoja( ) )
            return true;
        else if( izqNodo != null && derNodo != null )
            return izqNodo.esCompleto( ) && derNodo.esCompleto( );
        else
            return false;
    }

    public void darCamino( T pElem, IteradorSimple<T> resultado ) throws ElementoNoExisteException
    {
        PilaEncadenada<T> pila = new PilaEncadenada<T>( );
        if( !darCaminoAux( pElem, pila ) )
        {
            throw new ElementoNoExisteException( "Elemento no encontrado" );
        }
        else
        {
            while( !pila.estaVacia( ) )
            {
                try
                {
                    resultado.agregar( pila.tomarElemento( ) );
                }
                catch( IteradorException e )
                {
                    // Esta excepción nunca debería ocurrir
                    e.printStackTrace( );
                }
                catch( PilaVaciaException e )
                {
                    // Esta excepción nunca debería ocurrir
                    e.printStackTrace( );
                }
            }
        }

    }

    private boolean darCaminoAux( T pElem, PilaEncadenada<T> pila )
    {
        if( pElem.equals( elem ) )
        {
            pila.insertar( elem );
            return true;
        }
        else
        {
            if( izqNodo != null && izqNodo.darCaminoAux( pElem, pila ) )
            {
                pila.insertar( elem );
                return true;
            }
            if( derNodo != null && derNodo.darCaminoAux( pElem, pila ) )
            {
                pila.insertar( elem );
                return true;
            }
            return false;
        }
    }

    public T darSiguienteInorden( T pElem ) throws ElementoNoExisteException
    {
        Retorno ret = new Retorno( null, false );
        darSiguienteInordenAux( pElem, ret );
        if( ret.respuesta != null )
            return ret.respuesta;
        else
            throw new ElementoNoExisteException( "Elemento no encontrado" );
    }

    private void darSiguienteInordenAux( T pElem, Retorno ret )
    {
        if( izqNodo != null )
        {
            izqNodo.darSiguienteInordenAux( pElem, ret );
            if( ret.respuesta != null )
                return;
        }
        if( ret.encontrado )
        {
            ret.respuesta = elem;
            return;
        }
        else if( pElem.equals( elem ) )
        {
            ret.encontrado = true;
        }
        if( derNodo != null )
        {
            derNodo.darSiguienteInordenAux( pElem, ret );
        }
    }

    public boolean estaLleno( int altura )
    {
        if( esHoja( ) )
            return altura == 1;
        else if( izqNodo == null || derNodo == null )
            return false;
        else
            return izqNodo.estaLleno( altura - 1 ) && derNodo.estaLleno( altura - 1 );
    }

    public boolean estaCasiLleno( int altura )
    {
        if( esHoja( ) )
            return altura == 1;
        else if( izqNodo == null )
            return false;
        else if( derNodo == null )
            return altura == 2 && izqNodo.esHoja( );
        else
        {
            boolean llenoIzq = izqNodo.estaLleno( altura - 1 );
            if( llenoIzq && derNodo.estaLleno( altura - 1 ) )
                return true;
            else if( izqNodo.estaCasiLleno( altura - 1 ) && derNodo.estaLleno( altura - 2 ) )
                return true;
            else
                return llenoIzq && derNodo.estaCasiLleno( altura - 1 );
        }
    }

    public boolean darRamaMasLarga( int altura, IteradorSimple<T> itera )
    {
        try
        {
            if( esHoja( ) && altura == 1 )
            {
                itera.agregar( elem );
                return true;
            }
            else if( esHoja( ) )
            {
                // No ha llegado al nivel esperado y es una hoja
                return false;
            }
            else if( izqNodo != null && izqNodo.darRamaMasLarga( altura - 1, itera ) )
            {
                // Intenta buscar la rama más larga por el subárbol izquierdo
                itera.insertar( elem );
                return true;
            }
            else if( derNodo != null && derNodo.darRamaMasLarga( altura - 1, itera ) )
            {
                // Intenta buscar la rama más larga por el subárbol derecho
                itera.insertar( elem );
                return true;
            }
            else
                return false;
        }
        catch( IteradorException e )
        {
            // Nunca debería aparecer esta excepción
            return false;
        }
    }

    private class Retorno
    {
        private T respuesta;
        private boolean encontrado;

        private Retorno( T pRespuesta, boolean pEncontrado )
        {
            respuesta = pRespuesta;
            encontrado = pEncontrado;
        }
    }
}
