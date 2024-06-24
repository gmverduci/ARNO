package it.capstone.arno.controller;


import it.capstone.arno.DTO.UtenteDTO;
import it.capstone.arno.exception.BadRequestException;
import it.capstone.arno.model.Utente;
import it.capstone.arno.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UtenteController {

    @Autowired
    UtenteService utenteService;

    @GetMapping("/users")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Page<Utente> getUtenti(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "id") String sortBy){
        return utenteService.getAllUtentiPageable(page, size, sortBy);
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Utente getUtente(@PathVariable int id) {
        return utenteService.getUtenteById(id);
    }

    @PutMapping("/users/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Utente updateUtente(@PathVariable int id, @RequestBody @Validated UtenteDTO utenteDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage()).reduce("", (s, s2) -> s + s2));
        }
        return utenteService.updateUtente(utenteDTO, id);
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String deleteUser(@PathVariable int id){
        return utenteService.deleteUtente(id);
    }

    @GetMapping("/users/search")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public List<Utente> getUsersByNomeOCognomeParziale(@RequestParam(required = false) String nome, @RequestParam(required = false) String cognome) {
        return utenteService.getPazienteByNomeOCognomeParziale(nome, cognome);
    }




}
