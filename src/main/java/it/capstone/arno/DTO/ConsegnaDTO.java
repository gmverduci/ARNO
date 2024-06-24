package it.capstone.arno.DTO;

import it.capstone.arno.model.Paziente;
import it.capstone.arno.model.Ricovero;
import it.capstone.arno.model.Utente;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ConsegnaDTO {


    private Ricovero ricovero;

    private Paziente paziente;

    @NotBlank(message = "La consegna dev'essere associata a un utente.")
    private Utente utente;

    @NotBlank(message = "Contnenuto consegna mancante.")
    private String contenuto;

    @NotBlank(message = "Titolo consegna mancante.")
    private String titolo;
}
