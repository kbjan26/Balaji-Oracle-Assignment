package com.oracle.assignment.validator;

public interface InputValidator {

    boolean validate(String line);
    boolean validateFields(String[] fields);
}
