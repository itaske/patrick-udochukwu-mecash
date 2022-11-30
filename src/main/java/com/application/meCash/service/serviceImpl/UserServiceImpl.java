package com.application.meCash.service.serviceImpl;

import com.application.meCash.dto.request.LoginRequest;
import com.application.meCash.dto.request.UserRegistrationRequest;
import com.application.meCash.dto.response.UserDetailsResponse;
import com.application.meCash.dto.response.UserRegistrationResponse;
import com.application.meCash.enums.Currency;
import com.application.meCash.exception.EmailAlreadyTakenException;
import com.application.meCash.exception.IncorrectLoginDetailsException;
import com.application.meCash.model.Account;
import com.application.meCash.model.User;
import com.application.meCash.repository.AccountRepository;
import com.application.meCash.repository.UserRepository;
import com.application.meCash.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final AccountServiceImpl accountService;

    private final AccountRepository accountRepository;

    private  final HttpSession httpSession;

    //private final Pass passwordEncoder;







    @Override
    public UserRegistrationResponse registerUser(UserRegistrationRequest request) {
        log.info("registerUser/request={}", request);
        User user = new User();
        boolean userExists = userRepository.findByEmail(request.getEmail()).isPresent();

        if (userExists) {
            throw new EmailAlreadyTakenException("Email Already Taken");
        }

        if(request.getAccountCurrency().equals("A")){
            request.setAccountCurrency(Currency.A);
        } else if (request.getAccountCurrency().equals("B")) {
            request.setAccountCurrency(Currency.B);
        }

        BeanUtils.copyProperties(request, user);

        //TODO HASH PASSWORD

        userRepository.save(user);

        Account account = accountService.createAccount(request.getAccountCurrency());

        account.setUser(user);

        log.info("registerUser/Saving Account Details");
        accountRepository.save(account);

        UserRegistrationResponse response = UserRegistrationResponse.builder()
                .userId(user.getUserId())
                .accountNumber(account.getAccountNumber())
                .accountCurrency(account.getCurrency())
                .result("Success")
                .message("Registration Successful")
                .timeStamp(LocalDateTime.now())
                .build();


        return response;
    }

    @Override
    public String loginUser(LoginRequest request) {

        log.info("loginUser/request={}", request);

        //TODO ADD SPRING SECURITY AND USE TO HANDLE LOGIN
        User user = userRepository.findByEmail(request.getUsername()).get();

        if (!(user.getEmail().equals(request.getUsername()) && user.getPassword().equals(request.getPassword()))){
            throw new IncorrectLoginDetailsException("Incorrect Login details provided");
        }

        httpSession.setAttribute("userId", user.getUserId());

        return "Login Successful";
    }

    @Override
    public UserDetailsResponse getUserDetails() {

        Long userId = (Long) httpSession.getAttribute("userId");
        User user = userRepository.findById(userId).get();

        log.info("getUserDetails/Getting User details");
        UserDetailsResponse response = UserDetailsResponse.builder()
                .accountNumber(user.getAccount().getAccountNumber())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
        return response;
    }
}
