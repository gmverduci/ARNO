package it.capstone.arno.service;

import it.capstone.arno.DTO.TerapiaDTO;
import it.capstone.arno.exception.BadRequestException;
import it.capstone.arno.exception.NotFoundException;
import it.capstone.arno.model.CartellaClinica;
import it.capstone.arno.model.Paziente;
import it.capstone.arno.model.Terapia;
import it.capstone.arno.model.Utente;
import it.capstone.arno.repository.CartellaClinicaRepository;
import it.capstone.arno.repository.TerapiaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TerapiaService {

    @Autowired
    private TerapiaRepository terapiaRepository;

    @Autowired
    private CartellaClinicaRepository cartellaClinicaRepository;

    public String saveTerapia(@Valid TerapiaDTO terapiaDTO) {
        Terapia terapia = new Terapia();
        terapia.setNomeFarmaco(terapiaDTO.getNomeFarmaco());
        terapia.setDosaggio(terapiaDTO.getDosaggio());
        terapia.setFrequenza(terapiaDTO.getFrequenza());
        terapia.setDataInizio(LocalDateTime.now());
        terapia.setUtente(terapiaDTO.getUtente());
        terapia.setNote(terapiaDTO.getNote());

        if (terapiaDTO.getPaziente() != null) {
            Paziente paziente = terapiaDTO.getPaziente();
            terapia.setPaziente(paziente);

            // Trova CartellaClinica tramite Ricovero e Paziente
            Optional<CartellaClinica> optionalCartellaClinica = cartellaClinicaRepository.findByRicoveroPaziente(paziente);
            if (optionalCartellaClinica.isPresent()) {
                terapia.setCartellaClinica(optionalCartellaClinica.get());
            } else {
                throw new RuntimeException("Nessuna CartellaClinica trovata per il Paziente specificato");
            }
        }

        terapiaRepository.save(terapia);
        return "Terapia salvata con ID: " + terapia.getId();
    }


    public Terapia updateTerapia(int id, @Valid TerapiaDTO terapiaDTO) {
        Optional<Terapia> optionalTerapia = terapiaRepository.findById(id);
        if (optionalTerapia.isPresent()) {
            Terapia terapia = optionalTerapia.get();
            terapia.setNomeFarmaco(terapiaDTO.getNomeFarmaco());
            terapia.setDosaggio(terapiaDTO.getDosaggio());
            terapia.setFrequenza(terapiaDTO.getFrequenza());
            terapia.setDataFine(terapiaDTO.getDataFine());
            terapia.setUtente(terapiaDTO.getUtente());
            terapia.setNote(terapiaDTO.getNote());

            terapiaRepository.save(terapia);
            return terapia;
        } else {
            throw new NotFoundException("Terapia non trovata con ID: " + id);
        }
    }

    public String deleteTerapia(int id) {
        Optional<Terapia> optionalTerapia = terapiaRepository.findById(id);
        if (optionalTerapia.isPresent()) {
            terapiaRepository.deleteById(id);
            return "Terapia eliminata con successo.";
        } else {
            throw new NotFoundException("Terapia non trovata con ID: " + id);
        }
    }

    public List<Terapia> getTerapieByPaziente(int id) {
        return terapiaRepository.findByPazienteId(id);
    }
}
