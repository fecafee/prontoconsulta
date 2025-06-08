package com.prontoconsulta.repository;

import com.prontoconsulta.entity.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    List<Agenda> findByMedicoId(Long medicoId);
    List<Agenda> findByMedicoIdAndData(Long medicoId, LocalDate data);
    boolean existsByMedicoIdAndDataAndHora(Long medicoId, LocalDate data, LocalTime hora);
}
