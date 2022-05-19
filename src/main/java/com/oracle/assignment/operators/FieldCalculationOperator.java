package com.oracle.assignment.operators;

import com.oracle.assignment.domain.Rule;

public interface FieldCalculationOperator {
    void collectData(String[] input, Rule rule);
    void processOutput(Rule rule);

}
