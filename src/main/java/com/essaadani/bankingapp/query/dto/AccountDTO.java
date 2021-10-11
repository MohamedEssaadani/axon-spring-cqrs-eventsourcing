package com.essaadani.bankingapp.query.dto;

import com.essaadani.bankingapp.coreapi.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private String id;
    private BigDecimal balance;
    private String currency;
    private AccountStatus status;
}
