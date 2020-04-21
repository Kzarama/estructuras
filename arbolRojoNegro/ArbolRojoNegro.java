package uniandes.cupi2.collections.arbol.arbolRojoNegro;

public class ArbolRojoNegro<T extends Comparable<? super T>> implements Serializable, IArbolOrdenado<T>
{
	private static final long serialVersionUID = 1L;

    private NodoRojoNegro<T> raiz;
    
    public ArbolRojoNegro( )
    {
        raiz = null;
    }

    public void insertar( T elem ) throws ElementoExisteException
    {
        NodoRojoNegro<T> nodo = new NodoRojoNegro<T>( elem );

        NodoRojoNegro<T> r2 = null;

        if( raiz == null )
        {
            raiz = nodo;
            raiz.cambiarColor( NodoRojoNegro.NEGRO );
        }
        else
        {
            r2 = raiz.insertar( nodo );
        }

        raiz = r2 != null && r2.darPadre( ) == null ? r2 : raiz;
    }

    public void eliminar( T elem ) throws ElementoNoExisteException
    {
        if( raiz == null )
            throw new ElementoNoExisteException( "El árbol se encuentra vacio" );
        if( raiz.darInfoNodo( ).compareTo( elem ) == 0 && raiz.hijoDerechoHoja( ) && raiz.hijoIzquierdoHoja( ) )
            raiz = null;
        else
        {
            NodoRojoNegro<T> r2 = raiz.darNodo( elem ).eliminar( );
            raiz = r2 != null && r2.darPadre( ) == null ? r2 : raiz;
        }
    }

    public List<T> darPreorden( )
    {
        List<T> preorden = new LinkedList<T>( );
        if( raiz != null )
            raiz.darPreorden( preorden );
        return preorden;
    }

    public boolean existe( T elem )
    {
        return raiz != null ? raiz.existe( elem ) : false;
    }


    public T buscar( T modelo )
    {
        try
        {
            return raiz != null ? raiz.darNodo( modelo ).darInfoNodo( ) : null;
        }
        catch( ElementoNoExisteException e )
        {
            return null;
        }
    }

    public NodoRojoNegro<T> darRaiz( )
    {
        return raiz;
    }

    
    public int darPeso( )
    {
        return raiz == null ? 0 : raiz.darPeso( );
    }

    public int darAltura( )
    {
        return raiz == null ? 0 : raiz.darAltura( );
    }

    public T darMinimo( )
    {
        return raiz == null ? null : raiz.darMenor( ).darInfoNodo( );
    }

    public T darMayor( )
    {
        return raiz == null ? null : raiz.darMayor( ).darInfoNodo( );
    }

}