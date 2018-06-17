package com.ia.demo.dto;

import java.util.ArrayList;
import java.util.List;

public class PersonDTO {
    private Long id;
    private String dni;
    private String firstName;
    private String lastName;

    private List<DiagnosticDTO> diagnosticList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getDni() {
        return dni;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<DiagnosticDTO> getDiagnosticList() {
        return diagnosticList;
    }

    public void setDiagnosticList(List<DiagnosticDTO> diagnosticList) {
        this.diagnosticList = diagnosticList;
    }
}

