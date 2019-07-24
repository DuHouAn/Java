package code_09_usefulObjects.object.test4;

public class StudentDemo {
    public static void main(String[] args) throws CloneNotSupportedException {
        Student s=new Student();
        s.setName("大狗");
        s.setAge(14);

        Student s2=(Student)s.clone();
        System.out.println(s.getName()+"---"+s.getAge()); //大狗---14
        System.out.println(s2.getName()+"---"+s2.getAge());//大狗---14

        //以前的做法
        Student s3=s;
        System.out.println(s3.getName()+"---"+s3.getAge());//大狗---14

        System.out.println("===================================");
        //s3可以改变 s1中成员变量值
        s3.setName("旺仔");
        s3.setAge(15);
        System.out.println(s.getName()+"---"+s.getAge()); //旺仔---15
        System.out.println(s3.getName()+"---"+s3.getAge()); //旺仔---15
    }
}
