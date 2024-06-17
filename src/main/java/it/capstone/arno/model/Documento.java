package it.capstone.arno.model;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "documenti")
public class Documento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cartella_clinica_id")
    private CartellaClinica cartellaClinica;

    private String tipo;
    private String url;
    private LocalDateTime dataCaricamento;
}
