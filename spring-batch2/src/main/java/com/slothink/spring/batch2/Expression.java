package com.slothink.spring.batch2;

/**
 * Created by slothink on 2014-10-16.
 */
public class Expression {
    private String expression;
    private int result = 0;

    public Expression(String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
