package com.southeast.Object.test4;

/**
 *
 *	protected void finalize()：
 *  	当垃圾回收器确定不存在对该对象的更多引用时，
 *     	由对象的垃圾回收器调用此方法。用于垃圾回收，但是什么时候回收不确定。
 *	protected Object clone():创建并返回此对象的一个副本。
 *		A:重写该方法
 *
 *  Cloneable:此类实现了 Cloneable 接口，以指示 Object.clone() 方法可以合法地对该类实例进行按字段复制。
 *  	这个接口是标记接口，告诉我们实现该接口的类就可以实现对象的复制了。
 */
public class StudentDemo {
    public static void main(String[] args) throws CloneNotSupportedException {
        Student s=new Student();
        s.setName("大狗");
        s.setAge(14);

        Student s2=(Student)s.clone();
        System.out.println(s.getName()+"---"+s.getAge());
        System.out.println(s2.getName()+"---"+s2.getAge());

        //以前的做法
        Student s3=s;
        System.out.println(s3.getName()+"---"+s3.getAge());

        System.out.println("===================================");
        //s3可以改变 s1中成员变量值
        s3.setName("旺仔");
        s3.setAge(15);
        System.out.println(s.getName()+"---"+s.getAge());
        System.out.println(s3.getName()+"---"+s3.getAge());
    }
}
