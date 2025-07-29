package clases.conjuntistas;

import clases.lineales.dinamicas.Lista;

public class ArbolBB {
    NodoABB raiz;

    public ArbolBB() {
        this.raiz = null;
    }

    public boolean pertence(Comparable nodoElem) {
        boolean pertenece = false;
        if (this.raiz != null) {
            pertenece = perteneceAux(nodoElem, this.raiz);
        }
        return pertenece;
    }


    private boolean perteneceAux(Comparable nodoElem, NodoABB n) {
        boolean pertenece = false;
        if (n != null) {
            if (nodoElem.compareTo(n.getElem()) == 0) {
                pertenece = true;
            } else if (nodoElem.compareTo(n.getElem()) > 0) {
                pertenece = perteneceAux(nodoElem, n.getIzquierdo());
            } else {
                pertenece = perteneceAux(nodoElem, n.getDerecho());
            }
        }
        return pertenece;
    }

    public boolean insertar(Comparable elemento) {
        boolean exito = true;
        if (this.raiz == null) {
            this.raiz = new NodoABB(elemento);
        } else {
            exito = insertarAux(this.raiz, elemento);
        }
        return exito;
    }

    private boolean insertarAux(NodoABB n, Comparable elemento) {
        // precondición: n no es nulo
        boolean exito = true;

        if ((elemento.compareTo(n.getElem())) == 0) {
            // Reportar error: Elemento repetido
            exito = false;
        } else if (elemento.compareTo(n.getElem()) > 0) {
            // elemento es menor que n.getElem()
            // si tiene HI baja a la izquierda, sino agrega elemento
            if (n.getIzquierdo() != null) {
                exito = insertarAux(n.getIzquierdo(), elemento);
            } else {
                n.setIzquierdo(new NodoABB(elemento));
            }
        } else {
            // elemento es mayor que n.getElem()
            // si tiene HD baja a la derecha, sino agrega elemento
            if (n.getDerecho() != null) {
                exito = insertarAux(n.getDerecho(), elemento);
            } else {
                n.setDerecho(new NodoABB(elemento));
            }
        }
        return exito;
    }

    public boolean eliminar(Comparable elemNodo) {
        boolean fueEliminado = false;
        NodoABB ant = this.raiz;
        NodoABB aux = this.raiz;
        while (aux != null && !fueEliminado) {
            if (elemNodo.compareTo(aux.getElem()) == 0) {
                if (aux.getIzquierdo() == null || aux.getDerecho() == null) {
                    fueEliminado = casoUnHijo(ant, aux);
                } else {
                    fueEliminado = casoDosHijos(aux);
                }
            } else if (elemNodo.compareTo(aux.getElem()) < 0) {
                ant = aux;
                aux = aux.getIzquierdo();
            } else {
                ant = aux;
                aux = aux.getDerecho();
            }
        }
        return fueEliminado;
    }

    private boolean casoDosHijos(NodoABB n) {
        boolean fueEliminado = true;
        NodoABB aux = n.getDerecho();
        NodoABB ant = aux;
        while (aux.getIzquierdo() != null) {
            ant = aux;
            aux = aux.getIzquierdo();
        }
        n.setElem(aux.getElem());
        if (ant == aux) {
            casoUnHijo(n, aux);
        } else {
            casoUnHijo(ant, aux);
        }
        return fueEliminado;
    }

    private boolean casoUnHijo(NodoABB ant, NodoABB aux) {
        boolean fueEliminado = true;
        if (aux.equals(this.raiz)) { // CASO ESPECIAL, NODO A ELIMINAR ES LA RAIZ
            if (ant.getIzquierdo() != null) {
                this.raiz = aux.getIzquierdo();
            } else if (ant.getDerecho() != null) {
                this.raiz = aux.getDerecho();
            } else {
                this.raiz = null;
            }
        } else {
            if (aux.getIzquierdo() != null) {
                if (ant.getIzquierdo() != null && ant.getIzquierdo().equals(aux))
                    ant.setIzquierdo(aux.getIzquierdo());
                else if (ant.getDerecho() != null && ant.getDerecho().equals(aux))
                    ant.setDerecho(aux.getIzquierdo());
            } else if (aux.getDerecho() != null) {
                if (ant.getIzquierdo() != null && ant.getIzquierdo().equals(aux))
                    ant.setIzquierdo(aux.getDerecho());
                else if (ant.getDerecho() != null && ant.getDerecho().equals(aux))
                    ant.setDerecho(aux.getDerecho());
            } else {
                if (ant.getIzquierdo() != null && ant.getIzquierdo().equals(aux))
                    ant.setIzquierdo(null);
                else if (ant.getDerecho() != null && ant.getDerecho().equals(aux))
                    ant.setDerecho(null);
            }
        }
        return fueEliminado;
    }

