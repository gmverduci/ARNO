package it.capstone.arno.DTO;

import it.capstone.arno.enums.StatoOrdine;
import it.capstone.arno.model.Paziente;
import it.capstone.arno.model.Utente;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrdineDTO {

    private Paziente paziente;

    @NotBlank
    private StatoOrdine statoOrdine;

    @NotBlank(message = "Tipo ordine obbligatorio.")
    private String tipo;

    @NotBlank(message = "Dettagli ordine obbligatori.")
    private String dettagli;

    private Utente utente;
}
