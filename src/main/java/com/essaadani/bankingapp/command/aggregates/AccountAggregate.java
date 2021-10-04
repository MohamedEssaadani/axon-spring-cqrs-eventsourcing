package com.essaadani.bankingapp.command.aggregates;

import com.essaadani.bankingapp.coreapi.commands.CreateAccountCommand;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.spring.stereotype.Aggregate;
import com.essaadani.bankingapp.coreapi.enums.AccountStatus;

import java.math.BigDecimal;

@Aggregate
@Slf4j
public class AccountAggregate {
    private String accountId;
    private BigDecimal balance;
    private String currency;
    private AccountStatus status;


    public AccountAggregate() {
        // Default constructor required by AXON
    }

    @CommandHandler
    public AccountAggregate(CreateAccountCommand command) {
        log.info("CreateAccountCommand received!");
        // Business Logic
    }

}
