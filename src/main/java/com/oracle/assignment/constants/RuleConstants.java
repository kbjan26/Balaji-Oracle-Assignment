package com.oracle.assignment.constants;

import com.oracle.assignment.domain.Rule;

import java.util.ArrayList;
import java.util.List;

public class RuleConstants {

    private static final List<Rule> ruleList = new ArrayList<>();

    static {
        Rule uniqueCustomerIdCountForContractId = new Rule.RuleBuilder()
                .ruleName("uniqueCustomerIdCountForContractId")
                .sourceStatHeader(ColumnHeader.contractId)
                .operation(DataSetOperation.DISTINCT_COUNT)
                .destinationStatHeader(ColumnHeader.customerId).build();
        Rule uniqueCustomerIdCountForGeoZone = new Rule.RuleBuilder()
                .ruleName("uniqueCustomerIdCountForGeoZone")
                .sourceStatHeader(ColumnHeader.geoZone)
                .operation(DataSetOperation.DISTINCT_COUNT)
                .destinationStatHeader(ColumnHeader.customerId).build();
        Rule averageBuildDurationForEachGeoZone = new Rule.RuleBuilder()
                .ruleName("averageBuildDurationForEachGeoZone")
                .sourceStatHeader(ColumnHeader.geoZone)
                .operation(DataSetOperation.AVERAGE)
                .destinationStatHeader(ColumnHeader.buildDuration).build();
        getRuleList().add(uniqueCustomerIdCountForContractId);
        getRuleList().add(uniqueCustomerIdCountForGeoZone);
        getRuleList().add(averageBuildDurationForEachGeoZone);
    }

    public static List<Rule> getRuleList() {
        return ruleList;
    }
}
