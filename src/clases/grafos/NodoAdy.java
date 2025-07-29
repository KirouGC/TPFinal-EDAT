package clases.grafos;

public class NodoAdy {

    //Variables
    private NodoVert vertice;
    private NodoAdy sigAdyacente;
    private Object etiqueta;

    //Constructor
    public NodoAdy(){
        this.vertice = null;
        this.sigAdyacente = null;
        this.etiqueta = null;
    }
    public NodoAdy(NodoVert vertice, Object etiqueta){
        this.vertice = vertice;
        this.sigAdyacente = null;
        this.etiqueta = etiqueta;
    }

    //Getters
    public NodoVert getVertice(){
        return this.vertice;
    }
    public NodoAdy getSigAdyacente(){
        return this.sigAdyacente;
    }
    public Object getEtiqueta(){
        return this.etiqueta;
    }
    
    //Setters
    public void setVertice(NodoVert vert){
        this.vertice = vert;
    }

    public void setSigAdyacente(NodoAdy siguiente){
        this.sigAdyacente = siguiente;
    }

    public void setEtiqueta(Object etiqueta){
        this.etiqueta = etiqueta;
    }

}
