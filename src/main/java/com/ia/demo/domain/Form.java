package com.ia.demo.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Form {
    private String asimetria;
    private String superficie;
    private String diametro;
    private String escamas;
    private String elevada;
    private String borde;

    public String getAsimetria() {
        return asimetria;
    }

    public void setAsimetria(String asimetria) {
        this.asimetria = asimetria;
    }

    public String getSuperficie() {
        return superficie;
    }

    public void setSuperficie(String superficie) {
        this.superficie = superficie;
    }

    public String getDiametro() {
        return diametro;
    }

    public void setDiametro(String diametro) {
        this.diametro = diametro;
    }

    public String getEscamas() {
        return escamas;
    }

    public void setEscamas(String escamas) {
        this.escamas = escamas;
    }

    public String getElevada() {
        return elevada;
    }

    public void setElevada(String elevada) {
        this.elevada = elevada;
    }

    public String getBorde() {
        return borde;
    }

    public void setBorde(String borde) {
        this.borde = borde;
    }
}
