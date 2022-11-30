package com.application.meCash.service.serviceImpl;

import com.application.meCash.dto.request.TransferRequest;
import com.application.meCash.dto.response.TransactionHistoryResponse;
import com.application.meCash.dto.response.TransferResponse;
import com.application.meCash.enums.Currency;
import com.application.meCash.enums.TransactionStatus;
import com.application.meCash.enums.TransactionType;
import com.application.meCash.exception.*;
import com.application.meCash.model.Account;
import com.application.meCash.model.Transaction;
import com.application.meCash.model.User;
import com.application.meCash.repository.AccountRepository;
import com.application.meCash.repository.TransactionRepository;
import com.application.meCash.repository.UserRepository;
import com.application.meCash.service.TransactionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {


    private final HttpSession httpSession;

    private final UserRepository userRepository;

    private final AccountRepository accountRepository;

    private  final TransactionRepository transactionRepository;





    @Override
    public List<TransactionHistoryResponse> getTransactionHistory() {
        Long userId = (Long) httpSession.getAttribute("userId");
        User user = userRepository.findById(userId).get();

        Account account = accountRepository.findAccountByUser(user.getAccount().getUser());
        List<Transaction> allTransactions = transactionRepository.findAllByAccount(account);



        List<TransactionHistoryResponse> response = new ArrayList<>();
        for(Transaction transaction : allTransactions){
            TransactionHistoryResponse historyResponse = TransactionHistoryResponse.builder()
                    .transactionId(transaction.getTransactionId())
                    .sourceAccountName(transaction.getSourceAccountName())
                    .sourceAccountNumber(transaction.getSourceAccountNumber())
                    .destinationAccountNumber(transaction.getDestinationAccountNumber())
                    .destinationAccountName(transaction.getDestinationAccountName())
                    .amount(transaction.getAmount())
                    .transactionStatus(transaction.getTransactionStatus())
                    .transactionTime(transaction.getTransactionDate())
                    .build();
            response.add(historyResponse);


        }

        return response;
    }


    @Override
    public TransferResponse makeTransfer(TransferRequest request) {
        log.info("makeTransfer/request={}", request);

        Long userId = (Long) httpSession.getAttribute("userId");


        //TODO
        if (userId == null)
            throw new UserNotLoggedInException("User Not Logged in");

        User user = userRepository.findById(userId).get();


        Account recipientAccount = accountRepository.findAccountByAccountNumber(request.getAccountNumber());

        User recipient = recipientAccount.getUser();

        BigDecimal amount = request.getAmount();

        Account userAccount = accountRepository.findAccountByUser(user.getAccount().getUser());

        String recipientName = recipient.getFirstName() + " " + recipient.getLastName();

        if (user == null){
            throw new UserNotFoundException("User not Found");
        }

        if (recipientAccount == null) {
            throw new AccountNotFoundException("Account Not Found");
        }

        if (recipient == null) {

            throw new UserNotFoundException("Recipient Not Found");
        } else  if (user == recipient) {
            throw new IllegalTransferException("Can't transfer to self");
        }

        if (userAccount == null) {

            throw new AccountNotFoundException("Sender Account not Found");
        }
        if (request.getAmount().doubleValue() <= 0) {

            throw new InvalidAmountException("Invalid Transfer amount");
        }
        if (userAccount.getAccountBalance().compareTo(request.getAmount()) <= 0) {

            throw new InsufficientBalanceException("Insufficient Funds");
        }
        if (!(request.getTransactionPin().equals(user.getTransactionPin()))) {

            throw new IncorrectDetailsException("Incorrect Transfer pin");
        }

            log.info("makeTransfer/ Currency conversion");
        if (userAccount.getCurrency().equals(Currency.A) && recipientAccount.getCurrency().equals(Currency.B)){
          amount =  request.getAmount().multiply(BigDecimal.valueOf(1.3455), MathContext.UNLIMITED);
        } else if (userAccount.getCurrency().equals(Currency.B) && recipientAccount.getCurrency().equals(Currency.A)) {
            amount = request.getAmount().divide(BigDecimal.valueOf(1.3455), MathContext.DECIMAL32);
        } else if (userAccount.getCurrency().equals(recipientAccount.getCurrency())) {
            amount = request.getAmount();
        }


        Transaction sender = Transaction.builder()
                .sourceAccountNumber(userAccount.getAccountNumber())
                .sourceAccountName(user.getFirstName() + " " + user.getLastName())
                .amount(amount)
                .destinationAccountName(recipientName)
                .destinationAccountNumber(recipientAccount.getAccountNumber())
                .narration(request.getNarration())
                .user(user)
                .account(userAccount)
                .transactionType(TransactionType.DEBIT)
                .build();


        Transaction receiver = Transaction.builder()
                .sourceAccountNumber(userAccount.getAccountNumber())
                .sourceAccountName(user.getFirstName() + " " + user.getLastName())
                .amount(amount)
                .destinationAccountName(recipientName)
                .destinationAccountNumber(recipientAccount.getAccountNumber())
                .narration(request.getNarration())
                .user(recipient)
                .account(recipientAccount)
                .transactionType(TransactionType.CREDIT)
                .build();



        try {
            userAccount.setAccountBalance(userAccount.getAccountBalance().subtract(request.getAmount()));
            recipientAccount.setAccountBalance(recipientAccount.getAccountBalance().add(request.getAmount()));
            accountRepository.save(userAccount);
            accountRepository.save(recipientAccount);
            sender.setTransactionStatus(TransactionStatus.SUCCESS);
            receiver.setTransactionStatus(TransactionStatus.SUCCESS);
        } catch (Exception e) {
            sender.setTransactionStatus(TransactionStatus.FAILED);
            throw new TransactionFailedException("Transaction Failed " + e);
        }
        transactionRepository.save(sender);
        transactionRepository.save(receiver);




        TransferResponse response = TransferResponse.builder()
                .accountNumber(recipientAccount.getAccountNumber())
                .accountName(recipientName)
                .amount(request.getAmount())
                .result("Success")
                .build();


        return response;
    }


}
