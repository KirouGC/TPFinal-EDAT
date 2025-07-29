package clases.conjuntistas;

public class NodoAVL {
    Comparable elem;
    int altura;
    NodoAVL izquierdo;
    NodoAVL derecho;

    public NodoAVL(Comparable elem, NodoAVL izquierdo, NodoAVL derecho) {
        this.elem = elem;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
    }

    public Comparable getElem() {
        return this.elem;
    }

    public int getAltura() {
        return this.altura;
    }

    public NodoAVL getIzquierdo() {
        return this.izquierdo;
    }

    public NodoAVL getDerecho() {
        return this.derecho;
    }

    public void setElem(Comparable elem) {
        this.elem = elem;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public void setIzquierdo(NodoAVL izq) {
        this.izquierdo = izq;
    }

    public void setDerecho(NodoAVL der) {
        this.derecho = der;
    }

    public void recalcularAltura() {
        altura = alturaNodo(this);
    }

    private int alturaNodo(NodoAVL n) {
        int izq = 0;
        int der = 0;
        int altura = 0;
        if (n != null) {
            if (n.getIzquierdo() != null) {
                izq = 1 + alturaNodo(n.getIzquierdo());
            }
            if (n.getDerecho() != null) {
                der = 1 + alturaNodo(n.getDerecho());
            }
            if (izq != 0 || der != 0) {
                if (izq >= der) {
                    altura = izq;
                } else {
                    altura = der;
                }
            }
        }
        return altura;
    }
}
