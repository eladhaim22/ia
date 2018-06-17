package com.ia.demo.controller;

import com.ia.demo.domain.Diagnostic;
import com.ia.demo.dto.DiagnosticDTO;
import com.ia.demo.service.DiagnosticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value="/diagnostic/")
public class DiagnositcController {
    @Autowired
    private DiagnosticService diagnosticService;

    @PostMapping(value="diagnostic")
    public ResponseEntity<?> getDiagnostic(@RequestBody DiagnosticDTO diagnosticDTO){
        return new ResponseEntity<>(diagnosticService.calculateDiagnostic(diagnosticDTO),HttpStatus.OK);
    }

    @GetMapping(value="{id}")
    public ResponseEntity<?> DiagnosticService(@PathVariable Long id){
        return new ResponseEntity<>(diagnosticService.getById(id),HttpStatus.OK);
    }

    @GetMapping(value="byPersonId/{id}")
    public ResponseEntity<?> getAllDiagnostic(@PathVariable Long id){
        return new ResponseEntity<>(diagnosticService.getAllDiagnosticByPersonId(id),HttpStatus.OK);
    }

    @PostMapping(value="")
    public ResponseEntity<?> saveDiagnostic(@RequestBody DiagnosticDTO diagnosticDTO){
        return new ResponseEntity<>(diagnosticService.save(diagnosticDTO),HttpStatus.OK);
    }
}
