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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
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
        consegna.setDataCreazione(LocalDateTime.now());

        if (consegnaDTO.getPaziente() != null) {
            Paziente paziente = consegnaDTO.getPaziente();
            consegna.setPaziente(paziente);

            Optional<CartellaClinica> optionalCartellaClinica = cartellaClinicaRepository.findByRicoveroPaziente(paziente);
            if (optionalCartellaClinica.isPresent()) {
                consegna.setCartellaClinica(optionalCartellaClinica.get());
            } else {
                throw new RuntimeException("Nessuna CartellaClinica trovata per il Paziente specificato");
            }
        }

        consegnaRepository.save(consegna);
        return "Consegna creata con ID: " + consegna.getId() + ".";
    }


    public Consegna updateConsegna(int id, @Valid ConsegnaDTO consegnaDTO) {
        Optional<Consegna> optionalConsegna = consegnaRepository.findById(id);
        if (optionalConsegna.isPresent()) {
            Consegna consegna = optionalConsegna.get();
            consegna.setTitolo(consegnaDTO.getTitolo());
            consegna.setContenuto(consegnaDTO.getContenuto());
            consegna.setUtente(consegnaDTO.getUtente());

            consegnaRepository.save(consegna);
            return consegna;
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

    public Page<Consegna> getAllConsegnePageable(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return consegnaRepository.findAll(pageable);
    }


    public List<Consegna> getConsegneByPaziente(int id) {
        return consegnaRepository.findByPazienteId(id);
    }

    public List<Consegna> getConsegneByData(LocalDate data) {
        return consegnaRepository.findByDataCreazione(data);
    }
}
