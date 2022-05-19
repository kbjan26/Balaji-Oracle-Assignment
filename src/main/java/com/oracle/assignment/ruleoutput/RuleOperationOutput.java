package com.oracle.assignment.ruleoutput;

import com.oracle.assignment.configuration.RuleConfiguration;
import com.oracle.assignment.constants.DataSetOperation;
import com.oracle.assignment.domain.Rule;

import java.util.HashMap;
import java.util.Map;

public class RuleOperationOutput {

    private RuleConfiguration ruleConfiguration;
    private Map<String, Output> ruleOutput=new HashMap<>();

    public RuleOperationOutput(RuleConfiguration ruleConfiguration) {
        this.ruleConfiguration=ruleConfiguration;
        for (Rule rule : ruleConfiguration.getRulesList()) {
            String ruleName = rule.getRuleName();
            String operationName = rule.getOperation().name();
            if (DataSetOperation.DISTINCT_COUNT.name()
                    .equalsIgnoreCase(operationName)) {
                ruleOutput.put(ruleName, new DistinctCountOutput());
            } else if (DataSetOperation.DISTINCT_LIST.name()
                    .equalsIgnoreCase(operationName)) {
                ruleOutput.put(ruleName, new DistinctListOutput());
            } else if (DataSetOperation.AVERAGE.name().equalsIgnoreCase(operationName)) {
                ruleOutput.put(ruleName, new AverageOutput());
            }
        }
    }

    public <T extends Output> T getOutput(String ruleName, Class<T> type) {
        return type.cast(getRuleOutput().get(ruleName));
    }

    public Map<String, Output> getRuleOutput() {
        return ruleOutput;
    }

    public RuleConfiguration getRuleConfiguration() {
        return ruleConfiguration;
    }

}
