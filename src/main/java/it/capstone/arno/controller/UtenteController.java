package it.capstone.arno.controller;

import it.capstone.arno.DTO.UtenteDTO;
import it.capstone.arno.exception.BadRequestException;
import it.capstone.arno.model.Utente;
import it.capstone.arno.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UtenteController {

    @Autowired
    UtenteService utenteService;

    @GetMapping("/users")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Page<Utente>> getUtentiPageable(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size,
                                                          @RequestParam(defaultValue = "id") String sortBy){
        Page<Utente> utenti = utenteService.getAllUtentiPageable(page, size, sortBy);
        return ResponseEntity.ok(utenti);
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Utente> getUtenteById(@PathVariable int id) {
        Utente utente = utenteService.getUtenteById(id);
        if (utente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(utente);
    }

    @PutMapping("/users/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Utente> updateUtente(@PathVariable int id, @RequestBody @Validated UtenteDTO utenteDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage()).reduce("", (s, s2) -> s + s2));
        }

        Utente updatedUtente = utenteService.updateUtente(utenteDTO, id);
        if (updatedUtente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUtente);
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<String> deleteUtente(@PathVariable int id){
        String result = utenteService.deleteUtente(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/users/search")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<List<Utente>> getUtentiByNomeOCognomeParziale(@RequestParam(required = false) String nome, @RequestParam(required = false) String cognome) {
        List<Utente> utenti = utenteService.getUtenteByNomeOCognomeParziale(nome, cognome);
        return ResponseEntity.ok(utenti);
    }
}
