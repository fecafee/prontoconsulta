package com.prontoconsulta.service;

import com.prontoconsulta.entity.Consulta;
import com.prontoconsulta.entity.Consulta.StatusConsulta;
import com.prontoconsulta.exception.ResourceNotFoundException;
import com.prontoconsulta.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository repository;

    public List<Consulta> buscarTodas() {
        return repository.findAll();
    }

    public Consulta buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consulta não encontrada com o ID: " + id));
    }

    @Transactional
    public Consulta salvar(Consulta consulta) {
        if (consulta.getPaciente() == null || consulta.getPaciente().getId() == null) {
            throw new IllegalArgumentException("Paciente é obrigatório.");
        }
        if (consulta.getMedico() == null || consulta.getMedico().getId() == null) {
            throw new IllegalArgumentException("Médico é obrigatório.");
        }
        return repository.save(consulta);
    }

    @Transactional
    public Consulta atualizar(Long id, Consulta detalhes) {
        Consulta consulta = buscarPorId(id);

        if (detalhes.getData() != null) consulta.setData(detalhes.getData());
        if (detalhes.getHora() != null) consulta.setHora(detalhes.getHora());
        if (detalhes.getStatus() != null) consulta.setStatus(detalhes.getStatus());
        if (detalhes.getPaciente() != null) consulta.setPaciente(detalhes.getPaciente());
        if (detalhes.getMedico() != null) consulta.setMedico(detalhes.getMedico());

        return repository.save(consulta);
    }

    @Transactional
    public void deletar(Long id) {
        repository.delete(buscarPorId(id));
    }

    public List<Consulta> buscarPorPaciente(Long pacienteId) {
        return repository.findByPacienteId(pacienteId);
    }

    public List<Consulta> buscarPorMedico(Long medicoId) {
        return repository.findByMedicoId(medicoId);
    }

    public List<Consulta> buscarPorData(LocalDate data) {
        return repository.findByData(data);
    }

    public List<Consulta> buscarPorStatus(StatusConsulta status) {
        return repository.findByStatus(status);
    }

    @Transactional
    public Consulta cancelarConsulta(Long id) {
        Consulta consulta = buscarPorId(id);
        consulta.setStatus(StatusConsulta.CANCELADA);
        return repository.save(consulta);
    }
}
