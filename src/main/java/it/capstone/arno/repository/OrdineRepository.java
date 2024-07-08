package it.capstone.arno.repository;

import it.capstone.arno.enums.StatoOrdine;
import it.capstone.arno.enums.StatoRicovero;
import it.capstone.arno.model.Ordine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdineRepository extends JpaRepository<Ordine, Integer> {
    List<Ordine> findByPazienteId(Long id);
    List<Ordine> findByStato(StatoOrdine stato);

    @Query("SELECT o FROM Ordine o " +
            "JOIN o.cartellaClinica cc " +
            "JOIN cc.ricovero r " +
            "WHERE o.paziente.id = :pazienteId AND r.stato = :statoRicovero")
    List<Ordine> findByPazienteIdAndRicoveroStato(@Param("pazienteId") int pazienteId, @Param("statoRicovero") StatoRicovero statoRicovero);

}
