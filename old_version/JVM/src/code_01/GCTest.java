package code_01;

/**
 * VM参数：
 * -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
 *
 * -Xms20M：设置JVM堆内存为20M。此值可以设置与-Xmx相同，以避免每次垃圾回收完成后JVM重新分配内存。
 * -Xmx20M：设置JVM最大可用内存为20M。
 * -Xmn10M: 设置年轻代大小为10M。
 * -XX:SurvivorRatio=8: 设置新生代(年轻代),内存分配的比例： Eden:FromSurvivor:ToSurvivor=8:1:1
 */
public class GCTest {
    private static  final int _1MB = 1024*1024;
    public static void main(String[] args) {
            byte[] allocation1=new byte[2*_1MB];
            byte[] allocation2=new byte[2*_1MB];
            byte[] allocation3=new byte[6*_1MB];
            byte[] allocation4=new byte[4*_1MB]; //出现一次 Minor GC
    }
}
