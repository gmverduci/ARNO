package it.capstone.arno.controller;

import it.capstone.arno.DTO.PazienteDTO;
import it.capstone.arno.model.Paziente;
import it.capstone.arno.model.Utente;
import it.capstone.arno.service.PazienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PazienteController {

    @Autowired
    PazienteService pazienteService;

    @GetMapping("/pazienti-p")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INFERMIERE', 'MEDICO', 'OSS')")
    public ResponseEntity<Page<Paziente>> getPazientiPageable(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size,
                                                              @RequestParam(defaultValue = "id") String sortBy){
        Page<Paziente> pazientiPage = pazienteService.getAllPazientiPageable(page, size, sortBy);
        return ResponseEntity.ok().body(pazientiPage);
    }

    @GetMapping("/pazienti")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INFERMIERE', 'MEDICO', 'OSS')")
    public ResponseEntity<List<Paziente>> getPazienti() {
        List<Paziente> pazienti = pazienteService.getAllPazienti();
        return ResponseEntity.ok().body(pazienti);
    }

    @GetMapping("/pazienti/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INFERMIERE', 'MEDICO', 'OSS')")
    public ResponseEntity<Paziente> getPazienteById(@PathVariable("id") int id) {
        Paziente paziente = pazienteService.getPazienteById(id);
        return ResponseEntity.ok().body(paziente);
    }

    @GetMapping("/pazienti/cf/{codiceFiscale}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INFERMIERE', 'MEDICO', 'OSS')")
    public ResponseEntity<Paziente> getPazienteByCodiceFiscale(@PathVariable("codiceFiscale") String codiceFiscale) {
        Paziente paziente = pazienteService.getPazienteByCodiceFiscale(codiceFiscale);
        return ResponseEntity.ok().body(paziente);
    }

    @PostMapping("/pazienti")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INFERMIERE', 'MEDICO')")
    public ResponseEntity<String> savePaziente(@Valid @RequestBody PazienteDTO pazienteDTO) {
        String result = pazienteService.savePaziente(pazienteDTO);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/pazienti/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INFERMIERE', 'MEDICO')")
    public ResponseEntity<String> updatePaziente(@PathVariable("id") int id, @Valid @RequestBody PazienteDTO pazienteDTO) {
        String result = pazienteService.updatePaziente(id, pazienteDTO);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/pazienti/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INFERMIERE', 'MEDICO')")
    public ResponseEntity<String> deletePaziente(@PathVariable("id") int id) {
        pazienteService.deletePaziente(id);
        return ResponseEntity.ok().body("Paziente con ID: " + id + " eliminato correttamente.");
    }
}

