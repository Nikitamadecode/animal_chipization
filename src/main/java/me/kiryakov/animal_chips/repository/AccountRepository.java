package me.kiryakov.animal_chips.repository;

import me.kiryakov.animal_chips.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    boolean existsByEmail(String email);

    Optional<Account> findByEmail(String email);
}
