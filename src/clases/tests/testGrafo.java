package clases.tests;

import clases.grafos.Grafo;
import clases.lineales.dinamicas.Lista;

public class testGrafo {
    public static void main(String[] args) {
        Grafo g = new Grafo();

        // Test: Grafo vacío
        boolean esperado = true;
        boolean obtenido = g.vacio();
        System.out.println("¿Grafo vacío?: " + obtenido + " | Esperado: " + esperado);

        // Test: Insertar vértices
        System.out.println("Insertando vértices A, B, C, D...");
        g.insertarVertice("A");
        g.insertarVertice("B");
        g.insertarVertice("C");
        g.insertarVertice("D");
        System.out.println("Grafo tras insertar vértices: " + g);

        // Test: Insertar vértice repetido
        esperado = false;
        obtenido = g.insertarVertice("A");
        System.out.println("Insertando vértice repetido A: " + obtenido + " | Esperado: " + esperado);

        // Test: Insertar arcos
        System.out.println("Insertando arcos...");
        boolean ab = g.insertarArco("A", "B", "AB1");
        boolean ac = g.insertarArco("A", "C", "AC1");
        boolean bc = g.insertarArco("B", "C", "BC1");
        boolean cd = g.insertarArco("C", "D", "CD1");
        boolean ad = g.insertarArco("A", "D", "AD1");
        System.out.println("Arcos insertados: AB1=" + ab + " AC1=" + ac + " BC1=" + bc + " CD1=" + cd + " AD1=" + ad
                + " | Esperado: true para todos");
        System.out.println("Grafo tras insertar arcos: " + g);

        // Test: Insertar arco entre vértices inexistentes
        esperado = false;
        obtenido = g.insertarArco("X", "Y", "XY1");
        System.out.println("Insertando arco entre vértices inexistentes: " + obtenido + " | Esperado: " + esperado);

        // Test: Existe vértice
        esperado = true;
        obtenido = g.existeVertice("B");
        System.out.println("¿Existe vértice B?: " + obtenido + " | Esperado: " + esperado);
        esperado = false;
        obtenido = g.existeVertice("Z");
        System.out.println("¿Existe vértice Z?: " + obtenido + " | Esperado: " + esperado);

        // Test: Existe arco
        esperado = true;
        obtenido = g.existeArco("A", "D");
        System.out.println("¿Existe arco de A a D?: " + obtenido + " | Esperado: " + esperado);
        esperado = true;
        obtenido = g.existeArco("AB1");
        System.out.println("¿Existe arco con etiqueta 'AB1'?: " + obtenido + " | Esperado: " + esperado);
        esperado = false;
        obtenido = g.existeArco("ZZZ");
        System.out.println("¿Existe arco con etiqueta 'ZZZ'?: " + obtenido + " | Esperado: " + esperado);

        // Test: Eliminar arco
        esperado = true;
        obtenido = g.eliminarArco("AB1");
        System.out.println("Eliminando arco con etiqueta 'AB1': " + obtenido + " | Esperado: " + esperado);
        System.out.println("Grafo tras eliminar arco AB1: " + g);

        // Test: Modificar arco
        esperado = true;
        obtenido = g.modificarArco("AC1", "AC2");
        System.out.println("Modificando arco 'AC1' a 'AC2': " + obtenido + " | Esperado: " + esperado);
        System.out.println("Grafo tras modificar arco AC1->AC2: " + g);

        // Test: Eliminar vértice
        esperado = true;
        obtenido = g.eliminarVertice("C");
        System.out.println("Eliminando vértice C: " + obtenido + " | Esperado: " + esperado);
        System.out.println("Grafo tras eliminar vértice C: " + g);

        // Test: Modificar vértice
        esperado = true;
        obtenido = g.modificarVertice("D", "E");
        System.out.println("Modificando vértice D a E: " + obtenido + " | Esperado: " + esperado);
        System.out.println("Grafo tras modificar vértice D->E: " + g);

        // Test: Camino más corto y más largo
        Lista corto = g.caminoMasCorto("A", "E");
        Lista largo = g.caminoMasLargo("A", "E");
        System.out.println("Camino más corto de A a E: " + corto + " | Esperado: [A, E] o similar");
        System.out.println("Camino más largo de A a E: " + largo + " | Esperado: [A, ..., E]");

        // Test: Existe camino
        esperado = true;
        obtenido = g.existeCamino("A", "E");
        System.out.println("¿Existe camino de A a E?: " + obtenido + " | Esperado: " + esperado);
        esperado = false;
        obtenido = g.existeCamino("B", "A");
        System.out.println("¿Existe camino de B a A?: " + obtenido + " | Esperado: " + esperado);

        // Test: Listar en profundidad y anchura
        Lista profundidad = g.listarEnProfundidad();
        Lista anchura = g.listarEnAnchura();
        System.out.println("Listar en profundidad: " + profundidad);
        System.out.println("Listar en anchura: " + anchura);

        // Test: Listar vértice y arcos
        Lista verticeYArcos = g.listarVerticeYArcos("E");
        System.out.println("Listar vértice y arcos para E: " + verticeYArcos.toStringSecundario()
                + " | Esperado: lista de arcos de E");

        // Test: Obtener todos los caminos
        Lista caminos = g.obtenerTodosLosCaminos("A", "E");
        System.out.println("Todos los caminos de A a E:");
        for (int i = 1; i <= caminos.longitud(); i++) {
            System.out.println("Camino " + i + ": " + caminos.recuperar(i) + " | Esperado: camino válido de A a E");
        }

        // Test: Eliminar vértice inexistente
        esperado = false;
        obtenido = g.eliminarVertice("Z");
        System.out.println("Eliminando vértice inexistente Z: " + obtenido + " | Esperado: " + esperado);

        // Test: Grafo vacío tras eliminar todos los vértices
        g.eliminarVertice("A");
        g.eliminarVertice("B");
        g.eliminarVertice("E");
        esperado = true;
        obtenido = g.vacio();
        System.out.println("¿Grafo vacío tras eliminar todos?: " + obtenido + " | Esperado: " + esperado);
    }
}