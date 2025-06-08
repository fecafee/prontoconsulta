package com.prontoconsulta.controller;

import com.prontoconsulta.entity.Consulta;
import com.prontoconsulta.entity.Consulta.StatusConsulta;
import com.prontoconsulta.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {
    @Autowired private ConsultaService service;

    @GetMapping("/paciente/{id}")
    public List<Consulta> getByPaciente(@PathVariable Long id) {
        return service.buscarPorPaciente(id);
    }

    @GetMapping("/medico/{id}")
    public List<Consulta> getByMedico(@PathVariable Long id) {
        return service.buscarPorMedico(id);
    }

    @GetMapping("/data")
    public List<Consulta> getByData(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        return service.buscarPorData(data);
    }

    @GetMapping("/status")
    public List<Consulta> getByStatus(@RequestParam StatusConsulta status) {
        return service.buscarPorStatus(status);
    }

    @PostMapping
    public ResponseEntity<Consulta> create(@RequestBody Consulta consulta) {
        return new ResponseEntity<>(service.salvar(consulta), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Consulta> update(@PathVariable Long id, @RequestBody Consulta detalhes) {
        return ResponseEntity.ok(service.atualizar(id, detalhes));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
