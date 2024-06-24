package it.capstone.arno.service;

import it.capstone.arno.DTO.ConsegnaDTO;
import it.capstone.arno.DTO.PazienteDTO;
import it.capstone.arno.enums.StatoRicovero;
import it.capstone.arno.exception.BadRequestException;
import it.capstone.arno.exception.NotFoundException;
import it.capstone.arno.model.*;
import it.capstone.arno.repository.CartellaClinicaRepository;
import it.capstone.arno.repository.ConsegnaRepository;
import it.capstone.arno.repository.RicoveroRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

public class ConsegnaService {


    @Autowired
    RicoveroRepository ricoveroRepository;

    @Autowired
    CartellaClinicaRepository cartellaClinicaRepository;

    @Autowired
    ConsegnaRepository consegnaRepository;


    public String saveConsegna(@Valid ConsegnaDTO consegnaDTO) {
        Consegna consegna = new Consegna();
        consegna.setTitolo(consegnaDTO.getTitolo());
        consegna.setContenuto(consegnaDTO.getContenuto());
        consegna.setUtente(consegnaDTO.getUtente());
        if (consegnaDTO.getPaziente() != null) {
            Paziente paziente = consegnaDTO.getPaziente();

            Optional<Ricovero> optionalRicovero = ricoveroRepository.findTopByPazienteAndStatoOrderByDataInizioDesc(paziente, StatoRicovero.IN_CORSO);
            if (optionalRicovero.isPresent()) {
                Ricovero ricovero = optionalRicovero.get();
                consegna.setRicovero(ricovero);

                Optional<CartellaClinica> optionalCartellaClinica = cartellaClinicaRepository.findByRicovero(ricovero);
                if (optionalCartellaClinica.isPresent()) {
                    CartellaClinica cartellaClinica = optionalCartellaClinica.get();
                    consegna.setCartellaClinica(cartellaClinica);
                } else {
                    throw new NotFoundException("Cartella clinica non trovata per il ricovero specificato.");
                }
            } else {
                throw new NotFoundException("Ricovero in corso non trovato per il paziente specificato.");
            }
        }
        consegna.setDataCreazione(LocalDateTime.now());

        return "Consegna creata con ID: " + consegna.getId() + ".";
    }

    public String updateConsegna(int id, @Valid ConsegnaDTO consegnaDTO) {
        Optional<Consegna> optionalConsegna = consegnaRepository.findById(id);
        if (optionalConsegna.isPresent()) {
            Consegna consegna = optionalConsegna.get();
            consegna.setTitolo(consegnaDTO.getTitolo());
            consegna.setContenuto(consegnaDTO.getContenuto());
            consegna.setUtente(consegnaDTO.getUtente());

            consegnaRepository.save(consegna);
            return "Consegna aggiornata con successo.";
        } else {
            throw new NotFoundException("Consegna non trovata con ID: " + id);
        }
    }

    public String deleteConsegna(int id) {
        Optional<Consegna> optionalConsegna = consegnaRepository.findById(id);
        if (optionalConsegna.isPresent()) {
            consegnaRepository.deleteById(id);
            return "Consegna eliminata con successo.";
        } else {
            throw new NotFoundException("Consegna non trovata con ID: " + id);
        }
    }



}
