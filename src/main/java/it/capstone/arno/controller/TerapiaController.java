package it.capstone.arno.controller;

import it.capstone.arno.DTO.TerapiaDTO;
import it.capstone.arno.exception.BadRequestException;
import it.capstone.arno.model.Ordine;
import it.capstone.arno.model.Terapia;
import it.capstone.arno.service.TerapiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class TerapiaController {

    @Autowired
    TerapiaService terapiaService;


    @GetMapping("/terapie/paziente/{idPaziente}/incorso")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INFERMIERE', 'MEDICO', 'OSS')")
    public ResponseEntity<List<Terapia>> getTerapieByPazienteAndRicoveroInCorso(@PathVariable int idPaziente) {
        List<Terapia> terapia = terapiaService.getTerapieByPazienteAndRicoveroInCorso(idPaziente);
        return ResponseEntity.ok(terapia);
    }

    @PostMapping("/terapie")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INFERMIERE', 'MEDICO')")
    public ResponseEntity<String> saveTerapia(@Validated @RequestBody TerapiaDTO terapiaDTO,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage()).reduce("", (s, s2) -> s + s2));
        }
        String result = terapiaService.saveTerapia(terapiaDTO);
        return ResponseEntity.ok(result);
    }

    // Altri metodi del controller per update e delete
}
