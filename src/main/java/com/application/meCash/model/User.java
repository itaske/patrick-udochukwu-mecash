package com.application.meCash.model;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long userId;

    @Column(nullable = false)
    String firstName;

    @Column(nullable = false)
    String lastName;

    @OneToOne(mappedBy = "user")
    Account account;

    @OneToMany(mappedBy = "user")
    List<Transaction> transaction;

    String email;

    String password;

    String transactionPin;
}
