package clases.tests;
import clases.lineales.dinamicas.Cola;

public class testCola {
    public static void main(String[] args) {
        Cola cola = new Cola();
        // Probar esVacia en cola recién creada
        System.out.println("¿Cola vacía?: " + cola.esVacia());

        // Poner elementos
        System.out.println("Agregando elementos 1, 2, 3...");
        cola.poner(1);
        cola.poner(2);
        cola.poner(3);
        System.out.println("Cola: " + cola);

        // Obtener frente
        System.out.println("Frente de la cola: " + cola.obtenerFrente());

        // Sacar elementos
        System.out.println("Sacando elemento: " + cola.sacar());
        System.out.println("Cola tras sacar: " + cola);
        System.out.println("Nuevo frente: " + cola.obtenerFrente());

        // Clonar cola
        Cola clon = cola.clone();
        System.out.println("Clon de la cola: " + clon);

        // Vaciar cola
        cola.vaciar();
        System.out.println("Cola tras vaciar: " + cola);
        System.out.println("¿Cola vacía tras vaciar?: " + cola.esVacia());

        // Probar sacar de cola vacía
        System.out.println("Intentar sacar de cola vacía: " + cola.sacar());
        System.out.println("Intentar obtener frente de cola vacía: " + cola.obtenerFrente());
    }
}
