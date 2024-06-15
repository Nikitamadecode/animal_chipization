package me.kiryakov.animal_chips.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import me.kiryakov.animal_chips.dto.AccountDTO;
import me.kiryakov.animal_chips.dto.AccountSearchDTO;
import me.kiryakov.animal_chips.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Validated
@RestController
@RequestMapping(path = "/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping(path = "/{accountId}")
    public AccountDTO getById(@PathVariable @Min(1) Integer accountId) {
        return accountService.getAccountById(accountId);
    }
    @PutMapping(path = "/{accountId}")
    public AccountDTO updateById(@PathVariable @Min(1) Integer accountId, @RequestBody AccountDTO accountDTO) {
        return accountService.editAccount(accountId, accountDTO);
    }
    @DeleteMapping(path = "/{accountId}")
    public void deleteById(@PathVariable @Min(1) Integer accountId) {
        accountService.deleteAccount(accountId);
    }
    @GetMapping(path = "/search")
    public List<AccountDTO> search(@Valid AccountSearchDTO dto) {
        return accountService.search(dto);
    }
}
