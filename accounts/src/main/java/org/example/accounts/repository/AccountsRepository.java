package org.example.accounts.repository;

import jakarta.transaction.Transactional;
import org.example.accounts.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface AccountsRepository extends JpaRepository<Accounts, Long> {
    Optional<Accounts> findByCustomerID(Long customerId);

    Optional<Accounts> findByAccountNumber(Long accountNumber);

    @Transactional
    @Modifying
    void deleteByCustomerID(Long customerID);
}
