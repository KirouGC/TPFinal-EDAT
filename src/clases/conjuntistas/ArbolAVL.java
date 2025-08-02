package clases.conjuntistas;

import clases.lineales.dinamicas.Lista;
import java.util.LinkedList;
import java.util.Queue;

public class ArbolAVL {
    NodoAVL raiz;

    public ArbolAVL() {
        this.raiz = null;
    }

    public boolean insertar(Comparable elem) {
        boolean exito = false;
        if (this.raiz == null) {
            this.raiz = new NodoAVL(elem, null, null);
            this.raiz.setAltura(0);
            exito = true;
        } else {
            exito = insertarAux(this.raiz, null, elem);
        }
        return exito;
    }

    private boolean insertarAux(NodoAVL nodoActual, NodoAVL nodoPadre, Comparable elem) {
        boolean exito = false;
        if (nodoActual != null) {
            if (elem.compareTo(nodoActual.getElem()) == 0) {
                exito = false;
            } else if (elem.compareTo(nodoActual.getElem()) > 0) {
                if (nodoActual.getDerecho() == null) {
                    NodoAVL nodoAux = new NodoAVL(elem, null, null);
                    nodoAux.setAltura(0);
                    nodoActual.setDerecho(nodoAux);
                    exito = true;
                } else {
                    exito = insertarAux(nodoActual.getDerecho(), nodoActual, elem);
                }
            } else {
                if (nodoActual.getIzquierdo() == null) {
                    NodoAVL nodoAux = new NodoAVL(elem, null, null);
                    nodoAux.setAltura(0);
                    nodoActual.setIzquierdo(nodoAux);
                    exito = true;
                } else {
                    exito = insertarAux(nodoActual.getIzquierdo(), nodoActual, elem);
                }
            }
            if (nodoActual.getIzquierdo() != null || nodoActual.getDerecho() != null) {
                nodoActual.recalcularAltura();
                verificarRotacion(nodoActual, nodoPadre);
            }
        }
        return exito;
    }

    private void verificarRotacion(NodoAVL nodoActual, NodoAVL nodoPadre) {
        int balanceActual = 0;
        int balanceHijo = 0;
        NodoAVL nodoRotacion = null;
        balanceActual = calcularBalance(nodoActual);
        if (balanceActual < -1) {
            balanceHijo = calcularBalance(nodoActual.getDerecho());
            if (balanceHijo > 0) {
                nodoRotacion = rotarDerecha(nodoActual.getDerecho());
                nodoActual.setDerecho(nodoRotacion);
                nodoActual.getDerecho().recalcularAltura();
                nodoActual.recalcularAltura();
            }
            nodoRotacion = rotarIzquierda(nodoActual);
            if (nodoPadre != null) {
                if (nodoPadre.getIzquierdo() == nodoActual) {
                    nodoPadre.setIzquierdo(nodoRotacion);
                } else {
                    nodoPadre.setDerecho(nodoRotacion);
                }
            } else {
                this.raiz = nodoRotacion;
            }
        } else if (balanceActual > 1) {
            balanceHijo = calcularBalance(nodoActual.getIzquierdo());
            if (balanceHijo < 0) {
                nodoRotacion = rotarIzquierda(nodoActual.getIzquierdo());
                nodoActual.setIzquierdo(nodoRotacion);
                nodoActual.getIzquierdo().recalcularAltura();
                nodoActual.recalcularAltura();
            }
            nodoRotacion = rotarDerecha(nodoActual);
            if (nodoPadre != null) {
                if (nodoPadre.getDerecho() == nodoActual) {
                    nodoPadre.setDerecho(nodoRotacion);
                } else {
                    nodoPadre.setIzquierdo(nodoRotacion);
                }
            } else {
                this.raiz = nodoRotacion;
            }
        }
        nodoActual.recalcularAltura();
    }

    private int calcularBalance(NodoAVL nodo) {
        int balance = 0;
        int izq = -1;
        int der = -1;
        if (nodo.getIzquierdo() != null) {
            izq = nodo.getIzquierdo().getAltura();
        }
        if (nodo.getDerecho() != null) {
            der = nodo.getDerecho().getAltura();
        }
        balance = izq - der;
        return balance;
    }

    private NodoAVL rotarDerecha(NodoAVL nodoActual) {
        NodoAVL h = nodoActual.getIzquierdo();
        NodoAVL temp = h.getDerecho();
        h.setDerecho(nodoActual);
        nodoActual.setIzquierdo(temp);
        return h; // retorna la nueva subraiz del arbol
    }

