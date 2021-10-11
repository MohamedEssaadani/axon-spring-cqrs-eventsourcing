package com.essaadani.bankingapp.query.controllers;

import com.essaadani.bankingapp.query.dto.AccountDTO;
import com.essaadani.bankingapp.query.queries.GetAccountByIdQuery;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
