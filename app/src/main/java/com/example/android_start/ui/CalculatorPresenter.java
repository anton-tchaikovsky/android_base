package com.example.android_start.ui;

import com.example.android_start.calculator.Calculator;
import com.example.android_start.calculator.Operation;

import java.io.Serializable;
import java.text.DecimalFormat;


public class CalculatorPresenter implements Serializable {
    final private Calculator calculator; // объект калькулятора
    final private CalculatorDisplay calculatorDisplay; // объект дисплея
    private Operation operator; // объект операторов
    private double firstArg; //первый аргумент (вводится пользователем при запуске приложения или после нажатия AC)
    private double secondArg; // второй аргумент (вводится пользователем после нажатия оператора или равно)
    private boolean isSecond; // флаг, показывающий, какой аргумент вводит польователь: первый (false), второй (true)
    private boolean isEquals; // флаг, показывающий, нажата ли кнопка равно: true - нажата, false - не нажата.
    private Double numberAfterPoint; // счетчик количества десятичных разрядов дробной части аргумента (после разделителя (точки)).
    private final DecimalFormat formatter = new DecimalFormat();// объект форматера для корректного вывода информации на дисплей.

    public CalculatorPresenter(Calculator calculator, CalculatorDisplay calculatorDisplay) {
        this.calculator = calculator;
        this.calculatorDisplay = calculatorDisplay;
        firstArg = 0.0;
        secondArg = 0.0;
        isSecond = false;
        isEquals = false;
    }

    /**
     * Метод при нажатии соответствующей цифровой кнопки выполняет сохранение значения аргумента (первого или второго)
     * и выводит его актуальное значение на дисплей.
     * @param digit - значение (цифра) нажатой кнопки.
     */
    public void onDigitPressed (int digit){
        if (!isEquals){// после нажатия кнопки равно цифровые кнопки заблокированы (можно использовать только кнопки операторов или кнопку AC)
            // Определение текущей длины первого и второго аргумента для ограничения количества вводимых цифр (разделитель (точка) при этом не учитывается).
            int lengthFirstArg = numberAfterPoint==null? formatter.format(firstArg).length() : formatter.format(firstArg).length()-1;
            int lengthSecondArg = numberAfterPoint==null? formatter.format(secondArg).length() : formatter.format(secondArg).length()-1;
            // Ввод первого аргумента (не более 12 цифр).
            if (!isSecond && lengthFirstArg<12){
                // Ввод вещественной части первго аргумента.
                if (numberAfterPoint==null){
                    firstArg=firstArg*10 + digit;
                    displayFormatter(firstArg);
                }
                // Ввод дробной части первого аргумента.
                else{
                    numberAfterPoint++;
                    firstArg=firstArg+digit/Math.pow(10,numberAfterPoint);
                    displayFormatter(firstArg,numberAfterPoint);
                }
            // Ввод второго аргумента (не более 12 цифр).
            } else if (isSecond && lengthSecondArg<12) {
                // Ввод вещественной части второго аргумента.
                if (numberAfterPoint==null){
                    secondArg=secondArg*10 + digit;
                    displayFormatter(secondArg);
                }
                // Ввод дробной части второго аргумента.
                else {
                    numberAfterPoint++;
                    secondArg=secondArg+digit/Math.pow(10,numberAfterPoint);
                    displayFormatter(secondArg, numberAfterPoint);
                }
            }
        }
    }

    /**
     * Метод при нажатии кнопки оператора:
     * - производит расчет результата предыдущей математической операции, если таковая была, и вывод его на дисплей;
     * - запоминает значение (оператор) нажатой кнопки;
     * - подготавливает приложение для ввода второго аргумента.
     * @param operator - объект (enum) соответствующей математической операции.
     */
    public void onOperatorsPressed (Operation operator){
        // Если
        if (this.operator != null){
            firstArg = calculator.operation(firstArg,secondArg,this.operator);
            displayFormatter(firstArg);
        }
        isSecond = true;
        secondArg = 0.0;
        this.operator = operator;
        numberAfterPoint=null;
        isEquals = false;
    }

    /**
     * Метод при нажатии кнопки разделителя (точки) выводит разделитель на экран и
     * обеспечивает для последующих нажатий цифровых кнопок ввод дробной части первого или второго аргумента.
     */
    public void onPointPressed(){
        if (numberAfterPoint==null && !isEquals){ // Нажатие кнопки разделителя (точки) возможен,
            // если у вводимого аргумента еще нет разделителя и если не была нажата кнопка равно.
            numberAfterPoint=0.0;
            if (!isSecond)
                displayFormatter(firstArg,0.0);
            else
                displayFormatter(secondArg,0.0);
        }

    }

