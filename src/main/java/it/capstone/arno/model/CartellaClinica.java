package it.capstone.arno.model;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
public class CartellaClinica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ricovero_id")
    private Ricovero ricovero;

    private String diagnosiIniziale;
    private String terapie;
    private String noteCliniche;


}
