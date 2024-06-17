package it.capstone.arno.repository;

import it.capstone.arno.model.Paziente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PazienteRepository extends JpaRepository<Paziente, Long> {


}

