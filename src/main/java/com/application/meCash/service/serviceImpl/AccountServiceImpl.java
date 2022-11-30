package com.application.meCash.service.serviceImpl;


import com.application.meCash.dto.request.FundAccountRequest;
import com.application.meCash.dto.response.AccountDetailsResponse;
import com.application.meCash.dto.response.FundAccountResponse;
import com.application.meCash.enums.Currency;
import com.application.meCash.exception.InvalidAmountException;
import com.application.meCash.model.Account;
import com.application.meCash.model.User;
import com.application.meCash.repository.AccountRepository;
import com.application.meCash.repository.UserRepository;
import com.application.meCash.service.AccountService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    private final HttpSession httpSession;



    int num = 0;

    @Override
    public Account createAccount(Currency accountCurrency) {

        String account = generateTenDigitAccountNumber();

        while(accountRepository.existsByAccountNumber(account)){
            account = generateTenDigitAccountNumber();
        }
        Account userAccount = Account.builder()
                .accountBalance(BigDecimal.valueOf(0.0))
                .accountNumber(account)
                .currency(accountCurrency)
                .build();

        accountRepository.save(userAccount);
        return userAccount;
    }

    @Override
    public AccountDetailsResponse getAccountBalance() {
        Long userId = (Long) httpSession.getAttribute("userId");
        User user = userRepository.findById(userId).get();

        AccountDetailsResponse response = AccountDetailsResponse.builder()
                .currency(user.getAccount().getCurrency())
                .accountBalance(user.getAccount().getAccountBalance())
                .accountName(user.getFirstName() + " " + user.getLastName())
                .accountNumber(user.getAccount().getAccountNumber())
                .build();
        return response;
    }



    @Override
    public FundAccountResponse fundAccount(FundAccountRequest request) {
        Long userId = (Long) httpSession.getAttribute("userId");
        User user = userRepository.findById(userId).get();

        if (request.getAmount().compareTo(BigDecimal.ONE) < 0){
            throw new InvalidAmountException("Invalid amount");
        }

        Account account = accountRepository.findAccountByUser(user);


        BigDecimal amount = account.getAccountBalance().add(request.getAmount());
        account.setAccountBalance(amount);

       account = accountRepository.save(account);

        FundAccountResponse response = FundAccountResponse.builder()
                .result("Account Credited Successfully")
                .accountBalance(account.getAccountBalance())
                .accountNumber(account.getAccountNumber())
                .build();

        return response;
    }


    private String generateTenDigitAccountNumber(){

        Random random = new Random();
        StringBuilder builder = new StringBuilder();

        for (int i=0; i<10; i++){
            int rand = random.nextInt(0, 10);

            builder.append(rand);
        }

        return builder.toString();
    }

}
