package com.oracle.assignment.configuration;

import com.oracle.assignment.domain.Rule;

import java.util.List;

public class RuleConfiguration {


    private List<Rule> rulesList;

    public RuleConfiguration(List<Rule> rulesList){
        this.rulesList=rulesList;
    }

    public List<Rule> getRulesList() {
        return rulesList;
    }

    public void setRulesList(List<Rule> rulesList) {
        this.rulesList = rulesList;
    }
}
