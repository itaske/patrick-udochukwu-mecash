package com.application.meCash.dto.request;


import com.application.meCash.enums.Currency;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRegistrationRequest {



    @NonNull
    @NotBlank
    String firstName;

    @NonNull
    @NotBlank
    String lastName;

    @Email
    String email;

    @NonNull
    @NotBlank
    String password;

    @NonNull
    @NotBlank
    String transactionPin;


    Currency accountCurrency;
}
