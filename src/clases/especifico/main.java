package clases.especifico;

public class main {
    public static void main(String[] args) {
        DiccionarioAVL arbol = new DiccionarioAVL();
        String a = "a";
        int[] claves = {30, 20, 50, 15, 24, 35, 100, 12, 18, 22, 27, 33, 40, 90, 105, 13, 17, 19, 23, 37, 16};
        for (int i = 0; i < claves.length; i++) {
            arbol.insertar(claves[i], a);
        }
        System.out.println(arbol.toString());
        arbol.eliminar(30);
        System.out.println(arbol.toString()); // Elimina de manera correcto el nodo con clave 30 y rebalancea
    }
}
