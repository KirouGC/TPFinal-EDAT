package main;

import clases.lineales.dinamicas.Lista;

public class Ciudad {

    // Variables
    private String nombre;
    private Lista anios;
    private String nomenclatura;
    private Double superficie;
    private Double promPersonalConsumidosPorDia;

    // Constructor por defecto
    public Ciudad() {
        nombre = null;
        anios = new Lista();
        nomenclatura = null;
        superficie = 0.0;
        promPersonalConsumidosPorDia = 0.0;
    }

    // Constructor con parámetros
    public Ciudad(String unNombre, String unaNomenclatura, Double unaSuperficie, Double unProm) {
        nombre = unNombre;
        nomenclatura = unaNomenclatura;
        superficie = unaSuperficie;
        promPersonalConsumidosPorDia = unProm;
        anios = new Lista();
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public Lista getAnios() {
        return anios;
    }

    public String getNomenclatura() {
        return nomenclatura;
    }

    public Double getSuperficie() {
        return superficie;
    }

    public Double getPromPersonalConsumidosPorDia() {
        return promPersonalConsumidosPorDia;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setAnios(Lista anios) {
        this.anios = anios;
    }

    public void setNomenclatura(String nomenclatura) {
        this.nomenclatura = nomenclatura;
    }

    public void setSuperficie(Double superficie) {
        this.superficie = superficie;
    }

    public void setPromPersonalConsumidosPorDia(Double promPersonalConsumidosPorDia) {
        this.promPersonalConsumidosPorDia = promPersonalConsumidosPorDia;
    }

    // Propios
    public void insertarDatosAnio(String datos) {
        String[] cadaDato = datos.split(";");
        int[] habitantesMensual = new int[12];
        for (int i = 0; i < cadaDato.length; i++) {
            habitantesMensual[i] = Integer.parseInt(cadaDato[i]);
        }
        anios.insertar(habitantesMensual, anios.longitud() + 1);
    }

    public String insertarMesEspecifico(int mes, int anio, String habitantes) {
        String resultado = "";
        int longi = anios.longitud();
        int valorX = anio - longi;
        int habitantesMensual = Integer.parseInt(habitantes);
        int[] nuevosHabitantes = new int[12];
        
        if (valorX > 0) {
            while (valorX > 0) {
                longi = longi + 1;
                anios.insertar(null, longi);
                valorX--;
            }
            nuevosHabitantes[mes] = habitantesMensual;
            if(anios.insertar(nuevosHabitantes, longi))
                resultado = "Habitantes insertados correctamente";
            else
                resultado = "Error al ingresar habitantes";
        } else {
            if (anios.recuperar(anio) == null) {
                anios.eliminar(anio);
                nuevosHabitantes[mes] = habitantesMensual;
                if (anios.insertar(nuevosHabitantes, anio))
                    resultado = "Habitantes insertados correctamente";
                else
                    resultado = "Error al ingresar habitantes";
            } else{
                nuevosHabitantes = (int []) anios.recuperar(anio);
                nuevosHabitantes[mes] = habitantesMensual;
                resultado = "Habitantes insertados correctamente";
            }
        }
        return resultado;
    }

    public String insertarAnioEspecifico(int anio, String habitantes) {
        String resultado = "";
        int longi = anios.longitud();
        int valorX = anio - longi;
        String[] cadaDato = habitantes.split(";");
        int[] habitantesMensual = new int[12];

        if (valorX > 0) {
            while (valorX > 0) {
                longi = longi + 1;
                anios.insertar(null, longi);
                valorX--;
            }
            for (int i = 0; i < cadaDato.length; i++) {
                habitantesMensual[i] = Integer.parseInt(cadaDato[i]);
            }
            if (anios.insertar(habitantesMensual, longi))
                resultado = "Habitantes insertados correctamente";
            else
                resultado = "Error al ingresar habitantes";
        } else {
                for (int i = 0; i < cadaDato.length; i++){ 
                    habitantesMensual[i] = Integer.parseInt(cadaDato[i]);
                }
                if(anios.recuperar(anio) != null){
                    System.out.println("Año cargado previamente - Sobreescribiendo datos...");
                }
                anios.eliminar(anio);
                if (anios.insertar(habitantesMensual, anio))
                    resultado = "Habitantes insertados correctamente";
                else
                    resultado = "Error al ingresar habitantes";
            }
        return resultado;
    }

    public int obtenerHabitantes(int anio, int mes) {
        int habitantes = -1;
        mes--;
        if (anio <= anios.longitud() && mes >= 0 && mes <= 11) {
            int[] aux = (int[]) anios.recuperar(anio);
            if (aux != null)
                habitantes = aux[mes];
        }
        return habitantes;
    }

    public Double calcularPromedioVolumenAnio(int anio) {
        int[] meses = (int[]) anios.recuperar(anio);
        Double promedio = 0.0;
        if (meses != null) {
            for (int i = 0; i < meses.length; i++) {
                promedio = promedio + (meses[i] * promPersonalConsumidosPorDia);
            }
        }
        return promedio;
    }

    public Double calcularPromedioVolumenMes(int anio, int mes) {
        int[] meses = (int[]) anios.recuperar(anio);
        Double promedio = 0.0;
        if (meses != null)
            promedio = meses[mes] * promPersonalConsumidosPorDia;
        return promedio;
    }

    public boolean verificarAnio(int anio) {
        return (anio <= anios.longitud());
    }
}