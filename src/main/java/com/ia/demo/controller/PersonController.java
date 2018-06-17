package com.ia.demo.controller;

import com.ia.demo.dto.PersonDTO;
import com.ia.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/person/")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping(value="{dni}")
    public ResponseEntity<? > getPersonByDni(@PathVariable String dni) {
        try {
            return new ResponseEntity<>(personService.findByDni(dni), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="")
    public ResponseEntity<?> listPersons(){
        return new ResponseEntity<>(personService.getAll(),HttpStatus.OK);
    }

    @PostMapping(value="")
    public ResponseEntity<?> savePerson(@RequestBody PersonDTO person) {
        try {
            personService.save(person);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
