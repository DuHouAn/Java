package code_09_usefulObjects.object.test1;

/**
 *  Object:类 Object 是类层次结构的根类。每个类都使用 Object 作为超类。
 * 每个类都直接或者间接的继承自Object类。
 *
 * Object类的方法：
 * 		public int hashCode():返回该对象的哈希码值。
 * 			注意：哈希值是根据哈希算法计算出来的一个值，这个值和地址值有关，但是不是实际地址值。
 * 			           你可以理解为地址值。
 *
 *		public final Class getClass():返回此 Object 的运行时类
 *			Class类的方法：
 */
public class StudentDemo {
    public static void main(String[] args) {
        Student s1=new Student();
        Student s2=new Student();

        System.out.println(s1.hashCode()); //21685669
        //哈希值是根据哈希算法计算出来的一个值，这个值和地址值有关，但不是实际地址值。
        System.out.println(s2.hashCode());//

        Student s = new Student();

        Class c = s.getClass();
        String str = c.getName();
        System.out.println(str); //code_09_usefulObjects.object.test1.Student

        //链式编程
        String str2  = s.getClass().getName();
        System.out.println(str2); //code_09_usefulObjects.object.test1.Student
    }
}
