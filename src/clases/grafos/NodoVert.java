package clases.grafos;

public class NodoVert {

    //Variables
    private Object elem;
    private NodoVert sigVertice;
    private NodoAdy primerAdy;

    //Constructor
    public NodoVert(Object elem, NodoVert sigVertice){
        this.elem = elem;
        this.sigVertice = sigVertice;
        this.primerAdy = null;
    }

    //Getters
    public Object getElem(){
        return this.elem;
    }
    
    public NodoVert getSigVertice(){
        return this.sigVertice;
    }
    
    public NodoAdy getPrimerAdy(){
        return this.primerAdy;
    }

    //Setters
    public void setElem(Object elem){
        this.elem = elem;
    }

    public void setSigVertice(NodoVert sigVertice){
        this.sigVertice = sigVertice;
    }

    public void setPrimerAdy(NodoAdy primerAdy){
        this.primerAdy = primerAdy;
    }

}
