package com.prontoconsulta.service;

import com.prontoconsulta.entity.Medico;
import com.prontoconsulta.exception.ResourceNotFoundException;
import com.prontoconsulta.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MedicoService {
    @Autowired private MedicoRepository repository;

    public List<Medico> buscarTodos() {
        return repository.findAll();
    }

    public Medico buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Médico não encontrado com o ID: " + id));
    }

    public Medico salvar(Medico medico) {
        return repository.save(medico);
    }

    public Medico atualizar(Long id, Medico detalhes) {
        Medico medico = buscarPorId(id);
        medico.setNome(detalhes.getNome());
        medico.setCrm(detalhes.getCrm());
        medico.setEspecialidade(detalhes.getEspecialidade());
        medico.setTelefone(detalhes.getTelefone());
        medico.setEmail(detalhes.getEmail());
        medico.setAgendaDisponivel(detalhes.getAgendaDisponivel());
        return repository.save(medico);
    }

    public void deletar(Long id) {
        repository.delete(buscarPorId(id));
    }
}
