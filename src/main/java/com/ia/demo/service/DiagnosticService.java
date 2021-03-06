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
import java.util.ArrayList;
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
        String a = MessageFormat.format("(assert (paciente (antecedentes-clinicos {0})(cuidados {1})(motivo-consulta {2})))",
                diagnostic.getAntecedentes(),diagnostic.getCuidados(),diagnostic.getMotivoConsulta());
        clipsEnvironment.eval(a);

        a = MessageFormat.format("(assert \n" +
                        "(diagnostico \n" +
                        "(nombre {0})\n" +
                        "(resultado {1})\n" +
                        "(accion {1})\n" +
                        ")\n" +
                        "))",
                "nose","nose","nose");

        clipsEnvironment.eval(a);

        a = MessageFormat.format("(assert \n" +
                "(mancha \n" +
                "(color {0})\n" +
                "(evolucion {1})\n" +
                "(origen {2})\n" +
                "(sintoma {3})\n" +
                "(pelos {4})\n" +
                "(rasposa {5})\n" +
                ")\n" +
                "))",diagnostic.getStain().getColor(),diagnostic.getStain().getEvolucion(),diagnostic.getStain().getOrigin(),
                diagnostic.getStain().getSintoma(),diagnostic.getStain().getPelos(),diagnostic.getStain().getRasposa());
        clipsEnvironment.eval(a);
        a = MessageFormat.format("(assert \n" +
                        "(forma \n" +
                        "(asimetria {0})\n" +
                        "(superficie {1})\n" +
                        "(diametro {2})\n" +
                        "(elevada {3})\n" +
                        "(borde {4})\n" +
                        ")\n" +
                        "))",diagnostic.getForm().getAsimetria(),diagnostic.getForm().getSuperficie(),
                        String.format(java.util.Locale.US,"%.1f", diagnostic.getForm().getDiametro()),diagnostic.getForm().getElevada(),diagnostic.getForm().getBorde());

        clipsEnvironment.eval(a);
        clipsEnvironment.run();
        String evalstr= "(find-all-facts ((?J diagnostico)) TRUE)";
        MultifieldValue pv = (MultifieldValue) clipsEnvironment.eval(evalstr);
        FactAddressValue factAddressValue = (FactAddressValue) pv.get(0);
        FactAddressValue fv = (FactAddressValue) pv.get(0);
        String s = null;
        try {
            diagnostic.setResultado(parseResultado(fv.getFactSlot("resultado").toString()));
            diagnostic.setAccion(parseAccion(fv.getFactSlot("accion").toString()));
            diagnostic.setNombre(parseNombre(fv.getFactSlot("nombre").toString()));
            diagnostic.setImage(getImage(fv.getFactSlot("nombre").toString()));

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        clipsEnvironment.reset();
        return diagnostic;
    }
    private String getImage(String nombre){
        ArrayList<String> images = new ArrayList<>();
        switch(nombre){
            case "lunar-benigno":
                return "benigno.jpg";
            case "nevo-verrugoso":
                return "nevo_1.jpg";
            case "queratosis-seborreica":
                return "seborreica_2.jpg";
            case "queratosis-actinica":
                return "actinica_1.jpg";
            case "verruga-vulgar":
                return "verruga_1.jpg";
            case "posible-tumor-maligno":
                return "maligno.jpg";
            case "posible-lunar-benigno":
                return "benigno.jpg";
            case "posible-alergia":
                return "alergia_2.jpg";
            default:
                return null;
        }
    }

    private String parseNombre(String nombre){
        switch(nombre){
            case "lunar-benigno":
                return "Lunar Benigno";
            case "nevo-verrugoso":
                return "Nevo Verrugoso";
            case "queratosis-seborreica":
                return "Queratosis Seborreica";
            case "queratosis-actinica":
                return "queratosis Actinica";
            case "verruga-vulgar":
                return "Verruga Vulgar";
            case "posible-tumor-maligno":
                return "Posible Tumor Maligno";
            case "posible-lunar-benigno":
                return "Posible Lunar Benigno";
            case "posible-alergia":
                return "Posible Alergia";
            default:
                return "Sin Diagnostico";
        }
    }

    private String parseResultado(String accion){
        switch(accion){
            case "se-necesitan-mas-estudios":
                return "Se aconseja hacer mas estudios para mejor precisión.";
            case "diagnostico-realizado":
                return "Diagnostico realizado.";
            default:
                return "Se aconsejan hacer mas estudios para mejor precisión.";
        }
    }

    private String parseAccion(String accion) {
        switch (accion) {
            case "controlar":
                return "Controlar.";
            case "biopsia":
                return "Biopsia.";
            case "extraccion":
                return "Extraccion.";
            case "tratamiento":
                return "Tratamiento.";
            case "diagnostico-exitoso":
                return "";
            case "extraccion-previo-control":
                return "Extraccion previo control.";
            default:
                return "";
        }
    }

    public DiagnosticDTO save(DiagnosticDTO diagnostic){
        return diagnosticMapper.toDTO(diagnosticRepository.saveAndFlush(diagnosticMapper.toModel(diagnostic)));
    }

    public List<DiagnosticDTO> getAllDiagnosticByPersonId(Long id){
        return diagnosticRepository.findAllByPerson_Id(id).stream().map(d -> diagnosticMapper.toDTO(d)).collect(Collectors.toList());
    }
}
