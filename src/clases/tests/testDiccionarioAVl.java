package clases.tests;

import clases.especifico.DiccionarioAVL;

public class testDiccionarioAVl {

    public static void main(String[] args) {

        // ARBOL PLANTEADO EN LAS CORRRECIONES DEL TP
        DiccionarioAVL arbolTest = new DiccionarioAVL();
        String a = "a";
        int[] claves = {30, 20, 50, 15, 24, 35, 100, 12, 18, 22, 27, 33, 40, 90, 105, 13, 17, 19, 23, 37, 16};
        for (int i = 0; i < claves.length; i++) {
            arbolTest.insertar(claves[i], a);
        }
        System.out.println(arbolTest.toString());
        arbolTest.eliminar(30);
        System.out.println(arbolTest.toString()); // Elimina de manera correcto el nodo con clave 30 y rebalancea

        // ARBOL DE PRUEBA testDiccionarioAVL 

        DiccionarioAVL avl = new DiccionarioAVL();

        // Insertar elementos
        System.out.println("Insertando claves...");
        avl.insertar(30, "A");
        avl.insertar(20, "B");
        avl.insertar(40, "C");
        avl.insertar(10, "D");
        avl.insertar(25, "E");
        avl.insertar(35, "F");
        avl.insertar(50, "G");
        avl.insertar(5, "H");
        avl.insertar(15, "I");
        avl.insertar(45, "J");
        System.out.println(avl);

        // Probar vacio
        System.out.println("¿Árbol vacío?: " + avl.vacio());

        // Probar pertenece y existeClave
        System.out.println("¿Pertenece clave 25?: " + avl.pertenece(25));
        System.out.println("¿Existe clave 99?: " + avl.existeClave(99));

        // Probar obtenerDato
        System.out.println("Dato de clave 40: " + avl.obtenerDato(40));
        System.out.println("Dato de clave 99 (no existe): " + avl.obtenerDato(99));

        // Probar mínimo y máximo
        System.out.println("Clave mínima: " + avl.minimoClave());
        System.out.println("Dato mínimo: " + avl.minimoDato());
        System.out.println("Clave máxima: " + avl.maximoClave());
        System.out.println("Dato máximo: " + avl.maximoDato());

        // Probar listarClaves y listarDatos
        System.out.println("Claves en el árbol: " + avl.listarClaves());
        System.out.println("Datos en el árbol: " + avl.listarDatos());

        // Probar listarRango
        System.out.println("Datos en rango [15, 45]: " + avl.listarRango(15, 45));

        // Probar eliminar
        System.out.println("Eliminar clave 20: " + avl.eliminar(20));
        System.out.println("Eliminar clave 99 (no existe): " + avl.eliminar(99));
        System.out.println(avl);

        // Probar vaciar
        avl.vaciar();
        System.out.println("Árbol tras vaciar: " + avl);
        System.out.println("¿Árbol vacío tras vaciar?: " + avl.vacio());
    }
}