    private NodoAVL rotarIzquierda(NodoAVL nodoActual) {
        NodoAVL h = nodoActual.getDerecho();
        NodoAVL temp = h.getIzquierdo();
        h.setIzquierdo(nodoActual);
        nodoActual.setDerecho(temp);
        return h; // retorna la nueva subraiz del arbol
    }

    public boolean eliminar(Comparable elem) {
        boolean exito = eliminarAux(this.raiz, null, elem);
        return exito;
    }

    private boolean eliminarAux(NodoAVL nodoActual, NodoAVL padre, Comparable elem) {
        boolean exito = false;
        if (nodoActual != null) {
            if (elem.compareTo(nodoActual.getElem()) == 0) {
                if (nodoActual.getIzquierdo() == null && nodoActual.getDerecho() == null) {
                    eliminarCasoHoja(nodoActual, padre);
                    exito = true;
                } else {
                    if ((nodoActual.getIzquierdo() != null && nodoActual.getDerecho() == null)
                            || (nodoActual.getIzquierdo() == null && nodoActual.getDerecho() != null)) {
                        eliminarCaso1Hijo(nodoActual, padre);
                        exito = true;
                    } else {
                        eliminarCaso2Hijos(nodoActual);
                        exito = true;
                    }
                }
            } else if (elem.compareTo(nodoActual.getElem()) < 0) {
                exito = eliminarAux(nodoActual.getIzquierdo(), nodoActual, elem);
            } else {
                exito = eliminarAux(nodoActual.getDerecho(), nodoActual, elem);
            }
            if (nodoActual.getIzquierdo() != null || nodoActual.getDerecho() != null) {
                nodoActual.recalcularAltura();
                verificarRotacion(nodoActual, padre);
            }
        }
        return exito;
    }

    private void eliminarCasoHoja(NodoAVL nodoActual, NodoAVL padre) {
        if (padre != null) {
            if (padre.getIzquierdo() == nodoActual) {
                padre.setIzquierdo(null);
            } else {
                padre.setDerecho(null);
            }
        } else {
            this.raiz = null;
        }
    }

    private void eliminarCaso1Hijo(NodoAVL nodoActual, NodoAVL padre) {
        if (padre != null) {
            if (nodoActual.getIzquierdo() != null) {
                padre.setIzquierdo(nodoActual.getIzquierdo());
            } else {
                padre.setDerecho(nodoActual.getDerecho());
            }
        } else {
            if (nodoActual.getIzquierdo() != null) {
                this.raiz = nodoActual.getIzquierdo();
            } else {
                this.raiz = nodoActual.getDerecho();
            }
        }
    }

    private void eliminarCaso2Hijos(NodoAVL nodoActual) {
        if (nodoActual.getIzquierdo().getDerecho() != null) {
            NodoAVL nodoMenor = obtenerMenorEliminarAux(nodoActual.getIzquierdo(), nodoActual);
            nodoActual.setElem(nodoMenor.getDerecho().getElem());
            nodoMenor.setDerecho(nodoMenor.getDerecho().getIzquierdo());
        } else {
            nodoActual.setElem(nodoActual.getIzquierdo().getElem());
            nodoActual.setIzquierdo(nodoActual.getIzquierdo().getIzquierdo());
        }

    }

    private NodoAVL obtenerMenorEliminarAux(NodoAVL nodoActual, NodoAVL padre) {
        NodoAVL padreElemento = null;
        if (nodoActual != null) {
            if (nodoActual.getDerecho() != null) {
                padreElemento = obtenerMenorEliminarAux(nodoActual.getDerecho(), nodoActual);
            } else {
                padreElemento = padre;
            }
        }
        return padreElemento;
    }

    public boolean esVacio() {
        return this.raiz == null;
    }

    public String toString() {
        String s = "[";
        if (!esVacio()) {
            s += StringArbol(this.raiz);
        }
        s += "]";
        return s;
    }

    private String StringArbol(NodoAVL n) {
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

    public Lista listarPorNiveles() {
        Lista resultado = new Lista();
        if (raiz == null) {
            return resultado;
        }
        Queue<NodoAVL> cola = new LinkedList<>();
        cola.add(raiz);
        int pos = 1;

        while (!cola.isEmpty()) {
            NodoAVL actual = cola.poll();
            resultado.insertar(actual.getElem(), pos);
            pos++;

            if (actual.getIzquierdo() != null) {
                cola.add(actual.getIzquierdo());
            }
            if (actual.getDerecho() != null) {
                cola.add(actual.getDerecho());
            }
        }

        return resultado;
    }

}