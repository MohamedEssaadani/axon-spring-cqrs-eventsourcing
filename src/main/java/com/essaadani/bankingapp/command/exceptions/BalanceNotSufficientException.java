package com.essaadani.bankingapp.command.exceptions;

public class BalanceNotSufficientException extends RuntimeException {
    public BalanceNotSufficientException(String s) {
        super(s);
    }
}
