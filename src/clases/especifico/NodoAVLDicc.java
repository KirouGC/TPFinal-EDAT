package clases.especifico;

public class NodoAVLDicc {
    private Comparable clave;
    private Object dato;
    private int altura;
    private NodoAVLDicc izquierdo;
    private NodoAVLDicc derecho;

    public NodoAVLDicc(Comparable clave, Object dato, NodoAVLDicc izquierdo, NodoAVLDicc derecho) {
        this.clave = clave;
        this.dato = dato;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
        this.altura = 0;
    }

    public NodoAVLDicc(Comparable clave, Object dato) {
        this.clave = clave;
        this.dato = dato;
        this.altura = 0;
    }

    public Comparable getClave() {
        return this.clave;
    }

    public Object getDato() {
        return this.dato;
    }

    public NodoAVLDicc getIzquierdo() {
        return this.izquierdo;
    }

    public NodoAVLDicc getDerecho() {
        return this.derecho;
    }

    public int getAltura() {
        return this.altura;
    }

    public void setClave(Comparable clave) {
        this.clave = clave;
    }

    public void setDato(Object dato) {
        this.dato = dato;
    }

    public void setIzquierdo(NodoAVLDicc izquierdo) {
        this.izquierdo = izquierdo;
    }

    public void setDerecho(NodoAVLDicc derecho) {
        this.derecho = derecho;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public void recalcularAltura() {
        altura = alturaNodo(this);
    }

    private int alturaNodo(NodoAVLDicc n) {
        System.out.println("calculando Altura");
        int izq = 0;
        int der = 0;
        int altura = 0;
        if (n != null) {
            if (n.getIzquierdo() != null) {
                izq = 1 + n.getIzquierdo().getAltura();
            }
            if (n.getDerecho() != null) {
                der = 1 + n.getDerecho().getAltura();
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
