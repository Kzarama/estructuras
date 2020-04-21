import java.io.Serializable;
import java.util.List;

public class NodoRojoNegro<T extends Comparable<? super T>> implements Serializable
{
	private static final long serialVersionUID = 1L;
	
    public static final int NEGRO = 1;

    public static final int ROJO = 0;

    private NodoRojoNegro<T> hijoDerecho;

    private NodoRojoNegro<T> hijoIzquierdo;

    private T elem;

    private int color;

    private NodoRojoNegro<T> padre;

    protected NodoRojoNegro( T elem )
    {
        this.elem = elem;
        color = ROJO;
        cambiarHijoDerecho( new NodoRojoNegro<T>( ) );
        cambiarHijoIzquierdo( new NodoRojoNegro<T>( ) );
        padre = null;
    }

    private NodoRojoNegro( )
    {
        this.elem = null;
        color = NEGRO;
        padre = null;
    }

    public NodoRojoNegro<T> darPadre( )
    {
        return padre;
    }

    public NodoRojoNegro<T> darTio( )
    {
        if( padre == null || padre.padre == null )
        {
            return null;
        }
        else
        {
            if( padre.padre.esHijoDerecho( padre ) )
                return padre.padre.hijoIzquierdo;
            else
                return padre.padre.hijoDerecho;
        }

    }

    public int darColor( )
    {
        return color;
    }

    public NodoRojoNegro<T> darHijoDerecho( )
    {
        return hijoDerecho;
    }

    public boolean esHijoDerecho( NodoRojoNegro<T> nodo )
    {
        return hijoDerecho == nodo;
    }

    public NodoRojoNegro<T> darHijoIzquierdo( )
    {
        return hijoIzquierdo;
    }

    public boolean esHijoIzquierdo( NodoRojoNegro<T> nodo )
    {
        return hijoIzquierdo == nodo;
    }

    public boolean hijoDerechoHoja( )
    {
        return hijoDerecho.elem == null;
    }

    public boolean hijoIzquierdoHoja( )
    {
        return hijoIzquierdo.elem == null;
    }

    public NodoRojoNegro<T> darMayor( )
    {
        return hijoDerechoHoja( ) ? this : hijoDerecho.darMayor( );
    }

    public NodoRojoNegro<T> darMenor( )
    {
        return hijoIzquierdoHoja( ) ? this : hijoIzquierdo.darMenor( );
    }

    public void darPreorden( List<T> preorden )
    {
        preorden.add( elem );
        if( !hijoIzquierdoHoja( ) )
            hijoIzquierdo.darPreorden( preorden );
        if( !hijoDerechoHoja( ) )
            hijoDerecho.darPreorden( preorden );
    }

    public boolean esHoja( )
    {
        return elem == null;
    }

    public int darPeso( )
    {
        return esHoja( ) ? 0 : 1 + hijoDerecho.darPeso( ) + hijoIzquierdo.darPeso( );
    }

    public void darHojas( List<NodoRojoNegro<T>> hojas )
    {
        if( esHoja( ) )
            hojas.add( this );
        else
        {
            if( !hijoDerechoHoja( ) )
                hijoDerecho.darHojas( hojas );
            if( !hijoIzquierdoHoja( ) )
                hijoIzquierdo.darHojas( hojas );
        }
    }

    public int darAltura( )
    {
        if( esHoja( ) )
            return 0;
        int a1 = hijoIzquierdo.darAltura( );
        int a2 = hijoDerecho.darAltura( );
        return ( a1 >= a2 ) ? a1 + 1 : a2 + 1;
    }

    public boolean existe( T e )
    {
        try
        {
            darNodo( e );
            return true;
        }
        catch( ElementoNoExisteException e1 )
        {
            return false;
        }

    }

    public NodoRojoNegro<T> darNodo( T elem ) throws ElementoNoExisteException
    {
        int comp = elem.compareTo( this.elem );
        if( comp == 0 )
            return this;
        else if( comp < 0 )
        {
            if( !hijoIzquierdoHoja( ) )
                return hijoIzquierdo.darNodo( elem );
            else
                throw new ElementoNoExisteException( "El elemento buscado no existe" );
        }
        else
        {
            if( !hijoDerechoHoja( ) )
                return hijoDerecho.darNodo( elem );
            else
                throw new ElementoNoExisteException( "El elemento buscado no existe" );
        }

    }

