package ruleoutput;

import com.oracle.assignment.configuration.RuleConfiguration;
import com.oracle.assignment.constants.ColumnHeader;
import com.oracle.assignment.constants.DataSetOperation;
import com.oracle.assignment.domain.Rule;
import com.oracle.assignment.ruleoutput.RuleOperationOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

public class RuleOperationOutputTest {

    private RuleOperationOutput ruleOperationOutput;
    private RuleConfiguration ruleConfigurationMock;

    @BeforeEach
    public void setUp() {
        ruleConfigurationMock = Mockito.mock(RuleConfiguration.class);
        Rule averageBuildDurationForEachGeoZone = new Rule.RuleBuilder().ruleName("DummyRule1")
                .sourceStatHeader(ColumnHeader.geoZone)
                .operation(DataSetOperation.AVERAGE)
                .destinationStatHeader(ColumnHeader.buildDuration).build();
        Rule uniqueCustomerIdListForGeoZone = new Rule.RuleBuilder().ruleName("DummyRule2")
                .sourceStatHeader(ColumnHeader.geoZone)
                .operation(DataSetOperation.DISTINCT_LIST)
                .destinationStatHeader(ColumnHeader.customerId).build();
        Mockito.when(ruleConfigurationMock.getRulesList())
                .thenReturn(Arrays.asList(averageBuildDurationForEachGeoZone,uniqueCustomerIdListForGeoZone));
        ruleOperationOutput = new RuleOperationOutput(ruleConfigurationMock);
    }

    @Test
    public void checkRuleObjOperationCount() {
        Assertions.assertTrue(ruleOperationOutput.getRuleOutput().size() ==2);
    }
}
