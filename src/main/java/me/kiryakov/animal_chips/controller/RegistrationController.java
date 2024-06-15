package me.kiryakov.animal_chips.controller;

import me.kiryakov.animal_chips.dto.RegistrationRequest;
import me.kiryakov.animal_chips.dto.RegistrationResponse;
import me.kiryakov.animal_chips.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/registration")
public class RegistrationController {
    @Autowired
    private AccountService accountService;

    @PostMapping

    public RegistrationResponse createNewUser(@RequestBody RegistrationRequest registrationRequest) {

        return accountService.createAccount(registrationRequest);

    }
}
