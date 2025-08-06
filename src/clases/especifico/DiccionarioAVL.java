package clases.especifico;

import clases.lineales.dinamicas.Lista;

public class DiccionarioAVL {

    private NodoAVLDicc raiz;

    public DiccionarioAVL() {
        this.raiz = null;
    }

    public boolean insertar(Comparable clave, Object dato) {
        boolean[] exito = { false };
        if (this.raiz == null) {
            this.raiz = new NodoAVLDicc(clave, dato);
            exito[0] = true;
        } else {
            this.raiz = insertarRec(this.raiz, clave, dato, exito);
        }
        return exito[0];
    }

    private NodoAVLDicc insertarRec(NodoAVLDicc n, Comparable clave, Object dato, boolean[] exito) {
        int comparar;
        if (n != null) {
            comparar = clave.compareTo(n.getClave());
            if (comparar != 0) {
                if (comparar < 0) {
                    if (n.getIzquierdo() != null) {
                        n.setIzquierdo(insertarRec(n.getIzquierdo(), clave, dato, exito));
                    } else {
                        n.setIzquierdo(new NodoAVLDicc(clave, dato, null, null));
                        exito[0] = true;
                    }
                } else {
                    if (n.getDerecho() != null) {
                        n.setDerecho(insertarRec(n.getDerecho(), clave, dato, exito));
                    } else {
                        n.setDerecho(new NodoAVLDicc(clave, dato));
                        exito[0] = true;
                    }
                }
            }
        }
        if (exito[0]) {
            n.recalcularAltura();
            int balance = calcularBalance(n);
            if (balance > 1) {
                int balanceHijo = calcularBalance(n.getIzquierdo());
                if (balanceHijo < 0) {
                    n.setIzquierdo(balancearDer(n.getIzquierdo()));
                }
                n = balancearIzq(n);
            }
            if (balance < -1) {
                int balanceHijo = calcularBalance(n.getDerecho());
                if (balanceHijo > 0) {
                    n.setDerecho(balancearIzq(n.getDerecho()));
                }
                n = balancearDer(n);
            }
        }
        return n;
    }

    private NodoAVLDicc balancearIzq(NodoAVLDicc r) {
        NodoAVLDicc h = r.getIzquierdo();
        NodoAVLDicc temp = h.getDerecho();
        h.setDerecho(r);
        r.setIzquierdo(temp);
        r.recalcularAltura();
        h.recalcularAltura();
        return h;
    }

    private NodoAVLDicc balancearDer(NodoAVLDicc r) {
        NodoAVLDicc h = r.getDerecho();
        NodoAVLDicc temp = h.getIzquierdo();
        h.setIzquierdo(r);
        r.setDerecho(temp);
        r.recalcularAltura();
        h.recalcularAltura();
        return h;
    }

    private int calcularBalance(NodoAVLDicc n) {
        int altDer = (n.getDerecho() != null) ? n.getDerecho().getAltura() : -1;
        int altIzq = (n.getIzquierdo() != null) ? n.getIzquierdo().getAltura() : -1;
        int balance = altIzq - altDer;
        return balance;
    }

    public boolean vacio() {
        boolean esVacio = false;
        if (this.raiz == null) {
            esVacio = true;
        }
        return esVacio;
    }

    public boolean eliminar(Comparable elem) {
        boolean[] exito = { false };
        if (!this.vacio()) {
            this.raiz = eliminarRec(this.raiz, elem, exito);
        }
        return exito[0];
    }

    private NodoAVLDicc eliminarRec(NodoAVLDicc n, Comparable elem, boolean[] exito) {
        NodoAVLDicc eliminado = n;
        int comparar;
        if (n != null) {
            comparar = elem.compareTo(n.getClave());
            if (comparar == 0) {
                if (n.getDerecho() == null && n.getIzquierdo() == null) {
                    eliminado = null;
                } else if (n.getIzquierdo() != null && n.getDerecho() == null) {
                    eliminado = n.getIzquierdo();
                } else if (n.getIzquierdo() == null && n.getDerecho() != null) {
                    eliminado = n.getDerecho();
                } else if (n.getIzquierdo() != null && n.getDerecho() != null) {
                    // Buscar el mayor del subárbol izquierdo (predecesor)
                    NodoAVLDicc mayor = buscarMayorNodo(n.getIzquierdo());
                    n.setClave(mayor.getClave());
                    n.setDato(mayor.getDato());
                    n.setIzquierdo(eliminarRec(n.getIzquierdo(), mayor.getClave(), exito));
                    eliminado = n;
                }
                exito[0] = true;
            } else {
                if (comparar < 0) {
                    n.setIzquierdo(eliminarRec(n.getIzquierdo(), elem, exito));
                } else {
                    n.setDerecho(eliminarRec(n.getDerecho(), elem, exito));
                }
            }
        }
        if (exito[0] && eliminado != null) {
            eliminado.recalcularAltura();
            int balance = calcularBalance(eliminado);
            if (balance > 1) {
                int balanceHijo = calcularBalance(eliminado.getIzquierdo());
                if (balanceHijo < 0) {
                    eliminado.setIzquierdo(balancearDer(eliminado.getIzquierdo()));
                }
                eliminado = balancearIzq(eliminado);
            }
            if (balance < -1) {
                int balanceHijo = calcularBalance(eliminado.getDerecho());
                if (balanceHijo > 0) {
                    eliminado.setDerecho(balancearIzq(eliminado.getDerecho()));
                }
                eliminado = balancearDer(eliminado);
            }
        }
        return eliminado;
    }

    private NodoAVLDicc buscarMayorNodo(NodoAVLDicc n) {
        if (n != null) {
            while (n.getDerecho() != null) {
                n = n.getDerecho();
            }
        }
        return n;
    }

