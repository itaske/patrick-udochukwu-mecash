package com.application.meCash.dto.response;


import com.application.meCash.enums.Currency;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegistrationResponse {

    Long userId;
    String accountNumber;
    Currency accountCurrency;
    String result;
    String message;
    LocalDateTime timeStamp;



}
