package it.capstone.arno.repository;

import it.capstone.arno.enums.StatoRicovero;
import it.capstone.arno.model.Paziente;
import it.capstone.arno.model.Ricovero;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RicoveroRepository extends CrudRepository<Ricovero, Integer> {

    Optional<Ricovero> findTopByPazienteAndStatoOrderByDataInizioDesc(Paziente paziente, StatoRicovero stato);

}
