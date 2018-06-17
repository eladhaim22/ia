package com.ia.demo.repository;

import com.ia.demo.domain.Diagnostic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnosticRepository extends JpaRepository<Diagnostic,Long> {
    List<Diagnostic> findAllByPerson_Id(Long id);
}
