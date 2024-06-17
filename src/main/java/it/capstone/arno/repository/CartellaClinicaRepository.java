package it.capstone.arno.repository;

import it.capstone.arno.model.CartellaClinica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartellaClinicaRepository extends JpaRepository<CartellaClinica, Long> {
}
