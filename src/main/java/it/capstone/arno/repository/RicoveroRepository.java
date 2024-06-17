package it.capstone.arno.repository;

import it.capstone.arno.model.Ricovero;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RicoveroRepository extends CrudRepository<Ricovero, Long> {
}