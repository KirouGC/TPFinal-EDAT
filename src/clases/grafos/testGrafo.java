package clases.grafos;

public class testGrafo {
    public static void main(String[] args){
        Grafo grafo = new Grafo();
        grafo.insertarVertice('A');
        grafo.insertarVertice('B');
        grafo.insertarVertice('C');
        grafo.insertarArco('A', 'B', "NIGGER");
        grafo.insertarArco('A', 'B', "NIGGER2");
        grafo.insertarArco('B', 'A', "NIGGER");
        grafo.insertarArco('A', 'C', "NIGGER3");
        System.out.println(grafo.toString());
        System.out.println(grafo.existeArco('A', 'C'));
        grafo.eliminarArco("NIGGER3");
        System.out.println(grafo.existeArco('A', 'C'));
        System.out.println(grafo.toString());
    }
}
