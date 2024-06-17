package it.capstone.arno.model;

import it.capstone.arno.enums.StatoRicovero;
import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Ricovero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paziente_id")
    private Paziente paziente;

    private LocalDateTime dataInizio;
    private LocalDateTime dataFine;
    @Enumerated(EnumType.STRING)
    private StatoRicovero stato;

    @OneToOne(mappedBy = "ricovero", cascade = CascadeType.ALL, orphanRemoval = true)
    private SchedaRischio schedaRischio;


}