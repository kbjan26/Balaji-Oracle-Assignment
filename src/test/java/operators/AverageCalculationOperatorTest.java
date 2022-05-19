package operators;

import com.oracle.assignment.constants.ColumnHeader;
import com.oracle.assignment.constants.DataSetOperation;
import com.oracle.assignment.domain.Rule;
import com.oracle.assignment.operators.AverageCalculationOperator;
import com.oracle.assignment.ruleoutput.AverageOutput;
import com.oracle.assignment.ruleoutput.RuleOperationOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static constant.ReportGeneratorTestConstant.COMMA;


public class AverageCalculationOperatorTest {

    private AverageCalculationOperator averageCalculationOperator;
    private RuleOperationOutput ruleOperationOutputMock;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        ruleOperationOutputMock = Mockito.mock(RuleOperationOutput.class);
        averageCalculationOperator = new AverageCalculationOperator(ruleOperationOutputMock);
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void collectDataTest() {
        final String EXPECTED_GEO_ZONE = "us_east";
        String AverageCalculationRuleName = "AverageRule1";
        AverageOutput averageOutput = new AverageOutput();
        String input = "2343225,2345,us_east,RedTeam,ProjectApple,3445s";
        Rule averageBuildDurationForEachGeoZone = new Rule.RuleBuilder().ruleName(AverageCalculationRuleName)
                .sourceStatHeader(ColumnHeader.geoZone)
                .operation(DataSetOperation.AVERAGE)
                .destinationStatHeader(ColumnHeader.buildDuration).build();
        Mockito.when(ruleOperationOutputMock.getOutput(averageBuildDurationForEachGeoZone.getRuleName(),
                AverageOutput.class))
                .thenReturn(averageOutput);
        averageCalculationOperator.collectData(input.split(COMMA), averageBuildDurationForEachGeoZone);
        Assertions.assertTrue(averageOutput.getDistinctEntriesCountMap()
                .keySet().contains(EXPECTED_GEO_ZONE));
    }

    @Test
    public void collectDataNegativeTest() {
        final String EXPECTED_GEO_ZONE = "us_east";
        String AverageCalculationRuleName = "AverageRule1";
        AverageOutput averageOutput = new AverageOutput();
        String input = "123";
        Assertions.assertThrows(Exception.class, () -> {
            Rule averageBuildDurationForEachGeoZone = new Rule.RuleBuilder().ruleName(AverageCalculationRuleName)
                    .sourceStatHeader(ColumnHeader.geoZone)
                    .operation(DataSetOperation.AVERAGE)
                    .destinationStatHeader(ColumnHeader.buildDuration).build();
            Mockito.when(ruleOperationOutputMock.getOutput(averageBuildDurationForEachGeoZone.getRuleName(),
                    AverageOutput.class))
                    .thenReturn(averageOutput);
            averageCalculationOperator.collectData(input.split(COMMA), averageBuildDurationForEachGeoZone);
            Assertions.assertTrue(averageOutput.getDistinctEntriesCountMap()
                    .keySet().contains(EXPECTED_GEO_ZONE));
        });
    }

    @Test
    public void processOutputToConsoleValidationTest() {
        String AverageCalculationRuleName = "AverageRule1";
        AverageOutput averageOutput = new AverageOutput();
        String input = "2343225,2345,us_east,RedTeam,ProjectApple,3445s";
        String expected = "The average buildDuration for geoZone us_east is 3445";
        Rule averageBuildDurationForEachGeoZone = new Rule.RuleBuilder().ruleName(AverageCalculationRuleName)
                .sourceStatHeader(ColumnHeader.geoZone)
                .operation(DataSetOperation.AVERAGE)
                .destinationStatHeader(ColumnHeader.buildDuration).build();
        Mockito.when(ruleOperationOutputMock.getOutput(averageBuildDurationForEachGeoZone.getRuleName(),
                AverageOutput.class))
                .thenReturn(averageOutput);
        averageCalculationOperator.collectData(input.split(COMMA), averageBuildDurationForEachGeoZone);
        averageCalculationOperator.processOutput(averageBuildDurationForEachGeoZone);
        Assertions.assertEquals(expected, outputStreamCaptor.toString()
                .trim());
    }
}
