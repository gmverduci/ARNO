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
public class UtenteDTO {



    @NotBlank(message = "Inserire nome.")
    @Size(max = 28, message = "Lunghezza max. nome: 28 caratteri.")
    private String nome;

    @NotBlank(message = "Inserire cognome.")
    @Size(max = 28, message = "Lunghezza max. cognome: 28 caratteri.")
    private String cognome;

    private Sesso sesso;

    @NotBlank(message = "Inserire indirizzo.")
    @Size(max = 28, message = "Lunghezza max. indirizzo: 28 caratteri.")
    private String indirizzo;

    @NotBlank(message = "Inserire numero di telefono.")
    @Size(max = 12, message = "Lunghezza max. numero di telefono: 12 caratteri.")
    private String numeroTelefono;

    @NotBlank(message = "Inserire codice fiscale.")
    private String codiceFiscale;


    @NotNull(message = "Seleziona ruolo.")
    private Ruolo ruolo;

    @NotBlank(message = "Inserire username.")
    @Size(max = 14, message = "Lunghezza max. username: 14 caratteri.")
    private String username;

    @Email(regexp ="(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])" )
    @NotBlank(message = "Inserire email.")
    private String email;

    @NotBlank(message = "Inserire password.")
    private String password;


}
