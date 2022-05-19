package operators;

import com.oracle.assignment.constants.ColumnHeader;
import com.oracle.assignment.constants.DataSetOperation;
import com.oracle.assignment.domain.Rule;
import com.oracle.assignment.operators.DistinctCountCalculationOperator;
import com.oracle.assignment.ruleoutput.DistinctCountOutput;
import com.oracle.assignment.ruleoutput.RuleOperationOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static constant.ReportGeneratorTestConstant.COMMA;

public class DistinctCountCalculationOperatorTest {

    private DistinctCountCalculationOperator distinctCountCalculationOperator;
    private RuleOperationOutput ruleOperationOutputMock;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        ruleOperationOutputMock = Mockito.mock(RuleOperationOutput.class);
        distinctCountCalculationOperator = new DistinctCountCalculationOperator(ruleOperationOutputMock);
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void collectDataTest() {
        String DistinctCountCalculatorRuleName = "distinctCountCalculatorRule";
        String input = "2343225,2345,us_east,RedTeam,ProjectApple,3445s";
        DistinctCountOutput distinctCountOutput = new DistinctCountOutput();
        Rule uniqueCustomerIdCountForContractId = new Rule.RuleBuilder().ruleName(DistinctCountCalculatorRuleName)
                .sourceStatHeader(ColumnHeader.contractId)
                .operation(DataSetOperation.DISTINCT_COUNT)
                .destinationStatHeader(ColumnHeader.customerId).build();
        Mockito.when(ruleOperationOutputMock.getOutput(uniqueCustomerIdCountForContractId.getRuleName(),
                DistinctCountOutput.class))
                .thenReturn(distinctCountOutput);
        distinctCountCalculationOperator.collectData(input.split(COMMA), uniqueCustomerIdCountForContractId);
        Assertions.assertTrue(distinctCountOutput.getDistinctEntriesCountMap()
                .keySet().contains("2345"));
    }

    @Test
    public void collectDataNegativeTest() {
        String DistinctCountCalculatorRuleName = "distinctCountCalculatorRule";
        String input = "123";
        Assertions.assertThrows(Exception.class, () -> {
            DistinctCountOutput distinctCountOutput = new DistinctCountOutput();
            Rule uniqueCustomerIdCountForContractId = new Rule.RuleBuilder().ruleName(DistinctCountCalculatorRuleName)
                    .sourceStatHeader(ColumnHeader.contractId)
                    .operation(DataSetOperation.DISTINCT_COUNT)
                    .destinationStatHeader(ColumnHeader.customerId).build();
            Mockito.when(ruleOperationOutputMock.getOutput(uniqueCustomerIdCountForContractId.getRuleName(),
                    DistinctCountOutput.class))
                    .thenReturn(distinctCountOutput);
            distinctCountCalculationOperator.collectData(input.split(COMMA), uniqueCustomerIdCountForContractId);
        });

    }

    @Test
    public void processOutputToConsoleValidationTest() {
        String DistinctCountCalculatorRuleName = "distinctCountCalculatorRule";
        String input = "2343225,2345,us_east,RedTeam,ProjectApple,3445s";
        DistinctCountOutput distinctCountOutput = new DistinctCountOutput();
        String expected = "The number of unique customerId for contractId 2345 is 1";
        Rule uniqueCustomerIdCountForContractId = new Rule.RuleBuilder().ruleName(DistinctCountCalculatorRuleName)
                .sourceStatHeader(ColumnHeader.contractId)
                .operation(DataSetOperation.DISTINCT_COUNT)
                .destinationStatHeader(ColumnHeader.customerId).build();
        Mockito.when(ruleOperationOutputMock.getOutput(uniqueCustomerIdCountForContractId.getRuleName(),
                DistinctCountOutput.class))
                .thenReturn(distinctCountOutput);
        distinctCountCalculationOperator.collectData(input.split(COMMA), uniqueCustomerIdCountForContractId);
        distinctCountCalculationOperator.processOutput(uniqueCustomerIdCountForContractId);
        Assertions.assertEquals(expected, outputStreamCaptor.toString()
                .trim());
    }
}
