package code_03_object;

import java.io.*;

/**
 * 序列化就是将一个对象转换成字节序列，方便存储和传输。
 - 序列化：ObjectOutputStream.writeObject()
 - 反序列化：ObjectInputStream.readObject()
 */
public class SerializableDemo {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String objectFile="demo6.txt";

        //序列化
        serialize(objectFile);
        //反序列化
        deserialize(objectFile);
    }

    //序列化
    public static void serialize(String objectFile) throws IOException {
        A a=new A(1,"aaa");
        ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream(objectFile));
        //序列化
        objectOutputStream.writeObject(a);
        objectOutputStream.close();
    }

    //反序列化
    public static void deserialize(String objectFile) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream(objectFile));
        A a2=(A)objectInputStream.readObject();
        System.out.println(a2);
        objectInputStream.close();
    }

    private static class A implements Serializable {

        private int x;
        private transient String y;

        A(int x, String y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "x = " + x + "  " + "y = " + y;
        }
    }
}