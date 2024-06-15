package me.kiryakov.animal_chips.service;

import jakarta.persistence.criteria.CriteriaQuery;
import me.kiryakov.animal_chips.domain.Account;
import me.kiryakov.animal_chips.dto.AccountDTO;
import me.kiryakov.animal_chips.dto.AccountSearchDTO;
import me.kiryakov.animal_chips.dto.RegistrationRequest;
import me.kiryakov.animal_chips.dto.RegistrationResponse;
import me.kiryakov.animal_chips.exception.NotFoundException;
import me.kiryakov.animal_chips.mapper.AccountMapper;
import me.kiryakov.animal_chips.repository.AccountRepository;
import me.kiryakov.animal_chips.repository.EntityRepository;
import me.kiryakov.animal_chips.util.CriteriaManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CriteriaManager criteriaManager;
    @Autowired
    private EntityRepository entityRepository;
    @Autowired
    private AccountMapper accountMapper;

    public RegistrationResponse createAccount(RegistrationRequest registrationRequest) {
        Account account = new Account();
        account.setFirstName(registrationRequest.getFirstName());
        account.setLastName(registrationRequest.getLastName());
        account.setEmail(registrationRequest.getEmail());
        account.setPassword(registrationRequest.getPassword());
        accountRepository.save(account);
        RegistrationResponse registrationResponse = new RegistrationResponse();
        registrationResponse.setId(account.getId());
        registrationResponse.setFirstName(account.getFirstName());
        registrationResponse.setLastName(account.getLastName());
        registrationResponse.setEmail(account.getEmail());
        return registrationResponse;
    }

    public AccountDTO getAccountById(Integer id) {

        Optional<Account> optional = accountRepository.findById(id);
        if (optional.isPresent()) {
            Account account = optional.get();
            AccountDTO dto = new AccountDTO();
            dto.setId(account.getId());
            dto.setFirstName(account.getFirstName());
            dto.setLastName(account.getLastName());
            dto.setEmail(account.getEmail());
            return dto;
        }
        throw new NotFoundException("No account with id " + id + " found");
    }

    public AccountDTO editAccount(Integer id, AccountDTO accountDTO) {
        Optional<Account> optional = accountRepository.findById(id);
        if (optional.isPresent()) {
            Account account = optional.get();
            account.setFirstName(accountDTO.getFirstName());
            account.setLastName(accountDTO.getLastName());
            account.setEmail(accountDTO.getEmail());
            accountRepository.save(account);
            return accountDTO;
        }
        throw new NotFoundException("No account with id " + id + " found");
    }

    public void deleteAccount(Integer id) {
        Optional<Account> optional = accountRepository.findById(id);
        if (optional.isPresent()) {
            Account account = optional.get();
            accountRepository.delete(account);
        }
    }

    public List<AccountDTO> search(AccountSearchDTO dto) {
        Map<String, Object> params = new HashMap<>();
        params.put("firstName", dto.getFirstName());
        params.put("lastName", dto.getLastName());
        params.put("email", dto.getEmail());

        CriteriaQuery<Account> criteria = criteriaManager.buildCriteria(params, Account.class);
        List<Account> accounts = entityRepository.searchByCriteria(criteria, dto.getFrom(), dto.getSize());

        return accounts.stream()
                .map(account -> accountMapper.toDTO(account))
                .collect(Collectors.toList());
    }
}
