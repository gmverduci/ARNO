package it.capstone.arno.controller;

import it.capstone.arno.DTO.OrdineDTO;
import it.capstone.arno.enums.StatoOrdine;
import it.capstone.arno.exception.BadRequestException;
import it.capstone.arno.model.Ordine;
import it.capstone.arno.service.OrdineService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class OrdineController {

    @Autowired
    private OrdineService ordineService;

    @GetMapping("/ordini")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INFERMIERE', 'MEDICO', 'OSS')")
    public ResponseEntity<Page<Ordine>> getOrdini(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "20") int size,
                                                  @RequestParam(defaultValue = "id") String sortBy) {
        Page<Ordine> ordini = ordineService.getAllOrdiniPageable(page, size, sortBy);
        return ResponseEntity.ok(ordini);
    }

    @GetMapping("/ordini/paziente/{idPaziente}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INFERMIERE', 'MEDICO', 'OSS')")
    public ResponseEntity<List<Ordine>> getOrdiniByPaziente(@PathVariable Long idPaziente) {
        List<Ordine> ordini = ordineService.getOrdiniByPaziente(idPaziente);
        return ResponseEntity.ok(ordini);
    }

    @GetMapping("/ordini/stato/{stato}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INFERMIERE', 'MEDICO', 'OSS')")
    public ResponseEntity<List<Ordine>> getOrdiniByStato(@PathVariable String stato) {
        try {
            StatoOrdine statoOrdine = StatoOrdine.valueOf(stato.toUpperCase());
            List<Ordine> ordini = ordineService.getOrdiniByStato(statoOrdine);
            return ResponseEntity.ok(ordini);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Stato ordine non valido: " + stato);
        }
    }

    @PutMapping("/ordini/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INFERMIERE', 'MEDICO')")
    public ResponseEntity<Ordine> updateOrdine(@PathVariable int id,
                                               @RequestBody @Validated OrdineDTO ordineDTO,
                                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage()).reduce("", (s1, s2) -> s1 + s2));
        }

        Ordine updatedOrdine = ordineService.updateOrdine(id, ordineDTO);
        return ResponseEntity.ok(updatedOrdine);
    }

    @DeleteMapping("/ordini/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INFERMIERE', 'MEDICO')")
    public ResponseEntity<String> deleteOrdine(@PathVariable int id) {
        String result = ordineService.deleteOrdine(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/ordini")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INFERMIERE', 'MEDICO')")
    public ResponseEntity<String> saveOrdine(@Valid @RequestBody OrdineDTO ordineDTO,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage()).reduce("", (s1, s2) -> s1 + s2));
        }

        String result = ordineService.saveOrdine(ordineDTO);
        return ResponseEntity.ok(result);
    }
}

