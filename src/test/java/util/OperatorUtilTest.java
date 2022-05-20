package util;

import com.oracle.assignment.configuration.RuleConfiguration;
import com.oracle.assignment.constants.ColumnHeader;
import com.oracle.assignment.constants.DataSetOperation;
import com.oracle.assignment.domain.Rule;
import com.oracle.assignment.operators.AverageCalculationOperator;
import com.oracle.assignment.operators.FieldCalculationOperatorFactory;
import com.oracle.assignment.ruleoutput.AverageOutput;
import com.oracle.assignment.ruleoutput.RuleOperationOutput;
import com.oracle.assignment.util.OperatorUtil;
import com.oracle.assignment.validator.InputValidator;
import com.oracle.assignment.validator.ReportInputValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

public class OperatorUtilTest {

    private FieldCalculationOperatorFactory fieldOperatorFactoryMock;
    private InputValidator reportInputValidatorMock;
    private DataSetOperation[] dataSetOperationsMock;
    private RuleOperationOutput ruleOperationOutputMock;
    private RuleConfiguration ruleConfigurationMock;
    private OperatorUtil operatorUtil;
    private AverageCalculationOperator averageCalculationOperator;

    @BeforeEach
    public void setUp() {
        fieldOperatorFactoryMock = Mockito.mock(FieldCalculationOperatorFactory.class);
        reportInputValidatorMock = Mockito.mock(ReportInputValidator.class);
        dataSetOperationsMock = DataSetOperation.values();
        ruleConfigurationMock = Mockito.mock(RuleConfiguration.class);
        ruleOperationOutputMock = Mockito.mock(RuleOperationOutput.class);
        averageCalculationOperator = new AverageCalculationOperator(ruleOperationOutputMock);
        operatorUtil = new OperatorUtil(reportInputValidatorMock, fieldOperatorFactoryMock, dataSetOperationsMock,
                ruleOperationOutputMock);
    }

    @Test
    public void applyRulesAndCollectDataTest() {
        String input = "2343225,2345,us_east,RedTeam,ProjectApple,3445s";
        String AverageCalculationRuleName = "AverageRule1";
        Rule averageBuildDurationForEachGeoZone = new Rule.RuleBuilder().ruleName(AverageCalculationRuleName)
                .sourceStatHeader(ColumnHeader.geoZone)
                .operation(DataSetOperation.AVERAGE)
                .destinationStatHeader(ColumnHeader.buildDuration).build();
        AverageOutput averageOutput = new AverageOutput();
        Mockito.when(reportInputValidatorMock.validate(input))
                .thenReturn(true);
        Mockito.when(reportInputValidatorMock.validateFields(input.split(",")))
                .thenReturn(true);
        Mockito.when(ruleOperationOutputMock.getRuleConfiguration())
                .thenReturn(ruleConfigurationMock);
        Mockito.when(ruleConfigurationMock.getRulesList())
                .thenReturn(Arrays.asList(averageBuildDurationForEachGeoZone));
        Mockito.when(fieldOperatorFactoryMock.getOperator(averageBuildDurationForEachGeoZone.getOperation().name()))
                .thenReturn(averageCalculationOperator);
        Mockito.when(ruleOperationOutputMock.getOutput(averageBuildDurationForEachGeoZone.getRuleName(), AverageOutput.class))
                .thenReturn(averageOutput);
        operatorUtil.applyRulesAndCollectData(input);
        Assertions.assertTrue(averageOutput.getDistinctEntriesCountMap().size() == 1);

    }
}
