package it.capstone.arno.model;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
public class SchedaRischio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paziente_id")
    private Paziente paziente;

    @ManyToOne
    @JoinColumn(name = "ricovero_id")
    private Ricovero ricovero;

    private String rischio;
    private String descrizione;
}
