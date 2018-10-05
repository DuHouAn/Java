package com.southeast.genericClass;

public class GenericBox<T> {
    private T object;

    public void set(T object){
        this.object=object;
    }

    public T get(){
        return object;
    }
}
