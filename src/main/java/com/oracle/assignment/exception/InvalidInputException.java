package com.oracle.assignment.exception;

public class InvalidInputException extends RuntimeException{

    public InvalidInputException(String message){
        super("Invalid input values for :"+message+". COMMA delimited Input values should be of length 6");
    }
}
