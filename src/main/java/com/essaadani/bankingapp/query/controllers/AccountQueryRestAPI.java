package com.essaadani.bankingapp.query.controllers;

import com.essaadani.bankingapp.query.dto.AccountDTO;
import com.essaadani.bankingapp.query.dto.AccountHistoryDTO;
import com.essaadani.bankingapp.query.dto.AccountOperationDTO;
import com.essaadani.bankingapp.query.queries.GetAccountByIdQuery;
import com.essaadani.bankingapp.query.queries.GetAccountHistory;
import com.essaadani.bankingapp.query.queries.GetAccountOperations;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/query/accounts")
@RequiredArgsConstructor
public class AccountQueryRestAPI {
    private final QueryGateway queryGateway;

    @GetMapping("{accountId}")
    public CompletableFuture<AccountDTO> getAccountById(@PathVariable(name = "accountId") String accountId){
        CompletableFuture<AccountDTO> query = queryGateway.query(new GetAccountByIdQuery(accountId), AccountDTO.class);

        return query;
    }

    @GetMapping("{accountId}/operations")
    public CompletableFuture<List<AccountOperationDTO>> getAccountOperations(@PathVariable String accountId){
        CompletableFuture<List<AccountOperationDTO>> query = queryGateway.query(
                new GetAccountOperations(accountId),
                ResponseTypes.multipleInstancesOf(AccountOperationDTO.class)
        );

        return query;
    }

    @GetMapping("{accountId}/history")
    public CompletableFuture<AccountHistoryDTO> getAccountHistory(@PathVariable String accountId){
        CompletableFuture<AccountHistoryDTO> query = queryGateway.query(
                new GetAccountHistory(accountId),
                AccountHistoryDTO.class
        );

        return query;
    }
}
