package com.essaadani.bankingapp.command.aggregates;

import com.essaadani.bankingapp.command.exceptions.BalanceNotSufficientException;
import com.essaadani.bankingapp.coreapi.commands.CreateAccountCommand;
import com.essaadani.bankingapp.coreapi.commands.CreditAccountCommand;
import com.essaadani.bankingapp.coreapi.commands.DebitAccountCommand;
import com.essaadani.bankingapp.coreapi.events.AccountActivatedEvent;
import com.essaadani.bankingapp.coreapi.events.AccountCreatedEvent;
import com.essaadani.bankingapp.coreapi.events.AccountCreditedEvent;
import com.essaadani.bankingapp.coreapi.events.AccountDebitedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import com.essaadani.bankingapp.coreapi.enums.AccountStatus;

import java.math.BigDecimal;

@Aggregate
@Slf4j
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private BigDecimal balance;
    private String currency;
    private AccountStatus status;


    // Default constructor required by AXON
    public AccountAggregate() {
    }

    @CommandHandler
    public AccountAggregate(CreateAccountCommand command) {
        log.info("CreateAccountCommand received!");
        // Business Logic

        //Dispatch an event
        AggregateLifecycle.apply(new AccountCreatedEvent(
                command.getId(),
                command.getInitialBalance(),
                command.getCurrency()
        ));
    }


    //Account created event handler
    @EventSourcingHandler
    public void on(AccountCreatedEvent event){
        log.info("AccountCreatedEvent occured!");
        this.accountId = event.getId();
        this.balance = event.getInitialBalance();
        this.currency = event.getCurrency();
        this.status = AccountStatus.CREATED;

        AggregateLifecycle.apply(
                new AccountActivatedEvent(
                        event.getId(),
                        AccountStatus.ACTIVATED
                )
        );
    }

    //Account activated event handler
    @EventSourcingHandler
    public void on(AccountActivatedEvent event){
        log.info("AccountActivatedEvent occured!");
        this.status = event.getStatus();
    }

    @CommandHandler
    public void handle(CreditAccountCommand command) {
        log.info("CreditAccountCommand received!");
        // Business Logic

        //Dispatch an event
        AggregateLifecycle.apply(new AccountCreditedEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency()
        ));
    }

    //Account credited event handler
    @EventSourcingHandler
    public void on(AccountCreditedEvent event){
        log.info("AccountCreditedEvent occured!");
        this.balance = this.balance.add(event.getAmount());
    }

    @CommandHandler
    public void handle(DebitAccountCommand command) {
        log.info("DebitAccountCommand received!");
        // Business Logic

        if(this.balance.subtract(command.getAmount()).doubleValue()<0){
            throw new BalanceNotSufficientException("Balance Not Sufficient Exception");
        }

        //Dispatch an event
        AggregateLifecycle.apply(new AccountDebitedEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency()
        ));
    }

    //Account debited event handler
    @EventSourcingHandler
    public void on(AccountDebitedEvent event){
        log.info("AccountCreditedEvent occured!");
        this.balance = this.balance.subtract(event.getAmount());
    }
}
