package com.oracle.assignment.operators;

import com.oracle.assignment.domain.Rule;
import com.oracle.assignment.ruleoutput.AverageOutput;
import com.oracle.assignment.ruleoutput.RuleOperationOutput;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AverageCalculationOperator implements FieldCalculationOperator {

    private RuleOperationOutput ruleOperationOutput;

    public AverageCalculationOperator(RuleOperationOutput ruleOperationOutput){
        this.ruleOperationOutput=ruleOperationOutput;
    }

    @Override
    public void collectData(String[] input, Rule rule) {
        Set<String> distinctEntries;
        AverageOutput output = ruleOperationOutput.getOutput(rule.getRuleName(), AverageOutput.class);
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
        AverageOutput averageOutput = ruleOperationOutput.getOutput(rule.getRuleName(), AverageOutput.class);
        if (averageOutput != null) {
            Map<String, Set<String>> distinctEntriesCollectionMap = averageOutput.getDistinctEntriesCountMap();
            for (String key : distinctEntriesCollectionMap.keySet()) {
                int Total = distinctEntriesCollectionMap.get(key)
                        .stream()
                        .mapToInt(value -> Integer.valueOf(value.replaceAll("[^\\d.]", "")))
                        .sum();
                System.out.println("The average " + rule.getDestinationStatHeader() + " for " + rule.getSourceStatHeader() +
                        " " +
                        key + " is "
                        + Total / distinctEntriesCollectionMap.get(key).size());
            }
        }

    }
}
