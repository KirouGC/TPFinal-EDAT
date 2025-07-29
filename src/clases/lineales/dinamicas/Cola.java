package clases.lineales.dinamicas;

public class Cola {
    
    private Nodo frente;
    private Nodo fin;
    
    public Cola () {
        this.frente = null;
        this.fin = null;
    }
  
    public boolean poner (Object nuevoElem) {
        Nodo nodoAux = new Nodo(nuevoElem, null);
        if (this.frente == null) {
            this.frente = nodoAux;
        }else{
            this.fin.setEnlace(nodoAux);
        }
        this.fin = nodoAux;
        return true;
    }

    public boolean sacar(){
        boolean exito = true; 
        if (this.frente == null) {
            exito = false;
        }else{
            this.frente = this.frente.getEnlace();
            if(this.frente == null){
                this.fin = null;
            }
        }
        return exito;
    }

    public Object obtenerFrente () { 
        Object obj = null;
        if(this.frente != null) { 
            obj = frente.getElem();
        }
        return obj;
    }

    public boolean esVacia () {
        return this.frente == null;
    }
    
    public void vaciar () {
        this.frente = null;
        this.fin = null;
    } 
    
    public Cola clone() {
        Cola clon = new Cola();
        clon.frente = clonarCola(this.frente,clon.fin);
        return clon;
    }

    private Nodo clonarCola(Nodo frenteActual, Nodo fin) {
        Nodo nodoAux = null;
        if (frenteActual != null) {
            nodoAux = new Nodo(frenteActual.getElem(),clonarCola(frenteActual.getEnlace(), fin));
            if (frenteActual.getEnlace() == null) {
                fin = nodoAux;
            }
        }
        return nodoAux;
    }

    public String toString () {
        String texto = "["; 
        Nodo base = frente;
        while (base != null) {
            texto += base.getElem();
            base = base.getEnlace();
            if(base != null)
                texto+= ",";
            
        }
        texto += "]";
        return texto;
    }
    
}