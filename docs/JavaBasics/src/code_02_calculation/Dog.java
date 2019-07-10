package code_02_calculation;

/**
 * Created by 18351 on 2018/12/20.
 */
public class Dog {
    String name;

    Dog(String name) {
        this.name = name;
    }

    String getName() {
        return this.name;
    }

    void setName(String name) {
        this.name = name;
    }

    String getObjectAddress() {
        return super.toString();
    }
}
