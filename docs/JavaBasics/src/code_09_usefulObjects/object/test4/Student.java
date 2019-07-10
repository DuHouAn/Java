package code_09_usefulObjects.object.test4;

/**
 * Cloneable:此类实现了 Cloneable 接口，以指示 Object.clone() 方法可以合法地对该类实例进行按字段复制。
 *  	这个接口是标记接口，告诉我们实现该接口的类就可以实现对象的复制了。
 */
public class Student implements Cloneable{
    private String name;
    private int age;

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public Student() {
        super();
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (age != student.age) return false;
        return name != null ? name.equals(student.name) : student.name == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + age;
        return result;
    }

    //protected Object clone():创建并返回此对象的一个副本。可重写该方法
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
