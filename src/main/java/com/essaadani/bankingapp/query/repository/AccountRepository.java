package com.essaadani.bankingapp.query.repository;

import com.essaadani.bankingapp.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
