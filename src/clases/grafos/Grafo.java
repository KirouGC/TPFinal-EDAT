package clases.grafos;

import clases.lineales.dinamicas.Cola;
import clases.lineales.dinamicas.Lista;

public class Grafo {

    // Variables
    private NodoVert inicio;

    // Constructor
    public Grafo() {
        inicio = null;
    }

    // Propias
    public boolean insertarVertice(Object nuevoVertice) {
        boolean exito = false;
        NodoVert aux = this.ubicarVertice(nuevoVertice);
        if (aux == null) {
            this.inicio = new NodoVert(nuevoVertice, this.inicio);
            exito = true;
        }
        return exito;
    }

    private NodoVert ubicarVertice(Object buscado) {
        NodoVert aux = this.inicio;
        while (aux != null && !aux.getElem().equals(buscado)) {
            aux = aux.getSigVertice();
        }
        return aux;
    }

    public boolean insertarArco(Object desde, Object hasta, Object etiqueta) {
        boolean logrado = false;
        NodoVert auxDesde = this.ubicarVertice(desde);
        NodoVert auxHasta = this.ubicarVertice(hasta);

        if (auxDesde != null && auxHasta != null) {
            if (auxDesde.getPrimerAdy() != null) {
                NodoAdy ultimo = this.encontrarUltimoAdyacente(auxDesde.getPrimerAdy(), etiqueta);
                if (ultimo != null) {
                    ultimo.setSigAdyacente(new NodoAdy(auxHasta, etiqueta));
                    logrado = true;
                }
            } else {
                auxDesde.setPrimerAdy(new NodoAdy(auxHasta, etiqueta));
                logrado = true;
            }
        }
        return logrado;

    }

    private NodoAdy encontrarUltimoAdyacente(NodoAdy primero, Object etiqueta) {
        NodoAdy ultimo = null;
        if (primero != null) {
            ultimo = primero;
            while (ultimo.getSigAdyacente() != null && !ultimo.getEtiqueta().equals(etiqueta)) {
                ultimo = ultimo.getSigAdyacente();
            }
            if (ultimo.getEtiqueta().equals(etiqueta)) { // Si se descomenta, NO PERMITE ETIQUETAS DE ARCOS REPETIDOS
                ultimo = null;
            } 
        }
        return ultimo;
    }

    public boolean existeVertice(Object elem) {
        boolean existe = false;
        NodoVert aux = this.inicio;

        if (elem != null) {
            while (aux != null && !existe) {
                if (aux.getElem().equals(elem)) {
                    existe = true;
                } else {
                    aux = aux.getSigVertice();
                }

            }
        }
        return existe;
    }

    public boolean eliminarVertice(Object buscado) {
        boolean exito = false;
        if (inicio != null) {
            if (inicio.getElem().equals(buscado)) {
                this.inicio = inicio.getSigVertice();
                if (inicio != null) {
                    NodoVert aux = this.inicio;
                    while (aux != null) {
                        this.eliminarArcos(aux.getPrimerAdy(), buscado);
                        if (aux.getPrimerAdy() != null && aux.getPrimerAdy().getVertice().getElem().equals(buscado)) {
                            aux.setPrimerAdy(aux.getPrimerAdy().getSigAdyacente());
                        }
                        aux = aux.getSigVertice();
                    }
                }
                exito = true;
            } else {
                NodoVert aux = this.inicio;
                NodoVert auxSiguiente = aux.getSigVertice();
                while (aux != null) {
                    if (auxSiguiente != null && auxSiguiente.getElem().equals(buscado)) {
                        aux.setSigVertice(auxSiguiente.getSigVertice());
                    }
                    this.eliminarArcos(aux.getPrimerAdy(), buscado);
                    if (aux.getPrimerAdy() != null && aux.getPrimerAdy().getVertice().getElem().equals(buscado)) {
                        aux.setPrimerAdy(aux.getPrimerAdy().getSigAdyacente());
                    }
                    aux = aux.getSigVertice();
                    if (aux != null) {
                        auxSiguiente = aux.getSigVertice();
                    }
                }
                exito = true;
            }
        }
        return exito;
    }

    private void eliminarArcos(NodoAdy inicio, Object buscado) {
        if (inicio != null) {
            NodoAdy aux = inicio;
            NodoAdy auxSiguiente = inicio.getSigAdyacente();
            while (aux != null && auxSiguiente != null) {
                if (auxSiguiente.getVertice().getElem().equals(buscado)) {
                    aux.setSigAdyacente(auxSiguiente.getSigAdyacente());
                }
                aux = aux.getSigAdyacente();
                if (aux != null) {
                    auxSiguiente = aux.getSigAdyacente();
                }
            }
        }
    }

