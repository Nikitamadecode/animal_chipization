package me.kiryakov.animal_chips.controller;

import jakarta.validation.Valid;
import me.kiryakov.animal_chips.dto.RegistrationRequest;
import me.kiryakov.animal_chips.dto.RegistrationResponse;
import me.kiryakov.animal_chips.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(path = "/registration")
public class RegistrationController {
    @Autowired
    private AccountService accountService;

    @PostMapping

    public ResponseEntity<RegistrationResponse> createNewUser(@RequestBody @Valid RegistrationRequest registrationRequest) {

        RegistrationResponse accountDTO = accountService.createAccount(registrationRequest);
        return new ResponseEntity<>(accountDTO, HttpStatus.CREATED);
    }
}
