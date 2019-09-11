package com.evol.model;

public class Node<T extends People> {
    public static int num;

    private T obj;

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public static <T> void doPrint(String msg){
        System.out.format("msg: %s, num: %d", msg, num);

    }

    public <T> T getNode(T item){
        return item;
    }
}
