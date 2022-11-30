package com.application.meCash.repository;

import com.application.meCash.model.Account;
import com.application.meCash.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findAccountByAccountNumber(String accountNumber);

    Account findAccountByUser(User user);

    boolean existsByAccountNumber(String accountNumber);
}