    public T darInfoNodo( )
    {
        return elem;
    }

    public boolean hijoDerechoNegro( )
    {
        return hijoDerecho.color == NEGRO;
    }

    public boolean hijoIzquierdoNegro( )
    {
        return hijoIzquierdo.color == NEGRO;
    }

    public boolean hijosNegros( )
    {
        return hijoDerechoNegro( ) && hijoIzquierdoNegro( );
    }

    public NodoRojoNegro<T> darHermano( )
    {
        if( padre == null )
            return null;
        else
            return padre.esHijoDerecho( this ) ? padre.hijoIzquierdo : padre.hijoDerecho;
    }

    private void cambiarPadre( NodoRojoNegro<T> padre )
    {
        this.padre = padre;
    }

    protected void cambiarColor( int color )
    {
        this.color = color;
    }

    private void cambiarHijoDerecho( NodoRojoNegro<T> hijo )
    {
        if( hijo != null )
            hijo.cambiarPadre( this );
        hijoDerecho = hijo;
    }

    private void cambiarHijoIzquierdo( NodoRojoNegro<T> hijo )
    {
        if( hijo != null )
            hijo.cambiarPadre( this );
        hijoIzquierdo = hijo;
    }

    private void cambiarElem( NodoRojoNegro<T> nodo )
    {
        if( nodo.elem != null )
        {
            T aux = elem;
            elem = nodo.elem;
            nodo.elem = aux;
        }
        else
        {
            elem = null;
            color = NEGRO;
            hijoDerecho = hijoIzquierdo = null;
        }
    }

    private NodoRojoNegro<T> rotarIzquierda( )
    {
        if( hijoDerechoHoja( ) )
            return this;
        else
        {
            NodoRojoNegro<T> hijoDerechoAux = hijoDerecho;
            cambiarHijoDerecho( hijoDerechoAux.darHijoIzquierdo( ) );
            hijoDerechoAux.cambiarPadre( padre );
            hijoDerechoAux.cambiarHijoIzquierdo( this );
            return hijoDerechoAux;
        }
    }

    private NodoRojoNegro<T> rotarDerecha( )
    {
        if( hijoIzquierdoHoja( ) )
            return this;
        else
        {
            NodoRojoNegro<T> hijoIzquierdoAux = hijoIzquierdo;
            cambiarHijoIzquierdo( hijoIzquierdoAux.darHijoDerecho( ) );
            hijoIzquierdoAux.cambiarPadre( padre );
            hijoIzquierdoAux.cambiarHijoDerecho( this );
            return hijoIzquierdoAux;
        }
    }

    protected NodoRojoNegro<T> insertar( NodoRojoNegro<T> nodo ) throws ElementoExisteException
    {
        insertarNormal( nodo );
        Retorno r = new Retorno( null );
        nodo.balanceoRojoNegroCaso1( r );
        return r.respuesta;
    }

    private void insertarNormal( NodoRojoNegro<T> nodo ) throws ElementoExisteException
    {
        if( elem.compareTo( nodo.darInfoNodo( ) ) == 0 )
        {
            throw new ElementoExisteException( "El elemento " + nodo.darInfoNodo( ).toString( ) + " ya existe en el árbol" );
        }
        else if( elem.compareTo( nodo.darInfoNodo( ) ) < 0 )
        {
            if( hijoDerechoHoja( ) )
            {
                hijoDerecho = nodo;
                nodo.cambiarPadre( this );
            }
            else
            {
                hijoDerecho.insertarNormal( nodo );
            }
        }
        else
        {
            if( hijoIzquierdoHoja( ) )
            {
                hijoIzquierdo = nodo;
                nodo.cambiarPadre( this );
            }
            else
            {
                hijoIzquierdo.insertarNormal( nodo );
            }
        }
    }

    private NodoRojoNegro<T> balanceoRojoNegroCaso1( Retorno r )
    {
        if( padre == null )
        {
            color = NEGRO;
            r.respuesta = this;
        }
        else
        {
            balanceoRojoNegroCaso2( r );
        }
        return r.respuesta;
    }

