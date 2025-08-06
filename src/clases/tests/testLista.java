package clases.tests;
import clases.lineales.dinamicas.Lista;

public class testLista {
    public static void main(String[] args) {
        Lista lista = new Lista();

        // Insertar elementos
        System.out.println("Insertando elementos 1 a 5...");
        for (int i = 1; i <= 5; i++) {
            lista.insertar(i, i);
        }
        System.out.println("Lista: " + lista);

        // Insertar en posición intermedia
        lista.insertar(99, 3);
        System.out.println("Insertar 99 en posición 3: " + lista);

        // Eliminar elemento en posición 2
        lista.eliminar(2);
        System.out.println("Eliminar elemento en posición 2: " + lista);

        // Recuperar elemento en posición 3
        System.out.println("Elemento en posición 3: " + lista.recuperar(3));

        // Localizar elemento 99
        System.out.println("Posición del elemento 99: " + lista.localizar(99));

        // Longitud de la lista
        System.out.println("Longitud de la lista: " + lista.longitud());

        // Es vacía
        System.out.println("¿Lista vacía?: " + lista.esVacia());

        // Clonar lista
        Lista clon = lista.clone();
        System.out.println("Clon de la lista: " + clon);

        // Vaciar lista
        lista.vaciar();
        System.out.println("Lista tras vaciar: " + lista);
        System.out.println("¿Lista vacía tras vaciar?: " + lista.esVacia());

        // Insertar elementos repetidos para probar eliminarAparaciones
        lista.insertar(1, 1);
        lista.insertar(2, 2);
        lista.insertar(1, 3);
        lista.insertar(3, 4);
        lista.insertar(1, 5);
        System.out.println("Lista con elementos repetidos: " + lista);
        lista.eliminarAparaciones(1);
        System.out.println("Lista tras eliminar apariciones de 1: " + lista);

        // Insertar más elementos para probar obtenerMultiplos
        for (int i = 4; i <= 10; i++) {
            lista.insertar(i, lista.longitud() + 1);
        }
        System.out.println("Lista para obtener múltiplos: " + lista);
        Lista multiplos = lista.obtenerMultiplos(3);
        System.out.println("Elementos en posiciones múltiplos de 3: " + multiplos);

        // Probar moverAAnteultimaPosicion
        lista.insertar(100, 2);
        System.out.println("Lista antes de mover posición 2 a anteúltima: " + lista);
        boolean movido = lista.moverAAnteultimaPosicion(2);
        System.out.println("¿Se movió?: " + movido + " | Lista: " + lista);

        // Probar invertirLista
        Lista invertida = lista.invertirLista();
        System.out.println("Lista invertida: " + invertida);

        // Probar toStringSecundario con arrays
        Object[] arr1 = {1, 2, 3};
        Object[] arr2 = {4, 5};
        Lista listaArr = new Lista();
        listaArr.insertar(arr1, 1);
        listaArr.insertar(arr2, 2);
        System.out.println("Lista con arrays: " + listaArr.toStringSecundario());
    }
}
