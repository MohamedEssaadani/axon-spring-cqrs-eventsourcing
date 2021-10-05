package com.essaadani.bankingapp.query.service;

import com.essaadani.bankingapp.coreapi.enums.AccountStatus;
import com.essaadani.bankingapp.coreapi.events.AccountCreatedEvent;
import com.essaadani.bankingapp.coreapi.events.AccountCreditedEvent;
import com.essaadani.bankingapp.query.entities.Account;
import com.essaadani.bankingapp.query.repository.AccountOperationRepository;
import com.essaadani.bankingapp.query.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventHandlerService {
    private final AccountRepository accountRepository;
    private final AccountOperationRepository accountOperationRepository;

    @EventHandler
    public void on(AccountCreatedEvent event){
        log.info("******************QUERY SIDE******************");
        log.info("AccountCreatedEvent occured");

        Account account = new Account();
        account.setId(event.getId());
        account.setBalance(event.getInitialBalance());
        account.setCurrency(event.getCurrency());
        account.setStatus(AccountStatus.CREATED);

        accountRepository.save(account);
    }
}
