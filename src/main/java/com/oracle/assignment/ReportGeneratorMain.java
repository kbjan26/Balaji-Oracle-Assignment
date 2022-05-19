package com.oracle.assignment;

import com.oracle.assignment.configuration.RuleConfiguration;
import com.oracle.assignment.constants.DataSetOperation;
import com.oracle.assignment.constants.RuleConstants;
import com.oracle.assignment.operators.FieldCalculationOperatorFactory;
import com.oracle.assignment.ruleoutput.RuleOperationOutput;
import com.oracle.assignment.util.OperatorUtil;
import com.oracle.assignment.validator.ReportInputValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ReportGeneratorMain {

    public static final String INPUT_FILE = "source.txt";

    public static void main(String[] args) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        OperatorUtil operatorUtil = initializeOperatorUtil();
        try (InputStream is = classloader.getResourceAsStream(INPUT_FILE);
             InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {
            for (String line; (line = reader.readLine()) != null; ) {
                operatorUtil.applyRulesAndCollectData(line);
            }
            operatorUtil.processOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static OperatorUtil initializeOperatorUtil() {
        RuleConfiguration ruleConfiguration=new RuleConfiguration(RuleConstants.getRuleList());
        RuleOperationOutput ruleOperationOutput=new RuleOperationOutput(ruleConfiguration);
        OperatorUtil operatorUtil=
                new OperatorUtil(new ReportInputValidator(),new FieldCalculationOperatorFactory(), DataSetOperation.values(),
                        ruleOperationOutput);
        return operatorUtil;
    }

}
