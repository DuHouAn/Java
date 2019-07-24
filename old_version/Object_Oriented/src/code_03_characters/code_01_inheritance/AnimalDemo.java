package code_03_characters.code_01_inheritance;

/**
 * 继承:一个标准的动物类、猫类、狗类的代码及测试
 */
class Animal{
    private String name;
    private int age;
    private String color;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void eat(){
        System.out.print("吃什么东西？");
    }
}

class Dog extends Animal{
    @Override
    public void eat() {
        super.eat();
        System.out.println("狗粮");
    }

    public void bark(){
        System.out.println("小狗汪汪叫");
    }
}

class Cat extends Animal{
    @Override
    public void eat() {
        super.eat();
        System.out.println("猫粮");
    }

    public void play(){
        System.out.println("小猫在玩耍");
    }
}

public class AnimalDemo {
    public static void main(String[] args) {
        Dog dog=new Dog();
        dog.setName("旺财");
        dog.setAge(12);
        dog.setColor("黄色");
        System.out.println(dog.getName()+"\t"+dog.getAge()+"\t"+dog.getColor());
        dog.eat();
        dog.bark();

        Cat cat=new Cat();
        cat.setName("汤姆");
        cat.setAge(12);
        cat.setColor("蓝色");
        System.out.println(cat.getName()+"\t"+cat.getAge()+"\t"+cat.getColor());
        cat.eat();
        cat.play();
    }
}
