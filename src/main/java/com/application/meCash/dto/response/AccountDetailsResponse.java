package com.application.meCash.dto.response;

import com.application.meCash.enums.Currency;
import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDetailsResponse {


    String accountNumber;

    BigDecimal accountBalance;

    String accountName;

    Currency currency;
}