    public boolean eliminarArco(Object etiqueta) {
        boolean fueEliminado = false;
        NodoVert actual = this.inicio;
        while (!fueEliminado && actual != null) {
            fueEliminado = eliminarArcoAux(actual, etiqueta);
            actual = actual.getSigVertice();
        }
        return fueEliminado;
    }

    public boolean modificarArco(Object etiquetaABuscar, Object nuevoEtiqueta) {
        boolean exito = false;
        NodoVert actual = this.inicio;
        while (actual != null && !exito) {
            NodoAdy adyacente = actual.getPrimerAdy();
            while (adyacente != null && !exito) {
                if (adyacente.getEtiqueta().equals(etiquetaABuscar)) {
                    adyacente.setEtiqueta(nuevoEtiqueta);
                    exito = true;
                }
                adyacente = adyacente.getSigAdyacente();
            }
            actual = actual.getSigVertice();
        }
        return exito;
    }

    public boolean existeCamino(Object origen, Object destino) {
        NodoVert vertOrigen = ubicarVertice(origen);
        NodoVert vertDestino = ubicarVertice(destino);
        boolean existe = false;
        if (vertOrigen != null && vertDestino != null) {
            existe = existeCaminoAux(vertOrigen, destino, new Lista());
        }
        return existe;
    }

    private boolean existeCaminoAux(NodoVert actual, Object destino, Lista visitados) {
        boolean encontrado = false;
        if (actual != null && !pertenece(visitados, actual.getElem())) {
            if (actual.getElem().equals(destino)) {
                encontrado = true;
            } else {
                visitados.insertar(actual.getElem(), visitados.longitud() + 1);
                NodoAdy ady = actual.getPrimerAdy();
                while (ady != null && !encontrado) {
                    encontrado = existeCaminoAux(ady.getVertice(), destino, visitados);
                    ady = ady.getSigAdyacente();
                }
            }
        }
        return encontrado;
    }

    public Lista caminoMasLargo(Object origen, Object destino) {
        Lista caminoActual = new Lista();
        Lista caminoMax = new Lista();
        NodoVert vertOrigen = ubicarVertice(origen);
        NodoVert vertDestino = ubicarVertice(destino);
        if (vertOrigen != null && vertDestino != null) {
            caminoMasLargoAux(vertOrigen, destino, caminoActual, caminoMax, new Lista());
        }
        return caminoMax;
    }

    private void caminoMasLargoAux(NodoVert actual, Object destino, Lista camino, Lista caminoMax, Lista visitados) {
        if (!pertenece(visitados, actual.getElem())) {
            camino.insertar(actual.getElem(), camino.longitud() + 1);
            visitados.insertar(actual.getElem(), visitados.longitud() + 1);

            if (actual.getElem().equals(destino)) {
                if (camino.longitud() > caminoMax.longitud()) {
                    copiarLista(camino, caminoMax);
                }
            } else {
                NodoAdy ady = actual.getPrimerAdy();
                while (ady != null) {
                    caminoMasLargoAux(ady.getVertice(), destino, camino, caminoMax, visitados);
                    ady = ady.getSigAdyacente();
                }
            }

            camino.eliminar(camino.longitud());
            visitados.eliminar(visitados.longitud());
        }
    }

    private boolean pertenece(Lista lista, Object elem) {
        return lista.localizar(elem) != -1;
    }

    public Lista caminoMasCorto(Object origen, Object destino) {
        Lista camino = new Lista();
        NodoVert vertOrigen = ubicarVertice(origen);
        NodoVert vertDestino = ubicarVertice(destino);
        if (vertOrigen != null && vertDestino != null) {
            Lista visitados = new Lista();
            Lista mejorCamino = new Lista();
            caminoMasCortoAux(vertOrigen, destino, visitados, mejorCamino);
            return mejorCamino;
        }
        return camino;
    }

