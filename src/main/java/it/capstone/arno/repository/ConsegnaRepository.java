package it.capstone.arno.repository;


import it.capstone.arno.enums.StatoRicovero;
import it.capstone.arno.model.Consegna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ConsegnaRepository extends JpaRepository<Consegna, Integer> {
    List<Consegna> findByPazienteId(int id);

    List<Consegna> findByDataCreazione(LocalDate data);

    @Query("SELECT c FROM Consegna c " +
            "JOIN c.cartellaClinica cc " +
            "JOIN cc.ricovero r " +
            "WHERE c.paziente.id = :pazienteId AND r.stato = :statoRicovero")
    List<Consegna> findByPazienteIdAndRicoveroStato(@Param("pazienteId") int pazienteId, @Param("statoRicovero") StatoRicovero statoRicovero);
}
