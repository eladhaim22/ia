package com.ia.demo.domain;

public class DateId {
    private Long DiagnosticId;
    private String date;

    public DateId(Long diagnosticId, String date) {
        DiagnosticId = diagnosticId;
        this.date = date;
    }

    public Long getDiagnosticId() {
        return DiagnosticId;
    }

    public void setDiagnosticId(Long diagnosticId) {
        DiagnosticId = diagnosticId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString(){
        return date;
    }
}
