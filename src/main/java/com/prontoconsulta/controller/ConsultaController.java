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

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService service;

    @GetMapping
    public ResponseEntity<List<Consulta>> getAll() {
        return ResponseEntity.ok(service.buscarTodas());
    }

    @GetMapping("/paciente/{id}")
    public ResponseEntity<List<Consulta>> getByPaciente(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorPaciente(id));
    }

    @GetMapping("/medico/{id}")
    public ResponseEntity<List<Consulta>> getByMedico(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorMedico(id));
    }

    @GetMapping("/data")
    public ResponseEntity<List<Consulta>> getByData(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        return ResponseEntity.ok(service.buscarPorData(data));
    }

    @GetMapping("/status")
    public ResponseEntity<List<Consulta>> getByStatus(@RequestParam StatusConsulta status) {
        return ResponseEntity.ok(service.buscarPorStatus(status));
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

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Consulta> cancelar(@PathVariable Long id) {
        Consulta consultaCancelada = service.cancelarConsulta(id);
        return ResponseEntity.ok(consultaCancelada);
    }
}
