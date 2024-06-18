package it.capstone.arno.service;

import it.capstone.arno.model.Utente;
import it.capstone.arno.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class UtenteService {

    @Autowired
    UtenteRepository utenteRepository;

    public Utente getUtenteByUsername(String username) {
        Optional<Utente> utenteOptional = utenteRepository.findByUsername(username);

        if (utenteOptional.isPresent()) {
            return utenteOptional.get();
        } else {
            throw new RuntimeException ("Utente " + username + " non trovato.");

        }
    }

    public List<Utente> getUserByUsernameContaining(String usernameParziale) {
        return utenteRepository.findByUsernameContaining(usernameParziale);
    }


}
