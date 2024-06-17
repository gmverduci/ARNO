package it.capstone.arno.repository;

import it.capstone.arno.model.SchedaRischio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedaRischioRepository extends JpaRepository<SchedaRischio, Long> {
}
