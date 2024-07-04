package it.capstone.arno.service;

import it.capstone.arno.DTO.UtenteLoginDTO;
import it.capstone.arno.exception.UnauthorizedException;
import it.capstone.arno.model.Utente;
import it.capstone.arno.security.AuthenticationResponse;
import it.capstone.arno.security.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UtenteService utenteService;
    @Autowired
    private JwtTool jwtTool;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public AuthenticationResponse authenticateUserAndCreateToken(UtenteLoginDTO utenteLoginDTO) {
        Utente user = utenteService.getUtenteByEmail(utenteLoginDTO.getEmail());

        if (passwordEncoder.matches(utenteLoginDTO.getPassword(), user.getPassword())) {

            String token = jwtTool.createToken(user);

            return new AuthenticationResponse(token, user);
        } else {
            throw new UnauthorizedException("Errore login: verificare le credenziali inserite.");
        }
    }
}
