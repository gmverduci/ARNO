package it.capstone.arno.repository;

import it.capstone.arno.model.Paziente;
import it.capstone.arno.model.Utente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Integer> {
    public Optional<Utente> findByEmail(String email);


    public Optional<Utente> findByUsername(String username);

    Page<Utente> findByUsernameContainingPageable(String usernameParziale, Pageable pageable);


    public List<Utente> findByUsernameContaining (String usernameParziale);

    List<Utente> findByAnagraficaNomeContainingOrAnagraficaCognomeContaining(String nomeParziale, String cognomeParziale);

    List<Utente> findByAnagraficaNomeContaining(String nomeParziale);

    List<Utente> findByAnagraficaCognomeContaining(String cognomeParziale);
}
