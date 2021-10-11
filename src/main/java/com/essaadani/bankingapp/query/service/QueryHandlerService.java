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
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class QueryHandlerService {
    private final AccountRepository accountRepository;
    private final AccountOperationRepository accountOperationRepository;
    private final AccountMapper mapper;

    @QueryHandler
    public AccountDTO handle(GetAccountByIdQuery query){
        Account account = accountRepository.findById(query.getAccountId()).get();

        return mapper.fromAccount(account);
    }
}
