package it.capstone.arno.controller;


import it.capstone.arno.DTO.ConsegnaDTO;
import it.capstone.arno.DTO.UtenteDTO;
import it.capstone.arno.exception.BadRequestException;
import it.capstone.arno.model.Consegna;
import it.capstone.arno.model.Utente;
import it.capstone.arno.service.ConsegnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public Page<Consegna> getConsegne(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "20") int size,
                                      @RequestParam(defaultValue = "id") String sortBy){
        return consegnaService.getAllConsegnePageable(page, size, sortBy);

    }

    @GetMapping("/consegne/{idPaziente}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INFERMIERE', 'MEDICO', 'OSS')")
    public List<Consegna> getConsegneByPaziente(@PathVariable int idPaziente) {
        return  consegnaService.getConsegneByPaziente(idPaziente);
    }

    @GetMapping("/consegne/{data}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INFERMIERE', 'MEDICO', 'OSS')")
    public List<Consegna> getConsegneByData(@PathVariable LocalDate data) {
        return  consegnaService.getConsegneByData(data);
    }

    @PutMapping("/consegne/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INFERMIERE', 'MEDICO')")
    public Consegna updateConsegna(@PathVariable int id, @RequestBody @Validated ConsegnaDTO consegnaDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage()).reduce("", (s, s2) -> s + s2));
        }
        return consegnaService.updateConsegna(id, consegnaDTO);
    }

    @DeleteMapping("/consegne/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INFERMIERE', 'MEDICO')")
    public String deleteConsegna(@PathVariable int id){
        return consegnaService.deleteConsegna(id);
    }

}
