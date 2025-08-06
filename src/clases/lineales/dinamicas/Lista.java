package clases.lineales.dinamicas;

public class Lista {
    private Nodo cabecera;

    public Lista() {
        this.cabecera = null;
    }

    public boolean insertar(Object nuevoElem, int pos) {
        boolean exito = true;
        if (pos < 1 || pos > this.longitud() + 1) {
            exito = false;
        } else {
            if (pos == 1) {
                this.cabecera = new Nodo(nuevoElem, this.cabecera);
            } else {
                Nodo aux = this.cabecera;
                int i = 1;
                while (i < pos - 1) {
                    aux = aux.getEnlace();
                    i++;
                }
                Nodo nuevo = new Nodo(nuevoElem, aux.getEnlace());
                aux.setEnlace(nuevo);
            }
        }
        return exito;
    }

    public void eliminarAparaciones(Object x){
        Nodo nodoPrev = this.cabecera;
        Nodo nodoAux = this.cabecera;
        while(nodoAux != null && nodoAux.getEnlace()!= null){
            if(nodoAux == this.cabecera && nodoAux.getElem().equals(x)){
                this.cabecera = nodoAux.getEnlace();
                nodoPrev = this.cabecera;
                nodoAux = this.cabecera;
            } else{
                if(nodoAux == this.cabecera)
                    nodoAux = nodoAux.getEnlace();
                if(nodoAux.getElem().equals(x)){
                    nodoPrev.setEnlace(nodoAux.getEnlace());
                    nodoAux = nodoPrev.getEnlace();
                } else{
                    nodoPrev = nodoPrev.getEnlace();
                    nodoAux = nodoAux.getEnlace();
                }
            }
        }
        if(longitud() == 1){
            if(nodoPrev.getElem().equals(x))
            this.cabecera = null;
        } else if(longitud() > 1){
            if(nodoPrev.getEnlace().getElem().equals(x))
                nodoPrev.setEnlace(null);
        }
    }

    public Lista obtenerMultiplos(int num){
        Lista listaNueva = new Lista();
        Nodo nodoAux = this.cabecera;
        int cont = 1;
        while(nodoAux != null){
            if((cont%num) == 0){
                listaNueva.insertar(nodoAux.getElem(), listaNueva.longitud()+1);
                }
            cont++;
            nodoAux = nodoAux.getEnlace();
        }
        return listaNueva;
    }

    public boolean moverAAnteultimaPosicion(int pos) {
        boolean fueMovido = false;
        Nodo NodoAux = this.cabecera;
        Nodo NodoAuxMover;
        int cont = 1;
        if (this.longitud() > 1) {
            if (pos > 1 && pos < this.longitud()) {
                while (cont + 1 < pos) {
                    NodoAux = NodoAux.getEnlace();
                    cont++;
                }
                NodoAuxMover = NodoAux.getEnlace();
                NodoAux.setEnlace(NodoAuxMover.getEnlace());
                while (cont < this.longitud() - 1) {
                    NodoAux = NodoAux.getEnlace();
                    cont++;
                }
                NodoAuxMover.setEnlace(NodoAux.getEnlace());
                NodoAux.setEnlace(NodoAuxMover);
                fueMovido = true;
            } else if (pos == 1) {
                NodoAuxMover = this.cabecera;
                this.cabecera = NodoAux.getEnlace();
                NodoAux = this.cabecera;
                while (NodoAux.getEnlace().getEnlace() != null)
                    NodoAux = NodoAux.getEnlace();
                NodoAuxMover.setEnlace(NodoAux.getEnlace());
                NodoAux.setEnlace(NodoAuxMover);
                fueMovido = true;
            } else if (pos == this.longitud()) {
                while (cont < this.longitud()-2){
                    NodoAux = NodoAux.getEnlace();
                    cont++;
                }
                
                if(NodoAux.getEnlace().getEnlace() == null){
                    NodoAux.getEnlace().setEnlace(NodoAux);
                    this.cabecera = NodoAux.getEnlace();
                    NodoAux.setEnlace(null);
                } else{
                    NodoAux.getEnlace().getEnlace().setEnlace(NodoAux.getEnlace());
                    NodoAux.setEnlace(NodoAux.getEnlace().getEnlace());
                    NodoAux.getEnlace().getEnlace().setEnlace(null);
                    fueMovido = true;
                }
            }
        }
        return fueMovido;
    }

