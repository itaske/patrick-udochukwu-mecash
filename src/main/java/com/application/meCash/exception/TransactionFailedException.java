package com.application.meCash.exception;

public class TransactionFailedException extends RuntimeException {
    public TransactionFailedException(String s) {
        super(s);
    }
}
