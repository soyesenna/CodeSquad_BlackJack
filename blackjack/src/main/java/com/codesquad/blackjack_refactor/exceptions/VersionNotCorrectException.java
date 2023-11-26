package com.codesquad.blackjack_refactor.exceptions;

public class VersionNotCorrectException extends Exception{

    public VersionNotCorrectException() {
    }

    public VersionNotCorrectException(String message) {
        super(message);
    }

    public VersionNotCorrectException(String message, Throwable cause) {
        super(message, cause);
    }
}
