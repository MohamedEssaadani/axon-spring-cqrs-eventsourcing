package com.essaadani.bankingapp.query.entities;

import com.essaadani.bankingapp.coreapi.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    private String id;
    private BigDecimal balance;
    private String currency;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;
}
