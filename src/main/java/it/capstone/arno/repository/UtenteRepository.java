package it.capstone.arno.repository;

import it.capstone.arno.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {
    public Optional<Utente> findByEmail(String email);
    public Optional<Utente> findByUsername(String username);

    public List<Utente> findByUsernameContaining (String usernameParziale);
}