    public boolean vacio() {
        return this.raiz == null;
    }

    public Lista listar() {
        Lista listado = new Lista();
        listarAux(listado, raiz);
        return listado;
    }

    private void listarAux(Lista ls, NodoABB n) {
        if (n != null) {
            listarAux(ls, n.getIzquierdo());
            ls.insertar(n.getElem(), ls.longitud() + 1);
            listarAux(ls, n.getDerecho());
        }
    }

    public Lista listarRango(Comparable elemMinimo, Comparable elemMaximo) {
        Lista listado = new Lista();
        listarRangoAux(listado, elemMinimo, elemMaximo, this.raiz);
        return listado;
    }

    private void listarRangoAux(Lista ls, Comparable elemMin, Comparable elemMax, NodoABB n) {
        if (n != null) {
            listarRangoAux(ls, elemMin, elemMax, n.getIzquierdo());
            if (elemMin.compareTo(n.getElem()) <= 0 && elemMax.compareTo(n.getElem()) >= 0)
                ls.insertar(n.getElem(), ls.longitud() + 1);
            listarRangoAux(ls, elemMin, elemMax, n.getDerecho());
        }
    }

    public Object minimoElem() {
        NodoABB nodoAux = this.raiz;
        Object elemMin = null;
        if (nodoAux != null) {
            while (nodoAux.getIzquierdo() != null) {
                nodoAux = nodoAux.getIzquierdo();
            }
            elemMin = nodoAux.getElem();
        }
        return elemMin;
    }

    public Object maximoElem() {
        NodoABB nodoAux = this.raiz;
        Object elemMin = null;
        if (nodoAux != null) {
            while (nodoAux.getDerecho() != null) {
                nodoAux = nodoAux.getDerecho();
            }
            elemMin = nodoAux.getElem();
        }
        return elemMin;
    }

    public void vaciar() {
        this.raiz = null;
    }

    public String toString() {
        String s = "[";
        if (!vacio()) {
            s += StringArbol(this.raiz);
        }
        s += "]";
        return s;
    }

    private String StringArbol(NodoABB n) {
        String s = "";
        if (n != null) {
            s += n.getElem();
            if (n.getIzquierdo() != null) {
                s += "," + StringArbol(n.getIzquierdo());
            }
            if (n.getDerecho() != null) {
                s += "," + StringArbol(n.getDerecho());
            }
        }
        return s;
    }


    // METODOS DE PRACTICA

    public void eliminarMinimo(){
        NodoABB ant = this.raiz;
        NodoABB aux = this.raiz;
        if(ant.getIzquierdo()  == null){
            this.raiz = ant.getDerecho();
        } else{
            while(aux.getIzquierdo() != null){
                ant = aux;
                aux = aux.getIzquierdo();
            }
            if(aux.getDerecho() != null)
                ant.setIzquierdo(aux.getDerecho());
            else
                ant.setIzquierdo(null);
        }
    }

    public ArbolBB clonarParteInvertido(Comparable elemNodo){
        NodoABB nodoPadre = encontrarNodo(elemNodo, this.raiz);
        ArbolBB subArbol = new ArbolBB();
        if(nodoPadre != null)
            subArbol.raiz = clonarArbolInvertido(nodoPadre);
        return subArbol;
    }

    private NodoABB encontrarNodo(Comparable elemNodo,  NodoABB n){
        NodoABB aux = null;
        if(n != null){
            if(elemNodo.compareTo(n.getElem()) == 0){
                aux = n;
            } else if(elemNodo.compareTo(n.getElem()) < 0){
                aux = encontrarNodo(elemNodo, n.getIzquierdo());
            } else{
                aux = encontrarNodo(elemNodo, n.getDerecho());
            }
        }
        return aux;
    }

    private NodoABB clonarArbolInvertido(NodoABB n){
        NodoABB nodoClon = null;
        if(n!= null){
            nodoClon = new NodoABB(n.getElem(), clonarArbolInvertido(n.getDerecho()), clonarArbolInvertido(n.getIzquierdo()));
        }
        return nodoClon;
    }
}
