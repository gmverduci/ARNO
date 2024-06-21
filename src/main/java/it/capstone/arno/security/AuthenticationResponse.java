package it.capstone.arno.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.capstone.arno.model.Utente;
import lombok.Data;

@Data
public class AuthenticationResponse {

    private String token;

    @JsonIgnoreProperties(value = "password")
    private Utente user;

    public AuthenticationResponse(String token, Utente user) {
        this.token = token;
        this.user = user;
    }
}
