package com.ia.demo.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Diagnostic {
    @Id
    @GeneratedValue
    private Long id;
    private Date date;
    private String nombre;
    private String cuidados;
    private String motivoConsulta;
    private String resultado;
    private String accion;
    private String antecedentes;

    @ManyToOne
    @JoinColumn(name="personId")
    private Person person;

    @Embedded
    private Stain stain;

    @Embedded
    private Form form;

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Person getPerson() {
        return person;
    }

    public Stain getStain() {
        return stain;
    }

    public Form getForm() {
        return form;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setStain(Stain stain) {
        this.stain = stain;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public String getCuidados() {
        return cuidados;
    }

    public void setCuidados(String cuidados) {
        this.cuidados = cuidados;
    }

    public String getMotivoConsulta() {
        return motivoConsulta;
    }

    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getAntecedentes() {
        return antecedentes;
    }

    public void setAntecedentes(String antecedentes) {
        this.antecedentes = antecedentes;
    }
}
