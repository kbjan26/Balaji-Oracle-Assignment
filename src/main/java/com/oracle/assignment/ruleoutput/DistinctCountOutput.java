package com.oracle.assignment.ruleoutput;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DistinctCountOutput extends Output {

    Map<String, Set<String>> distinctEntriesCollectionMap = new HashMap<>();

    public Map<String, Set<String>> getDistinctEntriesCountMap() {
        return distinctEntriesCollectionMap;
    }
}