    private void caminoMasCortoAux(NodoVert actual, Object destino, Lista visitados, Lista mejorCamino) {
        visitados.insertar(actual.getElem(), visitados.longitud() + 1);
        
        //Si el nodo actual es el destino
        if (actual.getElem().equals(destino)) {
            //Verificamos si el camino encontrado es el mejor
            if (mejorCamino.longitud() == 0 || visitados.longitud() < mejorCamino.longitud()) {
                //Si lo es, copiamos la lista de visitados al mejor camino
                copiarLista(visitados, mejorCamino);
            }
        } else if(mejorCamino.longitud() == 0 || visitados.longitud() < mejorCamino.longitud()){ //Si no es el destino, seguimos buscando (siempre y cuando sea útil)
            NodoAdy ady = actual.getPrimerAdy();
            while (ady != null) {
                if (!pertenece(visitados, ady.getVertice().getElem())) {
                    caminoMasCortoAux(ady.getVertice(), destino, visitados, mejorCamino);
                }
                ady = ady.getSigAdyacente();
            }
        }

        visitados.eliminar(visitados.longitud());
    }


    public Lista listarEnProfundidad() {
        Lista visitados = new Lista();
        profundidadDesde(inicio, visitados);
        return visitados;
    }

    private void profundidadDesde(NodoVert nodo, Lista visitados) {
        if (nodo != null && !pertenece(visitados, nodo.getElem())) {
            visitados.insertar(nodo.getElem(), visitados.longitud() + 1);
            NodoAdy ady = nodo.getPrimerAdy();
            while (ady != null) {
                profundidadDesde(ady.getVertice(), visitados);
                ady = ady.getSigAdyacente();
            }
        }
    }

    public Lista listarEnAnchura() {
        Lista visitados = new Lista();
        Cola q = new Cola();
        if (inicio != null) {
            q.poner(inicio);
            while (!q.esVacia()) {
                NodoVert nodo = (NodoVert) q.obtenerFrente();
                q.sacar();
                if (!pertenece(visitados, nodo.getElem())) {
                    visitados.insertar(nodo.getElem(), visitados.longitud() + 1);
                    NodoAdy ady = nodo.getPrimerAdy();
                    while (ady != null) {
                        q.poner(ady.getVertice());
                        ady = ady.getSigAdyacente();
                    }
                }
            }
        }
        return visitados;
    }

    private void copiarLista(Lista origen, Lista destino) {
        destino.vaciar();
        for (int i = 1; i <= origen.longitud(); i++) {
            destino.insertar(origen.recuperar(i), i);
        }
    }

    private boolean eliminarArcoAux(NodoVert nodo, Object etiqueta) {
        boolean logrado = false;
        if (nodo != null) {
            NodoAdy arcoActual = nodo.getPrimerAdy();
            NodoAdy arcoAnterior = nodo.getPrimerAdy();
            while (arcoActual != null && !logrado) {
                if (arcoActual.getEtiqueta().equals(etiqueta) && arcoActual == nodo.getPrimerAdy()) {
                    nodo.setPrimerAdy(arcoActual.getSigAdyacente());
                    logrado = true;
                } else if (arcoActual.getEtiqueta().equals(etiqueta) && arcoActual != nodo.getPrimerAdy()) {
                    arcoAnterior.setSigAdyacente(arcoActual.getSigAdyacente());
                    logrado = true;
                }
                arcoAnterior = arcoActual;
                arcoActual = arcoActual.getSigAdyacente();
            }
        }
        return logrado;
    }

    public boolean existeArco(Object desde, Object hasta) {
        boolean existe = false;
        if (this.inicio != null) {
            NodoVert aux = this.inicio;
            while (aux != null && !existe) {
                if (aux.getElem().equals(desde)) {
                    existe = true;
                } else {
                    aux = aux.getSigVertice();
                }
            }
            if (existe) {
                existe = recorrerArcosAux(aux, hasta);
            }
        }
        return existe;
    }

    //Para verificar si existe un arco con una etiqueta específica
    public boolean existeArco(Object etiqueta) {
        boolean encontrado = false;
        NodoVert aux = this.inicio;
        while(aux != null && !encontrado){
            NodoAdy adyacente = aux.getPrimerAdy();
            while(adyacente != null && !encontrado){
                if(etiqueta.equals(adyacente.getEtiqueta())){
                    encontrado = true;
                } else {
                    adyacente = adyacente.getSigAdyacente();
                }
            }
            aux = aux.getSigVertice();
        }
        return encontrado;
    }


    private boolean recorrerArcosAux(NodoVert vertice, Object buscado) {
        boolean encontrado = false;
        if (vertice != null) {
            NodoAdy aux = vertice.getPrimerAdy();
            while (aux != null && !encontrado) {
                if (aux.getVertice().getElem().equals(buscado)) {
                    encontrado = true;
                }
                aux = aux.getSigAdyacente();
            }
        }
        return encontrado;
    }