    public boolean eliminar(int pos) {
        boolean exito = false;
        int aux = 1;
        Nodo nodoAux = this.cabecera;
        if (!esVacia()) {
            if (pos > 1 && pos <= this.longitud()) {
                while (aux < pos - 1) {
                    nodoAux = nodoAux.getEnlace();
                    aux++;
                }
                nodoAux.setEnlace(nodoAux.getEnlace().getEnlace());
                exito = true;

            } else if (pos == 1) {
                this.cabecera = this.cabecera.getEnlace();
                exito = true;
            }
        }
        return exito;
    }

    public Object recuperar(int pos) {
        int aux = 1;
        Object elem = null;
        Nodo nodoAux = this.cabecera;
        if (pos >= 1 && pos <= this.longitud()) {
            while (aux <= pos) {
                elem = nodoAux.getElem();
                nodoAux = nodoAux.getEnlace();
                aux++;
            }
        }
        return elem;
    }

    public int localizar(Object elemento) {
        int posicion = -1;
        Nodo nodoAux = this.cabecera;
        int cuenta = 1;
        while (posicion == -1 && nodoAux != null) {
            if (nodoAux.getElem().equals(elemento)) {
                posicion = cuenta;
            } else {
                nodoAux = nodoAux.getEnlace();
                cuenta++;
            }
        }
        return posicion;
    }

    public int longitud() {
        int pos = 0;
        Nodo aux = cabecera;
        while (aux != null) {
            pos++;
            aux = aux.getEnlace();
        }
        return pos;
    }

    public boolean esVacia() {
        return this.cabecera == null;
    }

    public void vaciar() {
        this.cabecera = null;
    }

    public Lista clone() {
        Lista listaClon = new Lista();
        listaClon.cabecera = clonarLista(this.cabecera);
        return listaClon;
    }

    private Nodo clonarLista(Nodo nodoActual) {
        Nodo retorno = null;
        if (nodoActual != null) {
            retorno = new Nodo(nodoActual.getElem(), clonarLista(nodoActual.getEnlace()));
        }
        return retorno;
    }

    public String toString() {
        String texto = "[";
        Nodo aux = cabecera;
        while (aux != null) {
            texto += aux.getElem();
            aux = aux.getEnlace();
            if (aux != null)
                texto += ",";
        }
        texto += "]";
        return texto;
    }

    //ToString secundario para listas que contienen arrays (necesario para el grafo)
    public String toStringSecundario() {
        String texto = "[";
        Nodo aux = cabecera;
        while (aux != null) {
            Object elem = aux.getElem();
            if (elem != null && elem.getClass().isArray()) {
                texto += arrayToString((Object[]) elem);
            } else {
                texto += elem;
            }
            aux = aux.getEnlace();
            if (aux != null)
                texto += ",";
        }
        texto += "]";
        return texto;
    }

    private String arrayToString(Object[] arr) {
        StringBuilder sb = new StringBuilder("(");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }

    // Propias del Tipo

    public Lista invertirLista(){
        Lista listaInvertida = new Lista();
        listaInvertida.insertarInvertido(listaInvertida, cabecera);
        return listaInvertida;
    }

    private void insertarInvertido(Lista inversa, Nodo n){
        if(n!= null){
            insertarInvertido(inversa, n.getEnlace());
            inversa.insertar(n.getElem(), inversa.longitud() + 1);
        }
    }
}