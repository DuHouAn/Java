package code_01_activity.code_05_memento;

/**
 * Memento Object，备忘录对象
 *
 * Note that this object implements both interfaces to Originator and CareTaker
 *
 * create a memento object used for restoring two numbers
 */
public class PreviousCalculationImp implements PreviousCalculationToCareTaker,
        PreviousCalculationToOriginator {
    private int firstNumber;
    private int secondNumber;

    public  PreviousCalculationImp(int firstNumber,int secondNumber){
            this.firstNumber=firstNumber;
            this.secondNumber=secondNumber;
    }

    @Override
    public int getFirstNumber() {
        return firstNumber;
    }

    @Override
    public int getSecondNumber() {
        return secondNumber;
    }
}
