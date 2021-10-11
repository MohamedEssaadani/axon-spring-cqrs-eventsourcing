package com.essaadani.bankingapp.query.service;

import com.essaadani.bankingapp.coreapi.enums.AccountStatus;
import com.essaadani.bankingapp.coreapi.events.AccountActivatedEvent;
import com.essaadani.bankingapp.coreapi.events.AccountCreatedEvent;
import com.essaadani.bankingapp.coreapi.events.AccountCreditedEvent;
import com.essaadani.bankingapp.coreapi.events.AccountDebitedEvent;
import com.essaadani.bankingapp.query.dto.AccountDTO;
import com.essaadani.bankingapp.query.dto.AccountHistoryDTO;
import com.essaadani.bankingapp.query.dto.AccountOperationDTO;
import com.essaadani.bankingapp.query.entities.Account;
import com.essaadani.bankingapp.query.entities.AccountOperation;
import com.essaadani.bankingapp.query.enums.OperationType;
import com.essaadani.bankingapp.query.mappers.AccountMapper;
import com.essaadani.bankingapp.query.queries.GetAccountByIdQuery;
import com.essaadani.bankingapp.query.queries.GetAccountHistory;
import com.essaadani.bankingapp.query.queries.GetAccountOperations;
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
import java.util.List;
import java.util.stream.Collectors;

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

    @QueryHandler
    public List<AccountOperationDTO> handle(GetAccountOperations query){
        List<AccountOperation> accountOperations = accountOperationRepository.findByAccountId(query.getAccountId());
        List<AccountOperationDTO> accountOperationDTOS =
                accountOperations.stream()
                .map(accountOperation -> mapper.fromAccountOperation(accountOperation))
                .collect(Collectors.toList());

        return accountOperationDTOS;
    }

    @QueryHandler
    public AccountHistoryDTO handle(GetAccountHistory query){
        // Get Account
        Account account = accountRepository.findById(query.getAccountId()).get();
        AccountDTO accountDTO = mapper.fromAccount(account);

        // Get Account Operations
        List<AccountOperation> operations = accountOperationRepository.findByAccountId(query.getAccountId());
        List<AccountOperationDTO> operationDTOS =
                operations.stream()
                        .map(accountOperation -> mapper.fromAccountOperation(accountOperation))
                        .collect(Collectors.toList());

        // Send Account History
        return new AccountHistoryDTO(accountDTO, operationDTOS);
    }
}
