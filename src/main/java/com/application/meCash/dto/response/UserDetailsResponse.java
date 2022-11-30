package com.application.meCash.dto.response;


import com.application.meCash.model.Account;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsResponse {

    String firstName;
    String lastName;
    String accountNumber;
    String email;

}
