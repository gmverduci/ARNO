package it.capstone.arno.controller;

import it.capstone.arno.DTO.ConsegnaDTO;
import it.capstone.arno.exception.BadRequestException;
import it.capstone.arno.model.Consegna;
import it.capstone.arno.service.ConsegnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ConsegnaController {

    @Autowired
    ConsegnaService consegnaService;

    @GetMapping("/consegne")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INFERMIERE', 'MEDICO', 'OSS')")
    public ResponseEntity<Page<Consegna>> getConsegne(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "20") int size,
                                                      @RequestParam(defaultValue = "id") String sortBy) {
        Page<Consegna> consegne = consegnaService.getAllConsegnePageable(page, size, sortBy);
        return ResponseEntity.ok(consegne);
    }

    @GetMapping("/consegne/paziente/{idPaziente}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INFERMIERE', 'MEDICO', 'OSS')")
    public ResponseEntity<List<Consegna>> getConsegneByPaziente(@PathVariable int idPaziente) {
        List<Consegna> consegne = consegnaService.getConsegneByPaziente(idPaziente);
        return ResponseEntity.ok(consegne);
    }

    @GetMapping("/consegne/data/{data}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INFERMIERE', 'MEDICO', 'OSS')")
    public ResponseEntity<List<Consegna>> getConsegneByData(@PathVariable String data) {
        LocalDate date;
        try {
            date = LocalDate.parse(data);
        } catch (Exception e) {
            throw new BadRequestException("Formato data non valido. Utilizzare il formato yyyy-MM-dd.");
        }

        List<Consegna> consegne = consegnaService.getConsegneByData(date);
        if (consegne.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(consegne);
    }

    @PutMapping("/consegne/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INFERMIERE', 'MEDICO')")
    public ResponseEntity<Consegna> updateConsegna(@PathVariable int id,
                                                   @RequestBody @Validated ConsegnaDTO consegnaDTO,
                                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage()).reduce("", (s, s2) -> s + s2));
        }

        Consegna updatedConsegna = consegnaService.updateConsegna(id, consegnaDTO);
        return ResponseEntity.ok(updatedConsegna);
    }

    @DeleteMapping("/consegne/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INFERMIERE', 'MEDICO')")
    public ResponseEntity<String> deleteConsegna(@PathVariable int id) {
        String result = consegnaService.deleteConsegna(id);
        return ResponseEntity.ok(result);
    }
}
