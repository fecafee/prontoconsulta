package com.prontoconsulta.repository;

import com.prontoconsulta.entity.Consulta;
import com.prontoconsulta.entity.Consulta.StatusConsulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    List<Consulta> findByPacienteId(Long pacienteId);
    List<Consulta> findByMedicoId(Long medicoId);
    List<Consulta> findByData(LocalDate data);
    List<Consulta> findByStatus(StatusConsulta status);
}
