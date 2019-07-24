package code_09_usefulObjects.object.test2;

/**
 * Created by 18351 on 2018/12/26.
 */
public class Student {
    private String name;
    private int age;

    public Student() {
        super();
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
