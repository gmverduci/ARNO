package it.capstone.arno.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UtenteLoginDTO {
    @NotBlank(message = "Inserire username.")
    private String username;

    @NotBlank(message = "Inserire la password.")
    private String password;
}
