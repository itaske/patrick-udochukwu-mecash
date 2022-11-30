package com.application.meCash.service;

import com.application.meCash.dto.request.FundAccountRequest;
import com.application.meCash.dto.request.TransferRequest;
import com.application.meCash.dto.response.TransactionHistoryResponse;
import com.application.meCash.dto.response.TransferResponse;
import org.springframework.data.domain.PageImpl;

import java.util.List;

public interface TransactionService {




    List<TransactionHistoryResponse> getTransactionHistory();

    TransferResponse makeTransfer(TransferRequest request);

}
