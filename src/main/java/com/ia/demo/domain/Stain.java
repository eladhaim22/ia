package com.ia.demo.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Stain {
    private String color;
    private String evolucion;
    private String origin;
    private String sintoma;
    private String pelos;
    private String rasposa;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getEvolucion() {
        return evolucion;
    }

    public void setEvolucion(String evolucion) {
        this.evolucion = evolucion;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getSintoma() {
        return sintoma;
    }

    public void setSintoma(String sintoma) {
        this.sintoma = sintoma;
    }

    public String getPelos() {
        return pelos;
    }

    public void setPelos(String pelos) {
        this.pelos = pelos;
    }

    public String getRasposa() {
        return rasposa;
    }

    public void setRasposa(String rasposa) {
        this.rasposa = rasposa;
    }
}
