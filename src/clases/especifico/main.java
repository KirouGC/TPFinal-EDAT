package clases.especifico;

public class main {
    public static void main(String[] args) {
        DiccionarioAVL arbol = new DiccionarioAVL();
        String val1 = "a";
        String val2 = "b";
        String val3 = "c";
        String obj1 = "yogur";
        arbol.insertar(val1, obj1);
        arbol.insertar(val2, obj1);
        arbol.insertar(val3, obj1);


    }
}
