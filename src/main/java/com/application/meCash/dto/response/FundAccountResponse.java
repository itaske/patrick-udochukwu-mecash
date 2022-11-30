package com.application.meCash.dto.response;


import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FundAccountResponse {


    String result;
    String accountNumber;
    BigDecimal accountBalance;

}
