package operators;

import com.oracle.assignment.constants.DataSetOperation;
import com.oracle.assignment.operators.FieldCalculationOperator;
import com.oracle.assignment.operators.FieldCalculationOperatorFactory;
import com.oracle.assignment.ruleoutput.RuleOperationOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FieldCalculationOperatorFactoryTest {

    private FieldCalculationOperatorFactory fieldCalculationOperatorFactory;
    private RuleOperationOutput ruleOperationOutputMock;

    @BeforeEach
    public void setUp(){
        this.fieldCalculationOperatorFactory=new FieldCalculationOperatorFactory(ruleOperationOutputMock);
    }

    @Test
    public void fieldCalculatorOperatorFactoryValidTest(){
        FieldCalculationOperator fieldCalculationOperator=fieldCalculationOperatorFactory
                .getOperator(DataSetOperation.DISTINCT_LIST.name());
        Assertions.assertNotNull(fieldCalculationOperator);
    }

    @Test
    public void fieldCalculatorOperatorFactoryNullTest(){
        FieldCalculationOperator fieldCalculationOperator=
                fieldCalculationOperatorFactory.getOperator(null);
        Assertions.assertNull(fieldCalculationOperator);
    }

    @Test
    public void fieldCalculatorOperatorFactoryInvalidInputTest(){
        String input = "ABC";
        FieldCalculationOperator fieldCalculationOperator=
                fieldCalculationOperatorFactory.getOperator(input);
        Assertions.assertNull(fieldCalculationOperator);
    }

}
