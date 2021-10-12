package com.essaadani.bankingapp.query.service;

import com.essaadani.bankingapp.coreapi.enums.AccountStatus;
import com.essaadani.bankingapp.coreapi.events.AccountActivatedEvent;
import com.essaadani.bankingapp.coreapi.events.AccountCreatedEvent;
import com.essaadani.bankingapp.coreapi.events.AccountCreditedEvent;
import com.essaadani.bankingapp.coreapi.events.AccountDebitedEvent;
import com.essaadani.bankingapp.query.dto.AccountDTO;
import com.essaadani.bankingapp.query.entities.Account;
import com.essaadani.bankingapp.query.entities.AccountOperation;
import com.essaadani.bankingapp.query.enums.OperationType;
import com.essaadani.bankingapp.query.mappers.AccountMapper;
import com.essaadani.bankingapp.query.queries.GetAccountByIdQuery;
import com.essaadani.bankingapp.query.repository.AccountOperationRepository;
import com.essaadani.bankingapp.query.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventHandlerService {
    private final AccountRepository accountRepository;
    private final AccountOperationRepository accountOperationRepository;
    private final QueryUpdateEmitter emitter;
    private final AccountMapper mapper;

    @ResetHandler
    public void resetDatabase(){
        log.info("Reset Database...");
        accountRepository.deleteAll();
        accountOperationRepository.deleteAll();
    }

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

    @EventHandler
    @Transactional
    public void on(AccountActivatedEvent event){
        log.info("******************QUERY SIDE******************");
        log.info("AccountActivatedEvent occured");

        Account account = accountRepository.findById(event.getId()).get();
        account.setStatus(AccountStatus.ACTIVATED);

    }

    @EventHandler
    @Transactional
    public void on(AccountCreditedEvent event){
        log.info("******************QUERY SIDE******************");
        log.info("AccountCreditedEvent occured");

        Account account = accountRepository.findById(event.getId()).get();

        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setOperationDate(new Date());
        accountOperation.setAmount(event.getAmount());
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAccount(account);
        accountOperationRepository.save(accountOperation);

        account.setBalance(account.getBalance().add(event.getAmount()));
        Account savedAccount = accountRepository.save(account);

        AccountDTO accountDTO = mapper.fromAccount(savedAccount);

        emitter.emit(
                m -> ((GetAccountByIdQuery)m
                        .getPayload())
                        .getAccountId()
                        .equals(event.getId()),
                accountDTO
        );
    }

    @EventHandler
    @Transactional
    public void on(AccountDebitedEvent event){
        log.info("******************QUERY SIDE******************");
        log.info("AccountDebitedEvent occured");

        Account account = accountRepository.findById(event.getId()).get();

        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setOperationDate(new Date());
        accountOperation.setAmount(event.getAmount());
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAccount(account);
        accountOperationRepository.save(accountOperation);

        account.setBalance(account.getBalance().subtract(event.getAmount()));
        accountRepository.save(account);
    }
}
