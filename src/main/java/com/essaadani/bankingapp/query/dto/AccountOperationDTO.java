package com.essaadani.bankingapp.query.dto;

import com.essaadani.bankingapp.query.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountOperationDTO {
    private Long id;
    private Date operationDate;
    private BigDecimal amount;
    private OperationType type;
}
