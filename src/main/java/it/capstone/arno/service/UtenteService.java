package it.capstone.arno.service;

import ch.qos.logback.classic.encoder.JsonEncoder;
import it.capstone.arno.DTO.UtenteDTO;
import it.capstone.arno.enums.StatoUtente;
import it.capstone.arno.exception.BadRequestException;
import it.capstone.arno.exception.NotFoundException;
import it.capstone.arno.model.Anagrafica;
import it.capstone.arno.model.Utente;
import it.capstone.arno.repository.AnagraficaRepository;
import it.capstone.arno.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class UtenteService {

    @Autowired
    UtenteRepository utenteRepository;

    @Autowired
    AnagraficaRepository anagraficaRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MailSender mailSender;

    public Utente getUtenteByUsername(String username) {
        Optional<Utente> utenteOptional = utenteRepository.findByUsername(username);

        if (utenteOptional.isPresent()) {
            return utenteOptional.get();
        } else {
            throw new NotFoundException("Utente " + username + " non trovato.");

        }
    }

    public List<Utente> getUserByUsernameContaining(String usernameParziale) {
        return utenteRepository.findByUsernameContaining(usernameParziale);
    }

    public Utente getUtenteByEmail(String email) {
        Optional<Utente> userOptional = utenteRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new NotFoundException("Utente con email: " + email + " non trovato.");
        }
    }

    public Utente getUtenteById(Long id) {
        Optional<Utente> userOptional = utenteRepository.findById(id);

        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new NotFoundException("Utente con id: " + id + " non trovato.");
        }
    }

    public Page<Utente> getAllUtentiPageable(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return utenteRepository.findAll(pageable);
    }

    public List<Utente> getAllUtenti() {
        return utenteRepository.findAll();
    }

    public Utente saveUtente(UtenteDTO utenteDTO) {
        Optional<Utente> userOptionalByEmail = utenteRepository.findByEmail(utenteDTO.getEmail());

        if (userOptionalByEmail.isPresent()) {
            throw new BadRequestException("L'email risulta già associata a un account esistente.");
        }

        Optional<Utente> userOptionalByUsername = utenteRepository.findByUsername(utenteDTO.getUsername());

        if (userOptionalByUsername.isPresent()) {
            throw new BadRequestException("Lo username risulta già associato a un account esistente.");
        }

        Anagrafica anagrafica = new Anagrafica();
        anagrafica.setNome(utenteDTO.getNome());
        anagrafica.setCognome(utenteDTO.getCognome());
        anagrafica.setDataNascita(utenteDTO.getDataNascita());
        anagrafica.setSesso(utenteDTO.getSesso());
        anagrafica.setCodiceFiscale(utenteDTO.getCodiceFiscale());
        anagrafica.setIndirizzo(utenteDTO.getIndirizzo());
        anagrafica.setNumeroTelefono(utenteDTO.getNumeroTelefono());
        anagrafica.setDataRegistrazione(LocalDateTime.now());

        anagrafica = anagraficaRepository.save(anagrafica);

        Utente utente = new Utente();
        utente.setEmail(utenteDTO.getEmail());
        utente.setUsername(utenteDTO.getUsername());
        utente.setPassword(passwordEncoder.encode(utenteDTO.getPassword()));
        utente.setRuolo(utenteDTO.getRuolo());
        utente.setStatoUtente(StatoUtente.INATTIVO);

        utente.setAnagrafica(anagrafica);

        sendEmail(utenteDTO.getEmail(), "Registrazione Arno", "Benvenuto in ARNO. Ecco i tuoi dati di registrazione:" + "\n" + "\nUsername: " + utenteDTO.getUsername() + "\nRuolo: " + utenteDTO.getRuolo().toString());

        return utenteRepository.save(utente);
    }

    private void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }


}
