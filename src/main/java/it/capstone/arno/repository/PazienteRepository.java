package it.capstone.arno.repository;

import it.capstone.arno.model.Paziente;
import it.capstone.arno.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PazienteRepository extends JpaRepository<Paziente, Integer> {

    public Optional<Paziente> findByAnagraficaCodiceFiscale(String codiceFiscale);

    List<Paziente> findByAnagraficaNomeContainingOrAnagraficaCognomeContaining(String nomeParziale, String cognomeParziale);

}

