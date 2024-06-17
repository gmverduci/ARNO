package it.capstone.arno.model;

import it.capstone.arno.enums.StatoOrdine;
import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ordini")
public class Ordine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paziente_id")
    private Paziente paziente;

    @ManyToOne
    @JoinColumn(name = "cartella_clinica_id")
    private CartellaClinica cartellaClinica;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    private LocalDateTime dataCreazione;
    private String tipo;
    private String dettagli;
    private StatoOrdine stato;
}
