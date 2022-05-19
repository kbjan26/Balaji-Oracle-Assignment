package com.oracle.assignment.util;

import com.oracle.assignment.constants.DataSetOperation;
import com.oracle.assignment.domain.Rule;
import com.oracle.assignment.exception.InvalidInputException;
import com.oracle.assignment.operators.FieldCalculationOperator;
import com.oracle.assignment.operators.FieldCalculationOperatorFactory;
import com.oracle.assignment.ruleoutput.RuleOperationOutput;
import com.oracle.assignment.validator.InputValidator;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.oracle.assignment.constants.ReportGeneratorConstants.COMMA;

public class OperatorUtil {

    private FieldCalculationOperatorFactory fieldOperatorFactory;
    private InputValidator reportInputValidator;
    private DataSetOperation[] dataSetOperations;
    private RuleOperationOutput ruleOperationOutput;
    Map<DataSetOperation, FieldCalculationOperator> operationFieldCalculationOperatorMap;

    public OperatorUtil(InputValidator reportInputValidator, FieldCalculationOperatorFactory fieldCalculationOperatorFactory,
                        DataSetOperation[] dataSetOperations, RuleOperationOutput ruleOperationOutput) {
        this.reportInputValidator = reportInputValidator;
        this.fieldOperatorFactory = fieldCalculationOperatorFactory;
        this.dataSetOperations = dataSetOperations;
        this.ruleOperationOutput = ruleOperationOutput;
        operationFieldCalculationOperatorMap = new HashMap<>();
        for (DataSetOperation operation : dataSetOperations) {
            operationFieldCalculationOperatorMap.put(operation,
                    fieldOperatorFactory.getOperator(operation.name()));
        }
    }


    public void applyRulesAndCollectData(String input) {
        if(!reportInputValidator.validate(input)){
            throw new InvalidInputException(input);
        }
        if(!reportInputValidator.validateFields(input.split(COMMA))){
            throw new InvalidInputException(input);
        }
        String[] splitLine = input.split(COMMA);
        for (Rule rule : ruleOperationOutput.getRuleConfiguration().getRulesList()) {
            FieldCalculationOperator fieldOperator = getFieldOperator(rule.getOperation().name());
            if (fieldOperator != null) {
                fieldOperator.collectData(splitLine, rule);
            }
        }
    }

    public void processOutput() {
        for (String ruleName : ruleOperationOutput.getRuleOutput().keySet()) {
            Optional<Rule> rule = ruleOperationOutput.getRuleConfiguration().getRulesList().stream()
                    .filter(ruleObj -> ruleObj.getRuleName()
                            .equalsIgnoreCase(ruleName))
                    .findFirst();
            if (rule.isPresent()) {
                FieldCalculationOperator fieldOperator = getFieldOperator(rule.get().getOperation().name());
                fieldOperator.processOutput(rule.get());
            }
        }
    }

    private FieldCalculationOperator getFieldOperator(String operationName) {
        return operationFieldCalculationOperatorMap.get(DataSetOperation.valueOf(operationName));
    }
}
