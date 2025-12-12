package com.appiancorp.ps.automatedtest.exception;

public class WaitForProgressBarTestException extends RuntimeException {
    public WaitForProgressBarTestException(RuntimeException e) {
        super(e);
    }

    public WaitForProgressBarTestException(String... vals) {
        super("message:<<Progress Bar remained for longer than timeout period, " +
              "investigate performance and test timeout parameter.>>");
    }
}
