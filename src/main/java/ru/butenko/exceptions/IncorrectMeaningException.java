package ru.butenko.exceptions;

public class IncorrectMeaningException extends RuntimeException {
    public IncorrectMeaningException(String message) {
        super(message);
    }
}