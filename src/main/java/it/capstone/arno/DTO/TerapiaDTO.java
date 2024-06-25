package it.capstone.arno.DTO;

import it.capstone.arno.model.Paziente;
import it.capstone.arno.model.Utente;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TerapiaDTO {

    private Paziente paziente;

    @NotBlank(message = "Nome farmaco obbligatorio.")
    private String nomeFarmaco;

    @NotBlank(message = "Dosaggio obbligatorio.")
    private String dosaggio;

    @NotBlank(message = "Frequenza obbligatoria.")
    private String frequenza;

    private LocalDateTime dataInizio;
    private LocalDateTime dataFine;
    private String note;

    @NotBlank(message = "La terapia deve essere assegnata da un utente con privilegio minimo 'MEDICO'.")
    Utente utente;
}
