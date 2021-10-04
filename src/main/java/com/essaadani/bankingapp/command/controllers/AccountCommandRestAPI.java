package com.essaadani.bankingapp.command.controllers;

import com.essaadani.bankingapp.command.dto.CreateAccountRequestDTO;
import com.essaadani.bankingapp.coreapi.commands.CreateAccountCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@Slf4j
@RequestMapping("/commands/accounts")
@RequiredArgsConstructor
public class AccountCommandRestAPI {
    private final CommandGateway commandGateway;
    private final EventStore eventStore;

    @PostMapping("/create")
    public CompletableFuture<String> newAccount(@RequestBody CreateAccountRequestDTO request){
        log.info("request: {}", request.getInitialBalance().toString());

        CompletableFuture<String> response = commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID().toString(),
                request.getInitialBalance(),
                request.getCurrency()
        ));

        return response;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/events/{accountId}")
    public Stream accountEvents(@PathVariable String accountId){
        return eventStore.readEvents(accountId).asStream();
    }
}
