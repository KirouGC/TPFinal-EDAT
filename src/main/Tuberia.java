package main;

public class Tuberia {
    private String nomenclatura;
    private int caudalMin;
    private int caudalMax;
    private int diametro;
    private String estado;

    // Constructor
    public Tuberia(String nomenclatura, int caudalMin, int caudalMax, int diametro, String estado) {
        this.nomenclatura = nomenclatura;
        this.caudalMin = caudalMin;
        this.caudalMax = caudalMax;
        this.diametro = diametro;
        this.estado = estado;
    }

    public Tuberia() {
        this.nomenclatura = null;
        this.caudalMin = 0;
        this.caudalMax = 0;
        this.diametro = 0;
        this.estado = null;
    }

    // Getters
    public String getNomenclatura() {
        return nomenclatura;
    }

    public int getCaudalMin() {
        return caudalMin;
    }

    public int getCaudalMax() {
        return caudalMax;
    }

    public int getDiametro() {
        return diametro;
    }

    public String getEstado() {
        return estado;
    }

    // Setters
    public void setNomenclatura(String nomenclatura) {
        this.nomenclatura = nomenclatura;
    }

    public void setCaudalMin(int caudalMin) {
        this.caudalMin = caudalMin;
    }

    public void setCaudalMax(int caudalMax) {
        this.caudalMax = caudalMax;
    }

    public void setDiametro(int diametro) {
        this.diametro = diametro;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
