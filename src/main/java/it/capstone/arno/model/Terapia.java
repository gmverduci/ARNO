package it.capstone.arno.model;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "terapie")
public class Terapia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "paziente_id")
    private Paziente paziente;

    @ManyToOne
    @JoinColumn(name = "cartella_clinica_id")
    private CartellaClinica cartellaClinica;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    private String nomeFarmaco;
    private String dosaggio;
    private String frequenza;
    private LocalDateTime dataInizio;
    private LocalDateTime dataFine;
    private String note;
}
