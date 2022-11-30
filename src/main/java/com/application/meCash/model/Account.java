package com.application.meCash.model;


import com.application.meCash.enums.Currency;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "accounts")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long accountId;

    String accountNumber;

    @Enumerated(EnumType.STRING)
    Currency currency;

    BigDecimal accountBalance = BigDecimal.valueOf(0.0000);

    @ManyToOne
    @JoinColumn(
            name = "user_Id",
            referencedColumnName = "userId"
    )
    User user;




}
