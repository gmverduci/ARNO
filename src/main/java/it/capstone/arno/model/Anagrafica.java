package it.capstone.arno.model;

import it.capstone.arno.enums.Sesso;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Anagrafica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cognome;
    private LocalDateTime dataNascita;

    @Enumerated(EnumType.STRING)
    private Sesso sesso;

    private String codiceFiscale;
    private String indirizzo;
    private String numeroTelefono;
    private String numeroTelefonoContatto;
    private String email;
    private LocalDateTime dataRegistrazione;
}