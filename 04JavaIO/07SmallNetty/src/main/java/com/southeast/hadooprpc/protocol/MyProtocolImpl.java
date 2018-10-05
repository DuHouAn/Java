package com.southeast.hadooprpc.protocol;

/**
 * Created by DHA on 2018/9/5.
 */
public class MyProtocolImpl implements MyProtocol{
    public String heartBeat(String name) {
        System.out.println("接收到客户端" + name + "的心跳，正常连接………………");
        return "心跳成功！";
    }
}
