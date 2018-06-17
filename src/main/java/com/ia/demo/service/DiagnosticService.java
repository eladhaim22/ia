package com.ia.demo.service;

import com.ia.demo.domain.Diagnostic;
import com.ia.demo.dto.DiagnosticDTO;
import com.ia.demo.mapper.DiagnosticMapper;
import com.ia.demo.repository.DiagnosticRepository;
import net.sf.clipsrules.jni.Environment;
import net.sf.clipsrules.jni.FactAddressValue;
import net.sf.clipsrules.jni.MultifieldValue;
import net.sf.clipsrules.jni.SymbolValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.MessageFormat;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DiagnosticService {
    @Autowired
    private DiagnosticRepository diagnosticRepository;

    @Autowired
    private DiagnosticMapper diagnosticMapper;

    @Autowired
    private Environment clipsEnvironment;

    public DiagnosticDTO getById(Long id){
        return diagnosticMapper.toDTO(diagnosticRepository.getOne(id));
    }

    public DiagnosticDTO getLastDiagnosticByPersonId(Long personId){
        List<Diagnostic> diagnosticList = diagnosticRepository.findAllByPerson_Id(personId);
        return diagnosticMapper.toDTO(diagnosticList.stream().sorted(Comparator.nullsLast(
                (e1, e2) -> e2.getDate().compareTo(e1.getDate())))
                .findFirst().get());
    }

    public DiagnosticDTO calculateDiagnostic(DiagnosticDTO diagnostic){
       String a = MessageFormat.format("(assert \n" +
                        "(paciente \n" +
                        "(id-paciente {0})\n" +
                        "(antecedentes-clinicos {1})\n" +
                        "(cuidados {2})\n" +
                        "(motivo-consulta {3})\n" +
                        ")\n" +
                        ")",
                diagnostic.getPersonId(),diagnostic.getAntecedentes(),diagnostic.getCuidados(),diagnostic.getMotivoConsulta());

        clipsEnvironment.assertString(a);
        a = MessageFormat.format("(assert \n" +
                "(mancha \n" +
                "(id-paciente {0})\n" +
                "(color {1})\n" +
                "(evolucion {2})\n" +
                "(origen {3})\n" +
                "(sintoma {4})\n" +
                "(pelos {5})\n" +
                "(rasposa {6})\n" +
                ")\n" +
                ")",diagnostic.getPersonId(),diagnostic.getStain().getColor(),diagnostic.getStain().getEvolucion(),diagnostic.getStain().getOrigin(),
                diagnostic.getStain().getSintoma(),diagnostic.getStain().getPelos(),diagnostic.getStain().getRasposa());
        clipsEnvironment.assertString(a);
        a = MessageFormat.format("(assert \n" +
                        "(forma \n" +
                        "(id-paciente {0})\n" +
                        "(asimetria {1})\n" +
                        "(superficie {2})\n" +
                        "(diametro {3})\n" +
                        "(elevada {4})\n" +
                        "(borde {5})\n" +
                        ")\n" +
                        ")",diagnostic.getPersonId(),diagnostic.getForm().getAsimetria(),diagnostic.getForm().getSuperficie(),
                            diagnostic.getForm().getDiametro(),diagnostic.getForm().getElevada(),diagnostic.getForm().getBorde());

        clipsEnvironment.assertString(a);
        clipsEnvironment.run();
        String evalstr= "(find-all-facts ((?J diagnostico)) TRUE)";
        SymbolValue pv = (SymbolValue) clipsEnvironment.eval(evalstr);
        //FactAddressValue factAddressValue = (FactAddressValue) pv.get(0);
        //FactAddressValue fv = (FactAddressValue) pv.get(0);
        FactAddressValue fv = null;
        String s = null;
        try {
            diagnostic.setResultado(fv.getFactSlot("resultado").toString());
            diagnostic.setAccion(fv.getFactSlot("accion").toString());
            diagnostic.setNombre(fv.getFactSlot("nombre").toString());
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return diagnostic;
    }

    public DiagnosticDTO save(DiagnosticDTO diagnostic){
        return diagnosticMapper.toDTO(diagnosticRepository.saveAndFlush(diagnosticMapper.toModel(diagnostic)));
    }

    public List<DiagnosticDTO> getAllDiagnosticByPersonId(Long id){
        return diagnosticRepository.findAllByPerson_Id(id).stream().map(d -> diagnosticMapper.toDTO(d)).collect(Collectors.toList());
    }
}
