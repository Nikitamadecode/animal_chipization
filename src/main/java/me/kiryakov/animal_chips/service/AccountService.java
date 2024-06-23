package me.kiryakov.animal_chips.service;

import jakarta.persistence.criteria.CriteriaQuery;
import me.kiryakov.animal_chips.domain.Account;
import me.kiryakov.animal_chips.dto.AccountDTO;
import me.kiryakov.animal_chips.dto.AccountSearchDTO;
import me.kiryakov.animal_chips.dto.RegistrationRequest;
import me.kiryakov.animal_chips.dto.RegistrationResponse;
import me.kiryakov.animal_chips.exception.DataConflictException;
import me.kiryakov.animal_chips.exception.InaccessibleEntityException;
import me.kiryakov.animal_chips.exception.InvalidDataException;
import me.kiryakov.animal_chips.exception.NotFoundException;
import me.kiryakov.animal_chips.mapper.AccountMapper;
import me.kiryakov.animal_chips.repository.AccountRepository;
import me.kiryakov.animal_chips.repository.EntityRepository;
import me.kiryakov.animal_chips.util.CriteriaManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    private PasswordEncoder passwordEncoder;

    public RegistrationResponse createAccount(RegistrationRequest registrationRequest) {
        boolean b = accountRepository.existsByEmail(registrationRequest.getEmail());
        if (b) {
            throw new InaccessibleEntityException(String.format("Account with email %s already exists", registrationRequest.getEmail()));
        }
        Account account = new Account();
        account.setFirstName(registrationRequest.getFirstName());
        account.setLastName(registrationRequest.getLastName());
        account.setEmail(registrationRequest.getEmail());
        String password = passwordEncoder.encode(registrationRequest.getPassword());
        account.setPassword(password);
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

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new InvalidDataException("Account with this this id not found"));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.isAuthenticated()){
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String email = userDetails.getUsername();
            if (!account.getEmail().equals(email)) {
                throw new InvalidDataException("Its not your account biach");
            }
        }

        account.setFirstName(accountDTO.getFirstName());
        account.setLastName(accountDTO.getLastName());
        account.setEmail(accountDTO.getEmail());
        String password = passwordEncoder.encode(accountDTO.getPassword());
        account.setPassword(password);
        Account save = accountRepository.save(account);
        return accountMapper.toDTO(save);
    }

    public void deleteAccount(Integer id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new InvalidDataException("No account with id " + id + " found"));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.isAuthenticated()){
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String email = userDetails.getUsername();
            if (!account.getEmail().equals(email)) {
                throw new InvalidDataException("Its not your account biach freal");
            }
        }
        try {
            accountRepository.delete(account);
        } catch (Exception e) {
            throw new DataConflictException("");
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
