package configuration;

import com.oracle.assignment.configuration.RuleConfiguration;
import com.oracle.assignment.constants.ColumnHeader;
import com.oracle.assignment.constants.DataSetOperation;
import com.oracle.assignment.domain.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class RuleConfigurationTest {


    private RuleConfiguration ruleConfiguration;

    @BeforeEach
    public void setUp() {
        Rule averageBuildDurationForEachGeoZone = new Rule.RuleBuilder().ruleName("DummyRule1")
                .sourceStatHeader(ColumnHeader.geoZone)
                .operation(DataSetOperation.AVERAGE)
                .destinationStatHeader(ColumnHeader.buildDuration).build();
        ruleConfiguration = new RuleConfiguration(Arrays.asList(averageBuildDurationForEachGeoZone));
    }

    @Test
    public void checkRuleConfigurationListCount() {
        Assertions.assertTrue(ruleConfiguration.getRulesList().size() == 1);
    }
}
