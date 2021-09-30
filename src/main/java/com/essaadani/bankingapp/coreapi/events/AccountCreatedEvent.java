package com.essaadani.bankingapp.coreapi.events;

import lombok.Getter;

import java.math.BigDecimal;

public class AccountCreatedEvent extends BaseEvent<String>{
    @Getter private BigDecimal initialBalance;
    @Getter private String currency;

    public AccountCreatedEvent(BigDecimal initialBalance, String currency) {
        this.initialBalance = initialBalance;
        this.currency = currency;
    }
}
