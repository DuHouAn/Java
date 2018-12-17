/**
 * Created by 18351 on 2018/12/14.
 */
public class FinalExample2 {
    private static class Person {
        private String name;
        private int age;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getAge() {
            return age;
        }

        public Person(String name, int age) {
            this.name=name;
            this.age = age;
        }

        @Override
        public String toString() {
            StringBuilder res=new StringBuilder();
            res.append("[").append("name="+name+",age="+age).append("]");
            return res.toString();
        }
    }

    private static final Person person=new Person("小李子",23);

    public static void main(String[] args) {
        System.out.println(person);
        person.setAge(24);
        System.out.println(person);
    }
}
