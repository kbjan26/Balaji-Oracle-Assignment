package com.oracle.assignment.operators;

import com.oracle.assignment.domain.Rule;
import com.oracle.assignment.ruleoutput.DistinctCountOutput;
import com.oracle.assignment.ruleoutput.RuleOperationOutput;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DistinctCountCalculationOperator implements FieldCalculationOperator {

    private RuleOperationOutput ruleOperationOutput;

    public DistinctCountCalculationOperator(RuleOperationOutput ruleOperationOutput) {
        this.ruleOperationOutput = ruleOperationOutput;
    }

    @Override
    public void collectData(String[] input, Rule rule) {
        Set<String> distinctEntries;
        DistinctCountOutput output = ruleOperationOutput.getOutput(rule.getRuleName(), DistinctCountOutput.class);
        if (output.getDistinctEntriesCountMap().containsKey(input[rule.getSourceStatHeader().ordinal()])) {
            distinctEntries = output.getDistinctEntriesCountMap()
                    .get(input[rule.getSourceStatHeader().ordinal()]);
        } else {
            distinctEntries = new HashSet<>();
        }
        distinctEntries.add(input[rule.getDestinationStatHeader().ordinal()]);
        output.getDistinctEntriesCountMap().put(input[rule.getSourceStatHeader().ordinal()], distinctEntries);
    }

    @Override
    public void processOutput(Rule rule) {
        DistinctCountOutput distinctListOutput =
                ruleOperationOutput.getOutput(rule.getRuleName(), DistinctCountOutput.class);
        if (distinctListOutput != null) {
            Map<String, Set<String>> distinctListAndCountMap = distinctListOutput.getDistinctEntriesCountMap();
            for (String key : distinctListAndCountMap.keySet()) {
                System.out.println("The number of unique " + rule.getDestinationStatHeader()
                        + " for " + rule.getSourceStatHeader() + " " + key +
                        " is " +
                        distinctListAndCountMap.get(key).size());
            }
        }
    }
}
