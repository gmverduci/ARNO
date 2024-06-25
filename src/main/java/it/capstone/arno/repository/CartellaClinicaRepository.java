package it.capstone.arno.repository;

import it.capstone.arno.model.CartellaClinica;
import it.capstone.arno.model.Paziente;
import it.capstone.arno.model.Ricovero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartellaClinicaRepository extends JpaRepository<CartellaClinica, Integer> {
    Optional<CartellaClinica> findByRicovero(Ricovero ricovero);

    CartellaClinica findByPaziente(Paziente paziente);
}
