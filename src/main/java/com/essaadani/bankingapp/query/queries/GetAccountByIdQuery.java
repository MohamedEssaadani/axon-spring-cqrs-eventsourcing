package com.essaadani.bankingapp.query.queries;

import lombok.Data;

@Data
public class GetAccountByIdQuery {
    private String accountId;

    public GetAccountByIdQuery(String accountId) {
        this.accountId = accountId;
    }
}
