package com.southeast.hadooprpc.protocol;

/**
 * 自定义一个传输协议
 */
public interface MyProtocol {
    public long versionID = Long.MAX_VALUE;

    public String heartBeat(String name);//用来发送心跳
}
