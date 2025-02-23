package com.desafioitau.vaga99.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "transacao")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transacao {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    UUID id;

    @Column(name = "VALOR", nullable = false)
    Double valor;

    @Column(name = "DATAHORA", nullable = false)
    OffsetDateTime dataHora;
}
