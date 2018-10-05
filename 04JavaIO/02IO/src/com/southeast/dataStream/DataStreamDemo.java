package com.southeast.dataStream;

import java.io.*;

/**
 * 可以读写基本数据类型的数据
 * 数据输入流：DataInputStream
 * 			DataInputStream(InputStream in)
 * 数据输出流：DataOutputStream
 * 			DataOutputStream(OutputStream out)
 */
public class DataStreamDemo {
    public static void main(String[] args) throws IOException {
        //先写后读
        wriite();
        //read();
    }

    private static void read() throws IOException {
        DataInputStream dis=new DataInputStream(new FileInputStream("dos.txt"));

        byte b = dis.readByte();
        short s = dis.readShort();
        int i = dis.readInt();
        long l = dis.readLong();
        float f = dis.readFloat();
        double d = dis.readDouble();
        char c = dis.readChar();
        boolean bb = dis.readBoolean();

        dis.close();

        System.out.println(b);
        System.out.println(s);
        System.out.println(i);
        System.out.println(l);
        System.out.println(f);
        System.out.println(d);
        System.out.println(c);
        System.out.println(bb);
    }

    private static void wriite() throws IOException {
        DataOutputStream dos=new DataOutputStream(new FileOutputStream("dos.txt"));
        // FileOutputStream 是字节流

        dos.writeByte(10);
        dos.writeShort(100);
        dos.writeInt(1000);
        dos.writeLong(10000);
        dos.writeFloat(12.34F);
        dos.writeDouble(12.56);
        dos.writeChar('a');
        dos.writeBoolean(true);

        dos.close();
    }
}
