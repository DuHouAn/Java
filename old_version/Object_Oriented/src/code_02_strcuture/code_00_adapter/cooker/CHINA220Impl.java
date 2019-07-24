package code_02_strcuture.code_00_adapter.cooker;

/**
 * Adaptee的具体实现类
 */
public class CHINA220Impl implements CHINA220{
    @Override
    public void connect() {
        System.out.println("220V 接通电源，开始工作...");
    }
}
