package org.microservices.exceptions;

import org.springframework.stereotype.Component;

@Component
public class InvalidBalanceException extends Exception {
    public String exceptionMessage = "Invalid Balance Amount";

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public InvalidBalanceException(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public InvalidBalanceException() {

    }
}
