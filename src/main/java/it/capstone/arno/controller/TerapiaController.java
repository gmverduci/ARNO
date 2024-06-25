package it.capstone.arno.controller;

import it.capstone.arno.DTO.TerapiaDTO;
import it.capstone.arno.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class TerapiaController {

    @Autowired
    TerapiaService terapiaService;

    @Autowired
    TerapiaService terapiaService;

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
