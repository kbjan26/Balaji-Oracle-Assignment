package com.oracle.assignment.exception;

public class InputFormatException extends RuntimeException{
    public InputFormatException(String message){
        super("Invalid input values for :"+message+
                ". CustomerId,ContractId should be of digits and GeoZone should be a bounded value");
    }
}
