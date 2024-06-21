package it.capstone.arno.service;

import it.capstone.arno.DTO.UtenteDTO;
import it.capstone.arno.enums.Ruolo;
import it.capstone.arno.enums.StatoUtente;
import it.capstone.arno.exception.BadRequestException;
import it.capstone.arno.exception.NotFoundException;
import it.capstone.arno.exception.UnauthorizedException;
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

    public Utente getUtenteById(int id) {
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

    public String saveUtente(UtenteDTO utenteDTO) {
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

        utente = utenteRepository.save(utente);

        return "Utente id: " + utente.getId() + " e ruolo: " + utente.getRuolo().toString() + " creato con successo.";
    }

    public Utente updateUtente(int id, UtenteDTO utenteDTO) {
        Utente utente = utenteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utente non trovato"));

        if (utenteDTO.getEmail() != null) {
            Optional<Utente> userOptionalByEmail = utenteRepository.findByEmail(utenteDTO.getEmail());
            if (userOptionalByEmail.isPresent() && userOptionalByEmail.get().getId() != (id)) {
                throw new BadRequestException("L'email risulta già associata a un account esistente.");
            }
            utente.setEmail(utenteDTO.getEmail());
        }

        if (utenteDTO.getUsername() != null) {
            Optional<Utente> userOptionalByUsername = utenteRepository.findByUsername(utenteDTO.getUsername());
            if (userOptionalByUsername.isPresent() && userOptionalByUsername.get().getId() != (id)) {
                throw new BadRequestException("Lo username risulta già associato a un account esistente.");
            }
            utente.setUsername(utenteDTO.getUsername());
        }

        Anagrafica anagrafica = utente.getAnagrafica();
            anagrafica.setNome(utenteDTO.getNome());
            anagrafica.setCognome(utenteDTO.getCognome());
            anagrafica.setDataNascita(utenteDTO.getDataNascita());
            anagrafica.setSesso(utenteDTO.getSesso());
            anagrafica.setCodiceFiscale(utenteDTO.getCodiceFiscale());
            anagrafica.setIndirizzo(utenteDTO.getIndirizzo());
            anagrafica.setNumeroTelefono(utenteDTO.getNumeroTelefono());

        anagrafica = anagraficaRepository.save(anagrafica);

        if (utenteDTO.getPassword() != null && !utenteDTO.getPassword().isEmpty()) {
            utente.setPassword(passwordEncoder.encode(utenteDTO.getPassword()));
        }
        if (utenteDTO.getRuolo() != utente.getRuolo()) {
            throw new UnauthorizedException("Non si hanno i permessi per modificare il ruolo di un utente.");
        }

        return utenteRepository.save(utente);
    }

    public void deleteUtente(int id) {
        Utente utente = utenteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utente non trovato"));

        utenteRepository.delete(utente);
    }


    public void updateRuoloUtente(int id, Ruolo ruoloUtente) {
        Utente utente = utenteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utente non trovato"));

        utente.setRuolo(ruoloUtente);

        utenteRepository.save(utente);
    }


    private void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }


}
