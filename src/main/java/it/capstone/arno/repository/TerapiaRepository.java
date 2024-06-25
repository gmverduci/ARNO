package it.capstone.arno.repository;

import it.capstone.arno.model.Paziente;
import it.capstone.arno.model.Terapia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TerapiaRepository extends JpaRepository<Terapia, Integer> {
    List<Terapia> findByPazienteId(int id);
}