    private NodoAVLDicc buscarMenorNodo(NodoAVLDicc n) {
        if (n != null) {
            while (n.getIzquierdo() != null) {
                n = n.getIzquierdo();
            }
        }
        return n;
    }

    public void vaciar() {
        this.raiz = null;
    }

    public Comparable minimoClave() {
        NodoAVLDicc nodo = buscarMenorNodo(this.raiz);
        return (nodo != null) ? nodo.getClave() : null;
    }

    public Object minimoDato() {
        NodoAVLDicc nodo = buscarMenorNodo(this.raiz);
        return (nodo != null) ? nodo.getDato() : null;
    }

    public Comparable maximoClave() {
        NodoAVLDicc nodo = buscarMayorNodo(this.raiz);
        return (nodo != null) ? nodo.getClave() : null;
    }

    public Object maximoDato() {
        NodoAVLDicc nodo = buscarMayorNodo(this.raiz);
        return (nodo != null) ? nodo.getDato() : null;
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
            string += " / Altura del Nodo: " + raiz.getAltura();
            string += stringArbol(raiz.getIzquierdo());
            string += stringArbol(raiz.getDerecho());
        } else {
            string = "Árbol vacío.";
        }
        return string;
    }

    private String stringArbol(NodoAVLDicc n) {
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
            s += " / Altura del Nodo: " + n.getAltura();
            s += stringArbol(n.getIzquierdo());
            s += stringArbol(n.getDerecho());
        }
        return s;
    }

    public Lista listarRango(Comparable elemMinimo, Comparable elemMaximo) {
        Lista listaRango = new Lista();
        int comparacion = (elemMinimo).compareTo(elemMaximo);
        if (comparacion < 0) {
            listarRangoRec(listaRango, this.raiz, elemMinimo, elemMaximo);
        }
        return listaRango;
    }

    private void listarRangoRec(Lista l, NodoAVLDicc n, Comparable min, Comparable max) {
        if (n != null) {
            Comparable compararElem = n.getClave();
            int comparacionMin = compararElem.compareTo(min);
            int comparacionMax = compararElem.compareTo(max);
            if (n.getIzquierdo() != null && comparacionMin >= 0) {
                listarRangoRec(l, n.getIzquierdo(), min, max);
            }
            if (comparacionMin >= 0 && comparacionMax <= 0) {
                l.insertar(n.getDato(), l.longitud() + 1);
            }
            if (n.getDerecho() != null && comparacionMax <= 0) {
                listarRangoRec(l, n.getDerecho(), min, max);
            }
        }
    }

    public boolean pertenece(Comparable elem) {
        return perteneceRecursivo(this.raiz, elem);
    }

    private boolean perteneceRecursivo(NodoAVLDicc nodoActual, Comparable elem) {
        boolean pertenece = false;
        int comparacion;
        if (nodoActual != null) {
            comparacion = elem.compareTo(nodoActual.getClave());
            if (comparacion == 0) {
                pertenece = true;
            } else {
                if (comparacion < 0) {
                    pertenece = perteneceRecursivo(nodoActual.getIzquierdo(), elem);
                } else {
                    pertenece = perteneceRecursivo(nodoActual.getDerecho(), elem);
                }
            }
        }
        return pertenece;
    }

    public Object obtenerDato(Object clave) {
        return obtenerDatoAux(this.raiz, (Comparable) clave);
    }

    private Object obtenerDatoAux(NodoAVLDicc nodoActual, Comparable clave) {
        Object dato = null;
        int comparacion;
        if (nodoActual != null) {
            comparacion = clave.compareTo(nodoActual.getClave());
            if (comparacion == 0) {
                dato = nodoActual.getDato();
            } else {
                if (comparacion < 0) {
                    dato = obtenerDatoAux(nodoActual.getIzquierdo(), clave);
                } else {
                    dato = obtenerDatoAux(nodoActual.getDerecho(), clave);
                }
            }
        }
        return dato;
    }

    public boolean existeClave(Object clave) {
        return existeClaveAux(this.raiz, (Comparable) clave);
    }

    private boolean existeClaveAux(NodoAVLDicc nodoActual, Comparable clave) {
        boolean existe = false;
        int comparacion;
        if (nodoActual != null) {
            comparacion = clave.compareTo(nodoActual.getClave());
            if (comparacion == 0) {
                existe = true;
            } else {
                if (comparacion < 0) {
                    existe = existeClaveAux(nodoActual.getIzquierdo(), clave);
                } else {
                    existe = existeClaveAux(nodoActual.getDerecho(), clave);
                }
            }
        }
        return existe;
    }

    public Lista listarClaves() {
        Lista arbolPreOrden = new Lista();
        listarClavesAux(arbolPreOrden, this.raiz);
        return arbolPreOrden;
    }

    private void listarClavesAux(Lista lista, NodoAVLDicc padre) {
        if (padre != null) {
            lista.insertar(padre.getClave(), lista.longitud() + 1);
            listarClavesAux(lista, padre.getIzquierdo());
            listarClavesAux(lista, padre.getDerecho());
        }
    }

    public Lista listarDatos() {
        Lista arbolPreOrden = new Lista();
        listarDatosAux(arbolPreOrden, this.raiz);
        return arbolPreOrden;
    }

    private void listarDatosAux(Lista lista, NodoAVLDicc padre) {
        if (padre != null) {
            lista.insertar(padre.getDato(), lista.longitud() + 1);
            listarDatosAux(lista, padre.getIzquierdo());
            listarDatosAux(lista, padre.getDerecho());
        }
    }
}
