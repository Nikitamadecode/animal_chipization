package me.kiryakov.animal_chips.mapper;

import me.kiryakov.animal_chips.domain.Account;
import me.kiryakov.animal_chips.dto.AccountDTO;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public AccountDTO toDTO(Account account) {
        AccountDTO dto = new AccountDTO();
        dto.setId(account.getId());
        dto.setFirstName(account.getFirstName());
        dto.setLastName(account.getLastName());
        dto.setEmail(account.getEmail());

        return dto;
    }
}
