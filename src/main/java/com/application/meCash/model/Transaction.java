package com.application.meCash.model;

import com.application.meCash.enums.TransactionStatus;
import com.application.meCash.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "transactions")
public class Transaction {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long transactionId;


    String sourceAccountNumber;


    String sourceAccountName;

    @Column(length = 10, nullable = false)
    String destinationAccountNumber;

    String destinationAccountName;

    @Enumerated(EnumType.STRING)
    TransactionType transactionType;

    @CreationTimestamp
    LocalDateTime transactionDate;

    @Enumerated(EnumType.STRING)
    TransactionStatus transactionStatus;

    @Column(nullable = false)
    BigDecimal amount;

    String narration;

    @ManyToOne
    @JoinColumn(name = "account_Id", referencedColumnName = "accountId")
    Account account;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    User user;


}
