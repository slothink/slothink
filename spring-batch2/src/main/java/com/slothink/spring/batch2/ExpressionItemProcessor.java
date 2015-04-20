package com.slothink.spring.batch2;

import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.item.ItemProcessor;

/**
 * Created by slothink on 2014-10-16.
 */
public class ExpressionItemProcessor implements ItemProcessor<Expression, Expression> {

    @Override
    public Expression process(Expression expression) throws Exception {
        String[] array = expression.getExpression().split("\\+");
        int a = Integer.valueOf(array[0]);
        int b = Integer.valueOf(array[1]);
        expression.setResult(a+b);
        return expression;
    }
}
