package com.ia.demo.mapper;

import com.ia.demo.domain.Diagnostic;
import com.ia.demo.domain.Person;
import com.ia.demo.dto.DiagnosticDTO;
import com.ia.demo.dto.PersonDTO;
import com.ia.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private DiagnosticMapper diagnosticMapper;

    public Person toModel(PersonDTO personDTO) {
        Person person = null;
        if (personDTO.getId() == null) {
            person = new Person();
        } else {
            person = personRepository.getOne(personDTO.getId());
        }
        person.setDni(personDTO.getDni());
        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
        person.getDiagnosticList().clear();
        for (DiagnosticDTO d : personDTO.getDiagnosticList()) {
            person.getDiagnosticList().add(diagnosticMapper.toModel(d));
        }
        return person;
    }

    public PersonDTO toDTO(Person person) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(person.getId());
        personDTO.setDni(person.getDni());
        personDTO.setFirstName(person.getFirstName());
        personDTO.setLastName(person.getLastName());
        personDTO.getDiagnosticList().clear();
        for (Diagnostic d : person.getDiagnosticList()) {
            personDTO.getDiagnosticList().add(diagnosticMapper.toDTO(d));
        }
        return personDTO;
    }
}