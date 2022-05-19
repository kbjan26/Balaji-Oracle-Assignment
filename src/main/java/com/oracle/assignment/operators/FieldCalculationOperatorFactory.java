package com.oracle.assignment.operators;

import com.oracle.assignment.configuration.RuleConfiguration;
import com.oracle.assignment.constants.DataSetOperation;
import com.oracle.assignment.constants.RuleConstants;
import com.oracle.assignment.ruleoutput.RuleOperationOutput;

public class FieldCalculationOperatorFactory {

    public FieldCalculationOperator getOperator(String filedOperatorName) {
        if (filedOperatorName == null) {
            return null;
        }
        FieldCalculationOperator fieldOperator = null;
        RuleConfiguration ruleConfiguration = new RuleConfiguration(RuleConstants.getRuleList());
        RuleOperationOutput ruleOperationOutput = new RuleOperationOutput(ruleConfiguration);
        if (DataSetOperation.DISTINCT_COUNT.name().equalsIgnoreCase(filedOperatorName)) {
            fieldOperator = new DistinctCountCalculationOperator(ruleOperationOutput);
        } else if (DataSetOperation.DISTINCT_LIST.name().equalsIgnoreCase(filedOperatorName)) {
            fieldOperator = new DistinctListCalculationOperator(ruleOperationOutput);
        } else if (DataSetOperation.AVERAGE.name().equalsIgnoreCase(filedOperatorName)) {
            fieldOperator = new AverageCalculationOperator(ruleOperationOutput);
        }
        return fieldOperator;
    }
}
