package com.prontoconsulta.service;

import com.prontoconsulta.entity.Consulta;
import com.prontoconsulta.entity.Consulta.StatusConsulta;
import com.prontoconsulta.exception.ResourceNotFoundException;
import com.prontoconsulta.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ConsultaService {
    @Autowired private ConsultaRepository repository;

    public List<Consulta> buscarTodas() {
        return repository.findAll();
    }

    public Consulta buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consulta não encontrada com o ID: " + id));
    }

    public Consulta salvar(Consulta consulta) {
        return repository.save(consulta);
    }

    public Consulta atualizar(Long id, Consulta detalhes) {
        Consulta consulta = buscarPorId(id);
        consulta.setData(detalhes.getData());
        consulta.setHora(detalhes.getHora());
        consulta.setStatus(detalhes.getStatus());
        return repository.save(consulta);
    }

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
}
