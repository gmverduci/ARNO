package it.capstone.arno.service;


import it.capstone.arno.DTO.OrdineDTO;
import it.capstone.arno.enums.StatoOrdine;
import it.capstone.arno.exception.BadRequestException;
import it.capstone.arno.exception.NotFoundException;
import it.capstone.arno.model.Ordine;
import it.capstone.arno.repository.OrdineRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrdineService {

    @Autowired
    private OrdineRepository ordineRepository;

    public String saveOrdine(@Valid OrdineDTO ordineDTO) {
        Ordine ordine = new Ordine();
        ordine.setTipo(ordineDTO.getTipo());
        ordine.setDettagli(ordineDTO.getDettagli());
        ordine.setUtente(ordineDTO.getUtente());
        ordine.setDataCreazione(LocalDateTime.now());
        ordine.setStato(StatoOrdine.NUOVO);

        return "Ordine creato con ID: " + ordine.getId() + ".";
    }

    public Ordine updateOrdine(int id, @Valid OrdineDTO ordineDTO) {
        Optional<Ordine> optionalOrdine = ordineRepository.findById(id);
        if (optionalOrdine.isPresent()) {
            Ordine ordine = optionalOrdine.get();
            ordine.setTipo(ordineDTO.getTipo());
            ordine.setDettagli(ordineDTO.getDettagli());
            ordine.setUtente(ordineDTO.getUtente());

            ordineRepository.save(ordine);
            return ordine;
        } else {
            throw new NotFoundException("Ordine non trovato con ID: " + id);
        }
    }

    public String deleteOrdine(int id) {
        Optional<Ordine> optionalOrdine = ordineRepository.findById(id);
        if (optionalOrdine.isPresent()) {
            ordineRepository.deleteById(id);
            return "Ordine eliminato con successo.";
        } else {
            throw new NotFoundException("Ordine non trovato con ID: " + id);
        }
    }

    public Page<Ordine> getAllOrdiniPageable(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ordineRepository.findAll(pageable);
    }

    public List<Ordine> getOrdiniByPaziente(Long id) {
        return ordineRepository.findByPazienteId(id);
    }

    public List<Ordine> getOrdiniByStato(StatoOrdine stato) {
        return ordineRepository.findByStato(stato);
    }
}
