package com.application.meCash.controller;

import com.application.meCash.dto.request.FundAccountRequest;
import com.application.meCash.dto.request.LoginRequest;
import com.application.meCash.dto.request.TransferRequest;
import com.application.meCash.dto.request.UserRegistrationRequest;
import com.application.meCash.dto.response.*;
import com.application.meCash.service.serviceImpl.AccountServiceImpl;
import com.application.meCash.service.serviceImpl.TransactionServiceImpl;
import com.application.meCash.service.serviceImpl.UserServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {


    private final UserServiceImpl userService;

    private final AccountServiceImpl accountService;

    private final TransactionServiceImpl transactionService;


    @PostMapping(path = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserRegistrationResponse> registerUser(@Valid @NotNull @RequestBody UserRegistrationRequest request){

        return new ResponseEntity<>(userService.registerUser(request), HttpStatus.CREATED);
    }


    @PostMapping(path = "/login", consumes = "application/json")
    public ResponseEntity<String> login(@Valid @NotNull @RequestBody LoginRequest request){
        return new ResponseEntity<>(userService.loginUser(request), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<UserDetailsResponse> getUserDetails(){
        return new ResponseEntity<>(userService.getUserDetails(), HttpStatus.OK);
    }


    @GetMapping(path = "/account-balance", produces = "application/json")
    public ResponseEntity<AccountDetailsResponse> getAccountBalance(){
        return new ResponseEntity<>(accountService.getAccountBalance(), HttpStatus.OK);
    }

    @GetMapping(path = "/transaction-history", produces = "application/json")
    public ResponseEntity<List<TransactionHistoryResponse>> getAllTransactionHistory(){
        return new ResponseEntity<>(transactionService.getTransactionHistory(), HttpStatus.OK);
    }

    @PostMapping(path = "/transfer", produces = "application/json", consumes = "application/json")
    public ResponseEntity<TransferResponse> makeTransfer (@Valid @RequestBody @NotNull TransferRequest request){
        return  new ResponseEntity<>(transactionService.makeTransfer(request), HttpStatus.OK);
    }


    @PostMapping(path = "/fund-account")
    public ResponseEntity<FundAccountResponse> fundAccount (@Valid @RequestBody @NotNull FundAccountRequest request){
        return new ResponseEntity<>(accountService.fundAccount(request), HttpStatus.OK);
    }



}
