package com.application.meCash.dto.response;

import com.application.meCash.enums.TransactionStatus;
import com.application.meCash.model.Transaction;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;



@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class TransactionHistoryResponse {

    private Long transactionId;
    private String sourceAccountNumber;
    private String sourceAccountName;
    private String destinationAccountNumber;
    private String destinationAccountName;
    private TransactionStatus transactionStatus;
    private LocalDateTime transactionTime;
    private BigDecimal amount;

}
