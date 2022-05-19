package com.oracle.assignment.validator;

import com.oracle.assignment.constants.GeoZone;

import static com.oracle.assignment.constants.ReportGeneratorConstants.COMMA;

public class ReportInputValidator implements InputValidator {

    public boolean validate(String line) {
        String[] splitString = line.split(COMMA);
        if (splitString.length != 6) {
            return false;
        }
        return true;
    }

    public boolean validateFields(String[] splitData) {
        if (!validateCustomerContractId(splitData)) {
            return false;
        }
        if (!validateGeoZone(splitData[2])) {
            return false;
        }
        return true;
    }

    public boolean validateGeoZone(String geoZone) {
        boolean isValid = true;
        try {
            GeoZone.valueOf(geoZone);
        } catch (IllegalArgumentException iae) {
            isValid = false;
        }
        return isValid;
    }

    public boolean validateCustomerContractId(String[] splitData) {
        String digitsRegex = "[0-9]+";
        if (!splitData[0].matches(digitsRegex) ||
                !splitData[1].matches(digitsRegex)) {
            return false;
        }
        return true;
    }
}
