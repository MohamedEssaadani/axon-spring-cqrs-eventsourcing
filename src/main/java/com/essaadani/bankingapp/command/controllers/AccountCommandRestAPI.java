package com.essaadani.bankingapp.command.controllers;

import com.essaadani.bankingapp.command.dto.CreateAccountRequestDTO;
import com.essaadani.bankingapp.coreapi.commands.CreateAccountCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
@RequestMapping("/commands/accounts")
@RequiredArgsConstructor
public class AccountCommandRestAPI {
    private final CommandGateway commandGateway;

    @PostMapping("/create")
    public CompletableFuture<String> newAccount(CreateAccountRequestDTO request){
        log.info("request: {}", request.getInitialBalance().toString());

        CompletableFuture<String> response = commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID().toString(),
                request.getInitialBalance(),
                request.getCurrency()
        ));

        return response;
    }
}
