package it.capstone.arno.repository;

import it.capstone.arno.model.Terapia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TerapiaRepository extends JpaRepository<Terapia, Long> {
}
