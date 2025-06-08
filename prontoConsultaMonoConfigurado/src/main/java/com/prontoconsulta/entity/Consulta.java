package com.prontoconsulta.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;

@Data
@Entity
public class Consulta {
    public enum StatusConsulta {
        AGENDADA, REALIZADA, CANCELADA
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    private LocalDate data;
    private LocalTime hora;

    @Enumerated(EnumType.STRING)
    private StatusConsulta status;
}