    /**
     * Метод сбрасывает настройки приложения к стартовым.
     */
    public void onCleanPressed(){
        firstArg = 0.0;
        secondArg = 0.0;
        isSecond=false;
        this.operator = null;
        numberAfterPoint=null;
        isEquals = false;
        displayFormatter(firstArg);
    }

    /**
     * Метод при нажатии кнопки равно:
     * - производит расчет результата предыдущей математической операции, если таковая была, и вывод его на дисплей;
     * - подготавливает приложение для ввода оператора.
     */
    public void onEqualsPressed(){ //
        if (this.operator != null){
            firstArg = calculator.operation(firstArg,secondArg,this.operator);
            displayFormatter(firstArg);
        }
        isSecond = false;
        secondArg = 0.0;
        this.operator = null;
        numberAfterPoint=null;
        isEquals = true;
    }

    /**
     * Метод при нажатии кнопки процент рассчитывает величину,
     * соответствующую процентам второго аргумента относительно первого, и выводит ее на дисплей.
     */
    public void onPercentPressed(){
        if (isSecond && secondArg!=0.0 && !isEquals){
            secondArg = (secondArg*firstArg)/100;
            numberAfterPoint=null;
            displayFormatter(secondArg);
        }
    }


    /**
     * Метод при нажатии кнопки удаление, удаляет последнюю введенную цифру или разделитель (точку) первого или второго аргумента.
     * Метод работает только в процессе ввода первого или второго аргумента.
     */
    public void onDeletePressed(){
        if (!isEquals){// после нажатия кнопки равно кнопка удаление блокируется (можно использовать только кнопки операторов или кнопку AC)
            // Удаление цифр первого аргумента.
            if (!isSecond){
                if (firstArg!=0.0){// Первый аргумент не ноль.
                    // Удаление цифр вещественной части первого аргумента.
                    if (numberAfterPoint==null){
                        firstArg = (int)(firstArg/10);
                        displayFormatter(firstArg);
                    // Удаление цифр дробной части первого аргумента.
                    } else {
                        if (numberAfterPoint>0){
                            numberAfterPoint--;
                            firstArg = ((int)(firstArg*Math.pow(10,numberAfterPoint)))/Math.pow(10,numberAfterPoint);
                            displayFormatter(firstArg, numberAfterPoint);
                        // Удаление разделителя (точки) первого аргумента.
                        } else {
                            numberAfterPoint=null;
                            displayFormatter(firstArg);
                        }
                    }
                }
            // Удаление цифр второго аргумента.
            } else{
                if (secondArg!=0.0){//Второй аргумент не ноль.
                    // Удаление цифр вещественной части второго аргумента.
                    if (numberAfterPoint==null){
                        secondArg = (int)(secondArg/10);
                        displayFormatter(secondArg);
                    // Удаление цифр дробной части второго аргумента.
                    } else {
                        if (numberAfterPoint>0){
                            numberAfterPoint--;
                            secondArg = ((int)(secondArg*Math.pow(10,numberAfterPoint)))/Math.pow(10,numberAfterPoint);
                            displayFormatter(secondArg, numberAfterPoint);
                        // Удаление разделителя (точки) второго аргумента.
                        } else{
                            numberAfterPoint=null;
                            displayFormatter(secondArg);
                        }

                    }
                }
            }
        }
    }

    /**
     * Метод выводит на дисплей аргумент в формате вещественной и дробной частей.
     * @param arg - выводимый на экран аргумент.
     */
    private void displayFormatter (double arg){
        formatter.applyPattern("#.#############");// Ограничение количества цифр после разделителя(точки).
        calculatorDisplay.display(formatter.format(arg));
    }

    /**
     * Метод выводит на экран аргумент в формате вещественной и дробной части с открытыми нулями.
     * Метод используется для возможности отображения набора пользователем нулей в дробной части аргумента.
     * @param arg - выводимый на экран аргумент.
     * @param numberAfterPoint - десятичный разряд дробной части аргумента.
     */
    private void displayFormatter (double arg, double numberAfterPoint){
        StringBuilder pattern = new StringBuilder("0.");
         for (int i = 0; i<numberAfterPoint; i++)
             pattern.append("0");

        formatter.applyPattern(pattern.toString());
        calculatorDisplay.display(formatter.format(arg));
    }

}
