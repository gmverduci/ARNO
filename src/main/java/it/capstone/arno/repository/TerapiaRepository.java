package it.capstone.arno.repository;

import it.capstone.arno.enums.StatoRicovero;
import it.capstone.arno.model.Paziente;
import it.capstone.arno.model.Terapia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TerapiaRepository extends JpaRepository<Terapia, Integer> {
    List<Terapia> findByPazienteId(int id);

    @Query("SELECT t FROM Terapia t " +
            "JOIN t.cartellaClinica cc " +
            "JOIN cc.ricovero r " +
            "WHERE t.paziente.id = :pazienteId AND r.stato = :statoRicovero")
    List<Terapia> findByPazienteIdAndRicoveroStato(@Param("pazienteId") int pazienteId, @Param("statoRicovero") StatoRicovero statoRicovero);
}
