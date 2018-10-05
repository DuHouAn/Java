package com.southeast.throwException;

/**
 * Created by 18351 on 2018/8/29.
 */
public class MyException extends Exception{
    public String message;

    public MyException(String message) {
        this.message=message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