    private void balanceoRojoNegroCaso2( Retorno r )
    {
        if( padre.darColor( ) == ROJO )
            balanceoRojoNegroCaso3( r );
        else
            r.respuesta = null;

    }

    private void balanceoRojoNegroCaso3( Retorno r )
    {
        NodoRojoNegro<T> tio = darTio( );
        NodoRojoNegro<T> abuelo = padre.darPadre( );
        r.respuesta = null;

        if( !tio.esHoja( ) && tio.darColor( ) == ROJO )
        {
            darPadre( ).cambiarColor( NEGRO );
            tio.cambiarColor( NEGRO );
            abuelo.cambiarColor( ROJO );
            abuelo.balanceoRojoNegroCaso1( r );
        }
        else
        {
            balanceoRojoNegroCaso4( r );
        }
    }

    private void balanceoRojoNegroCaso4( Retorno r )
    {
        NodoRojoNegro<T> abuelo = padre.darPadre( );
        r.respuesta = null;

        if( padre.esHijoDerecho( this ) && abuelo.esHijoIzquierdo( padre ) )
        {
            abuelo.cambiarHijoIzquierdo( padre.rotarIzquierda( ) );
            hijoIzquierdo.balanceoRojoNegroCaso5( r );
        }
        else if( padre.esHijoIzquierdo( this ) && abuelo.esHijoDerecho( padre ) )
        {
            abuelo.cambiarHijoDerecho( padre.rotarDerecha( ) );
            hijoDerecho.balanceoRojoNegroCaso5( r );
        }
        else
        {
            balanceoRojoNegroCaso5( r );
        }
    }

    private void balanceoRojoNegroCaso5( Retorno r )
    {
        NodoRojoNegro<T> abuelo = padre.darPadre( );

        padre.cambiarColor( NEGRO );
        abuelo.cambiarColor( ROJO );

        if( padre.esHijoIzquierdo( this ) && abuelo.esHijoIzquierdo( padre ) )
        {
            if( abuelo.darPadre( ) == null )
                abuelo.rotarDerecha( );
            else if( abuelo.darPadre( ).esHijoDerecho( abuelo ) )
                abuelo.darPadre( ).cambiarHijoDerecho( abuelo.rotarDerecha( ) );
            else
                abuelo.darPadre( ).cambiarHijoIzquierdo( abuelo.rotarDerecha( ) );

        }
        else
        {
            if( abuelo.darPadre( ) == null )
                abuelo.rotarIzquierda( );
            else if( abuelo.darPadre( ).esHijoDerecho( abuelo ) )
                abuelo.darPadre( ).cambiarHijoDerecho( abuelo.rotarIzquierda( ) );
            else
                abuelo.darPadre( ).cambiarHijoIzquierdo( abuelo.rotarIzquierda( ) );
        }
        r.respuesta = padre;
    }

    protected NodoRojoNegro<T> eliminar( )
    {
        NodoRojoNegro<T> reemplazo = !hijoIzquierdoHoja( ) ? hijoIzquierdo.darMayor( ) : this.darMenor( );

        cambiarElem( reemplazo );

        Retorno r = new Retorno( null );
        reemplazo.eliminarRojoNegro( r );

        return r.respuesta;
    }

    private void eliminarHijos( )
    {
        hijoDerecho = new NodoRojoNegro<T>( );
        hijoIzquierdo = new NodoRojoNegro<T>( );
    }

    private void eliminarRojoNegro( Retorno r )
    {
        NodoRojoNegro<T> hijo = !hijoDerechoHoja( ) ? hijoDerecho : hijoIzquierdo;

        int colorBorrar = darColor( );
        int colorHijo = hijo.darColor( );

        cambiarElem( hijo );
        eliminarHijos( );

        if( colorHijo == ROJO )
        {
            r.respuesta = this;
            return;
        }
        else if( colorHijo == NEGRO && colorBorrar == ROJO )
        {
            r.respuesta = this;
            cambiarColor( NEGRO );
        }
        else
        {
            eliminarCaso1( r );
        }
    }

    private void eliminarCaso1( Retorno r )
    {
        if( padre != null )
            this.eliminarCaso2( r );
        else
            r.respuesta = null;
    }

