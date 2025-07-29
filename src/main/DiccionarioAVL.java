package main;

import clases.lineales.dinamicas.Lista;

public class DiccionarioAVL {

    private NodoAVLDicc raiz;

    public DiccionarioAVL() {
        this.raiz = null;
    }

    public boolean insertar(Comparable elem, Object dato) {
        boolean exito = false;
        if (this.raiz == null) {
            this.raiz = new NodoAVLDicc(elem, dato, null, null);
            this.raiz.setAltura(0);
            exito = true;
        } else {
            exito = insertarAux(this.raiz, null, elem, dato);
        }
        return exito;
    }

    private boolean insertarAux(NodoAVLDicc nodoActual, NodoAVLDicc nodoPadre, Comparable elem, Object dato) {
        boolean exito = false;
        if (nodoActual != null) {
            if (elem.compareTo(nodoActual.getClave()) == 0) {
                exito = false;
            } else if (elem.compareTo(nodoActual.getClave()) > 0) {
                if (nodoActual.getDerecho() == null) {
                    NodoAVLDicc nodoAux = new NodoAVLDicc(elem, dato, null, null);
                    nodoAux.setAltura(0);
                    nodoActual.setDerecho(nodoAux);
                    exito = true;
                } else {
                    exito = insertarAux(nodoActual.getDerecho(), nodoActual, elem, dato);
                }
            } else {
                if (nodoActual.getIzquierdo() == null) {
                    NodoAVLDicc nodoAux = new NodoAVLDicc(elem, dato, null, null);
                    nodoAux.setAltura(0);
                    nodoActual.setIzquierdo(nodoAux);
                    exito = true;
                } else {
                    exito = insertarAux(nodoActual.getIzquierdo(), nodoActual, elem, dato);
                }
            }
            if (nodoActual.getIzquierdo() != null || nodoActual.getDerecho() != null) {
                nodoActual.recalcularAltura();
                verificarRotacion(nodoActual, nodoPadre);
            }
        }
        return exito;
    }

    public boolean existeClave(Comparable clave) {
        return existeClaveAux(this.raiz, clave);
    }

    private boolean existeClaveAux(NodoAVLDicc nodo, Comparable clave) {
        boolean resultado = false;
        if (nodo != null) {
            int comp = clave.compareTo(nodo.getClave());
            if (comp == 0) {
                resultado = true;
            } else if (comp < 0) {
                resultado = existeClaveAux(nodo.getIzquierdo(), clave);
            } else {
                resultado = existeClaveAux(nodo.getDerecho(), clave);
            }
        }
        return resultado;
    }

    public Object obtenerDato(Comparable clave) {
        return obtenerDatoAux(this.raiz, clave);
    }

    private Object obtenerDatoAux(NodoAVLDicc nodo, Comparable clave) {
        Object resultado = null;
        if (nodo != null) {
            int comp = clave.compareTo(nodo.getClave());
            if (comp == 0) {
                resultado = nodo.getDato();
            } else if (comp < 0) {
                resultado = obtenerDatoAux(nodo.getIzquierdo(), clave);
            } else {
                resultado = obtenerDatoAux(nodo.getDerecho(), clave);
            }
        }
        return resultado;
    }

    public Lista listarClaves() {
        Lista l = new Lista();
        listarClavesAux(this.raiz, l);
        return l;
    }

    private void listarClavesAux(NodoAVLDicc nodo, Lista l) {
        if (nodo != null) {
            listarClavesAux(nodo.getIzquierdo(), l);
            l.insertar(nodo.getClave(), l.longitud() + 1);
            listarClavesAux(nodo.getDerecho(), l);
        }
    }

    public Lista listarDatos() {
        Lista l = new Lista();
        listarDatosAux(this.raiz, l);
        return l;
    }

    private void listarDatosAux(NodoAVLDicc nodo, Lista l) {
        if (nodo != null) {
            listarDatosAux(nodo.getIzquierdo(), l);
            l.insertar(nodo.getDato(), l.longitud() + 1);
            listarDatosAux(nodo.getDerecho(), l);
        }
    }

    public boolean eliminar(Comparable elem) {
        boolean exito = eliminarAux(this.raiz, null, elem);
        return exito;
    }

