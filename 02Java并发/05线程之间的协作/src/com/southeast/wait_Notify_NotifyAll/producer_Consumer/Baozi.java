package com.southeast.wait_Notify_NotifyAll.producer_Consumer;

public class Baozi {
    private String name;
    private String pie;

    private boolean flag;//默认是 false,表示没有数据

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }


    public String getPie() {
        return pie;
    }

    public void setPie(String pie) {
        this.pie = pie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
