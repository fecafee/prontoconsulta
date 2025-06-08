package com.prontoconsulta.service;

import com.prontoconsulta.entity.Paciente;
import com.prontoconsulta.exception.ResourceNotFoundException;
import com.prontoconsulta.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PacienteService {
    @Autowired private PacienteRepository repository;

    public List<Paciente> buscarTodos() {
        return repository.findAll();
    }

    public Paciente buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado com o ID: " + id));
    }

    public Paciente salvar(Paciente paciente) {
        return repository.save(paciente);
    }

    public Paciente atualizar(Long id, Paciente detalhes) {
        Paciente paciente = buscarPorId(id);
        paciente.setNome(detalhes.getNome());
        paciente.setCpf(detalhes.getCpf());
        paciente.setDataNascimento(detalhes.getDataNascimento());
        paciente.setTelefone(detalhes.getTelefone());
        paciente.setEmail(detalhes.getEmail());
        paciente.setEndereco(detalhes.getEndereco());
        return repository.save(paciente);
    }

    public void deletar(Long id) {
        repository.delete(buscarPorId(id));
    }
}
