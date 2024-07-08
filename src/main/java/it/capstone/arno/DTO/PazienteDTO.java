package it.capstone.arno.DTO;

import it.capstone.arno.enums.Ruolo;
import it.capstone.arno.enums.Sesso;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PazienteDTO {

    @NotBlank(message = "Inserire nome.")
    @Size(max = 28, message = "Lunghezza max. nome: 28 caratteri.")
    private String nome;

    @NotBlank(message = "Inserire cognome.")
    @Size(max = 28, message = "Lunghezza max. cognome: 28 caratteri.")
    private String cognome;

    @NotNull(message = "Seleziona sesso.")
    private Sesso sesso;

    @NotNull(message = "Inserire data di nascita.")
    private LocalDate dataNascita;


    @NotBlank(message = "Inserire indirizzo.")
    @Size(max = 28, message = "Lunghezza max. indirizzo: 28 caratteri.")
    private String indirizzo;

    @NotBlank(message = "Inserire numero di telefono.")
    @Size(max = 12, message = "Lunghezza max. numero di telefono: 12 caratteri.")
    private String numeroTelefono;

    @NotBlank(message = "Inserire numero di telefono.")
    @Size(max = 12, message = "Lunghezza max. numero di telefono: 12 caratteri.")
    private String numeroTelefonoContatto;

    @NotBlank(message = "Inserire codice fiscale.")
    private String codiceFiscale;

}
