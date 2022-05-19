package com.oracle.assignment.domain;

import com.oracle.assignment.constants.DataSetOperation;

public class Rule {

    private String ruleName;
    private Enum sourceStatHeader;
    private DataSetOperation operation;
    private Enum destinationStatHeader;

    private Rule(RuleBuilder ruleBuilder){
        this.setRuleName(ruleBuilder.ruleName);
        this.sourceStatHeader=ruleBuilder.sourceStatHeader;
        this.destinationStatHeader=ruleBuilder.destinationStatHeader;
        this.operation=ruleBuilder.operation;
    }

    public Enum getSourceStatHeader() {
        return sourceStatHeader;
    }

    public DataSetOperation getOperation() {
        return operation;
    }

    public Enum getDestinationStatHeader() {
        return destinationStatHeader;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public static class RuleBuilder {
        private String ruleName;
        private static Enum sourceStatHeader;
        private static DataSetOperation operation;
        private static Enum destinationStatHeader;

        public RuleBuilder ruleName(String ruleName){
            this.ruleName=ruleName;
            return this;
        }
        public RuleBuilder sourceStatHeader(Enum sourceStatHeader) {
            this.sourceStatHeader = sourceStatHeader;
            return this;
        }

        public RuleBuilder destinationStatHeader(Enum destinationStatHeader) {
            this.destinationStatHeader = destinationStatHeader;
            return this;
        }

        public RuleBuilder operation(DataSetOperation operation) {
            this.operation = operation;
            return this;
        }

        public Rule build(){
            Rule rule=new Rule(this);
            return rule;
        }
    }
}
