package code_01_activity.code_05_memento;

/**
 * Originator 原始对象
 */
public class CalculatorImp implements Calculator{
    private int firstNumber;
    private int secondNumber;

    @Override
    public PreviousCalculationToCareTaker backupLastCalculation() {
        //create a memento object used for restoring two numbers
        return new PreviousCalculationImp(firstNumber,secondNumber);
    }

    //存储之前的数字
    @Override
    public void restorePreviousCalculation(PreviousCalculationToCareTaker memento) {
        this.firstNumber=((PreviousCalculationToOriginator)memento).getFirstNumber();
        this.secondNumber=((PreviousCalculationToOriginator)memento).getSecondNumber();
    }

    @Override
    public int getCalculationResult() {
        return this.firstNumber+this.secondNumber;
    }

    @Override
    public void setFirstNumber(int firstNumber) {
        this.firstNumber=firstNumber;
    }

    @Override
    public void setSecondNumber(int secondNumber) {
        this.secondNumber=secondNumber;
    }
}
