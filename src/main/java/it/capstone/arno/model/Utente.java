package it.capstone.arno.model;

import it.capstone.arno.enums.Ruolo;
import it.capstone.arno.enums.StatoUtente;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "anagrafica_id")
    private Anagrafica anagrafica;

    @Enumerated(EnumType.STRING)
    private Ruolo ruolo;

    private String email;
    private String username;
    private String password;

    private LocalDateTime dataAssunzione;

    private StatoUtente statoUtente;

    private boolean darkMode;
}
