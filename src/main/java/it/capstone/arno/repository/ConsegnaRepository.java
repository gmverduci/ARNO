package it.capstone.arno.repository;


import it.capstone.arno.model.Consegna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsegnaRepository extends JpaRepository<Consegna, Long> {
}
