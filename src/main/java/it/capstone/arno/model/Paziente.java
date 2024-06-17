package it.capstone.arno.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class Paziente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "anagrafica_id")
    private Anagrafica anagrafica;
}
