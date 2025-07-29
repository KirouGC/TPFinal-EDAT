package main;

import java.util.Objects;

public class Dom {
    private String nom1;
    private String nom2;

    public Dom() {
        this.nom1 = null;
        this.nom2 = null;
    }

    public Dom(String nom1, String nom2) {
        this.nom1 = nom1;
        this.nom2 = nom2;
    }

    public void setNom1(String nom1) {
        this.nom1 = nom1;
    }

    public void setNom2(String nom2) {
        this.nom2 = nom2;
    }

    public String getNom1() {
        return nom1;
    }

    public String getNom2() {
        return nom2;
    }

    // Metodo equals
    @Override
    public boolean equals(Object n) {
        Dom domX = (Dom) n;
        return (this.nom1.equals(domX.nom1) && this.nom2.equals(domX.nom2));
    }

    // Metodo hashCode
    @Override
    public int hashCode() {
        return Objects.hash(nom1, nom2); // usa java.util.Objects
    }
}
