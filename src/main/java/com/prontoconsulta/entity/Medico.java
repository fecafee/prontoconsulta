package com.prontoconsulta.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Medico {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Column(unique = true)
    private String crm;
    private String especialidade;
    private String telefone;
    private String email;
    private String agendaDisponivel;
}
