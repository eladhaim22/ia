package com.ia.demo.mapper;

import com.ia.demo.domain.Diagnostic;
import com.ia.demo.dto.DiagnosticDTO;
import com.ia.demo.repository.DiagnosticRepository;
import com.ia.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DiagnosticMapper {
    @Autowired
    private DiagnosticRepository diagnosticRepository;

    @Autowired
    private PersonRepository personRepository;

    public Diagnostic toModel(DiagnosticDTO diagnosticDTO){
        Diagnostic diagnostic = null;
        if(diagnosticDTO.getId() == null){
            diagnostic = new Diagnostic();
        }
        else {
            diagnostic = diagnosticRepository.getOne(diagnosticDTO.getId());
        }
        diagnostic.setMotivoConsulta(diagnosticDTO.getMotivoConsulta());
        diagnostic.setPerson(personRepository.getOne(diagnosticDTO.getPersonId()));
        diagnostic.setForm(diagnosticDTO.getForm());
        diagnostic.setStain(diagnosticDTO.getStain());
        diagnostic.setStain(diagnosticDTO.getStain());
        diagnostic.setDate(diagnosticDTO.getDate());
        diagnostic.setCuidados(diagnosticDTO.getCuidados());
        diagnostic.setAntecedentes(diagnosticDTO.getAntecedentes());
        diagnostic.setAccion(diagnosticDTO.getAccion());
        diagnostic.setResultado(diagnosticDTO.getResultado());
        diagnostic.setNombre(diagnosticDTO.getNombre());
        return diagnostic;
    }

    public DiagnosticDTO toDTO(Diagnostic diagnostic){
        DiagnosticDTO diagnosticDTO = new DiagnosticDTO();
        diagnosticDTO.setId(diagnostic.getId());
        diagnosticDTO.setMotivoConsulta(diagnostic.getMotivoConsulta());
        diagnosticDTO.setPersonId(diagnostic.getId());
        diagnosticDTO.setForm(diagnostic.getForm());
        diagnosticDTO.setStain(diagnostic.getStain());
        diagnosticDTO.setStain(diagnostic.getStain());
        diagnosticDTO.setDate(diagnostic.getDate());
        diagnosticDTO.setCuidados(diagnostic.getCuidados());
        diagnosticDTO.setAntecedentes(diagnostic.getAntecedentes());
        diagnosticDTO.setAccion(diagnostic.getAccion());
        diagnosticDTO.setResultado(diagnostic.getResultado());
        diagnosticDTO.setNombre(diagnostic.getNombre());
        diagnosticDTO.setPersonId(diagnostic.getPerson().getId());
        return diagnosticDTO;
    }


}
