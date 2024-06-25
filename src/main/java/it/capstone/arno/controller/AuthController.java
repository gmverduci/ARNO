package it.capstone.arno.controller;

import it.capstone.arno.DTO.UtenteDTO;
import it.capstone.arno.DTO.UtenteLoginDTO;
import it.capstone.arno.exception.BadRequestException;
import it.capstone.arno.security.AuthenticationResponse;
import it.capstone.arno.service.AuthService;
import it.capstone.arno.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> utenteSignupDTO(@RequestBody @Validated UtenteDTO utenteDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .reduce("", (s, s2) -> s + s2));
        }

        String result = utenteService.saveUtente(utenteDTO);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Validated UtenteLoginDTO utenteLoginDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .reduce("", (s, s2) -> s + s2));
        }

        AuthenticationResponse response = authService.authenticateUserAndCreateToken(utenteLoginDTO);
        return ResponseEntity.ok(response);
    }
}