    private void eliminarCaso2( Retorno r )
    {
        NodoRojoNegro<T> hermano = darHermano( );

        if( hermano.color == ROJO )
        {
            padre.color = ROJO;
            hermano.color = NEGRO;

            r.respuesta = hermano;

            NodoRojoNegro<T> abuelo = padre.padre;
            if( padre.esHijoDerecho( this ) )
            {
                if( abuelo != null )
                {
                    if( abuelo.esHijoDerecho( padre ) )
                        abuelo.cambiarHijoDerecho( padre.rotarDerecha( ) );
                    else
                        abuelo.cambiarHijoIzquierdo( padre.rotarDerecha( ) );
                }
                else
                    padre.rotarDerecha( );
            }
            else
            {
                if( abuelo != null )
                {
                    if( abuelo.esHijoDerecho( padre ) )
                        abuelo.cambiarHijoDerecho( padre.rotarIzquierda( ) );
                    else
                        abuelo.cambiarHijoIzquierdo( padre.rotarIzquierda( ) );
                }
                else
                    padre.rotarIzquierda( );
            }
        }
        eliminarCaso3( r );
    }

    private void eliminarCaso3( Retorno r )
    {
        NodoRojoNegro<T> hermano = darHermano( );

        if( padre.color == NEGRO && hermano.color == NEGRO && hermano.hijosNegros( ) )
        {
            hermano.cambiarColor( ROJO );
            padre.eliminarCaso1( r );
        }
        else
        {
            eliminarCaso4( r );
        }
    }

    private void eliminarCaso4( Retorno r )
    {
        NodoRojoNegro<T> hermano = darHermano( );

        if( padre.color == ROJO && hermano.color == NEGRO && hermano.hijosNegros( ) )
        {
            hermano.cambiarColor( ROJO );
            padre.cambiarColor( NEGRO );
        }
        else
        {
            eliminarCaso5( r );
        }
    }

    private void eliminarCaso5( Retorno r )
    {
        NodoRojoNegro<T> hermano = darHermano( );

        if( padre.esHijoIzquierdo( this ) && hermano.color == NEGRO && !hermano.hijoIzquierdoNegro( ) && hermano.hijoDerechoNegro( ) )
        {
            hermano.color = ROJO;
            hermano.hijoIzquierdo.color = NEGRO;
            padre.cambiarHijoDerecho( hermano.rotarDerecha( ) );
        }
        else if( padre.esHijoDerecho( this ) && hermano.color == NEGRO && !hermano.hijoDerechoNegro( ) && hermano.hijoIzquierdoNegro( ) )
        {
            hermano.color = ROJO;
            hermano.hijoDerecho.color = NEGRO;
            padre.cambiarHijoIzquierdo( hermano.rotarIzquierda( ) );
        }
        eliminarCaso6( r );
    }

    private void eliminarCaso6( Retorno r )
    {
        NodoRojoNegro<T> hermano = darHermano( );

        hermano.color = padre.color;
        padre.color = NEGRO;
        NodoRojoNegro<T> abuelo = padre.padre;

        r.respuesta = hermano;

        if( padre.esHijoIzquierdo( this ) )
        {
            hermano.hijoDerecho.color = NEGRO;

            if( abuelo != null )
            {
                if( abuelo.esHijoDerecho( padre ) )
                    abuelo.cambiarHijoDerecho( padre.rotarIzquierda( ) );
                else
                    abuelo.cambiarHijoIzquierdo( padre.rotarIzquierda( ) );
            }
            else
                padre.rotarIzquierda( );
        }
        else
        {
            hermano.hijoIzquierdo.color = NEGRO;

            if( abuelo != null )
            {
                if( abuelo.esHijoDerecho( padre ) )
                    abuelo.cambiarHijoDerecho( padre.rotarDerecha( ) );
                else
                    abuelo.cambiarHijoIzquierdo( padre.rotarDerecha( ) );
            }
            else
                padre.rotarDerecha( );
        }
    }

    public String toString( )
    {
        return ( elem != null ? elem.toString( ) : "null" ) + ( color == ROJO ? " red" : " black" );
    }
    
    private class Retorno
    {
        private NodoRojoNegro<T> respuesta;

        private Retorno( NodoRojoNegro<T> pRespuesta )
        {
            respuesta = pRespuesta;
        }
    }
}