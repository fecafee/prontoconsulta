package com.prontoconsulta.controller;

import com.prontoconsulta.entity.Agenda;
import com.prontoconsulta.service.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/agendas")
public class AgendaController {

    @Autowired
    private AgendaService service;

    @GetMapping
    public List<Agenda> listarTodas() {
        return service.buscarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agenda> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/medico/{id}")
    public List<Agenda> buscarPorMedico(@PathVariable Long id) {
        return service.buscarPorMedico(id);
    }

    @GetMapping("/medico/{id}/data")
    public List<Agenda> buscarPorMedicoEData(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        return service.buscarPorMedicoEData(id, data);
    }

    @PostMapping
    public ResponseEntity<Agenda> criar(@RequestBody Agenda agenda) {
        return new ResponseEntity<>(service.salvar(agenda), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Agenda> atualizar(@PathVariable Long id, @RequestBody Agenda agenda) {
        return ResponseEntity.ok(service.atualizar(id, agenda));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

