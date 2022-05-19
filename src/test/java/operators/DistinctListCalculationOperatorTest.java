package operators;

import com.oracle.assignment.constants.ColumnHeader;
import com.oracle.assignment.constants.DataSetOperation;
import com.oracle.assignment.domain.Rule;
import com.oracle.assignment.operators.DistinctListCalculationOperator;
import com.oracle.assignment.ruleoutput.DistinctListOutput;
import com.oracle.assignment.ruleoutput.RuleOperationOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static constant.ReportGeneratorTestConstant.COMMA;

public class DistinctListCalculationOperatorTest {

    private DistinctListCalculationOperator distinctListCalculationOperator;
    private RuleOperationOutput ruleOperationOutputMock;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();


    @BeforeEach
    public void setUp() {
        ruleOperationOutputMock = Mockito.mock(RuleOperationOutput.class);
        distinctListCalculationOperator = new DistinctListCalculationOperator(ruleOperationOutputMock);
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void collectDataTest() {
        String distinctListCalculatorRuleName = "distinctCalculatorRule1";
        String input = "2343225,2345,us_east,RedTeam,ProjectApple,3445s";
        DistinctListOutput distinctListOutput = new DistinctListOutput();
        Rule uniqueCustomerIdListForGeoZone = new Rule.RuleBuilder().ruleName(distinctListCalculatorRuleName)
                .sourceStatHeader(ColumnHeader.projectCode)
                .operation(DataSetOperation.DISTINCT_LIST)
                .destinationStatHeader(ColumnHeader.customerId).build();
        Mockito.when(ruleOperationOutputMock.getOutput(uniqueCustomerIdListForGeoZone.getRuleName(),
                DistinctListOutput.class))
                .thenReturn(distinctListOutput);
        distinctListCalculationOperator.collectData(input.split(COMMA), uniqueCustomerIdListForGeoZone);
        Assertions.assertTrue(distinctListOutput.getDistinctEntriesCountMap()
                .keySet().contains("ProjectApple"));
    }

    @Test
    public void collectDataNegativeTest() {
        String distinctListCalculatorRuleName = "distinctCalculatorRule1";
        String input = "123";
        Assertions.assertThrows(Exception.class, () -> {
            DistinctListOutput distinctListOutput = new DistinctListOutput();
            Rule uniqueCustomerIdListForGeoZone = new Rule.RuleBuilder().ruleName(distinctListCalculatorRuleName)
                    .sourceStatHeader(ColumnHeader.projectCode)
                    .operation(DataSetOperation.DISTINCT_LIST)
                    .destinationStatHeader(ColumnHeader.customerId).build();
            Mockito.when(ruleOperationOutputMock.getOutput(uniqueCustomerIdListForGeoZone.getRuleName(),
                    DistinctListOutput.class))
                    .thenReturn(distinctListOutput);
            distinctListCalculationOperator.collectData(input.split(COMMA), uniqueCustomerIdListForGeoZone);
            Assertions.assertTrue(distinctListOutput.getDistinctEntriesCountMap()
                    .keySet().contains("ProjectApple"));
        });
    }

    @Test
    public void processOutputToConsoleValidationTest() {
        String distinctListCalculatorRuleName = "distinctCalculatorRule1";
        String input = "2343225,2345,us_east,RedTeam,ProjectApple,3445s";
        DistinctListOutput distinctListOutput = new DistinctListOutput();
        String expected = "The list of unique customerId for projectCode ProjectApple is [2343225]";
        Rule uniqueCustomerIdListForGeoZone = new Rule.RuleBuilder().ruleName(distinctListCalculatorRuleName)
                .sourceStatHeader(ColumnHeader.projectCode)
                .operation(DataSetOperation.DISTINCT_LIST)
                .destinationStatHeader(ColumnHeader.customerId).build();

        Mockito.when(ruleOperationOutputMock.getOutput(uniqueCustomerIdListForGeoZone.getRuleName(),
                DistinctListOutput.class))
                .thenReturn(distinctListOutput);
        distinctListCalculationOperator.collectData(input.split(COMMA), uniqueCustomerIdListForGeoZone);
        distinctListCalculationOperator.processOutput(uniqueCustomerIdListForGeoZone);
        Assertions.assertEquals(expected, outputStreamCaptor.toString()
                .trim());
    }
}
