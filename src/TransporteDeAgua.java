
import clases.especifico.DiccionarioAVL;
import clases.grafos.Grafo;
import clases.lineales.dinamicas.Cola;
import clases.lineales.dinamicas.Lista;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import main.Ciudad;
import main.Dom;
import main.Tuberia;

public class TransporteDeAgua {

    public static void main(String[] args) {
        // Inicialización de scanner y variables principales
        Scanner sc = new Scanner(System.in);
        String accion = "1";
        String CiudadArchivoEntrada = System.getProperty("user.dir") + "\\textos\\ciudades.txt";
        String TuberiaArchivoEntrada = System.getProperty("user.dir") + "\\textos\\tuberias.txt";
        String linea = null;

        try {
            // Lectura de archivos y estructuras principales
            FileReader lectorCiudad = new FileReader(CiudadArchivoEntrada);
            BufferedReader bufferLecCiudad = new BufferedReader(lectorCiudad);
            FileReader lectorTuberia = new FileReader(TuberiaArchivoEntrada);
            BufferedReader bufferLecTuberia = new BufferedReader(lectorTuberia);

            int[] numNomen = {3000};
            Cola nomenclaturasLibres = new Cola();
            Grafo mapa = new Grafo();
            DiccionarioAVL arbolCiudades = new DiccionarioAVL();
            HashMap<Dom, Tuberia> hashTuberias = new HashMap<>();

            // Carga de datos de ciudades y tuberías desde archivos
            while (((linea = bufferLecCiudad.readLine()) != null)) {
                cargarCiudad(linea, mapa, arbolCiudades, numNomen);
            }
            while (((linea = bufferLecTuberia.readLine()) != null)) {
                cargarTuberia(linea, mapa, hashTuberias, arbolCiudades);
            }

            // TESTING
            // Lista listaTest = arbolCiudades.listarRango("OOOO", "TTTT");
            // for (int i = 0; i < listaTest.longitud(); i++) {
            // System.out.println(((Ciudad) listaTest.recuperar(i+1)).getNombre());
            // }
            // System.out.println(arbolCiudades.eliminar("RIO GALLEGOS"));
            // FIN TESTING
            // Bucle principal del menú
            boolean volverMenu = false;
            do {
                menu();
                System.out.println("Opción elegida:");
                accion = sc.nextLine();
                switch (accion) {
                    case "1":
                        menuCiudades(mapa, arbolCiudades, hashTuberias, numNomen, nomenclaturasLibres);
                        break;
                    case "2":
                        menuTuberias(arbolCiudades, mapa, hashTuberias);
                        break;
                    case "3":
                        agregarHabitantesAnioCiudad(arbolCiudades);
                        break;
                    case "4":
                        menuConsultarCiudad(arbolCiudades);
                        break;
                    case "5":
                        menuConsultarTuberia(arbolCiudades, mapa, hashTuberias);
                        break;
                    case "6":
                        listarCiudadesConsumoAgua(arbolCiudades);
                        break;
                    case "7":
                        mostrarSistema(mapa, hashTuberias, arbolCiudades);
                        break;
                    case "0":
                        volverMenu = true;
                        System.out.println("FINALIZANDO EJECUCION....");
                        break;
                    default:
                        System.out.println("Error: opción incorrecta. Por favor elija de nuevo");
                }
            } while (!volverMenu);

            bufferLecCiudad.close();
            bufferLecTuberia.close();
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage() + "\nSignifica que el archivo del que queriamos leer no existe.");
        } catch (IOException ex) {
            System.err.println("Error leyendo o escribiendo en algun archivo.");
        }
    }

    // Carga una ciudad desde una línea del archivo y la agrega a las estructuras
    public static void cargarCiudad(String linea, Grafo mapa, DiccionarioAVL arbolCiudades, int[] cantNomenclaturas) {
        String[] datos = linea.split(";");
        Ciudad nuevaCiudad = new Ciudad(datos[0], datos[1], Double.parseDouble(datos[2]), Double.parseDouble(datos[3]));
        mapa.insertarVertice(datos[1]);
        arbolCiudades.insertar(nuevaCiudad.getNombre(), nuevaCiudad);
        cantNomenclaturas[0]++;
        String rutaHabitantes = System.getProperty("user.dir") + "\\textos\\datosHabitantes\\"
                + nuevaCiudad.getNombre().toUpperCase() + ".txt";
        cargarHabitantesDesdeArchivo(rutaHabitantes, nuevaCiudad);
    }

    // Carga los habitantes de una ciudad desde un archivo
    public static void cargarHabitantesDesdeArchivo(String rutaArchivo, Ciudad ciudadX) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                ciudadX.insertarDatosAnio(linea);
            }
        } catch (IOException e) {
            System.out.println("Error cargando habitantes");
        }
    }

    // Carga una tubería desde una línea del archivo y la agrega a las estructuras
    public static void cargarTuberia(String linea, Grafo mapa, HashMap<Dom, Tuberia> hashTuberia,
            DiccionarioAVL arbolCiudades) {
        String[] partes = linea.split(";");
        Tuberia nuevaTuberia = new Tuberia(partes[0], Integer.parseInt(partes[1]), Integer.parseInt(partes[2]),
                Integer.parseInt(partes[3]), partes[4]);
        partes = nuevaTuberia.getNomenclatura().split("-");
        Dom nuevoDom = new Dom(partes[0], partes[1]);
        hashTuberia.put(nuevoDom, nuevaTuberia);
        mapa.insertarArco(partes[0], partes[1], nuevaTuberia.getCaudalMax());
    }

    // Muestra el menú principal
    public static void menu() {
        System.out.println("----------------------------------");
        System.out.println("***MENU EPICO DEL TP DE EDAT 2025***");
        System.out.println("Opciones:");
        System.out.println("1. Altas, bajas y modificaciones de ciudades");
        System.out.println("2. Altas, bajas y modificaciones de tuberias");
        System.out.println("3. Altas de transmisión de agua en un mes dado para una ciudad dada");
        System.out.println("4. Consultas sobre las ciudades");
        System.out.println("5. Consultas sobre transporte de agua");
        System.out.println("6. Listar ciudades por consumo de agua anual de mayor a menor");
        System.out.println("7. Mostrar sistema");
        System.out.println("0. Salir del sistema");
        System.out.println("----------------------------------");
    }

    // Menú para altas, bajas y modificaciones de ciudades
    public static void menuCiudades(Grafo mapa, DiccionarioAVL arbol, HashMap<Dom, Tuberia> hashTuberias,
            int[] cantNomenclaturas, Cola nomenclaturasLibres) {
        Scanner sc = new Scanner(System.in);
        String eleccion = "-1";
        do {
            System.out.println("----------------------------------");
            System.out.println("ALTAS, BAJAS Y MODIFICACIONES DE CIUDADES");
            System.out.println("Opciones:");
            System.out.println("1. Dar de alta una ciudad");
            System.out.println("2. Dar de baja una ciudad");
            System.out.println("3. Modificar una ciudad");
            System.out.println("4. Volver al menú principal");
            System.out.println("----------------------------------");
            System.out.println("Opción elegida:");
            eleccion = sc.nextLine();
            switch (eleccion) {
                case "1":
                    if (cantNomenclaturas[0] >= 4000 && nomenclaturasLibres.esVacia()) {
                        System.out.println(
                                "Error: No se pueden agregar más ciudades, se ha alcanzado el límite de nomenclaturas.");
                    } else {
                        agregarCiudad(mapa, arbol, cantNomenclaturas, nomenclaturasLibres);
                    }
                    break;
                case "2":
                    eliminarCiudad(arbol, mapa, cantNomenclaturas, nomenclaturasLibres, hashTuberias);
                    break;
                case "3":
                    modificarCiudad(arbol, mapa, hashTuberias);
                    break;
                case "4":
                    System.out.println("Volviendo al menú principal");
                    break;
                default:
                    System.out.println("Error: opción incorrecta. Por favor elija de nuevo");
            }
        } while (!eleccion.equals("4"));
    }

    public static void agregarCiudad(Grafo mapa, DiccionarioAVL arbol, int[] cantNomenclaturas,
            Cola nomenclaturasLibres) {
        Scanner sc = new Scanner(System.in);
        String nombre = "";
        String nomenclatura = "";
        String superficie = "";
        String promedio = "";
        String habitantes = "";
        String aux = "";
        boolean valido = false;

        System.out.println("----------------------------------");
        System.out.println("AGREGAR UNA NUEVA CIUDAD");
        System.out.print("Ingrese el nombre de la nueva ciudad: ");
        nombre = (sc.nextLine()).toUpperCase();
        while (!verificarValidezNombreCiudad(nombre, arbol)) {
            System.out.println("Error: el nombre ingresado es inválido o ya existe. Ingrese un nombre válido:");
            nombre = (sc.nextLine()).toUpperCase();
        }

        nomenclatura = crearNomenclatura(nombre, arbol, cantNomenclaturas, nomenclaturasLibres);
        System.out.println("La nomenclatura de la ciudad será: " + nomenclatura);
        System.out.print("Ingrese la superficie de la nueva ciudad (Formato x.x): ");
        superficie = sc.nextLine();
        while (!esDouble(superficie)) {
            System.out.println("Error: formato incorrecto. Ingrese la superficie de la nueva ciudad (Formato x.x): ");
            superficie = sc.nextLine();
        }
        System.out.print("Ingrese el promedio personal de consumo diario (Formato x.x): ");
        promedio = sc.nextLine();
        while (!esDouble(promedio)) {
            System.out.println(
                    "Error: formato incorrecto. Ingrese el promedio personal de consumo diario (Formato x.x): ");
            promedio = sc.nextLine();
        }

        // Carga de habitantes por mes
        for (int i = 1; i <= 12; i++) {
            valido = false;
            do {
                System.out.println("Ingrese la cantidad de habitantes del mes " + convertirIntAMes(i) + ": ");
                aux = sc.nextLine();
                valido = esInt(aux);
                if (valido) {
                    if (i < 11) {
                        habitantes += aux + ";";
                    } else {
                        habitantes += aux;
                    }
                } else {
                    System.out.println("Error: formato incorrecto.");
                }
            } while (!valido);
        }
        // Se crea y agrega la ciudad a las estructuras
        Ciudad nuevaCiudad = new Ciudad(nombre, nomenclatura, Double.parseDouble(superficie),
                Double.parseDouble(promedio));
        nuevaCiudad.insertarDatosAnio(habitantes);
        mapa.insertarVertice(nuevaCiudad.getNomenclatura());
        arbol.insertar(nombre, nuevaCiudad);
        System.out.println("La nueva ciudad se añadió con éxito.");
        System.out.println("----------------------------------");
    }

    // Verifica si el nombre de la ciudad es válido
    public static boolean verificarValidezNombreCiudad(String n, DiccionarioAVL arbol) {
        boolean valido = false;
        int i = 0;
        char act = ' ';
        if (n != null) {
            if (arbol.pertenece(n)) {
                valido = false;
            } else if (n.length() >= 2) {
                valido = true;
                while (i < n.length() && valido) {
                    act = n.charAt(i);
                    if (!(Character.isLetterOrDigit(act)) && !(act == ' ')) {
                        valido = false;
                    }
                    i++;
                }
            }
        }
        return valido;
    }

    // Crea la nomenclatura de una ciudad
    public static String crearNomenclatura(String n, DiccionarioAVL arbol, int[] cantNomenclaturas,
            Cola nomenclaturasLibres) {
        String nomenclatura = "";
        n = n.trim();
        int longi = (arbol.listarClaves()).longitud();
        if (n.contains(" ")) {
            nomenclatura = "" + n.charAt(0) + n.charAt((n.indexOf(' ') + 1));
            nomenclatura = nomenclatura.toUpperCase();
        } else {
            nomenclatura = "" + n.charAt(0) + n.charAt(1);
            nomenclatura = nomenclatura.toUpperCase();
        }
        if (nomenclaturasLibres.esVacia()) {
            cantNomenclaturas[0]++;
            nomenclatura = nomenclatura + (cantNomenclaturas[0]);
        } else {
            nomenclatura = nomenclatura + nomenclaturasLibres.obtenerFrente();
            nomenclaturasLibres.sacar();
        }
        return nomenclatura;
    }

    // Elimina una ciudad del sistema
    public static void eliminarCiudad(DiccionarioAVL arbol, Grafo mapa, int[] cantNomenclaturas,
            Cola nomenclaturasLibres, HashMap<Dom, Tuberia> hashTuberias) {
        Scanner sc = new Scanner(System.in);
        System.out.println("----------------------------------");
        System.out.println("ELIMINAR UNA NUEVA CIUDAD");
        System.out.println("Ingrese el nombre de la ciudad que desea eliminar, o presione Enter para salir");
        String eleccion = sc.nextLine().toUpperCase();
        if (eleccion != "") {
            Ciudad ciudadAEliminar = (Ciudad) arbol.obtenerDato(eleccion);
            if (ciudadAEliminar == null) {
                ciudadAEliminar = ciudadNoEncontrada(arbol, ciudadAEliminar, sc);
            }
            if (mapa.existeVertice(ciudadAEliminar.getNomenclatura())) {
                Lista listaX = mapa.listarVerticeYArcos((String) ciudadAEliminar.getNomenclatura());
                //Se eliminan las tuberias con algun extremo en esta ciudad
                eliminarTuberiasDeHash(hashTuberias, listaX);
                System.out.println("Se eliminó la ciudad " + ciudadAEliminar.getNombre() + " correctamente");
                int numNomen = Integer.parseInt(ciudadAEliminar.getNomenclatura().substring(2));
                nomenclaturasLibres.poner(numNomen);
                //Se elimina la ciudad del ArbolAVL
                arbol.eliminar(ciudadAEliminar.getNombre());
            } else {
                System.out.println("ERROR: Nombre encontrado, pero ciudad NO encontrada, cargue nuevamente las ciudades");
            }
        }
        System.out.println("----------------------------------");
    }

    public static void eliminarTuberiasDeHash(HashMap<Dom, Tuberia> hashTuberias, Lista listaX) {
        // Este método se puede implementar si se desea eliminar la ciudad de un HashMap
        // de tuberías
        int longi = listaX.longitud();
        Object[] nombre;
        Dom dominioX = new Dom();
        for (int i = 1; i <= longi; i++) {
            nombre = (Object[]) listaX.recuperar(i);
            dominioX.setNom1((String) nombre[0]);
            dominioX.setNom2((String) nombre[1]);
            if (hashTuberias.containsKey(dominioX)) {
                hashTuberias.remove(dominioX);
                System.out.println("Se eliminó la tubería " + dominioX.getNom1() + "-" + dominioX.getNom2() + " correctamente");
            } else {
                System.out.println("No se encontró la tubería " + dominioX.getNom1() + "-" + dominioX.getNom2());
            }
        }
    }

    // Solicita al usuario un nombre de ciudad válido si no se encuentra
    public static Ciudad ciudadNoEncontrada(DiccionarioAVL arbol, Ciudad ciudad, Scanner sc) {
        String eleccion = null;
        do {
            System.out.println("Nombre no encontrado, ingrese un nombre correcto");
            eleccion = (sc.nextLine()).toUpperCase();
            ciudad = (Ciudad) arbol.obtenerDato(eleccion);
        } while (ciudad == null);
        return ciudad;
    }

    // Permite modificar los datos de una ciudad
    public static void modificarCiudad(DiccionarioAVL arbol, Grafo mapa, HashMap<Dom, Tuberia> hashTuberias) {
        Scanner sc = new Scanner(System.in);
        System.out.println("----------------------------------");
        System.out.println("MODIFICAR UNA NUEVA CIUDAD");
        System.out.println("Ingrese el nombre de la ciudad que desea modificar, o presione Enter para salir");
        String eleccion = (sc.nextLine()).toUpperCase();
        if (eleccion != "") {
            Ciudad ciudadX = (Ciudad) arbol.obtenerDato(eleccion);
            if (ciudadX == null) {
                ciudadX = ciudadNoEncontrada(arbol, ciudadX, sc);
            }
            switch (menuModificacionCiudades()) {
                case 1:
                    modificarNombreCiudad(ciudadX, arbol, mapa, hashTuberias);
                    break;
                case 2:
                    modificarSuperficieCiudad(ciudadX);
                    break;
                case 3:
                    modificarPromedioConsCiudad(ciudadX);
                    break;
                case 4:
                    modificarNombreCiudad(ciudadX, arbol, mapa, hashTuberias);
                    modificarSuperficieCiudad(ciudadX);
                    modificarPromedioConsCiudad(ciudadX);
                    break;
            }
        }
    }

    // Menú para elegir qué dato de la ciudad modificar
    public static int menuModificacionCiudades() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Opciones:");
        System.out.println("1. Modificar nombre");
        System.out.println("2. Modificar superficie");
        System.out.println("3. Modificar promedio de consumo por dia");
        System.out.println("4. Modificar todo");
        String eleccion = sc.nextLine();
        int eleccionInt = Integer.parseInt(eleccion);
        while (eleccionInt < 1 && eleccionInt > 5) {
            System.out.println("Ingrese una opcion valida");
            eleccion = sc.nextLine();
            eleccionInt = Integer.parseInt(eleccion);
        }
        return eleccionInt;
    }

    // Permite modificar el nombre y nomenclatura de una ciudad
    public static void modificarNombreCiudad(Ciudad ciudadX, DiccionarioAVL arbol, Grafo mapa,
            HashMap<Dom, Tuberia> hashTuberias) {
        Scanner sc = new Scanner(System.in);
        if (ciudadX != null) {
            System.out.println("Ingrese el nuevo nombre de la ciudad " + ciudadX.getNombre());
            String nombre = sc.nextLine().toUpperCase();
            boolean valido = verificarValidezNombreCiudad(nombre, arbol);
            while (!valido) {
                System.out.println("Error: Formato invalido o nombre repetido. Ingrese un nombre valido");
                nombre = (sc.nextLine()).toUpperCase();
                valido = verificarValidezNombreCiudad(nombre, arbol);
            }
            if (valido) {
                arbol.eliminar(ciudadX.getNombre());
                ciudadX.setNombre(nombre);
                arbol.insertar(nombre, ciudadX);
                String nomenclaturaNueva = modificarNomenclatura(nombre, arbol, ciudadX.getNomenclatura());
                Lista listaNomenclaturas = mapa.listarVerticeYArcos(ciudadX.getNomenclatura());
                encontrarYModificarTuberia(ciudadX.getNomenclatura(), nomenclaturaNueva, hashTuberias, listaNomenclaturas);
                mapa.modificarVertice(ciudadX.getNomenclatura(), nomenclaturaNueva);
                ciudadX.setNomenclatura(nomenclaturaNueva);
                System.out.println("El nombre (y nomenclatura) de la ciudad fue modificada correctamente");
                System.out.println("NUEVA NOMENCLATURA: " + nomenclaturaNueva);
            }
        }
    }

    public static String modificarNomenclatura(String n, DiccionarioAVL arbol, String nomenclaturaOriginal) {
        String nomenclatura = "";
        n = n.trim();
        int longi = (arbol.listarClaves()).longitud();
        if (n.contains(" ")) {
            nomenclatura = "" + n.charAt(0) + n.charAt((n.indexOf(' ') + 1));
            nomenclatura = nomenclatura.toUpperCase();
            nomenclatura = nomenclatura + nomenclaturaOriginal.substring(2);
        } else {
            nomenclatura = "" + n.charAt(0) + n.charAt(1);
            nomenclatura = nomenclatura.toUpperCase();
            nomenclatura = nomenclatura + nomenclaturaOriginal.substring(2);
            ;
        }
        return nomenclatura;
    }

    public static void encontrarYModificarTuberia(String nomenclaturaVieja, String nomenclaturaNueva, HashMap<Dom, Tuberia> hashTuberias, Lista listaX) {
        int longitud = listaX.longitud();
        Object[] nombre;
        Tuberia tuberiaX;
        String nom;

        for (int i = 1; i <= longitud; i++) {
            nombre = (Object[]) listaX.recuperar(i);
            Dom domOriginal;
            Dom domNuevo;
            if (nombre[0].equals(nomenclaturaVieja)) {
                nom = nomenclaturaNueva + "-" + nombre[1];
                domOriginal = new Dom(nomenclaturaVieja, (String) nombre[1]);
                tuberiaX = hashTuberias.get(domOriginal);
                if (tuberiaX != null) {
                    hashTuberias.remove(domOriginal);
                    tuberiaX.setNomenclatura(nom);
                    domNuevo = new Dom(nomenclaturaNueva, (String) nombre[1]);
                    hashTuberias.put(domNuevo, tuberiaX);
                } else {
                    System.out.println("No se encontró la tubería: " + domOriginal.getNom1() + "-" + domOriginal.getNom2());
                }
            } else if (nombre[1].equals(nomenclaturaVieja)) {
                nom = nombre[0] + "-" + nomenclaturaNueva;
                domOriginal = new Dom((String) nombre[0], nomenclaturaVieja);
                tuberiaX = hashTuberias.get(domOriginal);
                if (tuberiaX != null) {
                    hashTuberias.remove(domOriginal);
                    tuberiaX.setNomenclatura(nom);
                    domNuevo = new Dom((String) nombre[0], nomenclaturaNueva);
                    hashTuberias.put(domNuevo, tuberiaX);
                } else {
                    System.out.println("No se encontró la tubería: " + domOriginal.getNom1() + "-" + domOriginal.getNom2());
                }
            } else {
                System.out.println("No coincide ninguna nomenclatura vieja en la tubería: " + nombre[0] + "-" + nombre[1]);
            }
        }
    }

    // Permite modificar la superficie de una ciudad
    public static void modificarSuperficieCiudad(Ciudad ciudadX) {
        Scanner sc = new Scanner(System.in);
        if (ciudadX != null) {
            // Solicita la nueva superficie
            System.out.println("Ingrese la nueva superficie de la ciudad " + ciudadX.getNombre());
            String eleccion = sc.nextLine();
            // Si es válida, la actualiza
            if (esDouble(eleccion)) {
                ciudadX.setSuperficie(Double.parseDouble(eleccion));
            } else {
                System.out.println("Error: Formato Invalido");
            }
        }
    }

    // Permite modificar el promedio de consumo personal de una ciudad
    public static void modificarPromedioConsCiudad(Ciudad ciudadX) {
        Scanner sc = new Scanner(System.in);
        if (ciudadX != null) {
            System.out.println("Ingrese el nuevo promedio de consumo personal de la ciudad " + ciudadX.getNombre());
            String eleccion = sc.nextLine();
            if (esDouble(eleccion)) {
                ciudadX.setPromPersonalConsumidosPorDia(Double.parseDouble(eleccion));
            } else {
                System.out.println("Error: Formato Invalido");
            }
        }
    }

    // Verifica si un string puede convertirse a double
    public static boolean esDouble(String n) {
        try {
            Double.parseDouble(n);
            return true;
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
    }

    // Menú para altas, bajas y modificaciones de tuberías
    public static void menuTuberias(DiccionarioAVL arbol, Grafo mapa, HashMap<Dom, Tuberia> hashTuberias) {
        Scanner sc = new Scanner(System.in);
        String eleccion = "0";
        do {
            System.out.println("----------------------------------");
            System.out.println("ALTAS, BAJAS Y MODIFICACIONES DE TUBERIAS");
            System.out.println("Opciones:");
            System.out.println("1. Dar de alta una tuberia");
            System.out.println("2. Dar de baja una tuberia");
            System.out.println("3. Modificar una tuberia");
            System.out.println("4. Volver al menú principal");
            System.out.println("----------------------------------");
            System.out.print("Opción elegida:");
            eleccion = sc.nextLine();
            switch (eleccion) {
                case "1":
                    agregarTuberia(arbol, mapa, hashTuberias);
                    break;
                case "2":
                    eliminarTuberia(mapa, hashTuberias, arbol);
                    break;
                case "3":
                    modificarTuberia(mapa, hashTuberias, arbol);
                    break;
                case "4":
                    System.out.println("Volviendo al menú principal.");
                    break;
                default:
                    System.out.println("Error: opción incorrecta. Por favor elija de nuevo");
            }
        } while (!eleccion.equals("4"));
    }

    // Agrega una nueva tubería al sistema
    public static void agregarTuberia(DiccionarioAVL arbol, Grafo mapa, HashMap<Dom, Tuberia> hashTuberias) {
        Scanner sc = new Scanner(System.in);
        String desde, hasta, caudalMin, caudalMax, diametro, estado;
        boolean valido = false;
        String nomen = buscarNomenclaturaTuberia(arbol);
        String[] partes = nomen.split("-");
        Dom dominio = new Dom(partes[0], partes[1]);
        Tuberia tuberia = hashTuberias.get(dominio);
        desde = (partes[0]);
        hasta = (partes[1]);
        if (tuberia == null) {
            System.out.println("Ingrese el diametro: ");
            diametro = sc.nextLine();
            while (!esInt(diametro)) {
                System.out.println("Error: formato incorrecto. Ingrese el diametro: ");
                diametro = sc.nextLine();
            }
            System.out.println("Ingrese el caudal minimo: ");
            caudalMin = sc.nextLine();
            while (!esInt(caudalMin)) {
                System.out.println("Error: formato incorrecto. Ingrese el caudal minimo: ");
                caudalMin = sc.nextLine();
            }

            System.out.println("Ingrese el caudal máximo: ");
            caudalMax = sc.nextLine();
            while (!esInt(caudalMax)) {
                System.out.println("Error: formato incorrecto. Ingrese el caudal máximo: ");
                caudalMax = sc.nextLine();
            }

            if (!mapa.existeArco(Integer.parseInt(caudalMax))) {
                System.out.println("Ingrese el estado (ACTIVO / EN REPARACIÓN / EN DISEÑO / INACTIVO): ");
                estado = sc.nextLine().toUpperCase();
                while (!verificarEstado(estado)) {
                    System.out.println(
                            "Error: estado inválido. Ingrese el estado (ACTIVO / EN REPARACIÓN / EN DISEÑO / INACTIVO): ");
                    estado = sc.nextLine().toUpperCase();
                }
                // Se crea y agrega la tubería a las estructuras
                mapa.insertarArco(desde, hasta, caudalMax);
                Tuberia nuevaTuberia = new Tuberia(nomen, Integer.valueOf(caudalMin),
                        Integer.valueOf(caudalMax), Integer.valueOf(diametro), estado);
                System.out.println("DESDE: " + desde + " / HASTA: " + hasta);
                Dom auxDom = new Dom(desde, hasta);
                hashTuberias.put(auxDom, nuevaTuberia);
                System.out.println("La nueva tuberia se añadió con éxito.");
            } else {
                System.out.println("Error: Caudal Máximo ya existente");
            }
        } else {
            System.out.println("Error: Ya existe una tubería desde " + desde + " hasta " + hasta);
        }
    }

    public static boolean verificarEstado(String estado) {
        return estado.equals("ACTIVO") || estado.equals("EN REPARACION") || estado.equals("EN REPARACIÓN")
                || estado.equals("EN DISEÑO") || estado.equals("INACTIVO");
    }

    // Elimina una tubería del sistema
    public static void eliminarTuberia(Grafo mapa, HashMap<Dom, Tuberia> hashTuberias, DiccionarioAVL arbol) {
        Scanner sc = new Scanner(System.in);
        String nomen = buscarNomenclaturaTuberia(arbol);
        String[] partes = nomen.split("-");
        Dom dominio = new Dom(partes[0], partes[1]);
        Tuberia tuberia = hashTuberias.get(dominio);
        if (tuberia != null) {
            int caudal = tuberia.getCaudalMax();
            boolean hecho = mapa.eliminarArco(caudal);
            if (hecho) {
                System.out.print("La tuberia '" + nomen + "' fue eliminada del mapa ");
                Object aux = hashTuberias.remove(dominio);
                if (aux != null) {
                    System.out.println("y del hash con éxito.");
                } else {
                    System.out.println(
                            "con éxito. Sin embargo NO fue eliminada del hash ya que no estaba cargada en el  mismo.");
                }
            } else {
                System.out.println("Error: Tuberia encontrada pero contiene errores.");
            }
        } else {
            System.out.println("Error: La tuberia no existe. Saliendo...");
        }
    }

    // Permite modificar los datos de una tubería (estructura a completar)
    public static void modificarTuberia(Grafo mapa, HashMap<Dom, Tuberia> hashTuberias, DiccionarioAVL arbol) {
        Scanner sc = new Scanner(System.in);
        String accion = "";
        String nomen = buscarNomenclaturaTuberia(arbol);
        // Aquí debería buscarse la tubería y permitir modificar sus datos
        String[] partes = nomen.split("-");
        Dom dominio = new Dom(partes[0], partes[1]);
        Tuberia tuberia = hashTuberias.get(dominio);
        if (tuberia != null) {
            while (!accion.equals("6")) {
                System.out.println("Ingrese que dato quiere modificar según su índice: ");
                System.out.println("1. Caudal Mínimo");
                System.out.println("2. Caudal Máximo");
                System.out.println("3. Diametro");
                System.out.println("4. Estado");
                System.out.println("5. Modificar todo");
                System.out.println("6. Salir al menu principal");
                System.out.print("\nOpción elegida: ");
                accion = sc.nextLine();
                switch (accion) {
                    case "1":
                        modificarCaudalTuberia(tuberia, mapa, "min");
                        break;
                    case "2":
                        modificarCaudalTuberia(tuberia, mapa, "max");
                        break;
                    case "3":
                        modificarDiametroTuberia(tuberia, mapa);
                        break;
                    case "4":
                        modificarEstadoTuberia(tuberia, mapa);
                        break;
                    case "5":
                        modificarCaudalTuberia(tuberia, mapa, "min");
                        modificarCaudalTuberia(tuberia, mapa, "max");
                        modificarDiametroTuberia(tuberia, mapa);
                        modificarEstadoTuberia(tuberia, mapa);
                        break;
                    case "6":
                        System.out.println("Volviendo al menú principal...");
                        break;
                    default:
                        System.out.println("Error: opción incorrecta.");
                        break;
                }
            }
        } else {
            System.out.println("Error: la nomenclatura es incorrecta o la tuberia no existe.");
        }
    }

    public static String buscarNomenclaturaTuberia(DiccionarioAVL arbol) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese el nombre de la ciudad origen: ");
        String origen = sc.nextLine();
        while (arbol.obtenerDato(origen.toUpperCase()) == null) {
            System.out.println("Error: la ciudad origen no existe. Ingrese un nombre válido.");
            origen = sc.nextLine();
        }
        System.out.print("Ingrese el nombre de la ciudad destino: ");
        String destino = sc.nextLine();
        while (arbol.obtenerDato(destino.toUpperCase()) == null) {
            System.out.println("Error: la ciudad destino no existe. Ingrese un nombre válido.");
            destino = sc.nextLine();
        }
        String nomen = ((Ciudad) (arbol.obtenerDato(origen.toUpperCase()))).getNomenclatura() + "-"
                + ((Ciudad) (arbol.obtenerDato(destino.toUpperCase()))).getNomenclatura();
        return nomen;
    }

    public static void modificarCaudalTuberia(Tuberia tuberia, Grafo mapa, String tipo) {
        Scanner sc = new Scanner(System.in);
        String caudal;
        if (tipo.equals("min")) {
            System.out.println("Ingrese el nuevo caudal mínimo de la tubería " + tuberia.getNomenclatura());
            caudal = sc.nextLine();
            while (!esInt(caudal) || Integer.parseInt(caudal) > tuberia.getCaudalMax()) {
                System.out.println(
                        "Error: formato incorrecto o caudal mayor al máximo. Vuelva a ingresar el caudal mínimo:");
                caudal = sc.nextLine();
            }
            tuberia.setCaudalMin(Integer.parseInt(caudal));
            System.out.println("El caudal mínimo fue modificado correctamente.");

        } else if (tipo.equals("max")) {
            System.out.println("Ingrese el nuevo caudal máximo de la tubería " + tuberia.getNomenclatura());
            caudal = sc.nextLine();
            while (!esInt(caudal) || Integer.parseInt(caudal) < tuberia.getCaudalMin()
                    || mapa.existeArco(Integer.parseInt(caudal))) {
                System.out.println(
                        "Error: formato incorrecto, caudal menor al mínimo, o caudal máximo repetido. Vuelva a ingresar el caudal máximo:");
                caudal = sc.nextLine();

            }
            tuberia.setCaudalMax(Integer.parseInt(caudal));
            mapa.modificarArco(tuberia.getCaudalMax(), Integer.parseInt(caudal));
            System.out.println("El caudal máximo fue modificado correctamente.");
        }
    }

    public static void modificarDiametroTuberia(Tuberia tuberia, Grafo mapa) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el nuevo diámetro de la tubería " + tuberia.getNomenclatura());
        String diametro = sc.nextLine();
        while (!esInt(diametro) || Integer.parseInt(diametro) <= 0) {
            System.out.println("Error: formato incorrecto o numero negativo. Vuelva a ingresar el diámetro:");
            diametro = sc.nextLine();
        }
        tuberia.setDiametro(Integer.parseInt(diametro));
        System.out.println("El diámetro fue modificado correctamente.");
    }

    public static void modificarEstadoTuberia(Tuberia tuberia, Grafo mapa) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el nuevo estado de la tubería " + tuberia.getNomenclatura()
                + " | OPCIONES: ACTIVO - EN REPARACION - EN DISEÑO - INACTIVO");
        String estado = sc.nextLine().toUpperCase();
        while (!verificarEstado(estado)) {
            System.out.println(
                    "Error: estado inválido. Ingrese el estado (ACTIVO / EN REPARACIÓN / EN DISEÑO / INACTIVO): ");
            estado = sc.nextLine().toUpperCase();
        }
    }

    // Verifica si un string puede convertirse a int
    public static boolean esInt(String n) {
        boolean esInt = true;
        if (n != null && !n.isEmpty()) {
            int i = 0;
            while (i < n.length() && esInt) {
                if (!Character.isDigit(n.charAt(i))) {
                    esInt = false;
                }
                i++;
            }
        } else {
            esInt = false;
        }
        return esInt;
    }

    // Menú de consultas sobre ciudades: permite elegir entre consultar
    // habitantes/agua o ciudades por rango
    public static void menuConsultarCiudad(DiccionarioAVL arbol) {
        Scanner sc = new Scanner(System.in);

        String eleccion = "-1";
        do {
            // Muestra el menú de consultas sobre ciudades
            System.out.println("----------------------------------");
            System.out.println("CONSULTAS SOBRE CIUDADES");
            System.out.println("Opciones:");
            System.out.println("1. Cantidad de habitantes y volúmen de agua distribuida en cierto año y mes");
            System.out.println("2. Obtener todas las ciudades dentro de un rango determinado");
            System.out.println("3. Salir al menu principal");
            System.out.println("----------------------------------");
            eleccion = sc.nextLine();

            switch (eleccion) {
                case "1":
                    // Consulta de habitantes y volumen de agua por año/mes
                    consultarCiudadPrimeraOpcion(arbol);
                    break;
                case "2":
                    // Consulta de ciudades en un rango de nombres y volumen
                    consultarCiudadSegundaOpcion(arbol);
                    break;
                case "3":
                    // Salida al menú principal
                    System.out.println("Volviendo al menú principal");
                    break;
                default:
                    System.out.println("Error: opción incorrecta. Por favor elija de nuevo");
            }

        } while (!eleccion.equals("3"));
    }

    // Consulta la cantidad de habitantes y volumen de agua de una ciudad en un año
    // y mes específico
    public static void consultarCiudadPrimeraOpcion(DiccionarioAVL arbol) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el nombre de la ciudad que desea consultar");
        String eleccion = (sc.nextLine()).toUpperCase();
        Double promedio = 0.0;
        if (eleccion != "") {
            Ciudad ciudadX = (Ciudad) arbol.obtenerDato(eleccion);
            if (ciudadX == null) {
                // Si la ciudad no existe, solicita un nombre válido
                ciudadX = ciudadNoEncontrada(arbol, ciudadX, sc);
            }
            if (ciudadX.verificarAnio(1)) {
                // Solicita año y mes, valida y muestra los datos de consumo
                System.out.println(
                        "Ingrese el año del cual desea consultar el volumen de agua");
                eleccion = sc.nextLine();
                int anio = convertirAnio(eleccion);
                while (anio == -1 || !ciudadX.verificarAnio(anio)) {
                    System.out.println("Año invalido o no presente en el registro, ingrese nuevamente");
                    eleccion = sc.nextLine();
                    anio = convertirAnio(eleccion);
                }
                String anioString = eleccion;
                System.out.println("Ingrese el mes del cual desea consultar el volumen de agua: ");
                eleccion = sc.nextLine();
                int mes = convertirMesAInt(eleccion);
                while (mes == -1) {
                    System.out.println("Mes invalido, ingrese nuevamente");
                    eleccion = sc.nextLine();
                    mes = convertirMesAInt(eleccion);
                }
                String mesString = eleccion;
                promedio = ciudadX.calcularPromedioVolumenAnio(anio);
                if (promedio > 0) {
                    // Muestra resultados
                    System.out.println("Nombre de la ciudad: " + ciudadX.getNombre());
                    System.out.println("Volumen de agua consumidos en el año " + anioString + ": "
                            + promedio);
                    promedio = ciudadX.calcularPromedioVolumenMes(anio, mes);
                    System.out.println("Volumen de agua consumidos en el mes " + mesString.toUpperCase() + ": "
                            + promedio);
                } else {
                    System.out.println("No se encontraron datos validos en el año ingresado");
                }
            } else {
                System.out.println("ERROR: La ciudad ingresada no tiene habitantes registrados");
            }
        }
    }

    // Verifica si una cadena contiene solo números
    public static boolean verificarStringDeNumeros(String cadena) {
        boolean valido = false;
        if (cadena.length() > 0) {
            valido = true;
            for (int i = 0; i < cadena.length(); i++) {
                if (cadena.charAt(i) < 48 || cadena.charAt(i) > 57) {
                    valido = false;
                }
            }
        }
        return valido;
    }

    // Convierte un string de año a su índice interno (ej: 2025 -> 1)
    public static int convertirAnio(String anio) {
        int out = -1;
        if (anio.length() > 0) {
            if (verificarStringDeNumeros(anio)) {
                int anioEntero = Integer.parseInt(anio);
                int i = 1;
                do {
                    if ((2026 - i) == anioEntero) {
                        out = i;
                    }
                    i++;
                } while (out == -1);
            }
        }
        return out;
    }

    // Convierte el nombre de un mes a su número correspondiente
    public static int convertirMesAInt(String mes) {
        int out = -1;
        if (mes.length() > 0) {
            switch (mes.toUpperCase()) {
                case "ENERO":
                    out = 1;
                    break;
                case "FEBRERO":
                    out = 2;
                    break;
                case "MARZO":
                    out = 3;
                    break;
                case "ABRIL":
                    out = 4;
                    break;
                case "MAYO":
                    out = 5;
                    break;
                case "JUNIO":
                    out = 6;
                    break;
                case "JULIO":
                    out = 7;
                    break;
                case "AGOSTO":
                    out = 8;
                    break;
                case "SEPTIEMBRE":
                    out = 9;
                    break;
                case "OCTUBRE":
                    out = 10;
                    break;
                case "NOVIEMBRE":
                    out = 11;
                    break;
                case "DICIEMBRE":
                    out = 12;
                    break;
            }
        }
        return out;
    }

    // Convierte el int a su mes correspondiente
    public static String convertirIntAMes(int num) {
        String out = "ERROR";
        if (num >= 1 && num <= 12) {
            switch (num) {
                case 1:
                    out = "ENERO";
                    break;
                case 2:
                    out = "FEBRERO";
                    break;
                case 3:
                    out = "MARZO";
                    break;
                case 4:
                    out = "ABRIL";
                    break;
                case 5:
                    out = "MAYO";
                    break;
                case 6:
                    out = "JUNIO";
                    break;
                case 7:
                    out = "JULIO";
                    break;
                case 8:
                    out = "AGOSTO";
                    break;
                case 9:
                    out = "SEPTIEMBRE";
                    break;
                case 10:
                    out = "OCTUBRE";
                    break;
                case 11:
                    out = "NOVIEMBRE";
                    break;
                case 12:
                    out = "DICIEMBRE";
                    break;
            }
        }
        return out;
    }

    // Consulta todas las ciudades dentro de un rango de nombres y volumen de agua
    public static void consultarCiudadSegundaOpcion(DiccionarioAVL arbol) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el nombre minimo de las ciudades que desea consultar");
        String minNom = (sc.nextLine()).toUpperCase();
        int longitud = arbol.listarClaves().longitud();
        Object ultimaClave = arbol.listarClaves().recuperar(longitud);
        while (ultimaClave.equals(minNom) || arbol.obtenerDato(minNom) == null) {
            System.out.println("Error: Se insertó un nombre inválido o el maximo nombre registrado como minimo. Ingrese un nombre valido");
            minNom = (sc.nextLine()).toUpperCase();
        }
        // Solicita y valida nombre máximo
        System.out.println("Ingrese el nombre maximo de las ciudades que desea consultar");
        String maxNom = (sc.nextLine()).toUpperCase();
        boolean maxNomValido = false;
        if (arbol.obtenerDato(maxNom) == null || maxNom.compareTo(minNom) < 0) {
            while (!maxNomValido) {
                if (arbol.obtenerDato(maxNom) == null) {
                    System.out.println("Error: Nombre no encontrado, ingrese nuevamente");
                    maxNom = (sc.nextLine()).toUpperCase();
                } else if (maxNom.compareTo(minNom) < 0) {
                    System.out.println("Error: Ingrese un nombre maximo mayor al nombre minimo");
                    maxNom = (sc.nextLine()).toUpperCase();
                } else {
                    maxNomValido = true;
                }
            }
        }
        // Solicita y valida volumen mínimo y máximo
        System.out.println("Ingrese el volumen de agua minimo de las ciudades que desea consultar");
        String StringMinVol = sc.nextLine();
        Double minVol;
        while (!esDouble(StringMinVol)) {
            System.out.println("Error: Ingrese un volumen minimo valido");
            StringMinVol = sc.nextLine();
        }
        minVol = Double.parseDouble(StringMinVol);
        System.out.println("Ingrese el volumen de agua maximo de las ciudades que desea consultar");
        String StringMaxVol = sc.nextLine();
        Double maxVol = -1.0;
        while (maxVol == -1.0) {
            if (!esDouble(StringMaxVol)) {
                System.out.println("Error: Ingrese un volumen maximo valido");
                StringMaxVol = sc.nextLine();
            } else {
                maxVol = Double.parseDouble(StringMaxVol);
                if (maxVol < minVol) {
                    maxVol = -1.0;
                    StringMaxVol = "ERROR";
                }
            }
        }
        // Solicita año y mes
        System.out.println("Ingrese el año donde quiere revisar el registro");
        String StringAnio = sc.nextLine();
        int anio = convertirAnio(StringAnio);
        while (anio == -1) {
            System.out.println("Año invalido, ingrese nuevamente");
            StringAnio = sc.nextLine();
            anio = convertirAnio(StringAnio);
        }
        System.out.println("Ingrese el mes donde quiere revisar el registro");
        String StringMes = sc.nextLine();
        int mes = convertirMesAInt(StringMes);
        while (mes == -1) {
            System.out.println("Mes invalido, ingrese nuevamente");
            StringMes = sc.nextLine();
            mes = convertirMesAInt(StringMes);
        }
        // Obtiene ciudades en el rango de nombres y volumen
        Lista listaRango = obtenerCiudadesEntreNombres(arbol.listarDatos(), minNom, maxNom);
        if (!listaRango.esVacia()) {
            Lista listaFinal = obtenerCiudadesEntrePromedios(listaRango, anio, mes, minVol, maxVol);
            if (listaFinal.esVacia()) {
                System.out.println("No se registró ninguna ciudad con los datos dados");
            } else {
                // Muestra las ciudades encontradas
                System.out.println("Las ciudades las cuales están entre " + minNom + " y " + maxNom + ",");
                System.out.println("ademas de tener un promedio entre " + minVol + " y " + maxVol + ",");
                System.out.println("en el año " + StringAnio + " y mes " + (StringMes.toUpperCase()) + " son: ");
                Ciudad ciudadX = null;
                for (int i = 1; i <= listaFinal.longitud(); i++) {
                    ciudadX = (Ciudad) listaFinal.recuperar(i);
                    System.out.println(ciudadX.getNombre());
                }
            }
        } else {
            System.out.println("No se registró ninguna ciudad con los datos dados");
        }

    }

    // Devuelve una lista de ciudades cuyos nombres están entre minNom y maxNom
    // (inclusive)
    public static Lista obtenerCiudadesEntreNombres(Lista listaCiudades, String minNom, String maxNom) {
        Lista listaRangoCiudades = new Lista();
        int i = 1;
        int h = 1;
        Ciudad aux = null;
        boolean parar = false;
        while (!parar) {
            aux = (Ciudad) listaCiudades.recuperar(i);
            if (aux != null) {
                if (aux.getNombre().compareTo(minNom) >= 0 && aux.getNombre().compareTo(maxNom) < 0) {
                    listaRangoCiudades.insertar(aux, h);
                    h++;
                } else if (aux.getNombre().compareTo(maxNom) == 0) {
                    listaRangoCiudades.insertar(aux, h);
                    parar = true;
                }
            }
            i++;
        }
        return listaRangoCiudades;
    }

    // Devuelve una lista de ciudades cuyo promedio de consumo está entre minVol y
    // maxVol
    public static Lista obtenerCiudadesEntrePromedios(Lista listaCiudades, int anio, int mes, Double minVol, Double maxVol) {
        Lista listaPromedioCiudades = new Lista();
        int i = 1;
        int h = 1;
        Ciudad aux = null;
        Double promedio = -1.0;
        int longitud = listaCiudades.longitud();

        while (i <= longitud) {
            aux = (Ciudad) listaCiudades.recuperar(i);
            if (aux != null) {
                promedio = aux.calcularPromedioVolumenMes(anio, mes);
            }
            if (promedio >= minVol && promedio <= maxVol) {
                listaPromedioCiudades.insertar(aux, h);
                h++;
            }
            i++;
        }
        return listaPromedioCiudades;
    }

    // Permite agregar habitantes a una ciudad para un año específico
    public static void agregarHabitantesAnioCiudad(DiccionarioAVL arbolCiudades) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el nombre el de la ciudad que desea agregar habitantes");
        String eleccion = sc.nextLine().toUpperCase();
        Ciudad ciudadX = (Ciudad) arbolCiudades.obtenerDato(eleccion);
        boolean valido = false;
        String habitantes = "";
        while (ciudadX == null) {
            System.out.println("Error: Ciudad ingresada no valida, ingrese nuevamente");
            eleccion = sc.nextLine().toUpperCase();
            ciudadX = (Ciudad) arbolCiudades.obtenerDato(eleccion);
        }

        // Solicita año y valida
        System.out.println("Ingrese el año que desea agregar habitantes");
        eleccion = sc.nextLine().toUpperCase();
        int anio = convertirAnio(eleccion);
        while (anio == -1) {
            System.out.println("Error: Año ingresado incorrecto, ingrese nuevamente");
            eleccion = sc.nextLine().toUpperCase();
            anio = convertirAnio(eleccion);
        }

        do {
            // Muestra el menú de consultas sobre ciudades
            System.out.println("----------------------------------");
            System.out.println("Desea ajustar solo los habitantes de un mes especifico? Y/N");
            eleccion = sc.nextLine().toUpperCase();

            switch (eleccion) {
                case "Y":
                    ajustarHabitantesporMes(anio, ciudadX);
                    break;
                case "N":
                    // Consulta de ciudades en un rango de nombres y volumen
                    ajustarHabitantesporAnio(anio, ciudadX);
                    break;
                default:
                    System.out.println("Error: opción incorrecta. Por favor elija de nuevo");
            }

        } while (!eleccion.equals("Y") && !eleccion.equals("N"));
    }

    public static void ajustarHabitantesporMes(int anio, Ciudad ciudadX) {
        Scanner sc = new Scanner(System.in);
        boolean valido = false;
        String eleccion, habitantes = "";
        int mes = 0;
        System.out.println("Ingrese el mes que desea agregar habitantes");
        eleccion = sc.nextLine().toUpperCase();
        mes = convertirMesAInt(eleccion);
        while (mes == -1) {
            System.out.println("Error: Mes ingresado incorrecto, ingrese nuevamente");
            eleccion = sc.nextLine().toUpperCase();
            mes = convertirMesAInt(eleccion);
        }
        do {
            System.out.println("Ingrese la cantidad de habitantes del mes ");
            eleccion = sc.nextLine();
            valido = esInt(eleccion);
            if (valido) {
                habitantes = eleccion;
            } else {
                System.out.println("Error: formato incorrecto.");
            }
        } while (!valido);

        // Inserta los datos en la ciudad
        System.out.println(ciudadX.insertarMesEspecifico(mes, anio, habitantes));
    }

    public static void ajustarHabitantesporAnio(int anio, Ciudad ciudadX) {
        Scanner sc = new Scanner(System.in);
        boolean valido = false;
        String eleccion, habitantes = "";

        for (int i = 0; i < 12; i++) {
            do {
                System.out.println("Ingrese la cantidad de habitantes del mes " + convertirIntAMes(i+1) + ": ");
                eleccion = sc.nextLine();
                valido = esInt(eleccion);
                if (valido) {
                    if (i < 11) {
                        habitantes += eleccion + ";";
                    } else {
                        habitantes += eleccion;
                    }
                } else {
                    System.out.println("Error: formato incorrecto.");
                }
            } while (!valido);
        }
        // Inserta los datos en la ciudad
        System.out.println(ciudadX.insertarAnioEspecifico(anio, habitantes));
    }

    // 5. Consultas sobre transporte de agua
    // ------------------------------------------
    // Menú de consultas sobre tuberías entre dos ciudades
    public static void menuConsultarTuberia(DiccionarioAVL arbol, Grafo mapa, HashMap<Dom, Tuberia> hashX) {
        Scanner sc = new Scanner(System.in);
        System.out.println("----------------------------------");
        System.out.println("CONSULTAS SOBRE TUBERIAS");
        // Solicita al usuario dos ciudades válidas
        Ciudad[] AyB = obtenerCiudadAyB(arbol);
        Ciudad ciudadA = AyB[0];
        Ciudad ciudadB = AyB[1];
        String nombreA = ciudadA.getNombre();
        String nombreB = ciudadB.getNombre();

        // Verifica si existe un camino entre ambas ciudades
        if (mapa.existeCamino(ciudadA.getNomenclatura(), ciudadB.getNomenclatura())) {
            // Muestra opciones de consulta sobre el camino entre las ciudades
            System.out.println("Opciones:");
            System.out.println("1. Obtener el camino con caudal pleno minimo desde " + nombreA + " a " + nombreB);
            System.out.println(
                    "2. Obtener el camino de " + nombreA + " a " + nombreB
                    + " pasando por la minima cantidad de ciudades");
            System.out.println("3. Salir al menu principal");
            System.out.println("----------------------------------");
            String eleccion = sc.nextLine();

            // Ejecuta la opción elegida
            switch (eleccion) {
                case "1":
                    // Consulta el camino con caudal pleno mínimo
                    consultarCaminoCaudalPlenoMin(mapa, ciudadA, ciudadB, hashX);
                    break;
                case "2":
                    // Consulta el camino más corto (menos ciudades)
                    consultarCaminoMinimo(mapa, ciudadA, ciudadB, hashX);
                    break;
                case "3":
                    // Vuelve al menú principal
                    System.out.println("Volviendo al menú principal");
                    break;
                default:
                    // Maneja opción incorrecta
                    System.out.println("Error: opción incorrecta. Por favor elija de nuevo");
                    eleccion = sc.nextLine();
            }
        } else {
            // Informa si no existe camino entre las ciudades
            System.out.println("Error: No existe ningun camino desde " + nombreA + " a " + nombreB + ", saliendo...");
        }
    }

    // Solicita al usuario dos nombres de ciudades válidas y las retorna en un
    // arreglo
    public static Ciudad[] obtenerCiudadAyB(DiccionarioAVL arbol) {
        Scanner sc = new Scanner(System.in);
        // Solicita la primera ciudad y valida su existencia
        System.out.println("Ingrese el nombre de la primera ciudad que desea buscar");
        String nombreA = (sc.nextLine()).toUpperCase();
        Ciudad ciudadA = (Ciudad) arbol.obtenerDato(nombreA);
        while (ciudadA == null) {
            System.out.println("Error: Ingrese una ciudad valida");
            nombreA = (sc.nextLine()).toUpperCase();
            ciudadA = (Ciudad) arbol.obtenerDato(nombreA);
        }
        // Solicita la segunda ciudad y valida su existencia
        System.out.println("Ingrese el nombre de la segunda ciudad que desea buscar");
        String nombreB = (sc.nextLine()).toUpperCase();
        Ciudad ciudadB = (Ciudad) arbol.obtenerDato(nombreB);
        while (ciudadB == null) {
            System.out.println("Error: Ingrese una ciudad valida");
            nombreB = (sc.nextLine()).toUpperCase();
            ciudadB = (Ciudad) arbol.obtenerDato(nombreB);
        }
        // Devuelve ambas ciudades en un arreglo
        Ciudad[] AyB = {ciudadA, ciudadB};
        return AyB;
    }

    // Consulta el camino con caudal pleno mínimo entre dos ciudades
    public static void consultarCaminoCaudalPlenoMin(Grafo mapa, Ciudad ciudadA, Ciudad ciudadB,
            HashMap<Dom, Tuberia> hashX) {
        String nomenclaturaA = ciudadA.getNomenclatura();
        String nomenclaturaB = ciudadB.getNomenclatura();
        if (!mapa.vacio()) {
            // Obtiene todos los caminos posibles entre las dos ciudades
            Lista caminosPosibles = mapa.obtenerTodosLosCaminos(nomenclaturaA, nomenclaturaB);
            if (!caminosPosibles.esVacia()) {
                // Busca el camino con el menor caudal pleno
                Lista caminoConCaudalMin = encontrarCaudalPlenoMin(caminosPosibles, hashX);
                if (!caminoConCaudalMin.esVacia()) {
                    // Muestra el camino y su estado
                    System.out.println("El camino con el caudal pleno minimo desde " + ciudadA.getNombre() + " hasta "
                            + ciudadB.getNombre() + " es:");
                    System.out.println(caminoConCaudalMin.toString());
                    System.out.println("Y el estado del camino actualmente es "
                            + decidirEstadoCamino(obtenerListaEstadosTuberias(caminoConCaudalMin, hashX)));

                } else {
                    System.out.println("ERROR: Hubo un error al encontrar la tuberia mas pequeña, saliendo...");
                }
            } else {
                System.out.println("ERROR: No existe ningun camino desde " + ciudadA.getNombre() + " hasta "
                        + ciudadB.getNombre() + ", saliendo...");
            }
        }
        System.out.println("");
    }

    // Busca el camino con el menor caudal pleno entre los posibles
    public static Lista encontrarCaudalPlenoMin(Lista caminosPosibles, HashMap<Dom, Tuberia> hashX) {
        Lista out = new Lista();
        if (!caminosPosibles.esVacia()) {
            int cadaLista = 1;
            Lista aux = (Lista) caminosPosibles.recuperar(1);
            int longi = caminosPosibles.longitud();
            int longiAux = 0;
            Dom dominio = new Dom((String) aux.recuperar(1), (String) aux.recuperar(2));
            int caudalPlenoMin = 0;
            int caudalPlenoActual = 0;
            int diametroMin = hashX.get(dominio).getDiametro();
            for (int i = 1; i <= longi; i++) {
                aux = (Lista) caminosPosibles.recuperar(i);
                if (aux != null) {
                    dominio.setNom1((String) aux.recuperar(1));
                    dominio.setNom2((String) aux.recuperar(2));
                    diametroMin = hashX.get(dominio).getDiametro();
                    caudalPlenoActual = 0;
                    longiAux = aux.longitud();
                    for (int g = 1; g < longiAux; g++) {
                        dominio.setNom1((String) aux.recuperar(g));
                        dominio.setNom2((String) aux.recuperar(g + 1));
                        if (hashX.get(dominio).getDiametro() <= diametroMin && !out.equals(aux)) {
                            diametroMin = hashX.get(dominio).getDiametro();
                            caudalPlenoActual = hashX.get(dominio).getCaudalMax();
                        }
                    }
                    if (caudalPlenoActual < caudalPlenoMin || i == 1) {
                        caudalPlenoMin = caudalPlenoActual;
                        out = aux;
                    }
                }
            }
        }
        return out;
    }

    // Consulta el camino más corto (menos ciudades) entre dos ciudades
    public static void consultarCaminoMinimo(Grafo mapa, Ciudad ciudadA, Ciudad ciudadB, HashMap<Dom, Tuberia> hashX) {
        String nomenclaturaA = ciudadA.getNomenclatura();
        String nomenclaturaB = ciudadB.getNomenclatura();
        // Obtiene el camino más corto
        Lista caminoMinimo = mapa.caminoMasCorto(nomenclaturaA, nomenclaturaB);
        if (!caminoMinimo.esVacia()) {
            // Obtiene y muestra el estado del camino
            Lista estadoTuberias = obtenerListaEstadosTuberias(caminoMinimo, hashX);
            String estadoCamino = decidirEstadoCamino(estadoTuberias);
            if (!estadoCamino.equals("ERROR")) {
                System.out.println("El camino mas corto desde " + ciudadA.getNombre() + " hasta " + ciudadB.getNombre()
                        + " es el siguiente:");
                System.out.println(caminoMinimo.toString());
                System.out.println("Y el estado del camino completo es: " + estadoCamino);
            } else {
                System.out.println("ERROR: Volviendo al menu principal...");
            }
        } else {
            System.out.println(
                    "ERROR: No existe un camino desde " + ciudadA.getNombre() + " hasta " + ciudadB.getNombre());
        }
    }

    // Obtiene una lista con los estados de las tuberías de un camino
    public static Lista obtenerListaEstadosTuberias(Lista tuberias, HashMap<Dom, Tuberia> hashX) {
        Tuberia aux = null;
        Lista estadoTuberias = new Lista();
        int i = 1;
        Dom dominio = new Dom();
        while ((i + 1) <= tuberias.longitud()) {
            dominio.setNom1((String) tuberias.recuperar(i));
            dominio.setNom2((String) tuberias.recuperar(i + 1));
            aux = hashX.get(dominio);
            if (aux != null) {
                estadoTuberias.insertar(aux.getEstado(), i);
            }
            i++;
        }
        return estadoTuberias;
    }

    // Decide el estado general de un camino según los estados de sus tuberías
    public static String decidirEstadoCamino(Lista estadoTuberias) {
        int i = 1;
        int condicion = 0;
        String estadoActual = "";
        while (i <= estadoTuberias.longitud()) {
            estadoActual = (String) estadoTuberias.recuperar(i);
            switch (estadoActual) {
                case "ACTIVO":
                    if (condicion == 0) {
                        condicion = 1;
                    }
                    break;
                case "EN REPARACION":
                    if (condicion != 3 || condicion != 4) {
                        condicion = 2;
                    }
                    break;
                case "EN DISEÑO":
                    if (condicion != 4) {
                        condicion = 3;
                    }
                    break;
                case "INACTIVO":
                    condicion = 4;
                    break;
                default:
                    System.out.println("ERROR");
                    condicion = 0;
                    break;
            }
            i++;
        }

        // Devuelve el estado final según la prioridad encontrada
        switch (condicion) {
            case 1:
                estadoActual = "ACTIVO";
                break;
            case 2:
                estadoActual = "EN REPARACION";
                break;
            case 3:
                estadoActual = "EN DISEÑO";
                break;
            case 4:
                estadoActual = "INACTIVO";
                break;
            default:
                estadoActual = "ERROR";
                break;
        }
        return estadoActual;
    }

    // Listado de ciudades ordenadas por consumo de agua de mayor a menor
    public static void listarCiudadesConsumoAgua(DiccionarioAVL arbol) {
        Lista ciudades = arbol.listarDatos();
        int longi = ciudades.longitud();
        int cont = 1;
        Scanner sc = new Scanner(System.in);
        String eleccion = " ";
        int anio = -1;
        DiccionarioAVL ciudadesAgua = new DiccionarioAVL();
        Double cantAguaCiudad = 0.0;
        Ciudad ciudadX = null;
        String clave = "";

        // Solicita año y valida
        do {
            System.out.println("Ingrese el año que desea obtener el listado de consumos de agua");
            eleccion = sc.nextLine();
            anio = convertirAnio(eleccion);
            if (anio == -1) {
                System.out.println("Error: Valor invalido");
            }
        } while (anio == -1);

        // Calcula el consumo de agua de cada ciudad y lo inserta en un diccionario
        // auxiliar
        while (cont <= longi) {
            ciudadX = (Ciudad) ciudades.recuperar(cont);
            cantAguaCiudad = ciudadX.calcularPromedioVolumenAnio(anio);
            if (cantAguaCiudad != 0) {
                clave = String.format("%012.3f", cantAguaCiudad) + "_" + ciudadX.getNombre();
                ciudadesAgua.insertar(cantAguaCiudad, ciudadX.getNombre());
            }
            cont++;
        }
        // Muestra el listado invertido (de mayor a menor)
        System.out.println(ciudadesAgua.listarDatos().invertirLista().toString());
    }

    // Muestra las estructuras principales del sistema según elección del usuario
    public static void mostrarSistema(Grafo mapa, HashMap<Dom, Tuberia> hashTuberias, DiccionarioAVL arbol) {
        Scanner sc = new Scanner(System.in);
        String eleccion = "";
        System.out.println(
                "Ingrese que estructura desea mostrar según su índice:\n1.Grafo mapa.\n2.Hash de tuberias.\n3.Diccionario AVL.");
        eleccion = sc.nextLine();
        switch (eleccion) {
            case "1":
                // Muestra el grafo de ciudades y tuberías
                System.out.println(mapa.toString());
                break;
            case "2":
                // Muestra el hash de tuberías
                System.out.println(construirStringDesdeHashMap(hashTuberias));
                break;
            case "3":
                // Muestra el diccionario AVL de ciudades
                System.out.println(arbol.toString());
                break;
            default:
                System.out.println("Error: opción incorrecta.");
        }
    }

    // Construye un string con la información de todas las tuberías del hash
    public static String construirStringDesdeHashMap(HashMap<Dom, Tuberia> mapa) {
        String resultado = ""; // Inicializamos el String que se devolverá

        if (mapa == null || mapa.isEmpty()) {
            // Si el mapa es nulo o vacío, establecemos el mensaje directamente en
            // 'resultado'
            resultado = "El mapa está vacío o es nulo.";
        } else {
            // Si el mapa tiene elementos, construimos el String
            for (Map.Entry<Dom, Tuberia> entry : mapa.entrySet()) {
                Dom dom = entry.getKey();
                Tuberia tuberia = entry.getValue();

                if (dom != null && tuberia != null) {
                    resultado += "\nDesde: " + dom.getNom1()
                            + " / Hasta: " + dom.getNom2()
                            + " / Caudal Máximo: " + tuberia.getCaudalMax();
                } else {
                    resultado += "\nEntrada inválida (Dom o Tuberia es nulo).";
                }
            }
        }
        return resultado;
    }

}
