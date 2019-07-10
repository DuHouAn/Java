package code_02_strcuture.code_00_adapter.cooker;

/**
 * Adapter：适配器，把Adaptee适配成为Client需要的Target。
 *
 * 将220V（Adapter）适配成110V(Target)
 */
public class PowerAdapter implements USA110{
    private CHINA220 china220;

    public PowerAdapter(CHINA220 china220){
        this.china220=china220;
    }

    @Override
    public void connect() {
        china220.connect();
    }
}
