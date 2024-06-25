package it.capstone.arno.repository;

import it.capstone.arno.enums.StatoOrdine;
import it.capstone.arno.model.Ordine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdineRepository extends JpaRepository<Ordine, Integer> {
    List<Ordine> findByPazienteId(Long id);
    List<Ordine> findByStato(StatoOrdine stato);
}
