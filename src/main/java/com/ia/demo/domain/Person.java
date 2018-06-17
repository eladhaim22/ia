package com.ia.demo.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="persons")
public class Person {
    @Id
    @GeneratedValue
    private Long id;
    private String dni;
    private String firstName;
    private String lastName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="personId")
    private List<Diagnostic> diagnosticList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getDni() {
        return dni;
    }

    public String getFirstName() {
        return firstName;
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

    public void setDiagnosticList(List<Diagnostic> diagnosticList) {
        this.diagnosticList = diagnosticList;
    }

    public String getLastName() {
        return lastName;
    }

    public List<Diagnostic> getDiagnosticList() {
        return diagnosticList;
    }
}
