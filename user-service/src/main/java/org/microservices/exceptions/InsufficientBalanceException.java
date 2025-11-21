package org.microservices.exceptions;

import org.springframework.stereotype.Component;

@Component
public class InsufficientBalanceException extends Exception {
    public String exceptionMessage = "Insufficient Balance";

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public InsufficientBalanceException(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public InsufficientBalanceException() {

    }
}
