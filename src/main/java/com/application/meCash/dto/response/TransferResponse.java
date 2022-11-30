package com.application.meCash.dto.response;


import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferResponse {


    String result;
    BigDecimal amount;
    String accountNumber;
    String accountName;
}