    private boolean eliminarAux(NodoAVLDicc nodoActual, NodoAVLDicc padre, Comparable elem) {
        boolean exito = false;
        if (nodoActual != null) {
            if (elem.compareTo(nodoActual.getClave()) == 0) {
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
            } else if (elem.compareTo(nodoActual.getClave()) < 0) {
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

    private void eliminarCasoHoja(NodoAVLDicc nodoActual, NodoAVLDicc padre) {
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

    private void eliminarCaso1Hijo(NodoAVLDicc nodoActual, NodoAVLDicc padre) {
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

    private void eliminarCaso2Hijos(NodoAVLDicc nodoActual) {
        if (nodoActual.getIzquierdo().getDerecho() != null) {
            NodoAVLDicc nodoMenor = obtenerMenorEliminarAux(nodoActual.getIzquierdo(), nodoActual);
            nodoActual.setClave(nodoMenor.getDerecho().getClave());
            nodoActual.setDato(nodoMenor.getDerecho().getDato());
            nodoMenor.setDerecho(nodoMenor.getDerecho().getIzquierdo());
        } else {
            nodoActual.setClave(nodoActual.getIzquierdo().getClave());
            nodoActual.setDato(nodoActual.getIzquierdo().getDato());
            nodoActual.setIzquierdo(nodoActual.getIzquierdo().getIzquierdo());
        }

    }

    private void verificarRotacion(NodoAVLDicc nodoActual, NodoAVLDicc nodoPadre) {
        int balanceActual = 0;
        int balanceHijo = 0;
        NodoAVLDicc nodoRotacion = null;
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

    private int calcularBalance(NodoAVLDicc nodo) {
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

    private NodoAVLDicc rotarDerecha(NodoAVLDicc nodoActual) {
        NodoAVLDicc h = nodoActual.getIzquierdo();
        NodoAVLDicc temp = h.getDerecho();
        h.setDerecho(nodoActual);
        nodoActual.setIzquierdo(temp);
        return h; // retorna la nueva subraiz del arbol
    }

    private NodoAVLDicc rotarIzquierda(NodoAVLDicc nodoActual) {
        NodoAVLDicc h = nodoActual.getDerecho();
        NodoAVLDicc temp = h.getIzquierdo();
        h.setIzquierdo(nodoActual);
        nodoActual.setDerecho(temp);
        return h; // retorna la nueva subraiz del arbol
    }

    private NodoAVLDicc obtenerMenorEliminarAux(NodoAVLDicc nodoActual, NodoAVLDicc padre) {
        NodoAVLDicc padreElemento = null;
        if (nodoActual != null) {
            if (nodoActual.getDerecho() != null) {
                padreElemento = obtenerMenorEliminarAux(nodoActual.getDerecho(), nodoActual);
            } else {
                padreElemento = padre;
            }
        }
        return padreElemento;
    }

    public String toString() {
        String string = "";
        if (raiz != null) {
            string = "\nRaíz: " + raiz.getClave();
            if (raiz.getIzquierdo() != null) {
                string += " / Hijo Izquierdo: " + raiz.getIzquierdo().getClave();
            } else {
                string += " / Hijo Izquierdo: null";
            }
            if (raiz.getDerecho() != null) {
                string += " / Hijo Derecho: " + raiz.getDerecho().getClave();
            } else {
                string += " / Hijo Derecho: null";
            }

            string += StringArbol(raiz.getIzquierdo());
            string += StringArbol(raiz.getDerecho());
        } else {
            string = "Árbol vacío.";
        }
        return string;
    }

    private String StringArbol(NodoAVLDicc n) {
        String s = "";
        if (n != null) {
            s = "\n Nodo: " + n.getClave();
            if (n.getIzquierdo() != null) {
                s += " / Hijo Izquierdo: " + n.getIzquierdo().getClave();
            } else {
                s += " / Hijo Izquierdo: null";
            }
            if (n.getDerecho() != null) {
                s += " / Hijo Derecho: " + n.getDerecho().getClave();
            } else {
                s += " / Hijo Derecho: null";
            }

            s += StringArbol(n.getIzquierdo());
            s += StringArbol(n.getDerecho());
        }
        return s;
    }
}
