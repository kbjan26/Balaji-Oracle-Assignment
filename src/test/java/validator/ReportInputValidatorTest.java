package validator;

import com.oracle.assignment.validator.ReportInputValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static constant.ReportGeneratorTestConstant.COMMA;

public class ReportInputValidatorTest {

    private ReportInputValidator reportInputValidator;

    @BeforeEach
    public void setUp() {
        reportInputValidator = new ReportInputValidator();
    }

    @Test
    public void reportInputValidatorTestValidInput() {
        String input = "2343225,2345,us_east,RedTeam,ProjectApple,3445s";
        Assertions.assertTrue(reportInputValidator.validate(input));
    }

    @Test
    public void reportInputValidatorTestInvalidInput() {
        String input = "2343222345,us_east,RedTeam,ProjectApple,3445s";
        Assertions.assertFalse(reportInputValidator.validate(input));
    }

    @Test
    public void customerIdInvalidInputTest() {
        String input = "asd,us_east,RedTeam,ProjectApple,3445s";
        Assertions.assertFalse(reportInputValidator.validateCustomerContractId(input.split(COMMA)));
    }

    @Test
    public void contractIdInvalidInputTest() {
        String input = "2343225,123az,us_east,RedTeam,ProjectApple,3445s";
        Assertions.assertFalse(reportInputValidator.validateCustomerContractId(input.split(COMMA)));
    }

    @Test void geoZoneValidInputTest() {
        String input="us_west";
        Assertions.assertTrue(reportInputValidator.validateGeoZone(input));
    }

    @Test void geoZoneInvalidInputTest() {
        String input="us_west1";
        Assertions.assertFalse(reportInputValidator.validateGeoZone(input));
    }
}
