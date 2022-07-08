package com.example.android_start.calculator;

import java.io.Serializable;

public class CalculatorImpl implements Calculator, Serializable {
    @Override
    public double operation(double firstArg, double secondArg, Operation operation) {
        switch (operation){
            case MULTIPLICATION:
                return firstArg*secondArg;
            case DIVIDE:
                return firstArg/secondArg;
            case ADDITION:
                return firstArg+secondArg;
            case SUBTRACT:
                return firstArg-secondArg;
        }
    return 0;
    }
}
