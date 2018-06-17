package com.ia.demo.dto;

import com.ia.demo.domain.Form;
import com.ia.demo.domain.Stain;

import java.util.Date;

public class DiagnosticDTO {
    private Long id;
    private Date date;
    private String nombre;
    private String cuidados;
    private String motivoConsulta;
    private String resultado;
    private String accion;
    private String antecedentes;

    private Long personId;
    private Stain stain = new Stain();
    private Form form = new Form();

    public DiagnosticDTO() {
    }

    public DiagnosticDTO(Long id, Date date, String nombre, String cuidados, String motivoConsulta, String resultado, String accion, String antecedentes, Long personId, Stain stain, Form form) {
        this.id = id;
        this.date = date;
        this.nombre = nombre;
        this.cuidados = cuidados;
        this.motivoConsulta = motivoConsulta;
        this.resultado = resultado;
        this.accion = accion;
        this.antecedentes = antecedentes;
        this.personId = personId;
        this.stain = stain;
        this.form = form;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Stain getStain() {
        return stain;
    }

    public void setStain(Stain stain) {
        this.stain = stain;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }
}
