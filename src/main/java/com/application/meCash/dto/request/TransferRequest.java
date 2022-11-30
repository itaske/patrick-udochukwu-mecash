package com.application.meCash.dto.request;

import com.application.meCash.enums.Currency;
import lombok.*;

import java.math.BigDecimal;


@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder

public class TransferRequest {

    String accountNumber;
    BigDecimal amount;
    String narration;
    String transactionPin;

}
