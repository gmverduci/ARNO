package it.capstone.arno.service;

import it.capstone.arno.DTO.PazienteDTO;
import it.capstone.arno.exception.BadRequestException;
import it.capstone.arno.exception.NotFoundException;
import it.capstone.arno.model.Anagrafica;
import it.capstone.arno.model.Paziente;
import it.capstone.arno.model.Utente;
import it.capstone.arno.repository.AnagraficaRepository;
import it.capstone.arno.repository.PazienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PazienteService {


    @Autowired
    PazienteRepository pazienteRepository;

    @Autowired
    AnagraficaRepository anagraficaRepository;

    @Autowired
    MailSender mailSender;


    public List<Paziente> getAllPazienti() {
        return pazienteRepository.findAll();
    }

    public Paziente getPazienteByCodiceFiscale(String codiceFiscale) {
        Optional<Paziente> pazienteOptional = pazienteRepository.findByAnagraficaCodiceFiscale(codiceFiscale);

        if (pazienteOptional.isPresent()) {
            return pazienteOptional.get();
        } else {
            throw new NotFoundException("Paziente con codice fiscale \"" + codiceFiscale + "\" non trovato.");

        }
    }

    public List<Paziente> getPazienteByNomeOCognomeParziale(String nomeParziale, String cognomeParziale) {
        if (nomeParziale == null) {
            nomeParziale = "";
        }
        if (cognomeParziale == null) {
            cognomeParziale = "";
        }
        return pazienteRepository.findByAnagraficaNomeContainingOrAnagraficaCognomeContaining(nomeParziale, cognomeParziale);
    }

    public Page<Paziente> getAllPazientiPageable(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return pazienteRepository.findAll(pageable);
    }

    public String savePaziente(@Valid PazienteDTO pazienteDTO) {
        Optional<Paziente> patientOptionalByEmail = pazienteRepository.findByAnagraficaCodiceFiscale(pazienteDTO.getCodiceFiscale());

        if (patientOptionalByEmail.isPresent()) {
            throw new BadRequestException("Paziente con codice fiscale \"" + pazienteDTO.getCodiceFiscale() + "\" già presente.");
        }

        Anagrafica anagrafica = new Anagrafica();
        anagrafica.setNome(pazienteDTO.getNome());
        anagrafica.setCognome(pazienteDTO.getCognome());
        anagrafica.setDataNascita(pazienteDTO.getDataNascita());
        anagrafica.setSesso(pazienteDTO.getSesso());
        anagrafica.setCodiceFiscale(pazienteDTO.getCodiceFiscale());
        anagrafica.setIndirizzo(pazienteDTO.getIndirizzo());
        anagrafica.setNumeroTelefono(pazienteDTO.getNumeroTelefono());
        anagrafica.setNumeroTelefonoContatto(pazienteDTO.getNumeroTelefonoContatto());
        anagrafica.setDataRegistrazione(LocalDateTime.now());
        anagrafica = anagraficaRepository.save(anagrafica);

        Paziente paziente = new Paziente();
        paziente.setAnagrafica(anagrafica);

        paziente = pazienteRepository.save(paziente);


        return "Paziente creato con id: " + paziente.getId() + ".";
    }

    public String updatePaziente(int id, @Valid PazienteDTO pazienteDTO) {
        Paziente paziente = pazienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paziente non trovato."));



        Anagrafica anagrafica = paziente.getAnagrafica();
            anagrafica.setNome(pazienteDTO.getNome());
            anagrafica.setCognome(pazienteDTO.getCognome());
            anagrafica.setDataNascita(pazienteDTO.getDataNascita());
            anagrafica.setSesso(pazienteDTO.getSesso());
            anagrafica.setCodiceFiscale(pazienteDTO.getCodiceFiscale());
            anagrafica.setIndirizzo(pazienteDTO.getIndirizzo());
            anagrafica.setNumeroTelefono(pazienteDTO.getNumeroTelefono());

        anagrafica = anagraficaRepository.save(anagrafica);

        paziente.setAnagrafica(anagrafica);
        paziente = pazienteRepository.save(paziente);

        return "Paziente con id: " + paziente.getId() + " aggiornato.";
    }

    public void deletePaziente (int id) {
        Paziente paziente = pazienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Paziente non trovato"));

        pazienteRepository.delete(paziente);
    }


    public Paziente getPazienteById(int id) {
        Optional<Paziente> pazienteOptional = pazienteRepository.findById(id);
        if (pazienteOptional.isPresent()) {
            return pazienteOptional.get();
        } else {
            throw new NotFoundException("Paziente con id \"" + id + "\" non trovato.");
        }
    }
}
