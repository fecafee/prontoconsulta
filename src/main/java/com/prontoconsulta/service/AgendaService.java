package com.prontoconsulta.service;

import com.prontoconsulta.entity.Agenda;
import com.prontoconsulta.exception.ResourceNotFoundException;
import com.prontoconsulta.repository.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class AgendaService {

    @Autowired
    private AgendaRepository repository;

    public List<Agenda> buscarTodas() {
        return repository.findAll();
    }

    public Agenda buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agenda não encontrada com o ID: " + id));
    }

    public List<Agenda> buscarPorMedico(Long medicoId) {
        return repository.findByMedicoId(medicoId);
    }

    public List<Agenda> buscarPorMedicoEData(Long medicoId, LocalDate data) {
        return repository.findByMedicoIdAndData(medicoId, data);
    }

    public Agenda salvar(Agenda agenda) {
        boolean existe = repository.existsByMedicoIdAndDataAndHora(
                agenda.getMedico().getId(), agenda.getData(), agenda.getHora()
        );
        if (existe) {
            throw new IllegalArgumentException("Já existe uma agenda cadastrada para este médico neste dia e hora.");
        }
        return repository.save(agenda);
    }

    public Agenda atualizar(Long id, Agenda novaAgenda) {
        Agenda existente = buscarPorId(id);
        existente.setData(novaAgenda.getData());
        existente.setHora(novaAgenda.getHora());
        existente.setMedico(novaAgenda.getMedico());
        return repository.save(existente);
    }

    public void deletar(Long id) {
        Agenda agenda = buscarPorId(id);
        repository.delete(agenda);
    }
}

