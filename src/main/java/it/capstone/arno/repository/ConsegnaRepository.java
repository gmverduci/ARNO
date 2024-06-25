package it.capstone.arno.repository;


import it.capstone.arno.model.Consegna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ConsegnaRepository extends JpaRepository<Consegna, Integer> {
    List<Consegna> findByPazienteId(int id);

    List<Consegna> findByDataCreazione(LocalDate data);
}
