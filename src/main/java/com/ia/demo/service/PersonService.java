package com.ia.demo.service;

import com.ia.demo.domain.DateId;
import com.ia.demo.domain.Person;
import com.ia.demo.dto.PersonDTO;
import com.ia.demo.mapper.PersonMapper;
import com.ia.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonMapper personMapper;

    public PersonDTO findByDni(String dni) throws Exception {
        Person person = personRepository.findByDni(dni);
        if(person != null) {
            return personMapper.toDTO(person);
        }
        else {
            throw new Exception("user doesn't exists");
        }
    }

    public List<DateId> getDiagnosticDates(PersonDTO person){
        return personRepository.getOne(person.getId()).getDiagnosticList().stream().sorted(Comparator.nullsLast(
                (e1, e2) -> e2.getDate().compareTo(e1.getDate()))).map(d -> new DateId(d.getId(),d.getDate().toString())).collect(Collectors.toList());
    }

    public void save(PersonDTO person) throws Exception {
        Person p = personRepository.findByDni(person.getDni());
        if (p == null) {
            personRepository.saveAndFlush(personMapper.toModel(person));
        }
        else {
            throw new Exception("El usuario ya existe.");
        }
    }

    public List<PersonDTO> getAll(){
        return personRepository.findAll().stream().map(p -> personMapper.toDTO(p)).collect(Collectors.toList());
    }
}
