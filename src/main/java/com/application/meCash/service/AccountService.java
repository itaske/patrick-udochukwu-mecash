package com.application.meCash.service;

import com.application.meCash.dto.request.FundAccountRequest;
import com.application.meCash.dto.response.AccountDetailsResponse;
import com.application.meCash.dto.response.FundAccountResponse;
import com.application.meCash.enums.Currency;
import com.application.meCash.model.Account;

public interface AccountService {



    Account createAccount(Currency currency);

    AccountDetailsResponse getAccountBalance();

    FundAccountResponse fundAccount(FundAccountRequest request);
}