    public boolean vacio() {
        return inicio == null;
    }

    public String toString() {

        String string = "";
        NodoVert verticeAux = this.inicio;
        NodoAdy nodoAdyacente = null;

        while (verticeAux != null) {
            string = string + "\nVertice: " + verticeAux.getElem() + " [";
            nodoAdyacente = verticeAux.getPrimerAdy();
            while (nodoAdyacente != null) {
                string += nodoAdyacente.getVertice().getElem() + "," + nodoAdyacente.getEtiqueta() + ";";
                nodoAdyacente = nodoAdyacente.getSigAdyacente();
            }
            string += "]";
            verticeAux = verticeAux.getSigVertice();
        }

        return string;

    }

    public Lista caminoMasCortoPorEtiquetaMinima(Object desde, Object hasta) {
        Lista out = new Lista();

        return out;
    }

    public Lista obtenerTodosLosCaminos(Object origen, Object destino) {
        Lista caminos = new Lista();
        NodoVert nodoOrigen = ubicarVertice(origen);
        NodoVert nodoDestino = ubicarVertice(destino);

        if (nodoOrigen != null && nodoDestino != null) {
            Lista caminoActual = new Lista();
            Lista visitados = new Lista(); // reemplazo de HashSet
            obtenerTodosLosCaminosAux(nodoOrigen, destino, caminoActual, caminos, visitados);
        }

        return caminos;
    }

    private void obtenerTodosLosCaminosAux(NodoVert actual, Object destino, Lista caminoActual, Lista caminos,
            Lista visitados) {
        visitados.insertar(actual.getElem(), visitados.longitud() + 1);
        caminoActual.insertar(actual.getElem(), caminoActual.longitud() + 1);

        if (actual.getElem().equals(destino)) {
            Lista copia = copiarLista(caminoActual);
            caminos.insertar(copia, caminos.longitud() + 1);
        } else {
            NodoAdy ady = actual.getPrimerAdy();
            while (ady != null) {
                NodoVert vecino = ady.getVertice();
                if (!contiene(visitados, vecino.getElem())) {
                    obtenerTodosLosCaminosAux(vecino, destino, caminoActual, caminos, visitados);
                }
                ady = ady.getSigAdyacente();
            }
        }

        visitados.eliminar(visitados.longitud());
        caminoActual.eliminar(caminoActual.longitud());
    }

    private Lista copiarLista(Lista original) {
        Lista copia = new Lista();
        for (int i = 1; i <= original.longitud(); i++) {
            Object elem = original.recuperar(i);
            copia.insertar(elem, i);
        }
        return copia;
    }

    private boolean contiene(Lista lista, Object elem) {
        boolean encontrado = false;
        int i = 1;
        while (i <= lista.longitud() && !encontrado) {
            if (lista.recuperar(i).equals(elem)) {
                encontrado = true;
            } else {
                i++;
            }
        }
        return encontrado;
    }

    public boolean modificarVertice(Object aCambiar, Object nuevoDato) {
        boolean exito = false;
        NodoVert aux = this.ubicarVertice(aCambiar);
        if (aux != null) {
            aux.setElem(nuevoDato);
            exito = true;
        }
        return exito;
    }
    
    public Lista listarVerticeYArcos(Object elem){
        int cont = 1;
        Lista listaArcos = new Lista ();
        NodoVert aux = this.inicio;
        NodoAdy auxAdy = this.inicio.getPrimerAdy();
        while(aux != null){
            auxAdy = aux.getPrimerAdy();
            while(auxAdy != null){
                if(auxAdy.getVertice().getElem().equals(elem)){
                    Object [] destinos = new Object[2];
                    destinos[1] = auxAdy.getVertice().getElem();
                    destinos[0] = aux.getElem();
                    listaArcos.insertar(destinos, cont);
                    cont++;
                } else if(aux.getElem().equals(elem)){
                    Object [] destinos = new Object[2];
                    destinos[0] = aux.getElem();
                    destinos[1] = auxAdy.getVertice().getElem();
                    listaArcos.insertar(destinos, cont);
                    cont++;
                }
                auxAdy = auxAdy.getSigAdyacente();
            }
            aux = aux.getSigVertice();
        }
    return listaArcos;
    }
}
